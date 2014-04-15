package com.darknova.postcardmailer.parser.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This converts from a String to an object.
 */
public abstract class Converter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(Converter.class);

    /**
     * Convert a string into T.
     * @param value the value to convert
     * @return the converted object
     */
    public abstract T convert(final String value) throws Exception;

    /**
     * Determine if the value can be converted
     * @param value the value to convert
     * @return true if it can be converted
     */
    public boolean canConvertFrom(final String value) {
        try {
            convert(value);
        } catch (Exception e) {
            LOG.trace("{} cannot convert {}", this.getClass().getSimpleName(), value);
            return false;
        }

        LOG.trace("{} can convert {}", this.getClass().getSimpleName(), value);
        return true;
    }

    /**
     * Determine if this converter can write to the provided type
     * @param target the target class
     * @return true if this can convert
     */
    public abstract boolean canConvertTo(final Class target);

}
