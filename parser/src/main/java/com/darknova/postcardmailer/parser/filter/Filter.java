package com.darknova.postcardmailer.parser.filter;

import com.darknova.postcardmailer.parser.model.Deed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

/**
 * Filter the list of deeds.
 */
public abstract class Filter {

    private static final Logger LOG = LoggerFactory.getLogger(Filter.class);

    /**
     * Filter deeds based on some criteria.
     * @param deeds the list of deeds to be filtered
     */
    public void filter(final List<Deed> deeds) {
        final Iterator<Deed> iterator = deeds.iterator();

        while (iterator.hasNext()) {
            final Deed deed = iterator.next();
            if (!shouldKeep(deed)) {
                LOG.debug("Removing deed {}", deed);
                iterator.remove();
            }
        }
    }

    /**
     * @return true if the filter should keep this deed.
     */
    abstract boolean shouldKeep(final Deed deed);
}
