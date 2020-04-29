package com.east.baselibrary.ioc.annotation

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 判断是否有网络
 *  @author: jamin
 *  @date: 2020/4/28
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Target(AnnotationTarget.FUNCTION)  //注解只能声明在方法上
@Retention(AnnotationRetention.RUNTIME) //运行时注解
annotation class CheckNet(
    val errorMsg:String = "亲，您的网络不太给力哦"    //View的ID值
)