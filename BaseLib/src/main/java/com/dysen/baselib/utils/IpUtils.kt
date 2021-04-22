package com.dysen.baselib.utils

import android.content.Context
import android.net.wifi.WifiManager
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException

/**
 * @author dysen
 * dy.sen@qq.com     4/14/21 11:44 AM
 *
 *
 * Info：
 */
object IpUtils {
    /**
     * 获取本机IPv4地址
     *
     * @param context
     * @return 本机IPv4地址；null：无网络连接
     */
    fun getIpAddress(context: Context): String? {
        // 获取WiFi服务
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        // 判断WiFi是否开启
        return if (wifiManager.isWifiEnabled) {
            // 已经开启了WiFi
            val wifiInfo = wifiManager.connectionInfo
            val ipAddress = wifiInfo.ipAddress
            intToIp(ipAddress)
        } else {
            // 未开启WiFi
//            ipAddress
            GetHostIp()
        }
    }

    private fun intToIp(ipAddress: Int): String {
        return (ipAddress and 0xFF).toString() + "." +
                (ipAddress shr 8 and 0xFF) + "." +
                (ipAddress shr 16 and 0xFF) + "." +
                (ipAddress shr 24 and 0xFF)
    }

    /**
     * 获取本机IPv4地址
     *
     * @return 本机IPv4地址；null：无网络连接
     */
    private val ipAddress: String
        private get() = try {
            var networkInterface: NetworkInterface
            var inetAddress: InetAddress
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                networkInterface = en.nextElement()
                val enumIpAddr = networkInterface.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && !inetAddress.isLinkLocalAddress) {
                         inetAddress.hostAddress
                    }
                }
            }
            ""
        } catch (ex: SocketException) {
            ex.printStackTrace()
            ""
        }

    fun GetHostIp(): String {
        try {
            val en = NetworkInterface
                    .getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val ipAddr = intf.inetAddresses
                while (ipAddr.hasMoreElements()) {
                    val inetAddress = ipAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString()
                    }
                }
            }
        } catch (ex: SocketException) {
        } catch (e: Exception) {
        }
        return ""
    }
}