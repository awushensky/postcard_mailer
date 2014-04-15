package com.darknova.postcardmailer.scraper.config;


import com.darknova.postcardmailer.scraper.Scraper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * This {@link Configuration} handles the data scraper.
 */
@Configuration
@ComponentScan(basePackageClasses = Scraper.class)
public class ScraperConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
