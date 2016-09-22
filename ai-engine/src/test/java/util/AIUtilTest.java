package util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amazon.speech.speechlet.IntentRequest;

import ai.api.AIServiceException;

import pojo.AuthUser;
import pojo.WebhookRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:config/test-applicationContext.xml")
public class AIUtilTest {
	
	@Autowired
	AIUtil aiUtil;
	
	@Test
    public void testAIConnecton() {
		try {
			aiUtil.sendMessage("i want to sleep");
		} catch (AIServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@Test
    public void testParseAIRequest() {
		String json = "{  \"id\": \"8efed4e9-6d3d-4868-98f2-2cacdc211a0e\",  \"timestamp\": \"2016-09-22T00:36:24.242Z\",  \"result\": {    \"source\": \"agent\",    \"resolvedQuery\": \"lounge\",    \"speech\": \"\",    \"action\": \"loungerfinder\",    \"actionIncomplete\": false,    \"parameters\": {      \"Lounge-Access\": \"Lounge\"    },    \"contexts\": [],    \"metadata\": {      \"intentId\": \"d749bf4c-7c8f-4b4a-9017-bdb46029c2bf\",      \"webhookUsed\": \"true\",      \"intentName\": \"Give me my lounge information\"    },    \"fulfillment\": {      \"speech\": \"\"    },    \"score\": 0.86  },  \"status\": {    \"code\": 200,    \"errorType\": \"success\"  },  \"sessionId\": \"e2b74f5c-55ff-455b-a980-35f3ac6b30e2\"}";
		WebhookRequest res = aiUtil.parseAIRequest(json);
		System.out.println(res.getResult().getAction());
    }	
	
	@Test
    public void testParseAlexaRequest() {
		String json = "{  \"id\": \"e29639b2-caef-4037-8195-520cc65bd89f\",  \"timestamp\": \"2016-09-22T20:52:42.16Z\",  \"result\": {    \"source\": \"agent\",    \"resolvedQuery\": \" lounge benefits\",    \"speech\": \"\",    \"action\": \"\",    \"actionIncomplete\": false,    \"parameters\": {      \"duration\": \"\",      \"city\": \"\",      \"geo-city\": \"\",      \"airport_code\": \"\"    },    \"contexts\": [],    \"metadata\": {      \"intentId\": \"d749bf4c-7c8f-4b4a-9017-bdb46029c2bf\",      \"webhookUsed\": \"true\",      \"intentName\": \"lounge-finder\"    },    \"fulfillment\": {      \"speech\": \"\"    },    \"score\": 1.0  },  \"status\": {    \"code\": 200,    \"errorType\": \"success\"  },  \"sessionId\": \"75559a30-8106-11e6-9dd6-9f116917bf6c\"}";
		IntentRequest res = aiUtil.parseAlexaRequest(json);
		System.out.println(res.toString());
    }	

	
	
	

}
