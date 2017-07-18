package net.dingyabin.com.filter;

import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by MrDing
 * Date: 2017/3/13.
 * Time:22:05
 */
public class MyFilter implements  Filter  {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        MDC.clear();
        MDC.put("traceId", httpServletRequest.getHeader("traceId") == null ? UUID.randomUUID().toString() : httpServletRequest.getHeader("traceId"));
        httpServletResponse.addHeader("traceId",MDC.get("traceId"));
        chain.doFilter(httpServletRequest,httpServletResponse);
    }

    @Override
    public void destroy() {

    }


}
