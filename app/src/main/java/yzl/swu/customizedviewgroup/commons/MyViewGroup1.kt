package yzl.swu.customizedviewgroup.commons

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import androidx.core.view.children
import yzl.swu.customizedviewgroup.R

class MyViewGroup1 : ViewGroup {
    val space = dp2px(10)
    var spanCount = 0

    constructor(context: Context) : super(context){}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        context.obtainStyledAttributes(attrs, R.styleable.MyViewGroup1).apply {
            spanCount = getInteger(R.styleable.MyViewGroup1_spanCount,2)
        }
    }

    //测量
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //测量自己的 size mode
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        when(MeasureSpec.getMode(widthMeasureSpec)){
            MeasureSpec.AT_MOST -> Log.v("yzll","AT_MOST")
            MeasureSpec.EXACTLY -> Log.v("yzll","EXACTLY")
            MeasureSpec.UNSPECIFIED -> Log.v("yzll","UNSPECIFIED")
        }

        //测量父容器的size
        val pWidth = MeasureSpec.getSize(widthMeasureSpec)
        val pHeight = MeasureSpec.getSize(heightMeasureSpec)

        //设置child尺寸
        var childWidth = 0
        var childHeight = 0
        if(childCount < spanCount){
            childWidth = (pWidth - (spanCount+1)*space)/spanCount
            childHeight = pHeight - 2*space
        }else{
            childWidth = (pWidth - (spanCount+1)*space)/spanCount
            val hSpanCount = (childCount+spanCount-1)/spanCount
            childHeight = (pHeight - (1+hSpanCount)*space) / hSpanCount
        }

        //设置限制条件
        val cWSpec = MeasureSpec.makeMeasureSpec(childWidth,MeasureSpec.EXACTLY)
        val cHSpec = MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY)

        //测量子控件
        for (child in children){
            child.measure(cWSpec,cHSpec)
        }
    }


    //布局
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index,child) in children.withIndex()){
            child.apply {
                val left = (index % spanCount)*(space+measuredWidth)+space
                val top = index/spanCount*(space+child.measuredHeight)+space
                layout(left,top,left+child.measuredWidth,top+child.measuredHeight)
            }
        }
    }

    fun dp2px(dp:Int):Int{
        return (context.resources.displayMetrics.density*dp).toInt()
    }
}