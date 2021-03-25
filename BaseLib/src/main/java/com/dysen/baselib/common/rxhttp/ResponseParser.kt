package com.dysen.baselib.common.rxhttp

import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.TimeoutCancellationException
import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.AbstractParser
import java.io.IOException
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


/**
 * @author dysen
 * dy.sen@qq.com     1/25/21 4:27 PM
 *
 * Info：自定义解析器
 */
@Parser(name = "BaseResponse")
open class ResponseParser<T> : AbstractParser<T> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
//    override fun onParse(response: Response): T {
    override fun onParse(response: Response): T {
        val type: Type = ParameterizedTypeImpl[BaseResponse::class.java, mType] //获取泛型类型
        val data: BaseResponse<T> = convert(response, type)   //获取Response对象
        val t = data.data                             //获取data字段
        if (data.code != 0 || t == null) { //code不等于0，说明数据不正确，抛出异常
//            RspCodeTip.codeTip(data.code, data.msg)
            throw ParseException(data.code.toString(), data.msg, response)
        }
        return t!!
    }

    val Throwable.code: Int
        get() {
            val errorCode = when (this) {
                is HttpStatusCodeException -> this.statusCode //Http状态码异常
                is ParseException -> this.errorCode     //业务code异常
                else -> "-1"
            }
            return try {
                errorCode.toInt()
            } catch (e: Exception) {
                -1
            }
        }

    val Throwable.msg: String
        get() {
            return if (this is UnknownHostException) { //网络异常
                "当前无网络，请检查你的网络设置"
            } else if (
                this is SocketTimeoutException  //okhttp全局设置超时
                || this is TimeoutException     //rxjava中的timeout方法超时
                || this is TimeoutCancellationException  //协程超时
            ) {
                "连接超时,请稍后再试"
            } else if (this is ConnectException) {
                "网络不给力，请稍候重试！"
            } else if (this is HttpStatusCodeException) {               //请求失败异常
                "Http状态码异常"
            } else if (this is JsonSyntaxException) {  //请求成功，但Json语法异常,导致解析失败
                "数据解析失败,请检查数据是否正确"
            } else if (this is ParseException) {       // ParseException异常表明请求成功，但是数据不正确
                this.message ?: errorCode   //msg为空，显示code
            } else {
                "请求失败，请稍后再试"
            }
        }

}

