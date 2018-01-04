package com.ucs.ganzhou.app.api.Interceptor;

import com.ucs.ganzhou.app.api.controller.ApiController;
import com.ucs.ganzhou.app.api.model.BaseRequestInfo;
import com.ucs.ganzhou.app.api.service.UspService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.UUID;


@Aspect
@Component
public class ControllerInterceptor {
    private  static Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);
    @Autowired
    private UspService uspService;

    @Pointcut("execution(* com.ucs.ganzhou.app.api.controller.ApiController..*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut(){}


    @Before("com.ucs.ganzhou.app.api.Interceptor.ControllerInterceptor.controllerMethodPointcut()")
    public void  doBeforeAdvice(JoinPoint joinPoint)throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        BufferedReader br = request.getReader();
        String str, requestXml = "";
        while((str = br.readLine()) != null){
            requestXml += str;
        }
        BaseRequestInfo baseRequestInfo =   new BaseRequestInfo(UUID.randomUUID().toString().replace("-",""),request.getRequestURL().toString(),requestXml);
        ApiController.setBaseRequestInfo(baseRequestInfo );
        logger.debug(baseRequestInfo.getRequestXml());
        uspService.log(baseRequestInfo.getUrl()+String.format("【%s】",baseRequestInfo.getRequestId()),"IN: "+ baseRequestInfo.getRequestXml());
    }
    @After("com.ucs.ganzhou.app.api.Interceptor.ControllerInterceptor.controllerMethodPointcut()")
    public  void doAfterAdvice(JoinPoint joinPoint){
        logger.debug(ApiController.getResponseXml());
        BaseRequestInfo baseRequestInfo =ApiController.getBaseRequestInfo();
        uspService.log(baseRequestInfo.getUrl()+String.format("【%s】",baseRequestInfo.getRequestId()), "OUT: "+ ApiController.getResponseXml());
    }

    @AfterThrowing( pointcut = "com.ucs.ganzhou.app.api.Interceptor.ControllerInterceptor.controllerMethodPointcut()", throwing = "ex")
    public  void doAfterErrorAdvice(JoinPoint joinPoint,Throwable ex){
        logger.error(ex.getMessage(),ex);
        BaseRequestInfo baseRequestInfo =ApiController.getBaseRequestInfo();
        uspService.warn(String.format("请求接口：%s 异常| 流水号：%s",baseRequestInfo.getUrl(),baseRequestInfo.getRequestId()),ex.toString());
    }
}
