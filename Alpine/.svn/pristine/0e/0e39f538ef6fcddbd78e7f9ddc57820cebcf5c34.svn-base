package com.pcs.alpine.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pcs.alpine.dto.FeatureDTO;

public class Features {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(Features.class);

	private List<FeatureDTO> featureList;

	private File featureJson;

	public Features(File featureJson) {
		this.featureJson = featureJson;
	}

	@PostConstruct
	public void intialize() {
		String featureJson = getFile();
		Gson gson = new Gson();
		List<FeatureDTO> features = gson.fromJson(featureJson,
		        new TypeToken<List<FeatureDTO>>() {
		        }.getType());
		featureList = features;
	}

	private String getFile() {
		StringBuilder result = new StringBuilder("");
		File file = featureJson;
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				// result.append(line).append("\n");
				result.append(line);
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public List<FeatureDTO> getAllFeatures() {
		return featureList;
	}
}
