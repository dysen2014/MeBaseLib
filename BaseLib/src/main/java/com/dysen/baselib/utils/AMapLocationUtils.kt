package com.dysen.baselib.utils

import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.dysen.baselib.base.AppContext

/**
 * dysen.
 * dy.sen@qq.com    2020/6/30  14:27

 * Info：
 */
object AMapLocationUtils {

    /**
     * 默认搜索间隔, 单次定位的定位器
     */
    fun newLocationClient(context: Context?, once: Boolean, listener: AMapLocationListener): AMapLocationClient {
        val client = AMapLocationClient(context)
        client.setLocationOption(getDefaultOption(once))
        client.setLocationListener(listener)
        client.startLocation()
        return client
    }

    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     */
    private fun initLocation() {
        //初始化client
        val locationClient = AMapLocationClient(AppContext.appContext)
        val locationOption = getDefaultOption()
        //设置定位参数
        locationClient.setLocationOption(locationOption)
        // 设置定位监听
        locationClient.setLocationListener(locationListener)
        //启动定位
        locationClient.startLocation()
    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     */
    private fun getDefaultOption(once: Boolean=false): AMapLocationClientOption {
        val mOption = AMapLocationClientOption()
        mOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.isGpsFirst = false //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.httpTimeOut = 30000 //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.interval = 2000 //可选，设置定位间隔。默认为2秒
        mOption.isNeedAddress = true //可选，设置是否返回逆地理地址信息。默认是true
        mOption.isOnceLocation = once //可选，设置是否单次定位。默认是false
        mOption.isOnceLocationLatest = false //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP) //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.isSensorEnable = false //可选，设置是否使用传感器。默认是false
        mOption.isWifiScan = true //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.isLocationCacheEnable = true //可选，设置是否使用缓存定位，默认为true
        mOption.geoLanguage = AMapLocationClientOption.GeoLanguage.DEFAULT //可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption
    }

    /**
     * 定位监听
     */
    var locationListener = AMapLocationListener { location ->
        if (null != location) {
            val sb = StringBuffer()
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.errorCode == 0) {
                sb.append("""
    定位成功
    
    """.trimIndent())
                sb.append("""
    定位类型: ${location.locationType}
    
    """.trimIndent())
                sb.append("""
    经    度    : ${location.longitude}
    
    """.trimIndent())
                sb.append("""
    纬    度    : ${location.latitude}
    
    """.trimIndent())
                sb.append("""
    精    度    : ${location.accuracy}米
    
    """.trimIndent())
                sb.append("""
    提供者    : ${location.provider}
    
    """.trimIndent())
                sb.append("""
    速    度    : ${location.speed}米/秒
    
    """.trimIndent())
                sb.append("""
    角    度    : ${location.bearing}
    
    """.trimIndent())
                // 获取当前提供定位服务的卫星个数
                sb.append("""
    星    数    : ${location.satellites}
    
    """.trimIndent())
                sb.append("""
    国    家    : ${location.country}
    
    """.trimIndent())
                sb.append("""
    省            : ${location.province}
    
    """.trimIndent())
                sb.append("""
    市            : ${location.city}
    
    """.trimIndent())
                sb.append("""
    城市编码 : ${location.cityCode}
    
    """.trimIndent())
                sb.append("""
    区            : ${location.district}
    
    """.trimIndent())
                sb.append("""
    区域 码   : ${location.adCode}
    
    """.trimIndent())
                sb.append("""
    地    址    : ${location.address}
    
    """.trimIndent())
                sb.append("""
    兴趣点    : ${location.poiName}
    
    """.trimIndent())
                //定位完成的时间
                sb.append("""
    
    """.trimIndent())
            } else {
                //定位失败
                sb.append("""
    定位失败
    
    """.trimIndent())
                sb.append("""
    错误码:${location.errorCode}
    
    """.trimIndent())
                sb.append("""
    错误信息:${location.errorInfo}
    
    """.trimIndent())
                sb.append("""
    错误描述:${location.locationDetail}
    
    """.trimIndent())
            }
            sb.append("***定位质量报告***").append("\n")
            sb.append("* WIFI开关：").append(if (location.locationQualityReport.isWifiAble) "开启" else "关闭").append("\n")
            sb.append("* GPS星数：").append(location.locationQualityReport.gpsSatellites).append("\n")
            sb.append("* 网络类型：" + location.locationQualityReport.networkType).append("\n")
            sb.append("* 网络耗时：" + location.locationQualityReport.netUseTime).append("\n")
            sb.append("****************").append("\n")
            //定位之后的回调时间
            sb.append("""
    
    """.trimIndent())

            //解析定位结果，
            val result = sb.toString()
            LogUtils.i("位置结果：\n", result)

        } else {
        }
    }
}