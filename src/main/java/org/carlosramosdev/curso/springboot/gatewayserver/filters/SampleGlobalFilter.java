package org.carlosramosdev.curso.springboot.gatewayserver.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class SampleGlobalFilter implements GlobalFilter {

    private final Logger logger = Logger.getLogger(String.valueOf(SampleGlobalFilter.class));

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Ejecutando el filtro antes del request PRE");

        return chain.filter(exchange).then(Mono.fromRunnable( ()-> {
                logger.info("Ejecutando el filtro POST response");
                exchange.getResponse().getCookies().add("color", ResponseCookie
                        .from("color","blue").build());
                exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        }));
    }
}
