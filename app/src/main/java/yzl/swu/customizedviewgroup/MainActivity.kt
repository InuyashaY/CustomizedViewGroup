package yzl.swu.customizedviewgroup

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mTabBar.models = mutableListOf(TabBarItemModel(
            R.drawable.books,
            R.drawable.s,
            "我的书架",
            Color.BLUE,
            false
        ),TabBarItemModel(
            R.drawable.bookstore,
            R.drawable.bookstoreselect,
            "书城",
            Color.BLUE,
            false
        ),TabBarItemModel(
            R.drawable.me,
            R.drawable.meselect,
            "我的",
            Color.BLUE,
            false
        ))
    }
}