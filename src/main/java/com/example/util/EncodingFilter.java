// prac(1)/prac/src/main/java/com/example/util/EncodingFilter.java

package com.example.util;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    // 可以在这里定义一个默认编码或者从 init-param 获取
    private String encoding = "UTF-8"; // 默认使用 UTF-8

    public void init(FilterConfig config) throws ServletException {
        // 从 web.xml 获取配置的编码参数
        String configEncoding = config.getInitParameter("encoding");
        if (configEncoding != null && !configEncoding.isEmpty()) {
            this.encoding = configEncoding;
        }
        System.out.println("EncodingFilter initialized with encoding: " + this.encoding);
    }

    public void destroy() {
        System.out.println("EncodingFilter destroyed.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 设置请求和响应的字符编码
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        response.setContentType("text/html;charset=utf-8"); // 也可以在这里设置默认的Content-Type

        // 将请求和响应传递给过滤器链中的下一个资源
        chain.doFilter(request, response);
    }
}