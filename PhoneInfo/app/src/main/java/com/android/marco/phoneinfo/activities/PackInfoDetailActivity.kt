package com.android.marco.phoneinfo.activities

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.android.marco.phoneinfo.R
import com.android.marco.phoneinfo.fragments.PackInfoDetailFragment

class PackInfoDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_packinfo_detail)
        val toolbar = findViewById(R.id.detail_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val appInfo = intent.getParcelableExtra<ApplicationInfo>(PackInfoDetailFragment.ARG_ITEM_ID)
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setImageDrawable(appInfo.loadIcon(packageManager))
        fab.setOnClickListener {
            val intent = packageManager.getLaunchIntentForPackage(appInfo.packageName)
            if (intent != null) {
                startActivity(intent)
            } else {
                Toast.makeText(baseContext,
                        "Could not start an activity!", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val arguments = Bundle()
            arguments.putParcelable(PackInfoDetailFragment.ARG_ITEM_ID, appInfo)
            val fragment = PackInfoDetailFragment()
            fragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                    .add(R.id.packinfo_detail_container, fragment)
                    .commit()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, PackInfoListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
