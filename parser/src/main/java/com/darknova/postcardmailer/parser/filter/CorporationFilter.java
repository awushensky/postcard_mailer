package com.darknova.postcardmailer.parser.filter;

import com.darknova.postcardmailer.parser.model.Deed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This {@link Filter} removes corporations.
 */
@Component
public class CorporationFilter extends Filter {

    private static final Logger LOG = LoggerFactory.getLogger(CorporationFilter.class);

    @Override
    boolean shouldKeep(Deed deed) {
        final String[] words = deed.getFirstPartyName().split(" ");
        final String lastWord = words[words.length - 1].toUpperCase();

        try {
            LOG.trace("Determining if name {} ends with a known company", deed.getFirstPartyName());
            return CompanySuffix.valueOf(lastWord) == null;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Common company suffixes.
     */
    public static enum CompanySuffix {

        LTD,
        LLC,
        EE,
        CORPORATION,
        CORP,
        PARTNERSHIP,
        NA,
        INC,
        INCORPORATED,
        TRUST,
        TRST,
        TR,
        TRS,
        LE,
        LEST
    }
}
