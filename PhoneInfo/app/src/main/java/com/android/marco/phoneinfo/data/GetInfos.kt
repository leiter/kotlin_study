package com.android.marco.phoneinfo.data

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.util.Log
import java.util.*

/**
 * Created by gen on 07.07.17.

 */

object GetInfos {

    internal var itemsPackage = HashMap<String, PackageInfo>()

    fun getPackageInfos(context: Context): List<PackageInfo> {
        //        Intent mIntent = new Intent(Intent.ACTION_ALL_APPS, null);
        val mInfos = ArrayList<PackageInfo>()

        try {
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_ACTIVITIES))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_RECEIVERS))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_SERVICES))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_PROVIDERS))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_INSTRUMENTATION))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_INTENT_FILTERS))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_SIGNATURES))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_META_DATA))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_GIDS))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_DISABLED_COMPONENTS))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_SHARED_LIBRARY_FILES))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_URI_PERMISSION_PATTERNS))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_PERMISSIONS))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_CONFIGURATIONS))
            mInfos.addAll(getPackageManager(context)
                    .getInstalledPackages(PackageManager.GET_DISABLED_UNTIL_USED_COMPONENTS))
        } catch (e: Throwable) {
            Log.d("program init", "Throwable: " + e)
        }

        return mInfos
    }


    fun allApplicationInfos(context: Context): List<ApplicationInfo> {
        return getPackageManager(context).getInstalledApplications(PackageManager.GET_META_DATA)
    }

    fun allResolveInfos(context: Context): List<ResolveInfo> {
        return getPackageManager(context).queryIntentActivities(
                Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER), 0)
    }

    private fun getPackageManager(context: Context): PackageManager {
        return context.packageManager
    }

}
