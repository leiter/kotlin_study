package com.android.marco.firstdecision.dataModels

import java.io.Serializable
import java.util.*

/**
 * Created by gen on 02.06.15.

 */
class ThingsListModel : Serializable {

    var listTitle: String? = null
    var things = ArrayList<ThingToDo>()

    fun add(t: ThingToDo) {
        things.add(t)
    }

    operator fun get(index: Int): ThingToDo {
        return things[index]
    }

    fun size(): Int {
        return things.size
    }

    fun addAtPosition(pos: Int, t: ThingToDo) {
        things.add(pos, t)

    }
}

