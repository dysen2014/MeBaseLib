package com.me.rxhttp.http

import rxhttp.toClass
import rxhttp.wrapper.param.RxHttp

/**
 * @author dysen
 * dy.sen@qq.com     4/6/21 9:02 AM
 *
 * Info：
 */
object Api {

    //获取Token
//    fun token() = CacheUtil.gString(Keys.TOKEN)

    //获取项目Id
//    fun companyId() = CacheUtil.gString(Keys.KEY_COMPANY_ID)

    //获取项目Id
//    fun communityId() = CacheUtil.gString(Keys.KEY_COMMUNITY_ID)


    /**
     * 公共参数提取 ,自定义请求方式
     *
     * GET, POST, DELETE
     *
     */
//    fun get(url:String) = RxHttp.get(url).addHeader("token", token())
//
//    fun postJson(url:String) = RxHttp.postJson(url).addHeader("token", token())
//
//    fun deleteJson(url:String) = RxHttp.deleteJson(url).addHeader("token", token())


    fun get(url:String) = RxHttp.get(url)

    suspend fun getProjects(url:String="https://api.github.com/search/repositories?sort=stars&q=Android&per_page=5&page=1") = get(url).toClass<String>().await()

}