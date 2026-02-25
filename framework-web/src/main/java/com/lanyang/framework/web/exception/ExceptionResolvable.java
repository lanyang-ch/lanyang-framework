package com.lanyang.framework.web.exception;

import com.lanyang.framework.web.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;


public interface ExceptionResolvable {

    Integer getCode();

    String getMessage();

    default String getMessage(String message) {
        MessageSource messageSource = SpringUtils.getBean(ReloadableResourceBundleMessageSource.class);
        Locale locale = LocaleContextHolder.getLocale();
        String localeMessage = messageSource.getMessage(message, null, StringUtils.EMPTY, locale);
        if (StringUtils.isNotBlank(localeMessage)) {
            localeMessage = message;
        }
        return localeMessage;
    }
}
