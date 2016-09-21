package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import pojo.AuthUser;
import service.EmailService;
import service.UserService;
import exception.UserRegistrationException;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
	
	
    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;
    
    @Autowired
    @Qualifier("customUserDetailsService")
    protected UserDetailsService userDetailsService;
    
    ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils();
    
    @RequestMapping(value = "/register", method = {RequestMethod.GET })
    public String register(@ModelAttribute AuthUser user, ModelMap model) throws IOException {
        String ret = "user/register";
        return ret;
    }
    
    @RequestMapping(value = "/register", method = { RequestMethod.POST })
    public String register(@ModelAttribute AuthUser user, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        String ret = "user/register";
        if (request.getMethod().equals(RequestMethod.POST.toString())) {            
            try{
                userService.handleUserRegistration(user, request, response);
                ret = "/";
            } catch(UserRegistrationException ex){
            	if(ex.getExceptionType() == UserRegistrationException.Exceptiontype.EXISTING_USER){
            		model.addAttribute("dangerMessage", ex.getMessage());
            	}
            } catch (ServletException e) {
                response.sendRedirect("/user/accessdenied");
            }
        }
        return ret;
    }
    
    @RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
    public String login(@RequestParam(value = "error", required = false) boolean error, ModelMap model) {
        return "user/login";
    }

    @RequestMapping(value = "/choosepassword", method = { RequestMethod.POST, RequestMethod.GET })
    public String choosePassword(@RequestParam(value = "error", required = false) boolean error, HttpServletRequest request, ModelMap model) {        
        if (request.getMethod().equals(RequestMethod.POST.toString())) {
            return "forward:/user/register";
        } else {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            Connection<?> connection = providerSignInUtils.getConnectionFromSession(requestAttributes);
            UserProfile profile = connection.fetchUserProfile();
            model.addAttribute("name", profile.getName());
            model.addAttribute("image", connection.getImageUrl());
            model.addAttribute("email", profile.getEmail());
            return "user/choosepassword";    
        }
        
    }
    
    @RequestMapping(value = "/handleSocialSignup", method = { RequestMethod.POST, RequestMethod.GET })
    public String handleSocial(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException, ServletException {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            Connection<?> connection = providerSignInUtils.getConnectionFromSession(requestAttributes);
            UserProfile profile = connection.fetchUserProfile();            
            AuthUser authUser = userService.findAuthUser(profile);
            
            if(authUser == null) {
                // New user
                authUser = new AuthUser();
                userService.handleUserRegistration(authUser, request, response);
            } else {
                providerSignInUtils.doPostSignUp(authUser.getId().toString(), requestAttributes);
                userService.linkProviderToAuthUser(authUser, connection, profile);
                if(isUserLoggedIn()) {
                    // Existing user
                    // TODO: Handle existing session

                } else {
                    // Handle new session
                    userService.logInUser(authUser);
                    authenticationSuccessHandler.onAuthenticationSuccess(request, response, getAuthentication());                    
                }                
            }
            return "";            
    }
}
