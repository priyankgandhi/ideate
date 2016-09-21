package controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pojo.CustomData;
import pojo.Response;
import service.GeneralService;

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
    public @ResponseBody Response webhook(HttpServletRequest request, HttpServletResponse response) {
      Response res = new Response();
      String key = "test";
      CustomData data = generalService.getValueForKey(key);
      return res;
    }
	
	

}
