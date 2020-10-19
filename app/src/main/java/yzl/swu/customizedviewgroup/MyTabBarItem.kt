package yzl.swu.customizedviewgroup

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MyTabBarItem: LinearLayout {
    //
    var selectCallBack:((Int)->Unit)? = null
    //正常图片
    var normalIconId:Int = 0
    //当前位置
    var index:Int = -1

    //选中图片
    var selectedIconId:Int = 0
    //标题
    var titleText:String = "null"
    //高亮色
    var highlightColor:Int = 0
        set(value) {
            field = value
            updateUI()
        }
    //记录是否选中
    var mSelected:Boolean = false
        set(value) {
            field = value
            updateUI()
        }
    //图标
    var iconImageView: ImageView? = null
    //标题
    private  var titleTextView: TextView? = null

    constructor(context: Context):super(context){
        initViews()
    }
    constructor(context: Context,attrs:AttributeSet):super(context,attrs){
        parseAttr(attrs)
        initViews()

    }

    //解析属性
    private fun parseAttr(attrs: AttributeSet){
        //将此类定义的属性从attrs里面解析出来
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyTabBarItem)
        //解析每一个属性
        normalIconId = typedArray.getResourceId(R.styleable.MyTabBarItem_normalIcon,R.drawable.bjx)
        selectedIconId = typedArray.getResourceId(R.styleable.MyTabBarItem_selectedIcon,R.drawable.bjx)
        titleText = typedArray.getString(R.styleable.MyTabBarItem_titleText).toString()
        highlightColor = typedArray.getColor(R.styleable.MyTabBarItem_highLightTextColor,Color.BLACK)
        mSelected = typedArray.getBoolean(R.styleable.MyTabBarItem_defaultSelected,false)
        typedArray.recycle()
    }

    //添加子视图
    private fun initViews(){
        //设置方向
        orientation = VERTICAL
        gravity = Gravity.CENTER

        //添加图片
        iconImageView = ImageView(context).apply {
            //布局参数
            var lp = LayoutParams(dp2px(32),dp2px(32))
            //图片资源
            setImageResource(R.drawable.books)
            //添加视图
            addView(this,lp)
        }


        //添加文字
        titleTextView = TextView(context).apply {
            val lp = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
            //设置文字
            text = titleText
            //添加视图
            addView(this,lp)
        }

        //更新UI
        updateUI()
    }

    //dp转px
    private fun dp2px(dp:Int):Int = (dp*context.resources.displayMetrics.density).toInt()


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN){
            if (!mSelected) mSelected = true
            //回调数据
            selectCallBack?.let { it(index) }
        }
        return true
    }

    //更新UI
    private fun updateUI(){
        if(mSelected){
            iconImageView?.setImageResource(selectedIconId)
            titleTextView?.setTextColor(highlightColor)
        }else{
            iconImageView?.setImageResource(normalIconId)
            titleTextView?.setTextColor(Color.BLACK)
        }
        titleTextView?.text = titleText
    }
}