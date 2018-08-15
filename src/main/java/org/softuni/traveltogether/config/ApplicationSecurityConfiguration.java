package org.softuni.traveltogether.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
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
                        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
                            for (String url : WebConstants.ANONYMOUS_URLS) {
                                if(httpServletRequest.getRequestURL().toString().endsWith(url)){
                                    httpServletResponse.sendRedirect("/home");
                                    return;
                                }
                            }
                        }
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