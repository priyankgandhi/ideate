package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import pojo.AuthUser;
import pojo.SocialProfile;
import pojo.AuthUser.SocialProfileData;
import repositories.AuthUserRepository;
import enums.UserRole;
import exception.UserRegistrationException;

@Service
public class UserService {
	
	@Autowired
	private AuthUserRepository authUserRepository;
	
	@Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    EmailService emailService;
	
    ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils();
    
	public void handleUserRegistration(AuthUser user, HttpServletRequest request, HttpServletResponse response) throws UserRegistrationException, ServletException, IOException {	    
	    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(requestAttributes);
        if (connection != null) {
            UserProfile profile = connection.fetchUserProfile();                    
            buildAuthUser(user, profile, connection);
        }
	    registerUser(user);
        logInUser(user);
        authenticationSuccessHandler.onAuthenticationSuccess(request, response, SecurityContextHolder.getContext().getAuthentication());                
        emailService.sendUserSignupEmail(user);
	}
	
	public void registerUser(AuthUser user) {
	    List<UserRole> roles = new ArrayList<UserRole>();
        roles.add(UserRole.ROLE_USER);                
        createUser(user, roles);        
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        providerSignInUtils.doPostSignUp(user.getId().toString(), requestAttributes);        
	}
	
    @Transactional
    public AuthUser createUser(AuthUser authUser,List<UserRole> roles) throws UserRegistrationException {
        authUser.setUserName(authUser.getEmail());
        authUser.setPassword(getHashedPassword(authUser.getPassword()));        
        authUser.setRoles(roles);
        try{
        	authUserRepository.saveAndFlush(authUser);
        }
        catch(org.hibernate.exception.ConstraintViolationException ex){
        	throw new UserRegistrationException("User already exists", UserRegistrationException.Exceptiontype.EXISTING_USER);
        }
        return authUser;
    }
    
    public void buildAuthUser(AuthUser authUser, UserProfile profile, Connection<?> connection) {        
        authUser.setFullName(profile.getName());
        authUser.setImageUrl(connection.getImageUrl());
        authUser.setEmail(profile.getEmail());
        authUser.setUserName(profile.getEmail());
        SocialProfile socialProfile = new SocialProfile(connection.getKey().getProviderId(), connection.getKey().getProviderUserId());
        SocialProfileData socialProfileData = authUser.new SocialProfileData();
        socialProfileData.add(socialProfile.getProvider(), socialProfile);
        authUser.setSocialProfileData(socialProfileData);        
    }
    
    public void logInUser(AuthUser user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, user.getPassword(),user.getAuthorities());        
        SecurityContextHolder.getContext().setAuthentication(token);        
    }
    
    public AuthUser findAuthUser(UserProfile profile) {
        String email = profile.getEmail();
        AuthUser authUser = findAuthUser(email);
        return authUser;
    }
    
    @Transactional
    public AuthUser getAuthUser(String username) {        
        AuthUser authUser = authUserRepository.findByUserName(username);
        return authUser;
    }
    
    @Transactional
    public AuthUser findAuthUser(String username) {        
        AuthUser authUser = authUserRepository.findByUserNameOrEmail(username, username);
        return authUser;
    }
    
    
    public String getHashedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Transactional
    public void linkProviderToAuthUser(AuthUser authUser, Connection<?> connection, UserProfile profile) {
        if(StringUtils.isNotEmpty(connection.getImageUrl())) {
            authUser.setImageUrl(connection.getImageUrl());    
        }        
        SocialProfileData data = authUser.getSocialProfileData(true);
        SocialProfile sp = new SocialProfile(connection.getKey().getProviderId(), connection.getKey().getProviderUserId());
        data.add(connection.getKey().getProviderId(), sp);
        authUser.setSocialProfileData(data);
        authUserRepository.saveAndFlush(authUser);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authUser, authUser.getPassword(),authUser.getAuthorities());        
        SecurityContextHolder.getContext().setAuthentication(token);
    }

}
