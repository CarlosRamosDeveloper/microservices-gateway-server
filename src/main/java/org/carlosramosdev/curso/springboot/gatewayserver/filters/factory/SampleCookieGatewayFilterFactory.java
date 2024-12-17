package org.carlosramosdev.curso.springboot.gatewayserver.filters.factory;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.ObjectInputFilter;

@Component
public class SampleCookieGatewayFilterFactory extends AbstractGatewayFilterFactory<SampleCookieGatewayFilterFactory.ConfigurationCookie> {

    @Override
    public GatewayFilter apply(ConfigurationCookie configurationCookie) {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(()->{

            }));
        };
    }

    public SampleCookieGatewayFilterFactory() {
        super(ConfigurationCookie.class);
    }

    public static class ConfigurationCookie {
        private String name;
        private String value;
        private String message;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
