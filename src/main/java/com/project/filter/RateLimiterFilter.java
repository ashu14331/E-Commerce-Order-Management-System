package com.project.filter;

import com.project.service.serviceImpl.RateLimiterService;
import io.github.bucket4j.Bucket;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


public class RateLimiterFilter implements WebFilter {

    private final RateLimiterService service;

    public RateLimiterFilter(RateLimiterService service) {
        this.service = service;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        {
            String ip =
                    exchange.getRequest()
                            .getRemoteAddress()
                            .getAddress()
                            .getHostAddress();
            Bucket bucket = service.resolveBucket(ip);
            if (bucket.tryConsume(1)) {
                return chain.filter(exchange);
            }
            exchange.getResponse()
                    .setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }
    }
}
