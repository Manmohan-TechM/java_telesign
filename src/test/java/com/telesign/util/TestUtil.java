package com.telesign.util;

import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class TestUtil {
	public static String CUSTOMER_ID;
	public static String SECRET_KEY;
	public static String PHONE_NUMBER;
	public static String CONNECT_TIMEOUT;
	public static String READ_TIMEOUT;
	public static int readTimeout;
	public static int connectTimeout;
	public static boolean timeouts = false;
	public static String ORIGINATING_IP;
	public static String SESSION_ID;
	public static String HTTPS_PROTOCOL;
	public static boolean isHttpsProtocolSet = false;
	public static String FRAUD_TYPE, OCCURRED_AT;
	public static String RESOURCE_DIR = "src/test/resources/";
	public static HashMap<String, String> params = new HashMap<String, String>();
	
	public static void initProperties() throws IOException {
		Properties props = new Properties();
		try {
		props.load(new FileInputStream(RESOURCE_DIR + "test.properties"));
		} catch (FileNotFoundException fne) {
			fail("Please create a \"test.properties\" file at the /src/test/resources project directory " +
					"and include your telesign customerid, secretkey and your phone number. " +
					"If you need assistance, please contact telesign support at support@telesign.com");
		}
		
		CUSTOMER_ID = props.getProperty("test.customerid");
		SECRET_KEY =  props.getProperty("test.secretkey");
		PHONE_NUMBER = props.getProperty("test.phonenumber");
		CONNECT_TIMEOUT =  props.getProperty("test.connecttimeout");
		READ_TIMEOUT =  props.getProperty("test.readtimeout");
		ORIGINATING_IP = props.getProperty("test.originating_ip");
		SESSION_ID = props.getProperty("test.session_id");
		HTTPS_PROTOCOL = props.getProperty("test.httpsprotocol");
		FRAUD_TYPE = props.getProperty("test.fraud_type");
		OCCURRED_AT = props.getProperty("test.occurred_at");
		
		boolean pass = true; 
		
		if(CUSTOMER_ID == null || CUSTOMER_ID.isEmpty()) {
			System.out.println("CUSTOMER_ID is not set. Please set the \"test.customerid\" property in the properties file");
			pass = false;
		}
		
		if(SECRET_KEY == null || SECRET_KEY.isEmpty()) {
			System.out.println("SECRET_KEY is not set. Please set the \"test.secretkey\" property in the properties file");
			pass = false;
		}
		if(PHONE_NUMBER == null || PHONE_NUMBER.isEmpty()) {
			System.out.println("PHONE_NUMBER is not set. Please set the \"test.phonenumber\" property in the properties file");
			pass = false;
		}
		
		if(CONNECT_TIMEOUT == null || CONNECT_TIMEOUT.isEmpty() || READ_TIMEOUT == null || READ_TIMEOUT.isEmpty()) {
			System.out.println("Either of CONNECT_TIMEOUT or READ_TIMEOUT is not set. Please set the \"test.connecttimeout\" & \"test.readtimeout\" property in the properties file. " +
					"Or default connect & read timeout values would be used");
			pass = true;
		} else {
			params.put("readTimeout", READ_TIMEOUT);
			params.put("connectTimeout", CONNECT_TIMEOUT);
			timeouts = true;
			pass = true;
		}		

		if(ORIGINATING_IP == null || ORIGINATING_IP.isEmpty()) {
			System.out.println("ORIGINATING_IP not set. Please set the \"test.originating_ip\" property in the properties file");
			pass = true;
		} else {
			params.put("originating_ip", ORIGINATING_IP);
		}
		
		if(SESSION_ID == null || SESSION_ID.isEmpty()) {
			System.out.println("SESSION_ID not set. Please set the \"test.session_id\" property in the properties file");
			pass = true;
		} else {
			params.put("session_id", SESSION_ID);
		}
		
		if(null == HTTPS_PROTOCOL || HTTPS_PROTOCOL.isEmpty()) {
			System.out.println("HTTPS_PROTOCOL is not set. Please set the \"test.httpsprotocol\" property in the properties file"
					+ ", or default value of TLSv1.2 would be used");
			pass = true;
		} else {
			params.put("httpsProtocol", HTTPS_PROTOCOL);
			isHttpsProtocolSet = true;
			pass = true;
		}
		
		if(null == FRAUD_TYPE || FRAUD_TYPE.isEmpty()){
			System.out.println("FRAUD_TYPE is not set. Please set the \"test.fraud_type\" property in the properties file");
			pass = false;
		}
		
		if(null == OCCURRED_AT || OCCURRED_AT.isEmpty()){
			System.out.println("OCCURRED_AT is not set. Please set the \"test.occurred_at\" property in the properties file");
			pass = false;
		}
		
		if(!pass) {
			fail("Configuration file not setup correctly!");
		}
	}
}
