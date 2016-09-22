package util;

import java.util.Date;

import com.amazon.speech.speechlet.IntentRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import pojo.WebhookRequest;
import service.JsonDateDeserializer;

public class AIUtil {
	
	private String apiKey;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public void sendMessage(String message) throws AIServiceException{
		
		AIConfiguration configuration = new AIConfiguration(apiKey);		
		AIDataService dataService = new AIDataService(configuration);	
		AIRequest request = new AIRequest(message);
		AIResponse response = dataService.request(request);				
		if (response.getStatus().getCode() == 200) {
			System.out.println(response.getResult().getFulfillment().getSpeech());
		} else {
			System.err.println(response.getStatus().getErrorDetails());
		}		
		
	}
	
	public WebhookRequest parseAIRequest(String jsonRequest){	
        Gson gson=  new Gson();
        WebhookRequest obj = gson.fromJson(jsonRequest, WebhookRequest.class);
	  return obj;
	}
	
	public IntentRequest parseAlexaRequest(String jsonRequest){	
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
        IntentRequest obj = gson.fromJson(jsonRequest, IntentRequest.class);
        return obj;
	}
}
