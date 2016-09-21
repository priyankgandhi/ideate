/**
 * 
 */
package pojo;

/**
 * @author priyank
 *
 */
public class SocialProfile {
    
    String provider;
    
    String providerUserId;
    
    public SocialProfile(String provider, String providerUserId) {
        this.provider = provider;
        this.providerUserId = providerUserId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }
    
}
