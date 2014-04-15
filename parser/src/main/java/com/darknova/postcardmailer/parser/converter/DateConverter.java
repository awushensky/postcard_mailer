package com.darknova.postcardmailer.parser.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Convert a string to a date.
 */
@Component
public class DateConverter extends Converter<Date> {

    private static final Logger LOG = LoggerFactory.getLogger(DateConverter.class);

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

    @Override
    public Date convert(String value) throws Exception {
        LOG.trace("Parsing {} to a date", value);
        return FORMAT.parse(value);
    }

    @Override
    public boolean canConvertTo(Class target) {
        LOG.trace("Determining if {} can convert to {}", getClass().getSimpleName(), target.getSimpleName());
        return Date.class.isAssignableFrom(target);
    }
}
