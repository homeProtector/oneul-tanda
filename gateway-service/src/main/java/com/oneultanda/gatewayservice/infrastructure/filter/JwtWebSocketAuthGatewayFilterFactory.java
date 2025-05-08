package com.oneultanda.gatewayservice.infrastructure.filter;

import com.oneultanda.gatewayservice.infrastructure.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class JwtWebSocketAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            URI requestUri = exchange.getRequest().getURI();
            String query = requestUri.getQuery();

            String token = UriComponentsBuilder.fromUri(requestUri).build().getQueryParams().getFirst("token");

            if (!StringUtils.hasText(token) || !jwtUtil.validateToken(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // 통과 시 계속 진행
            return chain.filter(exchange);
        };
    }
}

