package com.telesign.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class TeleSignResponse {
	private int statusCode;
	private String StatusLine;
	private Map<String, List<String>> headers = new HashMap<String, List<String>>();
	private String body;
	private final transient Gson gson = new Gson();

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusLine() {
		return StatusLine;
	}

	public void setStatusLine(String statusLine) {
		StatusLine = statusLine;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	
	/** Returns TeleSign Response body as JsonObject */
	public JsonElement getBody() {
		JsonParser parser = new JsonParser();
		JsonElement jsonElementBody = parser.parse(body);

		return jsonElementBody;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void addHeader(String headerName, List<String> headerValue) {
		this.headers.put(headerName, headerValue);
	}

	/* TeleSignResponse in Json Format */
	public String toString() {
		return gson.toJson(this);
	}
}
