package com.leonardomotta.services;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.leonardomotta.model.Role;
import com.leonardomotta.model.User;
import com.leonardomotta.repository.UserRepository;

@Transactional
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		  try {
	            User user = userRepository.findByUsername(username);
	            if(user==null){
	                return null;
	            }
	            //return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthories(user));
	            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, getAuthories(user));
		  }

	        catch (Exception e)
	        {
	         throw new UsernameNotFoundException("User not found!");
	        }
	}
	
	
	
	private Set<GrantedAuthority> getAuthories(User user){

        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role: user.getRoles()){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }

}
