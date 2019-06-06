package com.tuanhoang.randompes

import android.content.Context
import android.graphics.Color
import org.json.JSONArray
import org.json.JSONObject
import rubikstudio.library.model.LuckyItem


fun genListData(context: Context): ArrayList<LuckyItem> {
    val rawData = context.resources.openRawResource(R.raw.data).bufferedReader().use { it.readText() }
    val jsonData = JSONArray(rawData)
    val output = ArrayList<LuckyItem>()
    for (i in 0..(jsonData.length() - 1)) {
        val jsonObject = jsonData[i] as JSONObject
        val luckyItem = LuckyItem()
        luckyItem.topText = jsonObject.getString("name")
        luckyItem.icon = context.resources.getIdentifier(jsonObject.getString("icon"), "drawable", context.packageName)
        luckyItem.color = Color.parseColor(jsonObject.getString("color"))

        output.add(luckyItem)
    }

    return output
}