package com.darknova.postcardmailer.parser.config;

import com.darknova.postcardmailer.parser.CsvParser;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
@ComponentScan(basePackageClasses = CsvParser.class)
public class ParserConfig {
}
