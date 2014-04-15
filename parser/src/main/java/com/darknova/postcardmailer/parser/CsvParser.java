package com.darknova.postcardmailer.parser;

import com.darknova.postcardmailer.parser.converter.Converter;
import com.darknova.postcardmailer.parser.model.Deed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Parse a CSV into a list of Deeds.
 */
@Service
public class CsvParser {

    private static final Logger LOG = LoggerFactory.getLogger(CsvParser.class);

    @Autowired
    private List<Converter> converters;

    /**
     * Parse the {@link Deed} {@link List} from the CSV file.
     * @param reader the {@link Reader} for the CSV file to read from
     * @return the {@link Deed} {@link List}
     * @throws Exception if unable to parse the CSV file
     */
    public List<Deed> parseDeeds(final Reader reader) throws Exception {
        return parseDeeds(reader, ',', '"', true);
    }

    /**
     * Parse the deeds from a CSV file.
     * @param reader the {@link Reader} for the CSV file to read from.
     * @param delimiter the delimiter character in the CSV file
     * @param qualifier the qualifier that delimits values in the CSV file
     * @param skipFirstLine true to skip the first line of the CSV
     * @return the {@link Deed} {@link List}
     * @throws Exception if unable to parse the CSV file
     */
    public List<Deed> parseDeeds(final Reader reader, final char delimiter, final char qualifier, final boolean skipFirstLine) throws Exception {
        LOG.info("Parsing CSV from reader with delimiter '{}', qualifier '{}'." + (skipFirstLine ? " Skipping first line." : ""), delimiter, qualifier);

        final BufferedReader bufferedReader = new BufferedReader(reader);

        String line = bufferedReader.readLine();
        if (skipFirstLine) {
            LOG.debug("Skipping line " + line);
            line = bufferedReader.readLine();
        }

        final List<Deed> deeds = new LinkedList<>();

        while (line != null && !line.isEmpty()) {
            LOG.debug("Read line from CSV file: {}", line);
            deeds.add(parseDeed(line, delimiter, qualifier));
            line = bufferedReader.readLine();
        }

        LOG.debug("Read deeds {}", deeds);
        return deeds;
    }

    /**
     * Parse a single {@link Deed} from the CSV file.
     * @param line a single line in the CSV file
     * @param delimiter the column separator character
     * @param qualifier the qualifier that delimits values in the CSV file
     * @return the single parsed {@link Deed}
     * @throws Exception if unable to parse the deed
     */
    private Deed parseDeed(final String line, final char delimiter, final char qualifier) throws Exception {
        final List<String> values = parseLine(line, delimiter, qualifier);

        final Constructor<?>[] constructors = Deed.class.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == values.size()) {
                try {
                    return (Deed) constructor.newInstance(translateArguments(constructor, values));
                } catch (Exception e) {
                    LOG.trace("Found a constructor {} with the same number of arguments, but wrong types {}.", constructor, values);
                }
            }
        }

        LOG.error("Could not find a constructor matching the record {}", line);
        throw new IOException("Could not find a constructor matching the record!");
    }

    /**
     * Extract a list of string values from a line in the CSV file.
     * @param line the line in the CSV file
     * @param delimiter the column separator character
     * @param qualifier the qualifier that delimits values in the CSV file
     * @return the value {@link List}
     * @throws IOException if unable to parse the line
     */
    private List<String> parseLine(final String line, final char delimiter, final char qualifier) throws IOException {
        final StringReader reader = new StringReader(line);

        boolean readingValue = false;

        final List<String> values = new LinkedList<>();
        StringBuilder builder = new StringBuilder();

        int readByte;
        while ((readByte = reader.read()) != -1) {
            char readChar = (char) readByte;
            if (readChar == delimiter) {
                if (readingValue) {
                    builder.append(readChar);
                } else {
                    values.add(builder.toString());
                    LOG.trace("Extracted value {}", builder.toString());
                    builder = new StringBuilder();
                }
            } else if (readChar == qualifier) {
                readingValue = !readingValue;
            } else {
                builder.append(readChar);
            }
        }

        values.add(builder.toString());
        LOG.trace("Extracted value {}", builder.toString());

        LOG.debug("Values extracted from line: {}", values);
        return values;
    }

    /**
     * Convert a {@link List} of values into types that match the paramters of the provided {@link Constructor}.
     * @param constructor the {@link Constructor} to match the {@link List} of values to
     * @param values the {@link List} of values.
     * @return an {@link Object} array of matching types
     * @throws Exception if unable to convert the values
     */
    public Object[] translateArguments(final Constructor<?> constructor, final List<String> values) throws Exception {
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        final Object[] parameters = new Object[values.size()];
        final Iterator<String> iterator = values.iterator();

        LOG.trace("Converting {} values to {} arguments", values.size(), parameters.length);

        for (int i = 0; i < parameterTypes.length; i++) {
            final String value = iterator.next();
            final Class<?> type = parameterTypes[i];
            final Converter converter = findConverter(type, value);

            parameters[i] = converter.convert(value);
        }

        return parameters;
    }

    /**
     * Find a {@link Converter} to translate to a specified {@link Class} from a specified value.
     * @param type the {@link Class} to convert the value to
     * @param value the value to convert
     * @return a {@link Converter} that can make the required conversion
     */
    public Converter findConverter(final Class type, final String value) {
        for (Converter converter : converters) {
            if (converter.canConvertTo(type) && converter.canConvertFrom(value)) {
                LOG.trace("Found converter {} for type {} with value " + value, converter.getClass().getSimpleName(), type.getSimpleName());
                return converter;
            }
        }

        LOG.error("Could not find converter to {} from {}", type.getSimpleName(), value);
        throw new RuntimeException("Could not find a converter from " + value + " to " + type.getName());
    }
}
