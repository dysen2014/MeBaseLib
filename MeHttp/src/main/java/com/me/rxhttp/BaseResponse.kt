package com.me.rxhttp


/**
 * @author dysen
 * dy.sen@qq.com     1/25/21 4:35 PM
 *
 * Info：
 */
class BaseResponse<T> {
    var code = 0
    var msg : String? = null
    var data : T?=null
}