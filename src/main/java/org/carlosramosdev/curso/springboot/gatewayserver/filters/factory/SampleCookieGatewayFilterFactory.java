package org.carlosramosdev.curso.springboot.gatewayserver.filters.factory;

import org.carlosramosdev.curso.springboot.gatewayserver.filters.SampleGlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.logging.Logger;

@Component
public class SampleCookieGatewayFilterFactory extends AbstractGatewayFilterFactory<SampleCookieGatewayFilterFactory.ConfigurationCookie> {

    private final Logger logger = Logger.getLogger(String.valueOf(SampleCookieGatewayFilterFactory.class));

    @Override
    public GatewayFilter apply(ConfigurationCookie config) {
        return (exchange, chain) -> {
            logger.info("Pre gateway filter factory "+config.message);

            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                Optional.ofNullable(config.value).ifPresent(cookie -> {
                    exchange.getResponse().addCookie(ResponseCookie.from(config.name, cookie).build());
                });
                logger.info("Post gateway filter factory "+config.message);
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
