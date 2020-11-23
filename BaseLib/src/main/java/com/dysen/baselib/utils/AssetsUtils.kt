package com.dysen.baselib.utils

import android.content.Context
import com.dysen.baselib.data.entity.CountryData
import com.kcloudchina.member.app.utils.JsonUtils.getJsonObject
import com.kcloudchina.member.app.utils.JsonUtils.parseList
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * @package com.dysen.qrcode.utils
 * @email dy.sen@qq.com
 * created by dysen on 2019-11-20 - 16:24
 * @info
 */
object AssetsUtils {
    /**
     * 读取assets本地json
     *
     * @param fileName
     * @param context
     * @return
     */
    fun getJson(
        fileName: String?,
        context: Context
    ): String { //将json数据变成字符串
        val stringBuilder = StringBuilder()
        try { //获取assets资源管理器
            val assetManager = context.assets
            //通过管理器打开文件并读取
            val bf = BufferedReader(
                InputStreamReader(
                    assetManager.open(fileName!!)
                )
            )
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }

//    fun getCountrys(context: Context): MutableList<CountryData> {
//        val json = getJson("country.json", context)
//        val jsonObject: JSONObject? = null
//        var countryDatas: MutableList<CountryData> = mutableListOf()
//        try {
//            countryDatas = parseList(
//                getJsonObject(json)!!.optString("countrys"),
//                CountryData::class.java) as MutableList<CountryData>
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//        return countryDatas?: mutableListOf()
//    }

    fun getListData(context: Context, fileName: String, name:String): MutableList<Any> {
        val json = getJson(fileName, context)
        val jsonObject: JSONObject? = null
        var countryDatas: MutableList<Any> = mutableListOf()
        try {
            countryDatas = parseList(
                getJsonObject(json)!!.optString(name),
                CountryData::class.java) as MutableList<Any>
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return countryDatas?: mutableListOf()
    }

}