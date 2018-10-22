package com.springmvc.springmongodbweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
//import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

//import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.springmvc.springmongodbweb.domain.Role;
import com.springmvc.springmongodbweb.repositories.RoleRepository;



@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class )
@EnableMongoRepositories("com.springmvc.springmongodbweb.repositories")
//@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class SpringMongodbWebApplication extends org.springframework.boot.web.servlet.support.SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringMongodbWebApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringMongodbWebApplication.class, args);
    }
    @Bean
    CommandLineRunner init(RoleRepository roleRepository) {

        return args -> {

            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole("ADMIN");
                roleRepository.save(newAdminRole);
            }
            
            Role userRole = roleRepository.findByRole("USER");
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("USER");
                roleRepository.save(newUserRole);
            }
        };
    }
}
/*public class SpringMongodbWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMongodbWebApplication.class, args);
	}
}*/


