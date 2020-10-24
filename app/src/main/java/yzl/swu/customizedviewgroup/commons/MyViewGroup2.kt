package yzl.swu.customizedviewgroup.commons

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children

class MyViewGroup2 : ViewGroup {
    val space = dp2px(10)

    constructor(context:Context):super(context){}
    constructor(context: Context,attrs:AttributeSet):super(context,attrs){}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val pWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val pHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        var pWidth = 0
        var pHeight = 0
        measureChildren(widthMeasureSpec,heightMeasureSpec)
        if (pWidthMode == MeasureSpec.AT_MOST){

            if (childCount < 2){
                pWidth = space*2 + getChildAt(0).measuredWidth
            }else{
                pWidth = space*3 + getChildAt(0).measuredWidth*2
            }
            pHeight = (childCount+1)/2*(getChildAt(0).measuredHeight+space)+space
        }

        setMeasuredDimension(pWidth,pHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        for ((index,child) in children.withIndex()){
            val left = space+index%2*(space+child.measuredWidth)
            val top = space+index/2*(space+child.measuredHeight)
            child.apply {
                layout(left,top,left+measuredWidth,top+measuredHeight)
            }
        }
    }


    fun dp2px(dp:Int):Int{
        return (context.resources.displayMetrics.density*dp).toInt()
    }
}