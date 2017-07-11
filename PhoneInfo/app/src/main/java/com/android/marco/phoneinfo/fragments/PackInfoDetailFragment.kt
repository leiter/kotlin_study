package com.android.marco.phoneinfo.fragments

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.marco.phoneinfo.R
import java.util.*

class PackInfoDetailFragment : Fragment() {
    private var mItem: ApplicationInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments.containsKey(ARG_ITEM_ID)) {
            mItem = arguments.getParcelable<ApplicationInfo>(ARG_ITEM_ID)
            val activity = activity
            val appBarLayout = activity.findViewById(R.id.toolbar_layout) as CollapsingToolbarLayout
            appBarLayout.title = mItem!!.loadLabel(getActivity().packageManager)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.packinfo_detail, container, false)
        if (mItem != null) {

            Log.d(TAG, mItem!!.packageName)
            val packageInfo: PackageInfo
            try {
                packageInfo = activity.packageManager
                        .getPackageInfo(mItem!!.packageName, INFO_TYPES)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return rootView
            }
            (rootView.findViewById(R.id.packinfo_detail) as TextView).text = formatText(packageInfo)
        }
        return rootView
    }


    private fun formatText(pi: PackageInfo): String {
        var result = "\n"
        result += "Package name:       " + pi.packageName + "\n\n"
        result += "Shared user ID:    " + pi.sharedUserId + "\n\n"
        result += "Requested permissions: " +
                Arrays.deepToString(pi.requestedPermissions).replace("[", "\n")
                        .replace("]", "\n")
                        .replace(", ", "\n")
                        .replace("android.permission.", "") + "\n\n"
        return result

    }

    companion object {
        val TAG = PackInfoDetailFragment::class.java.simpleName
        val ARG_ITEM_ID = "item_id"
        private val INFO_TYPES = PackageManager.GET_ACTIVITIES or
                PackageManager.GET_META_DATA or
                PackageManager.GET_PROVIDERS or
                PackageManager.GET_SERVICES or
                PackageManager.GET_PROVIDERS or
                PackageManager.GET_PERMISSIONS
    }


}
