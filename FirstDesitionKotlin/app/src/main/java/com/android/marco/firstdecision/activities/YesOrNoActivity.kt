package com.android.marco.firstdecision.activities

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils

import com.android.marco.firstdecision.R
import com.android.marco.firstdecision.utils.AnimationUtil


class YesOrNoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yes_or_no)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
        findViewById(R.id.yn_fab).setOnLongClickListener {
            exit()
            true
        }

        flip()
    }

    fun go_flip(v: View) {
        flip()
    }

    fun flip() {
        AnimationUtil.FlipImageView(this,
                findViewById(R.id.flipper_1),
                findViewById(R.id.flipper_2))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //        getMenuInflater().inflate(R.menu.menu_yes_or_no, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            //            NavUtils.navigateUpFromSameTask(this);
            exit()
            //            finish();
            return true
        }
        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        exit()
        //        super.onBackPressed();
    }

    private fun exit() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.present_final_bubble)
        val b = findViewById(R.id.container)
        b.startAnimation(animation)
        val handler = Handler()
        handler.postDelayed({
            b.visibility = View.GONE
            b.clearAnimation()
            finish()
        }, 1500)
    }

}
