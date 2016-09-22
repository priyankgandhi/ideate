package controller;

import java.io.BufferedReader;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pojo.WebhookRequest;
import pojo.WebhookResponse;
import service.GeneralService;
import util.AIUtil;
import ai.api.model.AIResponse;

import com.google.gson.Gson;

@Controller
public class IndexController extends BaseController {

	@Autowired
	GeneralService generalService;

	@Autowired
	AIUtil aiUtil;

	@RequestMapping("/")
	public String index(ModelMap model, Principal principal) {
		// AuthUser authUser = getAuthUser();
		model.addAttribute("message", "hello world");
		return "index";
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
			if(intentName.equals("lost-baggage")){
				wr.setSpeech(getLostBaggageInfo());
			}
			else{
				wr.setSpeech("speech -Response from my AI Server");
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
			System.err.println(e);
		}
		return wr;
	}
	
	private String getLostBaggageInfo(){
		String returnMessage = "You can claim reimbursement up to $100 per day for a maximum of 5 days for the emergency purchase of essential items like toiletries and clothing, when baggage is delayed or misdirected for more than six hours by a common carrier, such as an airline, bus, cruise ship or train, while on covered trip.";
		return returnMessage;
	}

}
