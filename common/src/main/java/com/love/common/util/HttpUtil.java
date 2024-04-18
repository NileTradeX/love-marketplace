package com.love.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

import javax.net.ssl.*;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class HttpUtil {

    static final String WEB_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
    static final int CONN_TIME_OUT = 60 * 1000;
    static final int READ_TIME_OUT = 3 * 60 * 1000;
    private final static Object lock = new Object();

    private HttpUtil() {

    }

    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext context = SSLContext.getInstance("SSLv3");

        X509TrustManager trustManager = new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        context.init(null, new TrustManager[]{trustManager}, null);
        return context;
    }

    private static CloseableHttpClient createHttpClient(int maxTotal, int maxPerRoute) {
        ConnectionSocketFactory socketFactory = PlainConnectionSocketFactory.getSocketFactory();
        // 这里可以用上面的createIgnoreVerifySSL来创建SSLConnectionSocketFactory
        LayeredConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", socketFactory).register("https", sslConnectionSocketFactory).build();
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        poolingHttpClientConnectionManager.setMaxTotal(maxTotal);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);
        HttpRequestRetryHandler httpRequestRetryHandler = (IOException exception, int executionCount, HttpContext context) -> {
            if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                return false;
            }

            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                return true;
            }

            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                return false;
            }

            if (exception instanceof InterruptedIOException) {// 超时
                return false;
            }

            if (exception instanceof UnknownHostException) {// 目标服务器不可达
                return false;
            }

            if (exception instanceof SSLException) {// SSL握手异常
                return false;
            }

            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            return !(request instanceof HttpEntityEnclosingRequest);
        };

        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();

        return HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setRetryHandler(httpRequestRetryHandler)
                .setRedirectStrategy(redirectStrategy)
                .setUserAgent(WEB_USER_AGENT)
                .disableCookieManagement()
                .build();
    }

    private static final class HttpClientHolder {
        static final CloseableHttpClient httpClient = createHttpClient(200, 40);
    }

    private static void config(HttpRequestBase httpRequestBase, Map<String, String> headers, int readTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CONN_TIME_OUT)
                .setConnectTimeout(CONN_TIME_OUT)
                .setSocketTimeout(readTimeout > 0 ? readTimeout : READ_TIME_OUT)
                .setCircularRedirectsAllowed(true)
                .build();
        httpRequestBase.setConfig(requestConfig);
        httpRequestBase.addHeader("User-Agent", WEB_USER_AGENT);
        httpRequestBase.addHeader("Connection", "keep-alive");
        if (headers != null) {
            headers.forEach(httpRequestBase::addHeader);
        }
    }

    public static String get(String url) throws Exception {
        return get(url, null);
    }

    public static String get(String url, Map<String, String> headers) throws Exception {
        return get(url, headers, 0);
    }

    public static String get(String url, Map<String, String> headers, int readTimeout) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        config(httpGet, headers, readTimeout);
        return execute(httpGet);
    }


    public static String post(String url, Map<String, Object> params) throws Exception {
        return post(url, params, null);
    }

    public static String post(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        return post(url, params, headers, 0);
    }

    public static String post(String url, Map<String, Object> params, Map<String, String> headers, int readTimeout) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        config(httpPost, headers, readTimeout);
        httpPost.setEntity(new UrlEncodedFormEntity(params.keySet().stream().map(key -> new BasicNameValuePair(key, params.get(key) == null ? null : params.get(key).toString())).collect(Collectors.toList()), "UTF-8"));
        return execute(httpPost);
    }

    public static String post(String url, String content) throws Exception {
        return post(url, content, null);
    }

    public static String post(String url, String content, Map<String, String> headers) throws Exception {
        return post(url, content, headers, 0);
    }

    public static String post(String url, String content, Map<String, String> headers, int readTimeout) throws Exception {
        return post(url, content, null, headers, readTimeout);
    }

    public static String post(String url, String content, ContentType contentType, Map<String, String> headers, int readTimeout) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        config(httpPost, headers, readTimeout);
        if (contentType == null) {
            contentType = ContentType.getByMimeType(ContentType.APPLICATION_JSON.getMimeType());
        }
        httpPost.setEntity(new StringEntity(content, contentType));
        return execute(httpPost);
    }

    public static String multipart(String url, Map<String, Object> params) throws Exception {
        return multipart(url, params, null);
    }

    public static String multipart(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        return multipart(url, params, headers, 0);
    }

    public static String multipart(String url, Map<String, Object> params, Map<String, String> headers, int readTimeout) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        config(httpPost, headers, readTimeout);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        if (params != null && params.size() > 0) {
            params.forEach((key, val) -> {
                if (val instanceof File) {
                    builder.addPart(key, new FileBody(( File ) val));
                } else {
                    builder.addTextBody(key, val.toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
                }
            });
        }

        httpPost.setEntity(builder.build());
        return execute(httpPost);
    }


    private static String execute(HttpRequestBase request) throws Exception {
        CloseableHttpResponse response = null;
        try {
            HttpClientContext context = HttpClientContext.create();
            response = HttpClientHolder.httpClient.execute(request, context);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()).is2xxSuccessful()) {
                return result;
            }
            throw new RuntimeException(response.toString()+",result:"+result);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static CloseableHttpResponse request(HttpRequestBase request) throws Exception {
        return request(request, null);
    }

    public static CloseableHttpResponse request(HttpRequestBase request, Callback callback) throws Exception {
        CloseableHttpResponse response = null;
        try {
            HttpClientContext context = HttpClientContext.create();
            response = HttpClientHolder.httpClient.execute(request, context);
            if (callback != null) {
                callback.doInCallback(response, context);
            }
            return response;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, String> commonHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        headers.put("Connection", "keep-alive");
        return headers;
    }

    public static Map<String, String> jsonHeaders() {
        Map<String, String> headers = commonHeaders();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    public static Map<String, String> formHeaders() {
        Map<String, String> headers = commonHeaders();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        return headers;
    }

    public static Map<String, String> ajaxHeaders() {
        Map<String, String> headers = commonHeaders();
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    public static Map<String, String> ajaxFormHeaders() {
        Map<String, String> headers = commonHeaders();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    public static Map<String, String> ajaxJsonHeaders() {
        Map<String, String> headers = commonHeaders();
        headers.put("Content-Type", "application/json");
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    @FunctionalInterface
    public interface Callback {
        void doInCallback(CloseableHttpResponse response, HttpClientContext context);
    }
}
