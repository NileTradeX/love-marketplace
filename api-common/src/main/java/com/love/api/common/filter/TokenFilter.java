package com.love.api.common.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.love.api.common.util.JwtUtil;
import com.love.common.Constants;
import com.love.common.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class TokenFilter implements Filter, Ordered {

    private static final String TOKEN = "token";

    private static final String TOKEN_EXPIRED_CODE = "9998";
    private static final String TOKEN_INVALID_CODE = "9999";
    private List<String> whitelist;
    private HandlerExceptionResolver handlerExceptionResolver;

    public void setHandlerExceptionResolver(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = ( HttpServletRequest ) req;
        HttpServletResponse response = ( HttpServletResponse ) res;

        response.addHeader("timestamp", String.valueOf(System.currentTimeMillis()));

        String contextPath = request.getContextPath();
        String uri = request.getRequestURI().replace(contextPath, "");
        if (Objects.nonNull(whitelist) && (whitelist.contains(uri) || regexMatch(uri))) {
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(TOKEN);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(TOKEN);
        }

        if (Objects.isNull(token) || token.trim().isEmpty()) {
            this.handlerExceptionResolver.resolveException(request, response, null, BizException.buildWithCode(TOKEN_INVALID_CODE, "token is null"));
            return;
        }

        Exception exception = null;
        try {
            Map<String, String> map = JwtUtil.verifyJwt(token);
            if (map.containsKey(Constants.UID) || map.containsKey(Constants.USER_ID)) {
                map.forEach(request::setAttribute);
                chain.doFilter(request, response);
            } else {
                exception = BizException.buildWithCode(TOKEN_INVALID_CODE, "token is invalid");
            }
        } catch (TokenExpiredException e) {
            exception = BizException.buildWithCode(TOKEN_EXPIRED_CODE, "token expired");

        } catch (JWTVerificationException e) {
            exception = BizException.buildWithCode(TOKEN_INVALID_CODE, "token is invalid");
        } 

        if (Objects.nonNull(exception)) {
            this.handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

    private boolean regexMatch(String uri) {
        return this.whitelist.stream().anyMatch(s -> Pattern.compile(s).matcher(uri).matches());
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
