package cat.tecnocampus.college.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private DataSource dataSource;

    private static final String USERS_QUERY = "select email, password, enabled from student where email = ?";

    private static final String AUTHORITIES_QUERY = "select email, role from authorities where email = ?";

    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

        /*
    TODO 1 (Security): modify the matchers so that
        * "/subjectList" and "/" can be accessed even by non logged in users
        * "/lastRegistration", "/academicRecord", "/registerSubjects" can be accessed only by users with role USER
        * "studentList" can be accessed only by users with role ADMIN
     HINT: you may need to modify some of the already existing matchers, delete existing ones and/or add new ones. There is
           a TRICKY one that makes the others useless
     HINT: remember that the order matters
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/style/**").permitAll()
                .antMatchers("/error").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin() //to use forms (web)

                .and()
                .httpBasic() //to use http headers REST
                .and()

                .rememberMe()
                .tokenValiditySeconds(2419200)
                .key("tecnocampus")

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //needed only when csrf is enabled (as by default is post)
                .logoutSuccessUrl("/"); //where to go when logout is successful

        http
                .csrf().disable()
                .headers()
                .frameOptions().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USERS_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_QUERY);
    }

}
