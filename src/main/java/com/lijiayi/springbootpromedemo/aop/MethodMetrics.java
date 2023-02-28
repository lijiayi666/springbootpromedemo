package com.lijiayi.springbootpromedemo.aop;

import java.lang.annotation.*;

// 采集注解
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodMetrics {

}
