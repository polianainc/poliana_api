package com.poliana.config.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.poliana.config.ApplicationConfig;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.authentication.configurers.InMemoryClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.OAuth2ServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.OAuth2ServerConfigurer;
import org.springframework.security.oauth2.provider.token.JdbcTokenStore;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.inject.Inject;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * In conjunction with {@link WebApplicationInitializer}, this configuration class sets up Spring Data REST, In
 * conjunction with {@link WebApplicationInitializer}, this configuration class sets up Spring Data REST, Spring MVC,
 * Spring Security and Spring Security OAuth, along with importing all of our existing service implementations.
 *
 * @author David Gilmore
 * @author Josh Long
 * @see WebApplicationInitializer
 */
public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{ApplicationConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{RepositoryRestMvcConfiguration.class, WebMvcConfiguration.class, SecurityConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        File uploadDirectory = ApplicationConfig.CRM_STORAGE_UPLOADS_DIRECTORY;
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(uploadDirectory.getAbsolutePath(), maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);
        registration.setMultipartConfig(multipartConfigElement);
    }

}

@Configuration
@EnableWebSecurity
@Order(1)
class OAuth2ServerConfiguration extends OAuth2ServerConfigurerAdapter {

    private final String applicationName = ApplicationConfig.CRM_NAME;

    @Inject
    private javax.sql.DataSource dataSource;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private ContentNegotiationStrategy contentNegotiationStrategy;

      
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .and()
                .apply(new InMemoryClientDetailsServiceConfigurer())
                .withClient("android-crm")
                .resourceIds(applicationName)
                .scopes("read", "write")
                .authorities("ROLE_USER")
                .authorizedGrantTypes("authorization_code", "implicit", "password")
                .secret("123456")
                .and()
                .withClient("ios-crm")
                .resourceIds(applicationName)
                .scopes("read", "write")
                .authorities("ROLE_USER")
                .authorizedGrantTypes("password")
                .secret("123456");
    }
     

      
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .requestMatchers(oauthRequestMatcher())
                .and()
                .authorizeRequests()
                .antMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated();

        http
                .apply(new OAuth2ServerConfigurer())
                .tokenStore(new JdbcTokenStore(this.dataSource))
                .resourceId(applicationName);
    }
     

    @Bean
    public RequestMatcher oauthRequestMatcher() {
      /*  MediaTypeRequestMatcher mediaTypeRequestMatcher =
                new MediaTypeRequestMatcher(contentNegotiationStrategy, MediaType.APPLICATION_JSON, new MediaType("image", "*"));
        Set<MediaType> mediaTypes = new HashSet<MediaType>();
        mediaTypes.add(MediaType.ALL);
        mediaTypes.add( new MediaType("image","webp"));
        mediaTypeRequestMatcher.setIgnoredMediaTypes( mediaTypes);
        return mediaTypeRequestMatcher;*/

        // oauth is complicated
        // how do i detect when it's a browser? when it's not?
        // if a native REST client makes a call and submits a Accept: */*
        MediaTypeRequestMatcher mediaTypeRequestMatcher = new MediaTypeRequestMatcher( this.contentNegotiationStrategy, MediaType.ALL);
        return new NegatedRequestMatcher( mediaTypeRequestMatcher);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.noOpText();
    }
}


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

      
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2/**"); // h2 has its own security
    }
     

      
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // nb: the H2 administration console should *not* be left exposed.
        // comment out the mapping path below so that it requires an authentication to see it.
        String[] filesToLetThroughUnAuthorized =
                {
                        H2EmbeddedDatbaseConsoleInitializer.H2_DATABASE_CONSOLE_MAPPING,
                        "/favicon.ico"
                };

        http
                .authorizeRequests()
                .antMatchers(filesToLetThroughUnAuthorized).permitAll()
                // .antMatchers("/users/*").denyAll()
                .anyRequest().authenticated();

        http
                .formLogin()
                .loginPage("/crm/signin.html")
                .defaultSuccessUrl("/crm/welcome.html")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/signout")
                .permitAll();
    }
     
}

@Configuration
@ComponentScan
@EnableHypermediaSupport
@EnableWebMvc
class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    /** This application renders Spring Security UI pages to support logging into, and out of, the application. */
    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/crm/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Bean
    public LocaleResolver localeResolver() {

        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("en"));
        return cookieLocaleResolver;
    }

    @Bean
    public MessageSource messageSource() {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages/messages", "classpath:messages/validation");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(0);
        return messageSource;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        converters.add(converter());
    }

    @Bean
    public HttpMessageConverter converter() {

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setPrettyPrint(true);
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    public ObjectMapper objectMapper() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz"));
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

        return mapper;
    }
}
