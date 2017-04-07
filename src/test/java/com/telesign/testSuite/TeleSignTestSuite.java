package com.telesign.testSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.telesign.AutoVerifyClientTest;
import com.telesign.MessagingClientTest;
import com.telesign.PhoneIdClientTest;
import com.telesign.PhoneIdTest;
import com.telesign.ScoreClientTest;
import com.telesign.TeleSignRequestTest;
import com.telesign.TelebureauClientTest;
import com.telesign.VerifyTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(value = { AutoVerifyClientTest.class,
		MessagingClientTest.class, PhoneIdClientTest.class, PhoneIdTest.class,
		ScoreClientTest.class, TelebureauClientTest.class,
		TeleSignRequestTest.class, VerifyTest.class })
public class TeleSignTestSuite {

}
