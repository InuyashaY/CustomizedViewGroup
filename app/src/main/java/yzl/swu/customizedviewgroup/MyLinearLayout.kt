package yzl.swu.customizedviewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MyLinearLayout: ViewGroup {
    var menuItem:MenuItem = MenuItem(R.drawable.cs,"ccc")
    //代码创建
    constructor(context: Context) : super(context) {
    }
    //xml创建
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){

        context.obtainStyledAttributes(attrs,R.styleable.MyLinearLayout).apply {
            menuItem.resourceId = getResourceId(R.styleable.MyLinearLayout_iconResource,R.drawable.bjx)
            menuItem.title = getString(R.styleable.MyLinearLayout_menuTitle).toString()
        }

    }
//     private fun initViews(){
//         //设置方向
//         orientation = VERTICAL
//         gravity = Gravity.CENTER
//         ImageView(context).apply {
//             setImageResource(menuItem.resourceId)
//             layoutParams = LayoutParams(100,100).apply { bottomMargin = 10 }
//             addView(this,layoutParams)
//         }
//         TextView(context).apply {
//             text = menuItem.title
//             layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
//             addView(this,layoutParams)
//         }
//     }

    class MenuItem(var resourceId:Int,var title:String)
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        
    }
}