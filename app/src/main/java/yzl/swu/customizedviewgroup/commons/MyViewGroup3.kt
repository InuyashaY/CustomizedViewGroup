package yzl.swu.customizedviewgroup.commons

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class MyViewGroup3 : ViewGroup {
    private val space = dp2px(10)
    //所有View
    private var allViews = mutableListOf<List<View>>()
    private var allLineHeights = mutableListOf<Int>()
    private var isMeasured = false

    constructor(context: Context?) : super(context){}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //父容器  需要测量的结果
        var resultWidth = space
        var resultHeight = space
        //父容器measureSpec
        val parentWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val parentWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        val parentHeightSize = MeasureSpec.getSize(heightMeasureSpec)
        measureChildren(widthMeasureSpec,heightMeasureSpec)
        if (!isMeasured){
            isMeasured = true
            //测量子控件
            //measureChild(getChildAt(0),widthMeasureSpec,heightMeasureSpec)
            //measureChildWithMargins(getChildAt(0),widthMeasureSpec,0,heightMeasureSpec,0)
            var currentLineViews = mutableListOf<View>()
            var currentWidth = space
            var currentHeight = 0
            for (i in 0 until childCount){
                val child = getChildAt(i)
                val lp = child.layoutParams
                val widthChildSpec = getChildMeasureSpec(widthMeasureSpec,2*space,lp.width)
                val heightChildSpec = getChildMeasureSpec(heightMeasureSpec,2*space,lp.height)
                child.measure(widthChildSpec,heightChildSpec)

                if (currentWidth+child.measuredWidth+space < parentWidthSize){
                    //不换行
                    currentWidth += child.measuredWidth+space
                    currentHeight = Math.max(currentHeight,child.measuredHeight)
                    currentLineViews.add(child)
                }else{
                    //换行
                    //添加数据
                    allViews.add(currentLineViews)
                    allLineHeights.add(currentHeight)
                    //测量值更改
                    resultHeight += currentHeight+space
                    resultWidth = Math.max(resultWidth,currentWidth)
                    //重置
                    currentLineViews = mutableListOf()
                    currentWidth = space+child.measuredWidth
                    currentHeight = space+child.measuredHeight
                    currentLineViews.add(child)
                }
            }
            if (currentLineViews.size > 0){
                allViews.add(currentLineViews)
                allLineHeights.add(currentHeight)
                //测量值更改
                resultHeight += currentHeight+space
                resultWidth = Math.max(resultWidth,currentWidth)
            }
        }



        // 设置最终宽高
        setMeasuredDimension(resultWidth,resultHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var lastLeft = space
        var lastTop = space
        for ((i,lineList) in allViews.withIndex()){
            for ((index,view) in lineList.withIndex()){
                view.apply{
                    layout(lastLeft,lastTop,lastLeft+measuredWidth,lastTop+measuredHeight)
                }
                lastLeft += space+view.measuredWidth
            }
            lastLeft = space
            lastTop += allLineHeights[i]+space
        }
    }

    fun dp2px(dp:Int):Int{
        return (context.resources.displayMetrics.density*dp).toInt()
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context,attrs)
    }
}