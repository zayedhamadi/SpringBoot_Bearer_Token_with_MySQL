package SpringBoot.Bearer.Token.JWT.Security.with.MySQL.SpringToken.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordBean{
@Bean
    public BCryptPasswordEncoder BCryptPasswordEncoder(){
    return  new BCryptPasswordEncoder();
}
}
