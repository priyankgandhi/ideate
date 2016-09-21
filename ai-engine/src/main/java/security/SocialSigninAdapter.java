package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import pojo.AuthUser;
import repositories.AuthUserRepository;

@Service
public class SocialSigninAdapter implements SignInAdapter{

    @Autowired
    private AuthUserRepository userRepository;
    
    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        AuthUser user = userRepository.findById(Integer.parseInt(userId));
        SecurityContextHolder.getContext().setAuthentication(new SocialAuthenticationToken(connection, user, null, user.getAuthorities()));        
        return null;
    }

}
