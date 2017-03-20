package com.telesign;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.telesign.response.TeleSignResponse;
import com.telesign.score.ScoreClient;
import com.telesign.util.TestUtil;

public class ScoreClientTest {
	@BeforeClass
	public static void setUp() throws IOException {
		TestUtil.initProperties();
	}

	private ScoreClient getInstance() {
		if (TestUtil.CUSTOMER_ID.isEmpty() || TestUtil.SECRET_KEY.isEmpty()) {
			fail("CUSTOMER_ID, SECRET_KEY must be set to pass this test");
		}

		return new ScoreClient(TestUtil.CUSTOMER_ID, TestUtil.SECRET_KEY,
				TestUtil.params);
	}
	
	@Test
	public void scoreTest(){
		ScoreClient scoreClient = getInstance();
		String account_lifecycle_event = "create";
		HashMap<String, String> scoreParams = new HashMap<String, String>();
		//scoreParams.put("lifecycle_event", "create");
		TeleSignResponse response = scoreClient.score(TestUtil.PHONE_NUMBER, account_lifecycle_event, scoreParams);
		assertNotNull(response);
		JsonObject jsonObject = response.getBody().getAsJsonObject();
		System.out.println(response.getBody());
		assertTrue((jsonObject.getAsJsonObject("status")).get("code").getAsInt() == 300);
		
	}
}
