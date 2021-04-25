package com.dysen.lib.coil_test

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import coil.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.dysen.baselib.base.BaseActivity
import com.dysen.baselib.ui.image_paper.BigImagePagerActivity
import com.dysen.baselib.utils.ColorUtil
import com.dysen.baselib.widgets.TitleLayout
import com.dysen.lib.R
import kotlinx.android.synthetic.main.activity_coil_test.*
import java.io.File

class CoilTestActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_coil_test


    override fun initView(savedInstanceState: Bundle?) {
//        val imgUrl = "https://cdn.pixabay.com/photo/2020/06/14/22/31/the-caucasus-5299599__480.jpg"
        TitleLayout.title?.text = "Coil Test"
        TitleLayout.title?.isAllCaps = false

//        val imgUrl = "https://cdn.pixabay.com/photo/2020/10/17/11/55/elephants-5661842_1280.jpg"
        val imgUrl = ColorUtil.randomImage()
        iv.load(imgUrl){
            crossfade(true)
        }
        iv.setOnClickListener {
            BigImagePagerActivity.startImagePagerActivity(this,imgUrl)
        }
        iv2.load(imgUrl){
            crossfade(false)
            placeholder(R.drawable.ic_img_load_before)
            transformations(RoundedCornersTransformation(10f))
        }
        iv3.load(imgUrl){
            crossfade(true)
            placeholder(R.drawable.ic_img_load_before)
            transformations(BlurTransformation(this@CoilTestActivity, 10f))
        }

        val imageLoader = ImageLoader.Builder(this)
            .componentRegistry {
                //① Gif（GifDecoder 支持所有 API 级别，但速度较慢，ImageDecoderDecoder 的加载速度快，但仅在 API 28 及更高版本可用）
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder())
                } else {
                    add(GifDecoder())
                }
                //② SVG（如果请求的 MIME 类型是 image/svg+xml，则会自动检测并解码所有 SVG）
                add(SvgDecoder(this@CoilTestActivity))
                //③ 视频帧（仅支持 File 和 Uri）
                add(VideoFrameFileFetcher(this@CoilTestActivity))
                add(VideoFrameUriFetcher(this@CoilTestActivity))
            }
            .build()

        iv4.load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg2018.cnblogs.com%2Fblog%2F600079%2F201905%2F600079-20190513102302965-941998498" +
                ".gif&refer=http%3A%2F%2Fimg2018.cnblogs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1621676900&t=664eea55d92db2ae71091bb707d40fb1",imageLoader){
            crossfade(true)
            placeholder(R.drawable.ic_img_load_before)
        }
        iv5.load("https://img.zcool.cn/community/01b0d857b1a34d0000012e7e87f5eb.gif",imageLoader){
            crossfade(true)
            placeholder(R.drawable.ic_img_load_before)
            error(R.drawable.ic_img_load_failed)
//            transformations(CircleCropTransformation())
        }

        iv6.load("http://www.iqiyi.com/v_19ruv3n460.html",imageLoader){
            crossfade(true)
            placeholder(R.drawable.ic_img_load_before)
            error(R.drawable.ic_img_load_failed)

//            transformations(BlurTransformation(this@CoilTestActivity, 10f), RoundedCornersTransformation(30f))
        }
    }
}