package com.telesign.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

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

	/*
	 * check the need and delete
	 * public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}*/

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public void addHeader(String headerName, List<String> headerValue){
		this.headers.put(headerName, headerValue);
	}

	/* TeleSignResponse in Json Format */
	public String toString() {
		return gson.toJson(this);
	}
}
