package br.com.junitexample.config;

import br.com.junitexample.domain.Users;
import br.com.junitexample.repositories.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UsersRepository repository;

    @Bean
    public void startDB(){
        Users u1 = new Users(null, "Neres", "neresdev@gmail.com", "123");
        Users u2 = new Users(null, "David", "davidneres4554@gmail.com", "123");

        repository.saveAll(List.of(u1, u2));
    }
}
