package com.white.notifyservice.config;

import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Locale;

import static com.white.notifyservice.config.DateConfig.Constants.*;


@Configuration
@RequiredArgsConstructor
public class DateConfig {


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder ->
                builder.simpleDateFormat(DATE_TIME_FORMAT)
                       .serializers(new LocalDateSerializer(DATE_FORMATTER),
                                    new LocalDateTimeSerializer(DATE_TIME_FORMATTER),
                                    CustomInstantSerializer.INSTANCE)
                       .deserializers(new LocalDateDeserializer(DATE_FORMATTER),
                                      new LocalDateTimeDeserializer(DATE_TIME_FORMATTER),
                                      CustomInstantDeserializer.INSTANCE);
    }


    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) {
                return LocalDate.parse(text, DATE_FORMATTER);
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return DATE_FORMATTER.format(object);
            }
        };
    }

    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<LocalDateTime>() {
            @Override
            public LocalDateTime parse(String text, Locale locale) {
                return LocalDateTime.parse(text, DATE_TIME_FORMATTER);
            }

            @Override
            public String print(LocalDateTime object, Locale locale) {
                return DATE_TIME_FORMATTER.format(object);
            }
        };
    }

    @Bean
    public Formatter<Instant> instantFormatter() {
        return new Formatter<Instant>() {
            @Override
            public Instant parse(String text, Locale locale) {
                return Instant.from(DATE_TIME_FORMATTER.parse(text));
            }

            @Override
            public String print(Instant object, Locale locale) {
                return DATE_TIME_FORMATTER.format(object);
            }
        };
    }

    /**
     * Не нашел другого способа задать DateTimeFormat для Instant
     */
    private static class CustomInstantDeserializer<T extends Temporal> extends InstantDeserializer<T> {
        public static final CustomInstantDeserializer<Instant> INSTANCE = new CustomInstantDeserializer<>(InstantDeserializer.INSTANT, DATE_TIME_FORMATTER);

        protected CustomInstantDeserializer(InstantDeserializer<T> base, DateTimeFormatter f) {
            super(base, f);
        }
    }

    private static class CustomInstantSerializer extends InstantSerializer {
        public static final CustomInstantSerializer INSTANCE = new CustomInstantSerializer(InstantSerializer.INSTANCE, false, DATE_TIME_FORMATTER);

        protected CustomInstantSerializer(InstantSerializer base, Boolean useTimestamp, DateTimeFormatter formatter) {
            super(base, useTimestamp, formatter);
        }
    }

    public static class Constants {
        public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withZone(ZoneId.of("UTC"));
    }

}
