package com.dysen.baselib.utils

import java.util.*

/**
 * User: dysen
 * dy.sen@qq.com    @date : 11/24/20 17:06 PM

 * Info：
 */
object MeRandom {
    @JvmStatic
    fun main(args: Array<String>) {
        while (true) {
//            System.out.println(random2Int(65535) + "----------------" + random2Hex(65535));
            println(random2Int(2, 10).toString() + "----------------" + random2Hex(2, 10))
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 获取随机数 int类型
     * @param i
     * @return
     */
    fun random2Int(i: Int): Int {
        val random = Random()
        return random.nextInt(i)
    }

    /**
     * 获取 i~j 的随机数 int类型
     * @param i
     * @param j
     * @return
     */
    fun random2Int(i: Int, j: Int): Int {
        val random = Random()
        var randomData = random.nextInt(j)
        if (i >= j) ; else if (randomData < i) randomData = randomData + i
        return randomData
    }

    /**
     * 获取随机数 int类型
     * @return
     */
    fun random2Float(): Float {
        val random = Random()
        return random.nextFloat()
    }

    /**
     * 获取随机数 返回String类型 Hex字符
     * @param i
     * @return
     */
    fun random2Hex(i: Int): String {
        val random = Random()
        val randomData = random.nextInt(i)
        var rd = Integer.toHexString(randomData).toUpperCase()
        if (rd.length < 2) rd = "0$rd"
        println("随机数：$rd")
        return rd
    }

    /**
     * 获取 i~j 的随机数 返回String类型 Hex字符
     * @param i
     * @return
     */
    fun random2Hex(i: Int, j: Int): String {
        val random = Random()
        var randomData = random.nextInt()
        if (i >= j) ; else if (randomData < i) randomData = randomData + i
        var rd = Integer.toHexString(randomData).toUpperCase()
        if (rd.length < 2) rd = "0$rd"
        //        System.out.println("随机数："+rd);
        return rd
    }
}