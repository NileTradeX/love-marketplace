package com.love.api.common.filter;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GlobalParamFilter implements Filter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(GlobalParamFilter.class);

    public static final String APPLICATION_FORM_URLENCODED_VALUE = "application/x-www-form-urlencoded";

    public static final String MULTIPART_FORM_DATA_VALUE = "multipart/form-data";

    public static final String APPLICATION_JSON_VALUE = "application/json";

    private static final Gson gson = new GsonBuilder().create();

    private List<String> fieldList;

    public void setFields(String fields) {
        this.fieldList = Stream.of(fields.split(",")).collect(Collectors.toList());
    }

    public void setFieldList(List<String> fieldList) {
        this.fieldList = fieldList;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (req instanceof HttpServletRequest) {
            Map<String, String> headers = new HashMap<>();
            Map<String, String> params = new HashMap<>();

            HttpServletRequest request = ( HttpServletRequest ) req;

            Map<String, String> headersMap = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                headersMap.put(header, request.getHeader(header));
            }
            logger.debug("request uri:{} header: {}", request.getRequestURI(), headersMap);

            if (Objects.nonNull(fieldList) && !fieldList.isEmpty()) {
                for (String s : fieldList) {
                    Object o = request.getHeader(s);
                    if (Objects.isNull(o)) {
                        o = request.getAttribute(s);
                    }

                    if (Objects.nonNull(o)) {
                        params.put(s, o.toString());
                        headers.put(s, o.toString());
                    }
                }
            }

            String contentType = Optional.ofNullable(request.getContentType()).orElse(APPLICATION_FORM_URLENCODED_VALUE);
            String method = request.getMethod().toUpperCase();
            if (contentType.equals(APPLICATION_JSON_VALUE)) { // body形式(json)
                String body = getBody(request);
                if (StringUtils.isNotEmpty(body)) {
                    Map<String, Object> map = gson.fromJson(body, TypeToken.getParameterized(Map.class, String.class, Object.class).getType());
                    map.putAll(params);
                    String json = gson.toJson(map);
                    request = new BodyRequestWrapper(request, json, headers);
                    logger.debug("===> {}: merged json content > {}, extra header > {}", method, json, headers);
                }
            } else if (contentType.equals(APPLICATION_FORM_URLENCODED_VALUE) || contentType.contains(MULTIPART_FORM_DATA_VALUE)) {  // form表单形式或者默认get请求
                request = new ParameterRequestWrapper(request, new HashMap<>(params), headers);
                logger.debug("===> {}: extra form param > {}, extra header > {}", method, params, headers);
            } else {
                request = new HeaderWrapper(request, headers);
                logger.debug("===> {}: extra header: {}", method, headers);
            }

            chain.doFilter(request, response);
        } else {
            chain.doFilter(req, response);
        }
    }

    private String getBody(ServletRequest request) {
        StringBuilder builder = new StringBuilder();
        try (InputStream in = request.getInputStream(); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in))) {
            int read;
            char[] buffer = new char[128];
            while ((read = bufferedReader.read(buffer)) > 0) {
                builder.append(buffer, 0, read);
            }
        } catch (Exception ignored) {

        }
        String result = builder.toString();
        if ("\"\"".equals(result) || StringUtils.isBlank(result)) {
            return "{}";
        }
        return result;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE + 1;
    }
}
