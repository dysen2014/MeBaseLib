package com.dysen.lib.rxhttp

import android.os.Bundle
import androidx.lifecycle.rxLifeScope
import com.dysen.baselib.base.BaseActivity
import com.dysen.baselib.utils.LogUtils
import com.dysen.lib.R
import com.kongzue.dialog.v3.TipDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rxhttp.*
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toBaseResponse
import java.net.ConnectException


/**
 * @author dysen
 * dy.sen@qq.com     1/22/21 3:08 PM
 *
 * Info：
 */
class RxHttpActy : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_rx_http

    override fun initView(savedInstanceState: Bundle?) {
        GlobalScope.launch(Dispatchers.Main) { test() }
    }

    private suspend fun test() {
        //Kotlin 协程
        val str = RxHttp.get("https://www.mxnzp.com/api/daily_word/recommend?count=10") //第一步，确定请求方式，可以选择postForm、postJson等方法
            .toStr()    //第二步，确认返回类型，这里代表返回String类型
            .await()
        val weather =
            RxHttp.get("http://kctest.rkpcn.com/complex/patrol/task/page?companyId=16&patrolEndTime=2021-01-22&agent=im&taskType=2&limit=10&page=1&communityId=25&orderField=patrol_start_time&patrolStartTime=2021-01-22&userId=2063052491145217&taskStatus=1&order=asc&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMDYzMDUyNDkxMTQ1MjE3IiwiaWF0IjoxNjExMzA0MDU4LCJleHAiOjE2MTEzOTA0NTh9.U-NlpN-5zUHOjbsiBtMIt99-xcppcdYQd9tQ75YFjteLit9e0uom9L14duj0uqTrg9RQAUmns0-JLhfuWtDqlA") //第一步，确定请求方式，可以选择postForm、postJson等方法
                .toClass<BaseResponse<ResponseParser<String>>>()    //第二步，确认返回类型，这里代表返回String类型
                .timeout(30 * 1000)
                .retry(2, 10 * 1000) {                      //重试2次，每次间隔10s
                    it is ConnectException   //如果是网络异常就重试
                }
                .await()    //第三步，使用await方法拿到返回值

        showTip("$str \n $weather")
        LogUtils.e("$str \n $weather")

        rxLifeScope.launch {
            val str = RxHttp.get("https://www.mxnzp.com/api/daily_word/recommend?count=10") //第一步，确定请求方式，可以选择postForm、postJson等方法
                .toStr()    //第二步，确认返回类型，这里代表返回String类型
                .startDelay(5*1000)
                .await()
            showTip(str)
        }
        rxLifeScope.launch({
            val str = RxHttp.get("http://kctest.rkpcn.com/complex/patrol/task/page?companyId=16&patrolEndTime=2021-01-22&agent=im&taskType=2&limit=10&page=1&communityId=25&orderField=patrol_start_time&patrolStartTime=2021-01-22&userId=2063052491145217&taskStatus=1&order=asc&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMDYzMDUyNDkxMTQ1MjE3IiwiaWF0IjoxNjExMzA0MDU4LCJleHAiOjE2MTEzOTA0NTh9.U-NlpN-5zUHOjbsiBtMIt99-xcppcdYQd9tQ75YFjteLit9e0uom9L14duj0uqTrg9RQAUmns0-JLhfuWtDqlA") //第一步，确定请求方式，可以选择postForm、postJson等方法
                .toBaseResponse<BaseResponse<String>>()
            .startDelay(10*1000)
            .await()
                      showTip(str.toString())     }, {
                       showTip(" request error ${it.message}")
        }, {showLoading("请求开始", 30*1000)}, { TipDialog.dismiss(3*1000)})

        rxLifeScope.launch {
            RxHttp.postForm("")
                .add("", "")
                .toBaseResponse<String>()
                .await()
        }
    }
}