package yzl.swu.customizedviewgroup.commons

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import yzl.swu.customizedviewgroup.R

class MyTabBarController:LinearLayout {
    private var itemNumber:Int = 0
    private var currentItemIndex = 0    //当前item的索引
        set(value) {
            field = value
            items[value].mSelected = true
        }
    var models = mutableListOf<TabBarItemModel>()
        set(value) {
            field = value
            initViews()
        }
    private val items = mutableListOf<MyTabBarItem>()
    constructor(context: Context):super(context){
        //initViews()
    }
    constructor(context: Context,attrs:AttributeSet): super(context,attrs){
        parseAttrs(attrs)
        //initViews()
    }

    private fun parseAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs,
            R.styleable.MyTabBarController
        )
        itemNumber = typedArray.getInteger(R.styleable.MyTabBarController_itemNumber,1)
        typedArray.recycle()
    }

    private fun initViews(){
        for ((i,item) in models.withIndex()){
            val model = models[i]
            MyTabBarItem(context).apply {
                normalIconId = model.normalIconId
                selectedIconId = model.selectedIconId
                titleText = model.titleText
                highlightColor = model.highlightColor
                index = i
            }.also {
                val lp = LayoutParams(LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                lp.weight = 1F
                lp.gravity = Gravity.CENTER_VERTICAL
                addView(it,lp)
                items.add(it)

                //点击事件回调
                it.selectCallBack = {index ->
                    //还原之前选中栏目的状态
                    items[currentItemIndex].mSelected = false
                    currentItemIndex = index
                    it.iconImageView?.animate()
                        ?.rotationBy(360f)
                        ?.setDuration(2000)
                        ?.start()

                }
            }
        }
    }
}