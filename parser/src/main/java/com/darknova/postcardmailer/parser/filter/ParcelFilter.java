package com.darknova.postcardmailer.parser.filter;

import com.darknova.postcardmailer.parser.model.Deed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter deeds by parcel.
 */
@Component
public class ParcelFilter extends Filter {

    private static final Logger LOG = LoggerFactory.getLogger(ParcelFilter.class);

    private static final List<String> desiredParcels = new ArrayList<String>() {{
        add("176");
        add("177-28");
    }};

    @Override
    boolean shouldKeep(final Deed deed) {
        for (String parcel : desiredParcels) {
            if (deed.getParcelNumber().startsWith(parcel)) {
                LOG.trace("Parcel {} is in desired range {}", deed.getParcelNumber(), parcel);
                return true;
            }
        }

        LOG.trace("Parcel {} is not in desired range", deed.getParcelNumber());
        return false;
    }
}
