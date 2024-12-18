package org.carlosramosdev.curso.springboot.gatewayserver.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.logging.Logger;

@Component
public class SampleGlobalFilter implements GlobalFilter {

    private final Logger logger = Logger.getLogger(String.valueOf(SampleGlobalFilter.class));

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Ejecutando el filtro antes del request PRE");

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate().headers(h -> h
                .add("token","asdf")).build();
        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

        return chain.filter(mutatedExchange).then(Mono.fromRunnable( ()-> {
                logger.info("Ejecutando el filtro POST response");
                String token = mutatedExchange.getRequest().getHeaders().getFirst("token");

                if (token != null) {
                    logger.info("Token: "+ token);
                }

                Optional.ofNullable(mutatedExchange.getRequest().getHeaders().getFirst("token")).ifPresent( value -> {
                    logger.info("Token: "+ value);
                    exchange.getResponse().getHeaders().add("token",value);
                });

                exchange.getResponse().getCookies().add("color", ResponseCookie
                        .from("color","blue").build());
                exchange.getResponse().getHeaders();
                        //.setContentType(MediaType.TEXT_PLAIN);
        }));
    }
}
