package com.springmvc.springmongodbweb;

import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
//import org.springframework.boot.context.web.SpringBootServletInitializer;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.springmvc.springmongodbweb.repositories")
//@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class SpringMongodbWebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringMongodbWebApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringMongodbWebApplication.class, args);
    }

}
/*public class SpringMongodbWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMongodbWebApplication.class, args);
	}
}*/


