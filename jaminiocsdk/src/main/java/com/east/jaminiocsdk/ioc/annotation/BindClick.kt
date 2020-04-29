package com.east.baselibrary.ioc.annotation

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 绑定View的Onclick事件
 *  @author: jamin
 *  @date: 2020/4/28
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Target(AnnotationTarget.FUNCTION)  //注解只能声明在方法上
@Retention(AnnotationRetention.RUNTIME)  //运行时注解
annotation class BindClick(
    vararg val value:Int    //View的ID值,可以传多个
)