/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.alpine.repo.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.alpine.model.Entity;

/**
 * 
 * This class is responsible for Creating Entity Objects from Cassandra
 * ResultSet
 * 
 * @author Daniela (pcseg191)
 * @date Nov 2014
 * @since galaxy-1.0.0
 */
@Component
public class EntityConversion {
	@Autowired
	private CassandraDBUtil cassandraDBUtil;

	/**
	 * Responsible to return Entities after Mapping the resultSet
	 * 
	 * @param rs
	 * @param mapper
	 * @return
	 */
	public List<Entity> getEntities(ResultSet rs) {
		return getEntities(rs.all());
	}

	/**
	 * Responsible to return Entities after Mapping the resultSet
	 * 
	 * @param rs
	 * @param mapper
	 * @return
	 */
	public List<Entity> getEntities(List<Row> rs) {

		List<Entity> entities = new ArrayList<>();
		for (Row row : rs) {
			String string = row.getString(0);
			ObjectMapper mapper = new ObjectMapper();
			try {
				Entity entity = mapper.readValue(string, Entity.class);
				entities.add(entity);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return entities;
	}

	/**
	 * Responsible to return an Entity after Mapping the resultSet
	 * 
	 * @param rs
	 * @param mapper
	 * @return
	 */
	public Entity getEntity(ResultSet rs) {

		List<Entity> entities = getEntities(rs);
		if (CollectionUtils.isEmpty(entities)) {
			return null;
		}

		if (entities.size() > 1) {
			throw new DuplicateKeyException(
			        "More than one entity found with the key");
		}
		return entities.get(0);
	}

}
