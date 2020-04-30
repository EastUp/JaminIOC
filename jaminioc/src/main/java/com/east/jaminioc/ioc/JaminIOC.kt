package com.east.jaminioc.ioc

import android.app.Activity
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import com.east.jaminioc.ioc.annotation.BindClick
import com.east.jaminioc.ioc.annotation.BindView
import com.east.jaminioc.ioc.annotation.CheckNet
import com.east.jaminioc.ioc.annotation.ThrottleClick
import com.east.jaminioc.utils.NetworkUtils

import java.lang.reflect.Method
import kotlin.math.abs

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: IOC注解工具类
 *  @author: jamin
 *  @date: 2020/4/28
 * |---------------------------------------------------------------------------------------------------------------|
 */
object JaminIOC {


    /**
     *  绑定Activity
     */
    fun bind(activity:Activity){
        resolveBind(ViewFinder(activity),activity)
    }

    /**
     *  绑定View （自定义View）
     */
    fun bind(view: View){
        resolveBind(ViewFinder(view),view)
    }

    /**
     *  @param view  --> 绑定的View
     *  @param any   --> 可能@BindClick在其它的类中，方便查找并进行调用 举例在Fragment中调用
     */
    fun bind(view:View,any:Any){
        resolveBind(ViewFinder(view),any)
    }

    /**
     *  解析
     *  @param any 在这里面去进行查找属性和方法
     */
    private fun resolveBind(viewFinder: ViewFinder, any:Any){
        //解析绑定的View并通过反射赋值
        resolveBindFiled(viewFinder,any)

        //解析绑定的View 并设置OnClickListener并调用@BindClick的方法
        resolveBindFunction(viewFinder,any)
    }

    /**
     *  解析绑定的View并通过反射赋值
     */
    private fun resolveBindFiled(viewFinder: ViewFinder, any: Any) {
        // 1.获取所有的属性
        val clazz = any.javaClass
        val declaredFields = clazz.declaredFields
        // 2.判断有@BindView注解的属性并获取注解的value
        for (field in declaredFields) {
            //有BindView注解的属性
            if(field.isAnnotationPresent(BindView::class.java)){
                val bindView = field.getAnnotation(BindView::class.java)
                val viewId = bindView.value
                // 3.通过viewFinder 查找到View
                val view = viewFinder.findViewById(viewId)
                // 4.把查找到的View通过反射赋值进去
                // 设置accessible为true 代表即使是私有属性也能访问
                field.isAccessible = true
                field.set(any,view)
            }
        }
    }


    /**
     *  解析绑定的View 并设置OnClickListener并调用@BindClick的方法
     */
    private fun resolveBindFunction(viewFinder: ViewFinder, any: Any) {
        // 1.获取所有方法
        val clazz = any.javaClass
        val declaredMethods = clazz.declaredMethods
        // 2.判断出含有@BindClick注解的方法，并获取注解的value
        for (method in declaredMethods) {
            if(method.isAnnotationPresent(BindClick::class.java)){
                val bindClick = method.getAnnotation(BindClick::class.java)
                // 检测网络
                var checkNet = method.getAnnotation(CheckNet::class.java)
                //限制点击时间
                var throttleClick = method.getAnnotation(ThrottleClick::class.java)
                //初始化一个OnClickListener
                var clickListener = DeclaredOnClickListener(method,any,checkNet,throttleClick)
                val ids = bindClick.value
                // 3.通过viewFinder获取View并为其设置OnClickListener
                for (id in ids) {
                    viewFinder.findViewById(id).setOnClickListener(clickListener)
                }
            }
        }


    }

    private class DeclaredOnClickListener(val method:Method, val any:Any, var checkNet: CheckNet?= null, var throttleClick: ThrottleClick?= null) :View.OnClickListener{

        private var mLastClickTime = 0L  //最后一次点击的时间

        override fun onClick(v: View?) {

            //检测网络
            if(checkNet!=null){
                //没网时需要打印的信息
                val toastMsg = checkNet!!.errorMsg
                if(!NetworkUtils.isConnected(v!!.context)){
                    Toast.makeText(v!!.context,toastMsg,Toast.LENGTH_SHORT).show()
                    return
                }
            }

            // 4.在OnclickListener中调用@BindClick注解下面的方法
            // 设置accessible为true 代表即使是私有方法也能调用
            method.isAccessible = true

            if(throttleClick!=null){
                //点击响应的间隔时间
                val throttleTime = throttleClick!!.value
                //使用：System.currentTimeMillis()用于和日期相关的地方，比如日志。SystemClock.elapsedRealtime()用于某个事件经历的时间，比如两次点击的时间间隔。
                val time = SystemClock.elapsedRealtime()
                val timeInterval = abs(time - mLastClickTime)
                if(timeInterval >= throttleTime){
                    invokeMethod(v)
                    mLastClickTime = time
                }
            }else{
                invokeMethod(v)
            }
        }

        /**
         *  执行方法
         */
        private fun invokeMethod(v: View?) {
            try {
                method.invoke(any, v)
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    method.invoke(any)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}