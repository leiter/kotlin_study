package com.android.marco.firstdecision.activities

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import com.android.marco.firstdecision.R
import com.android.marco.firstdecision.adapters.ListThingsAdapter
import com.android.marco.firstdecision.dataModels.ThingToDo
import com.android.marco.firstdecision.fragments.OneOfManyFragment
import com.android.marco.firstdecision.helpers.ListDataHelper
import com.android.marco.firstdecision.utils.AnimationUtil
import com.android.marco.firstdecision.utils.SharedPrefUtil
import com.android.marco.firstdecision.views.BubbleButton
import com.android.marco.firstdecision.widgets.AddDialog
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val THREE_FABS = 1
    private val TWO_FABS = 0
    var listData = ArrayList<ThingToDo>()
    private var resultButton: Button? = null
    private var addThingDialog: AddDialog? = null
    private var showTrash = false
    private var CURRENT_FABS_STATE = TWO_FABS
//    private var layoutParams: RelativeLayout.LayoutParams? = null
    private var mainContainer: RelativeLayout? = null
//    private var myType: Typeface? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportActionBar != null) {
            val a = supportActionBar
            a!!.setDisplayShowHomeEnabled(true)
            a.setDisplayShowCustomEnabled(true)
            a.setCustomView(R.layout.actionbar_background)
            val t = a.customView.findViewById(R.id.ab_title) as TextView
            t.text = "Am Abend ins "
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, OneOfManyFragment())
                    .commit()
        }
        setListData()

        findViewById(R.id.myFAB_right).setOnLongClickListener { v ->
            startFlipping(v)
            true
        }

        resultButton = findViewById(R.id.btn_ma_result) as Button
        mainContainer = findViewById(R.id.container) as RelativeLayout

//        myType = Typeface.createFromAsset(assets, "fonts/An_Original_Font_By_Davi.ttf")
        addThingDialog = AddDialog(this)
    }


    private fun startFlipping(view: View) {
        val i = Intent(this, YesOrNoActivity::class.java)
        val optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, view.width, view.height)
        startActivity(i, optionsCompat.toBundle())
    }

    override fun onResume() {
        super.onResume()
        (findViewById(R.id.btn_ma_result) as Button).setTextAppearance(this, R.style.AppBubbles)
        (findViewById(R.id.btn_ma_result) as Button).typeface = Typeface.createFromAsset(assets, "fonts/James_Fajardo.ttf")
        AnimationUtil.slideInOut(this, findViewById(R.id.container_rel), true)
        showMiddleFAB(false)
    }

    fun setListData() {
        val s = SharedPrefUtil()
        listData = s.loadThings(this)

    }

    fun animate_now() {
        if (resultButton!!.visibility == View.VISIBLE) {
            resultButton!!.visibility = View.INVISIBLE
        }
        val bubbleBag = ArrayList<Button>()
        var j = 0

        val myType = Typeface.createFromAsset(assets, "fonts/An_Original_Font_By_Davi.ttf")
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.addRule(RelativeLayout.ABOVE, R.id.my_center)
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)

        for (i in 0..listData.size * 4 - 1) {
            if (listData[j].checked) {
                val b = BubbleButton(this, listData[j].named_thing, myType, layoutParams)
                bubbleBag.add(b)
                mainContainer!!.addView(b)
                j++
                if (j > listData.size - 1) {
                    j = 0
                }
            } else if (!listData[j].checked) {
                j++
                if (j > listData.size - 1) {
                    j = 0
                }
            }
        }
        AnimationUtil.AnimateBubbleSize(bubbleBag, resultButton as Button)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val prefs = getSharedPreferences("things_5", Context.MODE_PRIVATE)
        val helpe = ListDataHelper()

        when (id) {

            R.id.action_settings -> {
                try {
                    val e = prefs.edit()
                    e.putString("myListData", helpe.serialize(listData))
                    e.apply()
                } catch (e: IOException) {
                    Log.e(TAG, "IOExeption on action_settingID" + e.message)
                }

                return true
            }
        }

        if (id == R.id.action_use_touch_gestures) {
            try {
                val l = findViewById(R.id.lv_oneofmany_things) as ListView
                listData = helpe.deserialize(prefs.getString("myListData", null)) as ArrayList<ThingToDo>
                l.adapter = ListThingsAdapter(this, R.id.lv_oneofmany_things, listData, showTrash)
            } catch (e: IOException) {
                Log.e(TAG, "iiiioooo" + e.message)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        if (AnimationUtil.isInAnimation) {
            animate_now()
        }
        super.onConfigurationChanged(newConfig)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bnt_ma_add -> addThing()
            R.id.show_delete_items -> {
                val l = findViewById(R.id.lv_oneofmany_things) as ListView
                l.adapter = ListThingsAdapter(this, R.id.lv_oneofmany_things, listData, showTrash)
                showTrash = !showTrash
            }
            R.id.ib_oneofmany_edit -> {
            }
            R.id.myFAB_right -> {
                if (findViewById(R.id.container_rel).visibility == View.VISIBLE && !AnimationUtil.listIsSliding) {
                    showMiddleFAB(false)
                    AnimationUtil.slideInOut(this, findViewById(R.id.container_rel), true)
                }
                animate_now()
            }

            R.id.myFAB_left0 -> if (findViewById(R.id.container_rel).visibility > View.VISIBLE) {
                showMiddleFAB(true)
                AnimationUtil.slideInOut(this, findViewById(R.id.container_rel), false)
                resultButton!!.visibility = View.GONE
            }

            R.id.myFAB_middle -> addThingDialog!!.show()
        }
    }

    private fun showMiddleFAB(showMiddleFAB: Boolean) {
        val midFAB = findViewById(R.id.myFAB_left0) as FloatingActionButton
        if (showMiddleFAB) {
            findViewById(R.id.myFAB_middle).visibility = View.VISIBLE
            midFAB.setImageResource(R.drawable.ic_menu_add)
            CURRENT_FABS_STATE = THREE_FABS
        } else {
            findViewById(R.id.myFAB_middle).visibility = View.GONE
            midFAB.setImageResource(android.R.drawable.ic_menu_mylocation)
            CURRENT_FABS_STATE = TWO_FABS
        }
    }

    fun addThing() {
        val lv = findViewById(R.id.lv_oneofmany_things) as ListView
        listData.add(0, ThingToDo(addThingDialog!!.text, true))
        val adapter = lv.adapter as ListThingsAdapter
        adapter.notifyDataSetChanged()
        addThingDialog!!.dismiss()
    }

    companion object {

        protected var TAG = MainActivity::class.java.simpleName
    }
}
