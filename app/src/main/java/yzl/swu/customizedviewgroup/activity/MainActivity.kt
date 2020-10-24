package yzl.swu.customizedviewgroup.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import yzl.swu.customizedviewgroup.R

class MainActivity : AppCompatActivity() {
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this

        val imageList = mutableListOf<Int>()
        val titleList = mutableListOf<String>()
        for (i in 0 until 10){
            imageList.add(R.drawable.tgsw)
            titleList.add("this is$i")
        }


//        mTabBar.models = mutableListOf(TabBarItemModel(
//            R.drawable.books,
//            R.drawable.s,
//            "我的书架",
//            Color.BLUE,
//            false
//        ),TabBarItemModel(
//            R.drawable.bookstore,
//            R.drawable.bookstoreselect,
//            "书城",
//            Color.BLUE,
//            false
//        ),TabBarItemModel(
//            R.drawable.me,
//            R.drawable.meselect,
//            "我的",
//            Color.BLUE,
//            false
//        ))
    }


    fun dp2px(dp:Int):Int{
        return (context.resources.displayMetrics.density*dp).toInt()
    }
}