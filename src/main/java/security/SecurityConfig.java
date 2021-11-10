package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select user_email,user_password, enabled from users where user_email=?")
                .authoritiesByUsernameQuery("select user_email, authority from authorities where user_email=?");
//                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home").hasAuthority("USER")
                .antMatchers("/registration").permitAll().anyRequest().anonymous()
                .and()
                .formLogin().loginPage("/login").usernameParameter("email").permitAll()
                //.defaultSuccessUrl("/home")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/exit", "POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/login");
    }

}
