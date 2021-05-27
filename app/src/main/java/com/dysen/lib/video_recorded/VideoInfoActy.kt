package com.dysen.lib.video_recorded

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import coil.load
import coil.transform.RoundedCornersTransformation
import com.dysen.baselib.utils.rxRequestPermissions
import com.dysen.lib.R
import com.just.agentweb.AgentWeb
import com.zhaoss.weixinrecorded.activity.RecordedActivity
import kotlinx.android.synthetic.main.activity_video_info.*
import java.io.File


class VideoInfoActy : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_info)
        mBtnRecord.setOnClickListener {
            rxRequestPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, describe = "相机、存储、录音") {
//                VideoRecordActivity.newInstance(this)
                var mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(web, ViewGroup.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .createAgentWeb()
                    .ready()
                    .go("http://www.jd.com")


            }
        }
        mBtnRecord2.setOnClickListener {
            rxRequestPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, describe = "相机、存储、录音") {
                startActivityForResult(Intent(this, RecordedActivity::class.java), RecordedActivity.REQUEST_VIDEO_CODE)
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
             if (requestCode == RecordedActivity.REQUEST_VIDEO_CODE) {
                val dataType = data!!.getIntExtra(RecordedActivity.INTENT_DATA_TYPE, RecordedActivity.RESULT_TYPE_VIDEO)
                if (dataType == RecordedActivity.RESULT_TYPE_VIDEO) {
                    var videoPath = data.getStringExtra(RecordedActivity.INTENT_PATH)
                    val imageLoader = ImageLoader.Builder(this)
                            .componentRegistry {
//                //① Gif（GifDecoder 支持所有 API 级别，但速度较慢，ImageDecoderDecoder 的加载速度快，但仅在 API 28 及更高版本可用）
//                if (Build.VERSION.SDK_INT >= 28) {
//                    add(ImageDecoderDecoder())
//                } else {
//                    add(GifDecoder())
//                }
//                //② SVG（如果请求的 MIME 类型是 image/svg+xml，则会自动检测并解码所有 SVG）
//                add(SvgDecoder(this@CoilTestActivity))
                                //③ 视频帧（仅支持 File 和 Uri）
                                add(VideoFrameFileFetcher(this@VideoInfoActy))
                                add(VideoFrameUriFetcher(this@VideoInfoActy))
                                add(VideoFrameDecoder(this@VideoInfoActy))
                            }.build()
//                    val videoBitmap = Tools.getVideoPlayBitmap(videoPath)
//                    iv_upload_video.load(videoBitmap) {
                    iv.load(File(videoPath), imageLoader) {
                        placeholder(R.mipmap.video_camera)
                        error(R.mipmap.video_thumbnail)
                        transformations(RoundedCornersTransformation(topRight = 10f, topLeft = 10f, bottomLeft = 10f, bottomRight = 10f))
                    }
//                    Tools.getTheThumbnail4VideoOnline(videoPath, iv)
                } else if (dataType == RecordedActivity.RESULT_TYPE_PHOTO) {
                    var imagePath = data.getStringExtra(RecordedActivity.INTENT_PATH)
                    iv.load(File(imagePath)) {
                        placeholder(R.mipmap.video_camera)
                        error(R.mipmap.video_thumbnail)
                    }
                }
            }
        }
    }
}