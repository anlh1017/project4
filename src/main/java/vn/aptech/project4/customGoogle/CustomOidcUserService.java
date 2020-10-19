package vn.aptech.project4.customGoogle;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import vn.aptech.project4.repository.CustomerRepository;

import java.util.Map;
@Service
public class CustomOidcUserService extends OidcUserService {

	 @Autowired
	    private CustomerRepository userRepository;

	    @Override
	    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
	        OidcUser oidcUser = super.loadUser(userRequest);
	        Map<String, Object> attributes = oidcUser.getAttributes();
	        GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo();
	        userInfo.setEmail((String) attributes.get("email"));
	        userInfo.setId((String) attributes.get("sub"));
	        userInfo.setImageUrl((String) attributes.get("picture"));
	        userInfo.setName((String) attributes.get("name"));
	        return oidcUser;
	    }

	   
}
