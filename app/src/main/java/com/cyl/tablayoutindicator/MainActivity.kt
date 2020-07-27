package com.cyl.tablayoutindicator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val titles = listOf("推荐", "视频"/*, "小视频", "新时代", "娱乐", "漫画", "时尚", "文化", "健康", "搞笑段子"*/)
    private var fragments = mutableListOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragments()
        initTabLayout()
    }

    private fun initTabLayout() {
        val adapter =
            ViewPagerAdapter(fm = supportFragmentManager, fragments = fragments, titles = titles)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = titles.size

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPager.setCurrentItem(tab.position, true)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                tabLayout.getTabAt(position)?.select()
            }
        })
    }

    private fun loadFragments() {
        titles.forEach {
            fragments.add(ContentFragment.newInstance(it))
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }
        tabLayout.setupWithViewPager(viewPager)
    }
}