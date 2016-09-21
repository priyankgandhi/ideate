package pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;

import enums.SocialMediaService;
import enums.UserRole;


@Entity
@Table(name="auth_user")
public class AuthUser extends BaseData implements SocialUserDetails {

    private static final long serialVersionUID = 8728659410248704423L;

    private String password;

    @Column(unique = true)
    private String email;
    
    @Column(unique = true)
    private String userName;

	private String fullName;
		
	@ElementCollection(fetch=FetchType.EAGER)
	List<UserRole> roles = new ArrayList<UserRole>();

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    private boolean autoCreated = false;
    
    private String imageUrl;
    
    private String socialProfile;
    
    @Transient
    private SocialProfileData socialProfileData;

    public AuthUser() {
    }    

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

	public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAutoCreated() {
        return autoCreated;
    }

    public void setAutoCreated(boolean autoCreated) {
        this.autoCreated = autoCreated;
    }

    public String getSocialProfile() {
        return socialProfile;
    }

    public void setSocialProfile(String socialProfile) {
        this.socialProfile = socialProfile;
        Gson json = new Gson();
        this.socialProfileData =json.fromJson(socialProfile, SocialProfileData.class);
    }

    public void setSocialProfileData(SocialProfileData socialProfileData) {
        this.socialProfileData = socialProfileData;
        Gson json = new Gson();
        String socialProfileJson = json.toJson(socialProfileData);
        this.setSocialProfile(socialProfileJson);
    }
    
    public SocialProfileData getSocialProfileData(boolean create) {
        if(socialProfileData == null && create) {
            socialProfileData = new SocialProfileData();
        }
        return socialProfileData;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {        
        return getRoles();
    }

    @Override
    public String getUsername() {
        return getUserName();
    }

    @Override
    public String getUserId() {
        return getUserName();
    }
    
    public class SocialProfileData {
        
        private Map<String, SocialProfile> data;

        public Map<String, SocialProfile> getData() {
            return data;
        }

        public void setData(Map<String, SocialProfile> data) {
            this.data = data;
        }
        
        public SocialProfile get(String provider) {
            return data.get(provider);            
        }
        
        public SocialProfile add(String provider, SocialProfile profile) {
            if (CollectionUtils.isEmpty(data)) {
                data = new HashMap<String, SocialProfile>();
            }
            return data.put(provider, profile);         
        }
        
    }
    
}
