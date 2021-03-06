package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pojo.AuthUser;
import pojo.CustomData;
import pojo.WebhookRequest;
import pojo.WebhookResponse;
import service.AlexaService;
import service.GeneralService;
import util.AIUtil;
import ai.api.model.AIResponse;

import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import exception.UserRegistrationException;

@Controller
public class IndexController extends BaseController {

	@Autowired
	GeneralService generalService;

	@Autowired
	AlexaService alexaService;
	
	@Autowired
	AIUtil aiUtil;

	@RequestMapping("/")
	public String index(ModelMap model, Principal principal) {
		// AuthUser authUser = getAuthUser();
		model.addAttribute("message", "hello world");
		return "index";
	}

	@RequestMapping(value = "/alexa", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
	"application/json" })
	public @ResponseBody Object alexa(HttpServletRequest request, HttpServletResponse response) {
		String line = null;
		StringBuffer jb = new StringBuffer();
		SpeechletResponse res = new SpeechletResponse();
		Object obj = null;

		try {
			BufferedReader reader = request.getReader();			
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			
			IntentRequest req = aiUtil.parseAlexaRequest(jb.toString());
			String intentKey = req.getIntent().getName();
			CustomData customData = generalService.getValueForKey(intentKey);
//			res = alexaService.getWelcomeResponse(req, customData);
			String resStr = "{\"version\":\"1.0\",\"response\":{\"outputSpeech\":{\"type\":\"SSML\",\"ssml\":\"<speak> Here's your fact: On Mars, the Sun appears about half the size as it does on Earth. </speak>\"},\"card\":{\"content\":\"On Mars, the Sun appears about half the size as it does on Earth.\",\"title\":\"Space Facts\",\"type\":\"Simple\"},\"shouldEndSession\":true},\"sessionAttributes\":{}}";
			if(customData!= null && customData.getValue() != null) {
				resStr = customData.getValue(); 
			}		
			Gson gson = new Gson();
			obj = gson.fromJson(resStr, Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		System.out.println(jb.toString());		
		return obj;
	}
	
	
	@RequestMapping(value = "/webhook", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/json" })
	public @ResponseBody WebhookResponse webhook(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer jb = new StringBuffer();
		WebhookResponse wr = new WebhookResponse();
		String line = null;
		System.out.println("Hi there");
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			System.out.println(jb.toString());
			WebhookRequest obj = aiUtil.parseAIRequest(jb.toString());
			String action = obj.getResult().getAction();
			
			String intentName = obj.getResult().getMetadata().getIntentName();
			System.out.println("intentName - "+ intentName);
			HashMap<String, JsonElement> params = obj.getResult().getParameters();			
			String intentKey = generalService.intentKeyBuilder(intentName, params);
			CustomData customData = generalService.getValueForKey(intentKey);
			if (customData == null) {
				customData = generalService.getValueForKey("default-message");
			}
			if (customData != null) {
				wr.setSpeech(customData.getValue());
				if(!StringUtils.isEmpty(customData.getData())) {
					String jsonDb = customData.getData();
					Gson gson = new Gson();
					Object jsonObj = gson.fromJson(jsonDb, Object.class);
					System.out.println(jsonDb);
					wr.setData(jsonObj);
				}			
			} else {
				wr.setSpeech("Sorry I am just a week old. I am still learning :)");
			}
			System.out.println("action - " + action);
			System.out.println("intent name - " + intentName);
			wr.setDisplayText("DisplayText - Response from my AI server");
			Map<String, Object> data = new HashMap<String, Object>();

			List<Map<String, Object>> contextList = new ArrayList<Map<String, Object>>();
			data.put("facebook", "this is facebook data from my ai server");
			wr.setContextOut(contextList);
			wr.setSource("facebooker");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wr;
	}
	
    @RequestMapping(value = "/intents", method = { RequestMethod.POST,RequestMethod.GET })
    public String register(@ModelAttribute CustomData customData, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        String ret = "intents";
        if (request.getMethod().equals(RequestMethod.POST.toString())) {            
        	generalService.save(customData);
        }
        model.addAttribute("intents",generalService.findAll());
        return ret;
    }
    
    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
    public String delete(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        String ret = "intents";
        if (request.getMethod().equals(RequestMethod.POST.toString())) {            
        	String intentId = request.getParameter("intentId");
        	generalService.deleteKey(Integer.parseInt(intentId));
        }
        model.addAttribute("intents",generalService.findAll());
        return ret;
    }

}
