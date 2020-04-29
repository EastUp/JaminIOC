package com.east.baselibrary.ioc.annotation

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 绑定View的Annotation
 *  @author: jamin
 *  @date: 2020/4/28
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Target(AnnotationTarget.FIELD)  //注解只能声明在属性上
@Retention(AnnotationRetention.RUNTIME) //运行时注解
annotation class BindView(
    val value:Int    //View的ID值
)