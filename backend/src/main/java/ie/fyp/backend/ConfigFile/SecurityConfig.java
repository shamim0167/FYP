package ie.fyp.backend.ConfigFile;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

//@EnableWebSecurity
@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
        grantedAuthorityMapper.setPrefix("ROLE_");

        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }


    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }


    @Bean
    @Override
    @ConditionalOnMissingBean(HttpSessionManager.class)
    protected HttpSessionManager httpSessionManager() {
        return new HttpSessionManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .authorizeRequests()
//                .antMatchers("/api/**/view/**").permitAll()
//                .antMatchers("/api/courses").permitAll()
//                .antMatchers("/api/modules").permitAll()
//                .antMatchers("/api/**").hasRole("Application_Admin")



//
//                .antMatchers("/api/courses").hasAnyRole("Application_Student_Users", "Application_Teacher_Users")
//                .antMatchers("/api/modules").hasAnyRole("Application_Student_Users","Application_Teacher_Users")
//                .antMatchers("/api/**").hasRole("Application_Admin")

//
//
//
//
//
//
//                .antMatchers(HttpMethod.GET,"/api/**/view/**").permitAll()
//                .antMatchers(HttpMethod.GET,"/api/**").permitAll()
//
////                .antMatchers(HttpMethod.GET,"/api/courses").permitAll()
////                .antMatchers(HttpMethod.GET,"/api/modules").permitAll()
//
//                .antMatchers(HttpMethod.POST,"/api/**").hasRole("Application_Admin")
//                .antMatchers(HttpMethod.PUT,"/api/**").hasRole("Application_Admin")
//                .antMatchers(HttpMethod.DELETE,"/api/**").hasRole("Application_Admin")



                .antMatchers("/api/**/view/**").hasAnyRole("Application_Student_Users", "Application_Teacher_Users","Application_Admin")
                .antMatchers("/api/**").hasAnyRole("Application_Student_Users", "Application_Teacher_Users","Application_Admin")

//                .antMatchers(HttpMethod.GET,"/api/courses").permitAll()
//                .antMatchers(HttpMethod.GET,"/api/modules").permitAll()

                .antMatchers("/api/**/new").hasRole("Application_Admin")
                .antMatchers("/api/**/update").hasRole("Application_Admin")
                .antMatchers("/api/**/delete").hasRole("Application_Admin")
                .anyRequest().permitAll();
    }
}