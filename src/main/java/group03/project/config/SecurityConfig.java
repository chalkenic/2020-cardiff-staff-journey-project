package group03.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.util.LinkedHashMap;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /*
    Wires service for accessing UserDetailsService method LoadUserByUsername
     */
    final
    LoginDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(LoginDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Provides Adds authentication based
     * @param auth - authentication builder object that accepts provided authentication from provided method.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Handles individual page security depending on
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                /*
                    Only admin role has access to administration pages.
                 */
                .antMatchers("/admin/**").hasRole("ADMIN")
                /*
                    Both users and admin can access user pages.
                 */
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                /*
                    All users on application have access to initial page alongside visual libraries.
                 */
                .antMatchers("/login").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/register").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403-error")
                .and()
                /*
                    Default spring login page to /login. Allow all users access.
                 */
                .formLogin()
                .loginPage("/login")
                .permitAll()
                /*
                    Signal successful logins to respective dashboard. Deny failed logins back to login page.
                 */
                .defaultSuccessUrl("/dashboard")
                .failureForwardUrl("/failed-login")
                .and()
                /*
                    Logout will invalidate session & delete current cookie.
                 */
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
;

    }

    /**
     * hashes password with a strength of 10 (rounds). Salt applied at beginning of hash
     * to provide further protection.
     * @return a new Bcrypt encoder object.
     */
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }


    /**
     * Data Access Object Authentication that provides relevant user details via UserDetailsService interface
     * (user, pw, authorities), along with a custom password encoder defined above.
     * @return DaoAuthenticationProvider object back to configuration.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
