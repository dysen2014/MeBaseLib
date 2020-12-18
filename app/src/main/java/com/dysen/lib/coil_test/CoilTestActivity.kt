package com.dysen.lib.coil_test

import android.os.Bundle
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
        iv4.load(imgUrl){
            crossfade(true)
            placeholder(R.drawable.ic_img_load_before)
            transformations(CircleCropTransformation())
        }

        iv5.load(File("file:///android_asset/meidi.mp4")){
            crossfade(true)
            placeholder(R.drawable.ic_img_load_before)
            error(R.drawable.ic_img_load_failed)
            transformations(CircleCropTransformation(),BlurTransformation(this@CoilTestActivity, 10f))
        }

        iv6.load("https://media.giphy.com/media/l0HlMICf8eLZE8uxG/giphy.gif"){
            crossfade(true)
            placeholder(R.drawable.ic_img_load_before)
            transformations(BlurTransformation(this@CoilTestActivity, 10f), RoundedCornersTransformation(30f))
        }
    }
}