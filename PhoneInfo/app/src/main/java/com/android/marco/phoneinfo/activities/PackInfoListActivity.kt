package com.android.marco.phoneinfo.activities

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.marco.phoneinfo.R
import com.android.marco.phoneinfo.data.GetInfos
import com.android.marco.phoneinfo.fragments.PackInfoDetailFragment


class PackInfoListActivity : AppCompatActivity() {

    private var mTwoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_packinfo_list)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val recyclerView = findViewById(R.id.packinfo_list)!!
        setupRecyclerView(recyclerView as RecyclerView)

        if (findViewById(R.id.packinfo_detail_container) != null) {
            mTwoPane = true
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(GetInfos.allApplicationInfos(this))
    }

    inner class SimpleItemRecyclerViewAdapter(private val mValues: List<ApplicationInfo>) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.packinfo_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mItem = mValues[position]

            holder.mAppLogo.setImageDrawable(mValues[position].loadIcon(packageManager))
            holder.mIdView.text = mValues[position].loadLabel(packageManager)
            holder.mContentView.text = mValues[position].packageName

            holder.mView.setOnClickListener { v ->
                if (mTwoPane) {
                    val arguments = Bundle()
                    arguments.putParcelable(PackInfoDetailFragment.ARG_ITEM_ID, holder.mItem)
                    val fragment = PackInfoDetailFragment()
                    fragment.arguments = arguments
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.packinfo_detail_container, fragment)
                            .commit()
                } else {
                    val context = v.context
                    val intent = Intent(context, PackInfoDetailActivity::class.java)
                    intent.putExtra(PackInfoDetailFragment.ARG_ITEM_ID, holder.mItem)
                    context.startActivity(intent)
                }
            }
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
            val mAppLogo: ImageView = mView.findViewById(R.id.app_icon) as ImageView
            val mIdView: TextView = mView.findViewById(R.id.id) as TextView
            val mContentView: TextView = mView.findViewById(R.id.content) as TextView
            var mItem: ApplicationInfo? = null

            override fun toString(): String {
                return super.toString() + " '" + mContentView.text + "'"
            }
        }
    }
}
