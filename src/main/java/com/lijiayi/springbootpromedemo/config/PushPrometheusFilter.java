//package com.lijiayi.springbootpromedemo.config;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.lijiayi.springbootpromedemo.dto.PushResultDTO;
//import com.lijiayi.springbootpromedemo.metrics.PushRequestCounter;
//import com.lijiayi.springbootpromedemo.metrics.PushResponseAvgTimer;
//import com.lijiayi.springbootpromedemo.metrics.PushResponseMaxTimer;
//import io.prometheus.client.Gauge;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
////@Component
//public class PushPrometheusFilter implements Filter, ApplicationContextAware {
//
//    private static PushRequestCounter requestCounter;
//
//    private static PushResponseAvgTimer responseAvgTimer;
//
//    private static PushResponseMaxTimer responseMaxTimer;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        // 包装response
//        ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper((HttpServletResponse) response);
//
//        Gauge.Child child = new Gauge.Child();
//        Gauge.Timer durationTimer = child.startTimer();
//        String responseStr;
//        PushResultDTO resultDTO = new PushResultDTO();
//        try {
//            chain.doFilter(request, responseCacheWrapperObject);
//            // 获取请求结果
//            byte[] responseArray = responseCacheWrapperObject.getContentAsByteArray();
//            responseStr = new String(responseArray, responseCacheWrapperObject.getCharacterEncoding());
//            resultDTO = JSONObject.parseObject(responseStr, PushResultDTO.class);
//            // 将结果重新写回response
//            responseCacheWrapperObject.copyBodyToResponse();
//        } catch (Exception e) {
//            log.info("业务中的异常", e.getMessage());
//            throw e;
//        } finally {
//            // counter 记录请求成功失败次数
//            // 总请求次数 sum(increase(cy_request_counter_total{instance='cyComputer'}[1m]))
//            // 总请求成功次数 sum(increase(cy_request_counter_total{instance='cyComputer',code='000000'}[1m]))
//            // 总请求失败次数 sum(increase(cy_request_counter_total{instance='cyComputer',code='999999'}[1m]))
//            requestCounter.count(resultDTO.getCode());
//
//            // 暂停计时
//            durationTimer.setDuration();
//
//            // counter 记录平均相应时长
//            // sum(increase(cy_total_time_counter_total{instance='cyComputer'}[1m]))/sum(increase(cy_request_counter_total{instance='cyComputer'}[1m]))
//            // sum(increase(cy_total_time_counter_total{instance='cyComputer',code='000000'}[1m]))/sum(increase(cy_request_counter_total{instance='cyComputer',code='000000'}[1m]))
//            // sum(increase(cy_total_time_counter_total{instance='cyComputer',code='999999'}[1m]))/sum(increase(cy_request_counter_total{instance='cyComputer',code='999999'}[1m]))
//            // 根据code 直接分类
//            // sum(increase(cy_total_time_counter_total{instance='cyComputer'}[30s]))by (code)/sum(increase(cy_request_counter_total{instance='cyComputer'}[30s]))by (code)
//            responseAvgTimer.count(child, resultDTO.getCode());
//
//            // gauge 记录请求最大消耗时间
//            // max(cy_max_timer_gauge)
//            responseMaxTimer.maxTimer(child, resultDTO.getCode());
//        }
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        requestCounter = applicationContext.getBean(PushRequestCounter.class);
//        responseAvgTimer = applicationContext.getBean(PushResponseAvgTimer.class);
//        responseMaxTimer = applicationContext.getBean(PushResponseMaxTimer.class);
//    }
//}
//
