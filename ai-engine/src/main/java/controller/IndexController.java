package controller;

import java.io.BufferedReader;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pojo.WebhookResponse;
import service.GeneralService;
import ai.api.model.AIResponse;

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
      try {
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null){
            jb.append(line);
        }
          ObjectMapper mapper = new ObjectMapper();
          AIResponse obj = mapper.readValue(jb.toString(), AIResponse.class);
          String action = obj.getResult().getAction();
          System.out.println("action - " + action );
          wr.setSpeech("speech -Response from my AI Server");
          wr.setDisplayText("DisplayText - Response from my AI server");
          Map<String, Object> data = new HashMap<String, Object>();
          data.put("facebook", "this is facebook data from my ai server");
          wr.setData(data);
      } catch (Exception e) {
    	  e.printStackTrace(); 
      }     
      return wr;
    }
	
	

}
