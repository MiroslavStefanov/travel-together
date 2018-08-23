package org.softuni.traveltogether.config;

import org.softuni.traveltogether.specific.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import static org.softuni.traveltogether.config.WebConstants.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final int MAX_SESSIONS_PER_USER = 100;

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .csrfTokenRepository(csrfTokenRepository())
                .and()
                .authorizeRequests()
                    .antMatchers(ANONYMOUS_URLS).anonymous()
                    .antMatchers(PERMITTED_URLS).permitAll()
                    .antMatchers("/admin").hasAnyRole(UserRole.ROLE_ADMIN.name())
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage(LOGIN_URL)
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl(HOME_URL, true)
                    .failureHandler((req, res, e) -> res.sendRedirect(LOGIN_URL + "?error=" + e.getLocalizedMessage()))
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(((httpServletRequest, httpServletResponse, e) -> {
                        for (String url : ANONYMOUS_URLS) {
                            if (httpServletRequest.getRequestURL().toString().endsWith(url)) {
                                httpServletResponse.sendRedirect(HOME_URL);
                                return;
                            }
                        }
                        httpServletResponse.sendRedirect(ACCESS_DENIED_URL);
                    }))

                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                .and()
                .sessionManagement()
                    .maximumSessions(MAX_SESSIONS_PER_USER)
                    .maxSessionsPreventsLogin(false)
                    .expiredUrl(LOGIN_URL)
                    .sessionRegistry(sessionRegistry())
        ;
    }
}