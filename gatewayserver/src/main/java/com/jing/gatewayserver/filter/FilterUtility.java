package com.jing.gatewayserver.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;


@Component
public class FilterUtility {

    public static final String CORRELATION_ID = "eazybank-correlation-id";

    public String getCorrelationId(HttpHeaders requestHeaders) {
        if(requestHeaders.get(CORRELATION_ID) != null) {
            List<String> requestHeaderLists = requestHeaders.get(CORRELATION_ID);
            return requestHeaderLists.stream().findFirst().get();
        }
        return null;
    }
    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange getCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, correlationId, "eazybank-correlation-id");
    }
}
