package com.darknova.postcardmailer.mailer.config;

import com.darknova.postcardmailer.mailer.PostcardMailer;
import com.darknova.postcardmailer.parser.config.ParserConfig;
import com.darknova.postcardmailer.scraper.config.ScraperConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * This spring {@link Configuration} instantiates application level beans.
 */
@Configuration
@Import({ParserConfig.class, ScraperConfig.class})
@ComponentScan(basePackageClasses = PostcardMailer.class)
public class ApplicationConfig {
}
