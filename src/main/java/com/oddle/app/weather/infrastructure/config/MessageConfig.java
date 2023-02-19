package com.oddle.app.weather.infrastructure.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author cuongnd9
 * @date 15/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.config
 */
@Configuration
public class MessageConfig extends AcceptHeaderLocaleResolver {

    private final List<Locale> LOCALES = Arrays.asList(new Locale("vi"), new Locale("en"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String acceptLanguage = request.getHeader("Accept-Language");
        return StringUtils.hasText(acceptLanguage) ?
                Locale.lookup(Locale.LanguageRange.parse(acceptLanguage), LOCALES) :
                Locale.getDefault();
    }

    @Bean(name = "localeResolver")
    @Primary
    public LocaleResolver getLocaleResolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(Locale.US);
        return acceptHeaderLocaleResolver;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();
        messageResource.setBasename("classpath:i18n/message");
        messageResource.setDefaultEncoding("UTF-8");
        messageResource.setUseCodeAsDefaultMessage(true);
        return messageResource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

}
