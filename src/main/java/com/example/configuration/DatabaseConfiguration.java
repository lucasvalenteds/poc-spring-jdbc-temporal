package com.example.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.mapping.JdbcValue;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Workaround to add support for ZonedDateTime in Spring Data JDBC
 *
 * @see <a href="https://github.com/spring-projects/spring-data-relational/issues/1136">#1136</a>
 * @see <a href="https://github.com/spring-projects/spring-data-relational/issues/1089">#1089</a>
 * @see <a href="https://github.com/pgjdbc/pgjdbc/issues/1325">#1325</a>
 * @see <a href="https://github.com/pgjdbc/pgjdbc/issues/996">#996</a>
 */
@Configuration
public class DatabaseConfiguration extends AbstractJdbcConfiguration {

    @Override
    protected List<?> userConverters() {
        return List.of(
                new ZonedDateTimeWriterConverter(),
                new ZonedDateTimeReaderConverter()
        );
    }

    @WritingConverter
    private static final class ZonedDateTimeWriterConverter implements Converter<ZonedDateTime, JdbcValue> {

        @Override
        public JdbcValue convert(ZonedDateTime source) {
            final var timestamp = Timestamp.valueOf(source.withZoneSameInstant(ZoneOffset.UTC)
                    .toLocalDateTime());

            return JdbcValue.of(timestamp, JDBCType.TIMESTAMP);
        }
    }

    @ReadingConverter
    private static final class ZonedDateTimeReaderConverter implements Converter<Timestamp, ZonedDateTime> {

        @Override
        public ZonedDateTime convert(Timestamp source) {
            return source.toLocalDateTime().atZone(ZoneOffset.UTC);
        }
    }
}
