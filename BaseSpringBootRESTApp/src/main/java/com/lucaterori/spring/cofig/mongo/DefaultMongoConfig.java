package com.lucaterori.spring.cofig.mongo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.lucaterori")
public class DefaultMongoConfig {
    public DefaultMongoConfig() {}
}
