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

import pojo.WebhookResponse;
import service.GeneralService;
import ai.api.model.AIResponse;

import com.google.gson.Gson;

@Controller
public class IndexController extends BaseController {
	
	@Autowired
	GeneralService generalService;
	
	@RequestMapping("/")
	public String index(ModelMap model, Principal principal){	   
//	    AuthUser authUser = getAuthUser();	    
		model.addAttribute("message","hello world");
		return "index";
	}
	
	
	@RequestMapping(value="/webhook", method = { RequestMethod.POST, RequestMethod.GET }, produces={"application/json"})
    public @ResponseBody WebhookResponse webhook(HttpServletRequest request, HttpServletResponse response) {
      StringBuffer jb = new StringBuffer();
      WebhookResponse wr = new WebhookResponse();
      String line = null;
      System.out.println("Hi there");
      try {
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null){
            jb.append(line);
        }
        System.out.println(jb.toString());
          Gson gson = new Gson();
//          AIResponse obj = gson.fromJson(jb.toString(), AIResponse.class);
//          String action = obj.getResult().getAction();
//          System.out.println("action - " + action );
          wr.setSpeech("speech -Response from my AI Server");
          wr.setDisplayText("DisplayText - Response from my AI server");
          Map<String, Object> data = new HashMap<String, Object>();
          data.put("facebook", "this is facebook data from my ai server");
          
          
          List<Map<String, Object>> contextList = new ArrayList<Map<String,Object>>();
          Map<String, Object> contextMap = new HashMap<String, Object>();
          Map<String, Object> contextMap1 = new HashMap<String, Object>();
          Map<String, Object> contextMap2 = new HashMap<String, Object>();
          
          contextMap.put("name","aiengine");          
          contextMap1.put("lifespan",2);     
          contextList.add(contextMap);
          contextList.add(contextMap1);
          
          data.put("facebook", "this is facebook data from my ai server");
//          wr.setContextOut(contextList);
          wr.setData(data);
          wr.setSource("aiengine");
//          Gson gson2 = new Gson();
//          gson2.toJson(wr);
//          System.out.println(gson2.toString());
      } catch (Exception e) {
    	  System.err.println(e); 
      }     
      return wr;
    }

}
