package com.darknova.postcardmailer.mailer;

import com.darknova.postcardmailer.mailer.config.ApplicationConfig;
import com.darknova.postcardmailer.parser.config.ParserConfig;
import com.darknova.postcardmailer.parser.filter.Filter;
import com.darknova.postcardmailer.parser.model.Deed;
import com.darknova.postcardmailer.parser.CsvParser;
import com.darknova.postcardmailer.scraper.Scraper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * Create a list of postcard stuffs.
 */
@Component
public class PostcardMailer {

    final static Logger LOG = LoggerFactory.getLogger(PostcardMailer.class);

    @Autowired
    private List<Filter> filters;

    @Autowired
    private CsvParser parser;

    public static void main(final String[] args) throws Exception {
        LOG.debug("Initializing spring application context.");
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        LOG.debug("Retrieving postcard mailer from spring context.");
        final PostcardMailer postcardMailer = applicationContext.getBean(PostcardMailer.class);

        LOG.debug("Starting mailer.");
//        final InputStreamReader csvReader = new InputStreamReader(PostcardMailer.class.getResourceAsStream("/example.csv"));
//        postcardMailer.readRecords(csvReader);

        new Scraper().test();
    }

    /**
     * Read the records from the CSV and extracts desired {@link Deed}s.
     * @param reader the {@link Reader} for the CSV
     * @return the {@link Deed} {@link List}
     * @throws Exception if unable to read the CSV
     */
    public List<Deed> readRecords(final Reader reader) throws Exception {

        final List<Deed> deeds = parser.parseDeeds(reader);

        for (Filter filter : filters) {
            filter.filter(deeds);
        }

        return deeds;
    }

}
