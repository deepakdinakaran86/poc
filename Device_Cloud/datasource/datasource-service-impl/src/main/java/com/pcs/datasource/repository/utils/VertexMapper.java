package com.pcs.datasource.repository.utils;

import static org.apache.commons.beanutils.PropertyUtils.getPropertyType;
import static org.apache.commons.beanutils.PropertyUtils.setProperty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.tinkerpop.gremlin.driver.Result;

public class VertexMapper {

	/**
	 * Method to convert {@link Result} to Desired class
	 * 
	 * @param result
	 * @param classOfT
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromResult(Result result, Class<T> classOfT) {
		if (result == null || result.getObject() == null) {
			throw new NoResultException("Result Object is null");

		}
		Object object = result.getObject();
		if (object instanceof LinkedHashMap) {
			return setProperties((LinkedHashMap<String, Object>)object,
			        classOfT);
		} else {
			return null;
		}
	}

	/**
	 * Method to convert {@link List<Result>} to List<Desiredclass>
	 * 
	 * @param results
	 * @param classOfT
	 * @return
	 */
	@SuppressWarnings({
	        "unchecked", "rawtypes"})
	public static <T> List<T> fromResults(List<Result> results,
	        Class<T> classOfT) {

		if (results == null || CollectionUtils.isEmpty(results)) {
			throw new NoResultException("Result Object is null or empty");
		}
		List destList = new ArrayList<>();
		for (Result result : results) {
			Object object = result.getObject();
			if (object instanceof LinkedHashMap) {
				destList.add(setProperties(
				        (LinkedHashMap<String, Object>)object, classOfT));
			} else if (object instanceof String
			        && classOfT.equals(String.class)) {
				destList.add(object);
			}
		}
		return destList;
	}

	/**
	 * Private method to map the properties
	 * 
	 * @param resultObj
	 * @param classOfT
	 * @return
	 */
	@SuppressWarnings({
	        "rawtypes", "unchecked"})
	private static <T> T setProperties(LinkedHashMap<String, Object> resultObj,
	        Class<T> classOfT) {
		Set<String> keySet = resultObj.keySet();
		T newInstance = null;
		try {
			newInstance = classOfT.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		for (String property : keySet) {
			try {
				Object value = resultObj.get(property);
				Class<?> propertyType = getPropertyType(newInstance, property);

				Object toSet = null;
				if (value instanceof ArrayList) {
					List listOfValues = (List)value;
					if (isNotEmpty(listOfValues)) {
						if (propertyType.equals(List.class)) {
							Field f = classOfT.getDeclaredField(property);
							String string = f.getGenericType().toString();
							// TODO to change the approach
							Class<?> listClass = ClassUtils.getClass(string
							        .substring(string.indexOf("<") + 1,
							                string.indexOf(">")));
							toSet = getList(listOfValues, listClass);
						} else {
							Object valueFromList = listOfValues.get(0);
							if (valueFromList instanceof LinkedHashMap) {
								toSet = setProperties(
								        (LinkedHashMap)valueFromList,
								        propertyType);
							} else {
								toSet = convert(valueFromList, propertyType);
							}
						}
					}

				} else if (value instanceof LinkedHashMap) {
					toSet = setProperties((LinkedHashMap)value, propertyType);
				} else {
					toSet = value;
				}
				if (toSet != null) {
					setProperty(newInstance, property, toSet);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return newInstance;
	}

	@SuppressWarnings({
	        "rawtypes", "unchecked"})
	private static <T> List<T> getList(Object value, Class<T> classOfT)
	        throws InstantiationException, IllegalAccessException {
		List destList = new ArrayList<>();
		List valueList = (List)value;
		for (Object obj : valueList) {
			destList.add(setProperties((LinkedHashMap<String, Object>)obj,
			        classOfT));
		}
		return destList;
	}

	private static <I, O> O convert(I input, Class<O> outputClass)
	        throws Exception {
		return input == null ? null : outputClass.getConstructor(String.class)
		        .newInstance(input.toString());

	}

	public static void nullAwareBeanCopy(Object dest, Object source)
	        throws IllegalAccessException, InvocationTargetException {
		new BeanUtilsBean() {
			@Override
			public void copyProperty(Object dest, String name, Object value)
			        throws IllegalAccessException, InvocationTargetException {
				if (value != null) {
					super.copyProperty(dest, name, value);
				}
			}
		}.copyProperties(dest, source);
	}

	public static String getAddVertexQuery(String label, Object obj) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("graph.addVertex(label,").append(addQoutes(label));
		Map<String, String> fields = null;
		try {
			fields = (Map<String, String>)BeanUtils.describe(obj);
		} catch (
		        IllegalAccessException | InvocationTargetException
		        | NoSuchMethodException e1) {
			e1.printStackTrace();
		}

		for (String key : fields.keySet()) {
			Object value = null;
			Class<?> propertyType = null;
			try {
				value = fields.get(key);
				propertyType = PropertyUtils.getPropertyType(obj, key);
			} catch (
			        IllegalAccessException | InvocationTargetException
			        | NoSuchMethodException e) {
				e.printStackTrace();
			}
			if (key.equals("class") || value == null) {
				continue;
			} else {
				stringBuilder.append("," + addQoutes(key));
			}
			if (propertyType.isPrimitive()
			        || propertyType.getSuperclass().equals(Number.class)
			        || propertyType.equals(Boolean.class)) {
				stringBuilder.append("," + String.valueOf(value));
			} else {
				stringBuilder.append("," + addQoutes(value.toString()));
			}
		}
		stringBuilder.append(");");
		return stringBuilder.toString();
	}

	private static String addQoutes(String value) {
		return "'" + value + "'";
	}

	public static String getMap(String name, Object obj) {
		StringBuilder stringBuilder = new StringBuilder();
		Map<String, String> fields = null;
		try {
			fields = (Map<String, String>)BeanUtils.describe(obj);
		} catch (
		        IllegalAccessException | InvocationTargetException
		        | NoSuchMethodException e1) {
			e1.printStackTrace();
		}

		for (String key : fields.keySet()) {
			Object value = null;
			Class<?> propertyType = null;
			try {
				value = fields.get(key);
				propertyType = PropertyUtils.getPropertyType(obj, key);
			} catch (
			        IllegalAccessException | InvocationTargetException
			        | NoSuchMethodException e) {
				e.printStackTrace();
			}
			if (key.equals("class") || value == null) {
				continue;
			} else {
				if (StringUtils.isEmpty(stringBuilder.toString())) {
					if (StringUtils.isEmpty(name)) {
						stringBuilder.append("  [" + addQoutes(key));
					} else {
						stringBuilder.append(name + " = [" + addQoutes(key));
					}
				} else {
					stringBuilder.append("," + addQoutes(key));
				}
			}
			if (propertyType.isPrimitive()
			        || propertyType.getSuperclass().equals(Number.class)
			        || propertyType.equals(Boolean.class)) {
				stringBuilder.append(":" + String.valueOf(value));
			} else {
				stringBuilder.append(":" + addQoutes(value.toString()));
			}
		}
		stringBuilder.append("];");
		return stringBuilder.toString();
	}

	public static String getListOfMap(Object obj) {
		StringBuilder stringBuilder = new StringBuilder();
		Map<String, String> fields = null;
		try {
			fields = (Map<String, String>)BeanUtils.describe(obj);
		} catch (
		        IllegalAccessException | InvocationTargetException
		        | NoSuchMethodException e1) {
			e1.printStackTrace();
		}

		for (String key : fields.keySet()) {
			Object value = null;
			Class<?> propertyType = null;
			try {
				value = fields.get(key);
				propertyType = PropertyUtils.getPropertyType(obj, key);
			} catch (
			        IllegalAccessException | InvocationTargetException
			        | NoSuchMethodException e) {
				e.printStackTrace();
			}
			if (key.equals("class") || value == null) {
				continue;
			} else {
				if (StringUtils.isEmpty(stringBuilder.toString())) {

					stringBuilder.append("  [" + addQoutes(key));

				} else {
					stringBuilder.append("," + addQoutes(key));
				}
			}
			if (propertyType.isPrimitive()
			        || propertyType.getSuperclass().equals(Number.class)
			        || propertyType.equals(Boolean.class)) {
				stringBuilder.append(":" + String.valueOf(value));
			} else {
				stringBuilder.append(":" + addQoutes(value.toString()));
			}
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

}
