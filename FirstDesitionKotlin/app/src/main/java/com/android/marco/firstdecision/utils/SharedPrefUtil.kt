package com.android.marco.firstdecision.utils

import android.content.Context
import android.util.Log
import com.android.marco.firstdecision.R
import com.android.marco.firstdecision.dataModels.ThingToDo
import java.util.*

/**
 * Created by gen on 01.07.15.

 */

class SharedPrefUtil {

    fun saveThings(mContext: Context, list: ArrayList<ThingToDo>): Boolean {
        val prefs = mContext.getSharedPreferences("things", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val boolMark = "bool"
        var result = ""
//        for (i in list.size - 1 downTo 1) {   //todo
//            editor.putBoolean(boolMark + i, list[i].checked)
//            result = "," + list[i].named_thing + result
//        }
        result = list[0].named_thing + result
        editor.putString("thing_titles", result)
        Log.e("sdfsddfasdf", result)
        return editor.commit()
        //        return true;
    }


    fun loadThings(mContext: Context): ArrayList<ThingToDo> {
        val result = ArrayList<ThingToDo>()
        //        SharedPreferences prefs = mContext.getSharedPreferences("things", Context.MODE_PRIVATE);
        val res = mContext.resources
        val start = res.getStringArray(R.array.things_evening)
        for (aStart in start) {
            result.add(ThingToDo(aStart, true))
        }
        return result
    }
}
