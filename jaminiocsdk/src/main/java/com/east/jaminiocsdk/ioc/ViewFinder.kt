package com.east.baselibrary.ioc

import android.app.Activity
import android.view.View

/**
 *  查找View的辅助类
 */
class ViewFinder private constructor() {

    var activity:Activity ?= null
    var view:View ?= null

    constructor(activity: Activity) :this(){
        this.activity = activity
    }
    constructor(view: View) : this(){
        this.view = view;
    }

    fun findViewById(id:Int):View{
        return if(activity!=null) activity!!.findViewById(id) else view!!.findViewById(id)
    }
}
