package com.darknova.postcardmailer.parser.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A dummy converter for strings.
 */
@Component
public class StringConverter extends Converter<String> {

    private static final Logger LOG = LoggerFactory.getLogger(StringConverter.class);

    @Override
    public String convert(String value) throws Exception {
        LOG.trace("Converting {} to String", value);
        return value;
    }

    @Override
    public boolean canConvertTo(Class target) {
        LOG.trace("Determining if {} can convert to {}", getClass().getSimpleName(), target.getSimpleName());
        return String.class.isAssignableFrom(target);
    }
}
