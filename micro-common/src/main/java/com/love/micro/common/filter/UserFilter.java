package com.love.micro.common.filter;

import com.love.common.Constants;
import com.love.common.user.IUser;
import com.love.common.user.UserThreadLocal;
import com.love.common.util.RedisUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Component
public class UserFilter implements Filter {

    private final RedisUtil redisUtil;

    public UserFilter(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = ( HttpServletRequest ) servletRequest;
            String uid = request.getHeader(Constants.UID);
            IUser user = ( IUser ) redisUtil.get(Constants.UID + ":" + uid);
            if (Objects.nonNull(user)) {
                UserThreadLocal.set(user);
            }
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserThreadLocal.remove();
        }
    }
}
