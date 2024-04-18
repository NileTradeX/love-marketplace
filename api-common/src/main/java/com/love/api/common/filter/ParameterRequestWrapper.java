package com.love.api.common.filter;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ParameterRequestWrapper extends HeaderWrapper {

    private final Map<String, String[]> params = new HashMap<>();

    public ParameterRequestWrapper(HttpServletRequest request) {
        this(request, null, null);
    }

    /**
     * 重载一个构造方法
     *
     * @param request      HttpServletRequest
     * @param extendParams Map<String, Object>
     * @param headers      Map<String, String>
     */
    public ParameterRequestWrapper(HttpServletRequest request, Map<String, Object> extendParams, Map<String, String> headers) {
        super(request, headers);
        this.params.putAll(request.getParameterMap());
        this.addAllParameters(extendParams);
    }

    /**
     * 在获取所有的参数名,必须重写此方法，否则对象中参数值映射不上
     *
     * @return Enumeration
     */
    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(params.keySet());
    }

    /**
     * 重写getParameter方法
     *
     * @param name 参数名
     * @return 返回参数值
     */
    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (Objects.isNull(values) || values.length == 0) {
            return null;
        }
        return values[0];
    }


    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    /**
     * 增加多个参数
     *
     * @param extendParams 增加的多个参数
     */
    public void addAllParameters(Map<String, Object> extendParams) {
        if (Objects.nonNull(extendParams) && !extendParams.isEmpty()) {
            for (Map.Entry<String, Object> entry : extendParams.entrySet()) {
                addParameter(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 增加参数
     *
     * @param name  参数名
     * @param value 参数值
     */
    public void addParameter(String name, Object value) {
        if (Objects.nonNull(value)) {
            if (value instanceof String[]) {
                params.put(name, ( String[] ) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{( String ) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.params;
    }
}
