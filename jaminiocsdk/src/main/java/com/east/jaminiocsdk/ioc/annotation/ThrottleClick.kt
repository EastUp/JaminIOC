package com.east.baselibrary.ioc.annotation

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 限制快速点击
 *  @author: jamin
 *  @date: 2020/4/28
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Target(AnnotationTarget.FUNCTION)  //注解只能声明在方法上
@Retention(AnnotationRetention.RUNTIME)  //运行时注解
annotation class ThrottleClick(
    val value:Long = 1000L  //点击响应的间隔时间，默认1秒
)