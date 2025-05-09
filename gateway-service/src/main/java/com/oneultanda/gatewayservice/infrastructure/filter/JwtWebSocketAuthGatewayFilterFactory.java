//package com.oneultanda.gatewayservice.infrastructure.filter;
//
//import com.oneultanda.gatewayservice.infrastructure.jwt.JwtUtil;
//import io.jsonwebtoken.Claims;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.server.ServerWebExchange;
//
//@Slf4j
//@Component
//public class JwtWebSocketAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Override
//    public GatewayFilter apply(Object config) {
//        return (exchange, chain) -> {
//            log.info("토큰 검증 시작");
//            String tokenValue = jwtUtil.extractToken(exchange);
//
//            if (!StringUtils.hasText(tokenValue) || !jwtUtil.validateToken(tokenValue)) {
//                log.error("invalid token");
//                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                return exchange.getResponse().setComplete();
//            }
//
//            Claims claims = jwtUtil.getUserInfoFromToken(tokenValue);
//
//            String userId = String.valueOf(claims.get("id"));
//            String username = String.valueOf(claims.getSubject());
//            String role = String.valueOf(claims.get("role"));
//
//            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
//                    .header("X-User-Id", userId)
//                    .header("X-User-Name", username)
//                    .header("X-User-Role", role)
//                    .build();
//
//            ServerWebExchange mutatedExchange = exchange.mutate()
//                    .request(mutatedRequest)
//                    .build();
//
//            log.info("토큰 검즘확인: " + username);
//
//            // 통과 시 계속 진행
//            return chain.filter(exchange);
//        };
//    }
//}
//
