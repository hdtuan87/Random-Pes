package com.tuanhoang.randompes

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.btnSpin
import kotlinx.android.synthetic.main.activity_main.imClub1
import kotlinx.android.synthetic.main.activity_main.imClub2
import kotlinx.android.synthetic.main.activity_main.lw
import kotlinx.android.synthetic.main.activity_main.tvClub1
import kotlinx.android.synthetic.main.activity_main.tvClub2
import rubikstudio.library.LuckyWheelView
import rubikstudio.library.model.LuckyItem
import java.util.Random

class MainActivity : AppCompatActivity() {

    private var layoutVersion: Int = LAYOUT_VERSION_2
    private var club1: LuckyItem? = null
    private var club2: LuckyItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutVersion = getLayoutVersion(this)
        when (layoutVersion) {
            LAYOUT_VERSION_2 -> setContentView(R.layout.activity_main_v2)
            else -> setContentView(R.layout.activity_main)
        }

        lw.setOnLongClickListener {
            if (layoutVersion == LAYOUT_VERSION_1) {
                saveLayoutVersion(this, LAYOUT_VERSION_2)
            } else {
                saveLayoutVersion(this, LAYOUT_VERSION_1)
            }

            recreate()

            true
        }

        val data = genListData(this)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        val luckyWheel = genLuckyWheel(width)
        lw.addView(luckyWheel)

        luckyWheel.setData(data)
        luckyWheel.isClickable = false
        luckyWheel.setLuckyRoundItemSelectedListener {
            showFlag()
            imClub1.isClickable = true
            imClub2.isClickable = true
        }

        btnSpin.setOnClickListener {
            club1 = null
            club2 = null
            showFlag()
        }

        imClub1.setOnClickListener {
            imClub1.isClickable = false
            imClub2.isClickable = false
            val index = random(data)
            club1 = data[index]
            luckyWheel.startLuckyWheelWithTargetIndex(index)
        }

        imClub2.setOnClickListener {
            imClub1.isClickable = false
            imClub2.isClickable = false
            val index = random(data)
            club2 = data[index]
            luckyWheel.startLuckyWheelWithTargetIndex(index)
        }
    }

    private fun random(data: ArrayList<LuckyItem>): Int {
        val index = Random().nextInt(data.size)
        val item = data[index]
        if (club1 != null) {
            if (item == club1) {
                return random(data)
            }
        }

        if (club2 != null) {
            if (item == club2) {
                return random(data)
            }
        }

        return index

    }

    private fun showFlag() {
        if (club1 == null) {
            imClub1.setImageResource(R.drawable.ic_empty)
            tvClub1.text = ""
        } else {
            imClub1.setImageResource(club1!!.icon)
            tvClub1.text = club1!!.topText
        }

        if (club2 == null) {
            imClub2.setImageResource(R.drawable.ic_empty)
            tvClub2.text = ""
        } else {
            imClub2.setImageResource(club2!!.icon)
            tvClub2.text = club2!!.topText
        }
    }

    private fun genLuckyWheel(width: Int): LuckyWheelView {

        return if (layoutVersion == LAYOUT_VERSION_1) {
            genLuckyWeelVersion1(width)
        } else {
            genLuckyWeelVersion2(width)
        }
    }

    private fun genLuckyWeelVersion1(width: Int): LuckyWheelView {
        val luckyWheel = LuckyWheelView(this)
        luckyWheel.setLuckyWheelCenterImage(
            ContextCompat.getDrawable(
                this,
                R.drawable.wheel_center
            )
        )
        luckyWheel.setLuckyWheelCursorImage(R.drawable.ic_down_arrow)

        luckyWheel.layoutParams = FrameLayout.LayoutParams(width, width)

        return luckyWheel
    }

    private fun genLuckyWeelVersion2(width: Int): LuckyWheelView {
        val luckyWheel = LuckyWheelView(this)
        luckyWheel.setLuckyWheelCenterImage(
            ContextCompat.getDrawable(
                this,
                R.drawable.wheel_center_2x
            )
        )
        val lp = FrameLayout.LayoutParams((width * 1.5).toInt(), (width * 1.5).toInt())
        lp.gravity = Gravity.CENTER_HORIZONTAL
        luckyWheel.layoutParams = lp

        return luckyWheel
    }
}
