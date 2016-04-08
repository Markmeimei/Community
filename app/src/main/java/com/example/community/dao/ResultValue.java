package com.example.community.dao;

import org.json.JSONObject;

public class ResultValue {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ResultValue parseJson(JSONObject jsonObject) {

		try {

			setValue(jsonObject.getString("state"));

		} catch (Exception e) {

			e.printStackTrace();

		}

		return this;

	}

}
