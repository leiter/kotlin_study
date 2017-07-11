package com.android.marco.firstdecision.utils

/**
 * Created by gen on 22.11.15.

 */
class FlipperCounter {

    constructor() {
        count -= 1
    }

    constructor(b: Boolean) {
        if (b) {
            count += 1
        }
    }

    companion object {
        private var count = 0
    }

}
