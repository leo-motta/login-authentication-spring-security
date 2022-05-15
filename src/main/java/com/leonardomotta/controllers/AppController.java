package com.leonardomotta.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leonardomotta.model.Role;
import com.leonardomotta.model.User;
import com.leonardomotta.repository.RoleRepository;
import com.leonardomotta.repository.UserRepository;

@Controller
public class AppController {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@RequestMapping("/success")
	public String secure() {
		return "success";
	}
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    
    @PostMapping("/process_register")
    public String processRegister(User user) {
    	
        user.setEnabled(true);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        return "success";
    }
}
