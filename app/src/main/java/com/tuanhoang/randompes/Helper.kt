package com.tuanhoang.randompes

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

const val LAYOUT_VERSION_1 = 1
const val LAYOUT_VERSION_2 = 2

private const val KEY_LAYOUT_VERSION = "KEY_LAYOUT_VERSION"

private fun getSharePreference(context: Context): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(context)
}

fun getLayoutVersion(context: Context): Int {
    val sp = getSharePreference(context)
    return sp.getInt(KEY_LAYOUT_VERSION, LAYOUT_VERSION_1)
}

fun saveLayoutVersion(context: Context, version: Int) {
    val editor = getSharePreference(context).edit()
    editor.putInt(KEY_LAYOUT_VERSION, version)
    editor.apply()
}