package org.softuni.traveltogether.config;

import org.softuni.traveltogether.specific.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.security.Principal;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

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
                    .antMatchers(WebConstants.ANONYMOUS_URLS).anonymous()
                    .antMatchers("/css/**", "/scripts/**", "/assets/**", "/favicon.ico", "/travel_api/search/findTop5ByOrderByPublishedAtDesc").permitAll()
                    .antMatchers("/admin").hasAnyRole(UserRole.ROLE_ADMIN.name())
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/home", true)
                    .failureHandler((req, res, e) -> res.sendRedirect("/login?error=" + e.getLocalizedMessage()))
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(((httpServletRequest, httpServletResponse, e) -> {
                        for (String url : WebConstants.ANONYMOUS_URLS) {
                            if (httpServletRequest.getRequestURL().toString().endsWith(url)) {
                                httpServletResponse.sendRedirect("/home");
                                return;
                            }
                        }
                        e.printStackTrace();
                        httpServletResponse.sendRedirect("/unauthorized");
                    }))

                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
        ;
    }
}