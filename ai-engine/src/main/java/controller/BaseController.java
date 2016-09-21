/**
 * 
 */
package controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import pojo.AuthUser;


/**
 * @author priyank
 *
 */
@Controller
public abstract class BaseController {
    
    @ModelAttribute("user")
    protected AuthUser getAuthUser() {
        AuthUser authUser = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!auth.getName().equalsIgnoreCase("anonymousUser")) {
            authUser = (AuthUser) auth.getPrincipal();
        }
        return authUser;
    }
        
    protected boolean isUserLoggedIn() {
        boolean ret = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && !auth.getName().equalsIgnoreCase("anonymousUser")) {
            ret = true;
        }
        return ret;
    }
    
    
    protected Authentication getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth;
    }
    
}
