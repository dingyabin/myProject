package net.dingyabin.com.filter;

import org.slf4j.MDC;
import org.springframework.web.filter.CharacterEncodingFilter;

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
public class MyFilter extends CharacterEncodingFilter {



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
        doEncodings(httpServletRequest,httpServletResponse,filterChain);
        MDC.clear();
        MDC.put("traceId", httpServletRequest.getHeader("traceId") == null ? UUID.randomUUID().toString() : httpServletRequest.getHeader("traceId"));
        httpServletResponse.addHeader("traceId",MDC.get("traceId"));
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }




    /**
     * 编码过滤
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     */
    private void doEncodings(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain){
        try {
            super.doFilterInternal(httpServletRequest,httpServletResponse,filterChain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
