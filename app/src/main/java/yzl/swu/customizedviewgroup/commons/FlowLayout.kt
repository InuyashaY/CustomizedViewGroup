package yzl.swu.customizedviewgroup.commons

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.*

class FlowLayout : ViewGroup {
    //记录子控件
    var list:ArrayList<ArrayList<View>> = ArrayList()
    var lineHeightList = ArrayList<Int>()

    var isMeasured = false
    //布局宽高 （自适应时为最大子控件宽高）
    private var layoutWidth = 0
    private var layoutHeight = 0

    constructor(context: Context?) : super(context){}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){}


    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context,attrs)
    }

    //测量子视图
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //测量模式
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        var sizeHeight = MeasureSpec.getSize(heightMeasureSpec)

        //计算所有的childView的宽和高
        measureChildren(widthMeasureSpec,heightMeasureSpec)


        if (!isMeasured){
            isMeasured = true
            //子空间宽高
            var cWidth = 0
            var cHeight = 0
            //存储当前行宽高
            var lineWidth = 0
            var lineHeight = 0
            //当前行
            var currentRowList = ArrayList<View>()
            for ((index,child) in children.withIndex()){
                //测量子控件 带margin
                measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,0)
                (layoutParams as MarginLayoutParams).apply {
                    cWidth = child.measuredWidth + leftMargin + rightMargin
                    cHeight = child.measuredHeight + topMargin + bottomMargin
                }

                //判断是否换行
                if (cWidth+lineWidth < sizeWidth-paddingLeft-paddingRight){
                    //不需要换行
                    lineWidth += cWidth
                    lineHeight = Math.max(cHeight,lineHeight)
                    currentRowList.add(child)
                }else{
                    //换行
                    list.add(currentRowList)
                    lineHeightList.add(lineHeight)
                    //累加行高
                    layoutHeight += lineHeight
                    layoutWidth = Math.max(layoutWidth,lineWidth)

                    //初始化换行后的
                    lineWidth = cWidth
                    lineHeight = cHeight
                    currentRowList = ArrayList<View>()
                    currentRowList.add(child)
                }
            }
            //最后一行 一定是不要换行的
            list.add(currentRowList)
            lineHeightList.add(lineHeight)
            layoutHeight+=lineHeight
        }



        if (widthMode == MeasureSpec.EXACTLY){
            //如果布局容器宽度模式确定   具体size或者match_parent
            //使用父视图建议高度
            layoutWidth = sizeWidth
            Log.v("yzll","EXACTLY")
        }else{
            Log.v("yzll","${widthMode==MeasureSpec.AT_MOST}")
        }

        //高度
        if (heightMode == MeasureSpec.EXACTLY){
            layoutHeight = sizeHeight
        }


        //保存测量结果    设置自己最终大小
        setMeasuredDimension(layoutWidth,layoutHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        var right = 0
        var top = 0
        var bottom = 0
        //记录上一个控件的位置
        var currentLeft = paddingLeft
        var currentTop = paddingTop

        for ((linePosition,listView) in list.withIndex()){
            for (childView in listView){
                (layoutParams as MarginLayoutParams).apply {
                    left = currentLeft + leftMargin
                    top = currentTop + topMargin
                    right = left + childView.measuredWidth
                    //如果控件超出父容器宽度   设为父容器宽度
                    if (right > r){
                        right = r - rightMargin
                    }
                    bottom = top + childView.measuredHeight

                    //布局
                    childView.layout(left,top,right,bottom)
                    currentLeft += leftMargin + childView.measuredWidth
                }
            }

            currentLeft = paddingLeft
            currentTop += lineHeightList[linePosition]
        }
    }

}