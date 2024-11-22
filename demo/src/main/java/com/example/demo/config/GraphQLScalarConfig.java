package com.example.demo.config;

import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class GraphQLScalarConfig {

    @Bean
    public GraphQLScalarType dateScalar() {
        return GraphQLScalarType.newScalar()
                .name("Date")
                .description("A custom scalar for Java LocalDate")
                .coercing(new Coercing<LocalDate, String>() {
                    @Override
                    public String serialize(Object dataFetcherResult) {
                        if (dataFetcherResult instanceof LocalDate) {
                            return ((LocalDate) dataFetcherResult).format(DateTimeFormatter.ISO_LOCAL_DATE);
                        }
                        throw new IllegalArgumentException("Invalid type for Date scalar: " + dataFetcherResult);
                    }

                    @Override
                    public LocalDate parseValue(Object input) {
                        try {
                            return LocalDate.parse(input.toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                        } catch (Exception e) {
                            throw new IllegalArgumentException("Invalid value for Date scalar: " + input);
                        }
                    }
                    @Bean
                    public GraphQLScalarType dateScalar() {
                        System.out.println("Registering custom scalar: Date");
                        return GraphQLScalarType.newScalar()
                                .name("Date")
                                .description("A custom scalar for Java LocalDate")
                                .coercing(new Coercing<LocalDate, String>() {
                                    // Serialize, ParseValue, ParseLiteral...
                                })
                                .build();
                    }

                    @Override
                    public LocalDate parseLiteral(Object input) {
                        return parseValue(input);
                    }
                })
                .build();
    }
}
