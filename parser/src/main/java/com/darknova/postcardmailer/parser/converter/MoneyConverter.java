package com.darknova.postcardmailer.parser.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Convert money to a float.
 */
@Component
public class MoneyConverter extends Converter<Float> {

    private static final Logger LOG = LoggerFactory.getLogger(MoneyConverter.class);

    @Override
    public Float convert(final String value) throws Exception {
        final Reader reader = new StringReader(value);
        final StringBuilder builder = new StringBuilder();
        LOG.trace("Converting '{}' to a float.", value);

        int readByte;
        while ((readByte = reader.read()) != -1) {
            final char readChar = (char) readByte;
            if (readChar >= '0' && readChar <= '9' || readChar == '.') {
                builder.append(readChar);
            } else if (readChar != ',' && readChar != '$') {
                LOG.error("Encountered invalid character in money, '{}'", readChar);
                throw new IOException("Encountered invalid character in money, " + readChar);
            }
        }

        LOG.trace("Converted {} to {}", value, builder.toString());
        return Float.parseFloat(builder.toString());
    }

    @Override
    public boolean canConvertTo(Class target) {
        LOG.trace("Determining if {} can convert to {}", getClass().getSimpleName(), target.getSimpleName());
        return float.class.isAssignableFrom(target);
    }
}
