package com.darknova.postcardmailer.parser.filter;

import com.darknova.postcardmailer.parser.model.Deed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This {@link Filter} filters based on price.
 */
@Component
public class PriceFilter extends Filter {

    private static final Logger LOG = LoggerFactory.getLogger(PriceFilter.class);

    private static final int MIN_PRICE = 150000;

    @Override
    boolean shouldKeep(final Deed deed) {
        LOG.trace("Determining if price {} is above minimum threshold", deed.getTotalValue());
        return deed.getTotalValue() >= MIN_PRICE;
    }
}
