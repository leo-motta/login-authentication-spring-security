package com.leonardomotta;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.leonardomotta.model.Role;
import com.leonardomotta.model.User;
import com.leonardomotta.repository.RoleRepository;
import com.leonardomotta.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserTestDB {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Test
	public void run() throws Exception {
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));

        Role adminRole = roleRepository.findByRole("ADMIN");
        Role userRole = roleRepository.findByRole("USER");

        User user = new User("admin@code.com", "password","Admin", "Super", true, "admin" );
        user.setPassword("password");
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);

        User user2 = new User("user@code.com", "password","User", "Super", true, "user" );
        user2.setPassword("password");
        user2.setRoles(Arrays.asList(userRole));
        userRepository.save(user2);

    }
}
