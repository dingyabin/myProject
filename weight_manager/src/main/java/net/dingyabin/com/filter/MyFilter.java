package net.dingyabin.com.filter;

import org.slf4j.MDC;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by MrDing
 * Date: 2017/3/13.
 * Time:22:05
 */
public class MyFilter extends OncePerRequestFilter {



    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        MDC.clear();
        MDC.put("traceId", httpServletRequest.getHeader("traceId") == null ? UUID.randomUUID().toString() : httpServletRequest.getHeader("traceId"));
        httpServletResponse.addHeader("traceId",MDC.get("traceId"));
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }


}
