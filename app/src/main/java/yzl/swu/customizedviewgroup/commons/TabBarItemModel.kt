package yzl.swu.customizedviewgroup.commons

import android.graphics.Color

data class TabBarItemModel(
    var normalIconId:Int,
    var selectedIconId:Int,
    var titleText:String,
    var highlightColor:Int = Color.BLACK,
    var mSelected:Boolean = false
)