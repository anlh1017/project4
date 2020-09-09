package vn.aptech.project4.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.aptech.project4.entity.User;
import vn.aptech.project4.repository.UserRepository;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	     User user = userRepository.findByUsername(userName)
	    	       .orElseThrow(() -> new UsernameNotFoundException("Username " + userName + " not found"));
	    	         return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
	    	         getAuthorities(user));
	}
    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getAuthorities().stream().map((role) -> role.getAuthority()).toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}
