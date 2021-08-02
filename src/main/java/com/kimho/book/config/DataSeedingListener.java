package com.kimho.book.config;

import com.kimho.book.model.dao.Role;
import com.kimho.book.model.dao.User;
import com.kimho.book.repository.RoleRepository;
import com.kimho.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private void addRoleIfMissing(String name) {
        if (roleRepository.findByName(name) == null) {
            roleRepository.save(new Role(name));
        }
    }

    public void addUserIfMissing(String email, String firstname, String lastname, String password, String roleName) {
        if (userRepository.findByEmail(email) == null) {
            User user = new User(email, firstname, lastname, new BCryptPasswordEncoder().encode(password));
            Role role = roleRepository.findByName(roleName);
            user.setRole(role);
            if (roleName.equals("ROLE_SUPER_ADMIN")) {
                user.setEnabled(true);
            }
            userRepository.save(user);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        addRoleIfMissing("ROLE_SUPER_ADMIN");
        addRoleIfMissing("ROLE_ADMIN");
        addRoleIfMissing("ROLE_USER");

        addUserIfMissing("user@gmail.com", "Kim", "Ho", "password", "ROLE_USER");
        addUserIfMissing("admin@gmail.com", "Admin", "Spring", "password", "ROLE_ADMIN");
        addUserIfMissing("superadmin@gmail.com", "SuperAdmin", "Spring", "password", "ROLE_SUPER_ADMIN");
    }
}
