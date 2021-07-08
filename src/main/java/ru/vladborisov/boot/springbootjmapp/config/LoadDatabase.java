package ru.vladborisov.boot.springbootjmapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vladborisov.boot.springbootjmapp.model.Role;
import ru.vladborisov.boot.springbootjmapp.model.User;
import ru.vladborisov.boot.springbootjmapp.service.UserService;

import java.util.Set;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserService service) {
        return args -> {
            log.info("Set roles");
            service.setRole(new Role("ROLE_ADMIN"));
            service.setRole(new Role("ROLE_USER"));

            log.info("Preloading user admin");
            User userAdmin = new User("Admin", "Admin", 25L, "admin@admin", "admin", "admin");
            userAdmin.setRoles(Set.of(service.getRole("ROLE_ADMIN")));
            service.add(userAdmin);

            log.info("Preloading user user");
            User userUser = new User("User", "User", 28L, "user@user", "user", "user");
            userUser.setRoles(Set.of(service.getRole("ROLE_USER")));
            service.add(userUser);
        };
    }
}