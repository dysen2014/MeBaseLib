package com.me.baselib.utils

import android.graphics.Color
import java.util.*

/**
 * dysen.
 * dy.sen@qq.com    11/20/20  14:11 PM

 * Info：
 */
object ColorUtil {
    //自定义颜色，过滤掉与白色相近的颜色
    var ACCENT_COLORS = intArrayOf(
        Color.parseColor("#EF5350"),
        Color.parseColor("#EC407A"),
        Color.parseColor("#AB47BC"),
        Color.parseColor("#7E57C2"),
        Color.parseColor("#5C6BC0"),
        Color.parseColor("#42A5F5"),
        Color.parseColor("#29B6F6"),
        Color.parseColor("#26C6DA"),
        Color.parseColor("#26A69A"),
        Color.parseColor("#66BB6A"),
        Color.parseColor("#9CCC65"),
        Color.parseColor("#D4E157"),
        Color.parseColor("#FFEE58"),
        Color.parseColor("#FFCA28"),
        Color.parseColor("#FFA726"),
        Color.parseColor("#FF7043"),
        Color.parseColor("#8D6E63"),
        Color.parseColor("#BDBDBD"),
        Color.parseColor("#78909C")
    )

    val PRIMARY_COLORS_SUB = arrayOf(
        intArrayOf(
            Color.parseColor("#EF5350"), Color.parseColor("#F44336"), Color.parseColor("#E53935"), Color.parseColor("#D32F2F"), Color.parseColor("#C62828"), Color.parseColor(
                "#B71C1C"
            )
        ),
        intArrayOf(
            Color.parseColor("#EC407A"), Color.parseColor("#E91E63"), Color.parseColor("#D81B60"), Color.parseColor("#C2185B"), Color.parseColor("#AD1457"), Color.parseColor(
                "#880E4F"
            )
        ),
        intArrayOf(
            Color.parseColor("#AB47BC"), Color.parseColor("#9C27B0"), Color.parseColor("#8E24AA"), Color.parseColor("#7B1FA2"), Color.parseColor("#6A1B9A"), Color.parseColor(
                "#4A148C"
            )
        ),
        intArrayOf(
            Color.parseColor("#7E57C2"), Color.parseColor("#673AB7"), Color.parseColor("#5E35B1"), Color.parseColor("#512DA8"), Color.parseColor("#4527A0"), Color.parseColor(
                "#311B92"
            )
        ),
        intArrayOf(
            Color.parseColor("#5C6BC0"), Color.parseColor("#3F51B5"), Color.parseColor("#3949AB"), Color.parseColor("#303F9F"), Color.parseColor("#283593"), Color.parseColor(
                "#1A237E"
            )
        ),
        intArrayOf(
            Color.parseColor("#42A5F5"), Color.parseColor("#2196F3"), Color.parseColor("#1E88E5"), Color.parseColor("#1976D2"), Color.parseColor("#1565C0"), Color.parseColor(
                "#0D47A1"
            )
        ),
        intArrayOf(
            Color.parseColor("#29B6F6"), Color.parseColor("#03A9F4"), Color.parseColor("#039BE5"), Color.parseColor("#0288D1"), Color.parseColor("#0277BD"), Color.parseColor(
                "#01579B"
            )
        ),
        intArrayOf(
            Color.parseColor("#26C6DA"), Color.parseColor("#00BCD4"), Color.parseColor("#00ACC1"), Color.parseColor("#0097A7"), Color.parseColor("#00838F"), Color.parseColor(
                "#006064"
            )
        ),
        intArrayOf(
            Color.parseColor("#26A69A"), Color.parseColor("#009688"), Color.parseColor("#00897B"), Color.parseColor("#00796B"), Color.parseColor("#00695C"), Color.parseColor(
                "#004D40"
            )
        ),
        intArrayOf(
            Color.parseColor("#66BB6A"), Color.parseColor("#4CAF50"), Color.parseColor("#43A047"), Color.parseColor("#388E3C"), Color.parseColor("#2E7D32"), Color.parseColor(
                "#1B5E20"
            )
        ),
        intArrayOf(
            Color.parseColor("#9CCC65"), Color.parseColor("#8BC34A"), Color.parseColor("#7CB342"), Color.parseColor("#689F38"), Color.parseColor("#558B2F"), Color.parseColor(
                "#33691E"
            )
        ),
        intArrayOf(
            Color.parseColor("#D4E157"), Color.parseColor("#CDDC39"), Color.parseColor("#C0CA33"), Color.parseColor("#AFB42B"), Color.parseColor("#9E9D24"), Color.parseColor(
                "#827717"
            )
        ),
        intArrayOf(
            Color.parseColor("#FFEE58"), Color.parseColor("#FFEB3B"), Color.parseColor("#FDD835"), Color.parseColor("#FBC02D"), Color.parseColor("#F9A825"), Color.parseColor(
                "#F57F17"
            )
        ),
        intArrayOf(
            Color.parseColor("#FFCA28"), Color.parseColor("#FFC107"), Color.parseColor("#FFB300"), Color.parseColor("#FFA000"), Color.parseColor("#FF8F00"), Color.parseColor(
                "#FF6F00"
            )
        ),
        intArrayOf(
            Color.parseColor("#FFA726"), Color.parseColor("#FF9800"), Color.parseColor("#FB8C00"), Color.parseColor("#F57C00"), Color.parseColor("#EF6C00"), Color.parseColor(
                "#E65100"
            )
        ),
        intArrayOf(
            Color.parseColor("#FF7043"), Color.parseColor("#FF5722"), Color.parseColor("#F4511E"), Color.parseColor("#E64A19"), Color.parseColor("#D84315"), Color.parseColor(
                "#BF360C"
            )
        ),
        intArrayOf(
            Color.parseColor("#8D6E63"), Color.parseColor("#795548"), Color.parseColor("#6D4C41"), Color.parseColor("#5D4037"), Color.parseColor("#4E342E"), Color.parseColor(
                "#3E2723"
            )
        ),
        intArrayOf(
            Color.parseColor("#BDBDBD"), Color.parseColor("#9E9E9E"), Color.parseColor("#757575"), Color.parseColor("#616161"), Color.parseColor("#424242"), Color.parseColor(
                "#212121"
            )
        ),
        intArrayOf(
            Color.parseColor("#78909C"), Color.parseColor("#607D8B"), Color.parseColor("#546E7A"), Color.parseColor("#455A64"), Color.parseColor("#37474F"), Color.parseColor(
                "#263238"
            )
        )
    )

    //网络图片数据 给用户随机使用
    var IMAGE_URL = arrayListOf(
        "https://img2.woyaogexing.com/2019/08/30/abf3568adb8745ac9d5dc6cf9a3da895!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/30/246e7cea8e0849cc88140f1973fdb95d!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/30/516b60c208824015ab2fb736cea1eb8c!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/30/b6c6bce7acc84e7aabed05ccc5ec9f80!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/30/749c2d6c2f174a91b1f1f92c2de30004!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/30/38304054b90447b9bc21bb07176ab058!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/30/917e8cbef5344431a9b31c51a483f363!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/30/bdaae1c240af46ac98a95eaac3be843f!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/30/13908fcec57a4738917fec458d581bdf!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/30/7f9c86f6cbbf4f9892df28e50321f477!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/30/f6d7b8480a4b40298d7c9beb079ba8d4!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/30/f240d46657cf446eb0b5eb6290ff4528!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/29/a85407f91da24953a619df9315993e34!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/29/d5d365f1458a41d891b7ada030e74232!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/29/11217143b1ef433b8a914c16548fa50e!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/29/c8436d280f49418b8825fadfeeb0c0f8!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/29/81513514d2c04d3e901c33823ba3c492!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/29/1e300689c6a845b99e8daf32d72a1929!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/29/3adc12526ac541b5a4efd4493b4a3e85!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/29/a0c0493a57164cf9985cdbc972c22dd1!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/29/62884a8178f543fabcfb47db2ef7003d!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/29/9204eb1b05844d5c9723d37fcd76dc77!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/29/15937d014e13450c9e11ad5c3a015cf2!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/29/0366969e21b14d479696d444302be8a4!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/29/4739431c257643a29b3a3343a4f5fb2e!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/28/3a918ed489af4490884ebef741f8df91!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/28/9a4c7ca101434b46bf03751c88e378a4!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/28/0af19f96dbb84ea7bb98d7bbc7b11a57!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/28/4a1e7dde2b5c47f4b025c15388dc290f!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/28/626d23fe1c2b411fa33f92e6c94d7af0!600x600.jpeg",
        "https://img2.woyaogexing.com/2019/08/28/667ebc1b9d7c4783bad801a2a3be199d!600x600.jpeg"
    )

    val IMAGE_URL2 = arrayListOf(
        "https://img-my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383290_9329.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383290_1042.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383275_3977.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383265_8550.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383264_3954.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383264_4787.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383264_8243.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383248_3693.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383243_5120.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383242_3127.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383242_9576.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383242_1721.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383219_5806.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383214_7794.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383213_4418.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383213_3557.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383210_8779.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383172_4577.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383166_3407.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383166_2224.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383166_7301.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383165_7197.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383150_8410.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383131_3736.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383130_5094.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383130_7393.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383129_8813.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383100_3554.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383093_7894.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383092_2432.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383092_3071.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383091_3119.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383059_6589.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383059_8814.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383059_2237.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383058_4330.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406383038_3602.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382942_3079.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382942_8125.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382942_4881.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382941_4559.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382941_3845.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382924_8955.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382923_2141.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382923_8437.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382922_6166.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382922_4843.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382905_5804.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382904_3362.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382904_2312.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382904_4960.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382900_2418.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382881_4490.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382881_5935.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382880_3865.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382880_4662.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382879_2553.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382862_5375.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382862_1748.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382861_7618.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382861_8606.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382861_8949.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382841_9821.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382840_6603.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382840_2405.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382840_6354.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382839_5779.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382810_7578.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382810_2436.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382809_3883.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382809_6269.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382808_4179.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382790_8326.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382789_7174.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382789_5170.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382789_4118.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382788_9532.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382767_3184.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382767_4772.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382766_4924.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382766_5762.jpg",
        "https://img-my.csdn.net/uploads/201407/26/1406382765_7341.jpg"
    )

    val IMAGE_URL3 = arrayListOf(
        "http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg",
        "http://c.hiphotos.baidu.com/image/pic/item/30adcbef76094b36de8a2fe5a1cc7cd98d109d99.jpg",
        "http://h.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd5f2c2a9e953da81cb39db3d1d.jpg",
        "http://g.hiphotos.baidu.com/image/pic/item/55e736d12f2eb938d5277fd5d0628535e5dd6f4a.jpg",
        "http://e.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f7e41f5cfe760e0cf3d6cad6ee.jpg",
        "http://b.hiphotos.baidu.com/image/pic/item/9d82d158ccbf6c81b94575cfb93eb13533fa40a2.jpg",
        "http://e.hiphotos.baidu.com/image/pic/item/4bed2e738bd4b31c1badd5a685d6277f9e2ff81e.jpg",
        "http://g.hiphotos.baidu.com/image/pic/item/0d338744ebf81a4c87a3add4d52a6059252da61e.jpg",
        "http://a.hiphotos.baidu.com/image/pic/item/f2deb48f8c5494ee5080c8142ff5e0fe99257e19.jpg",
        "http://f.hiphotos.baidu.com/image/pic/item/4034970a304e251f503521f5a586c9177e3e53f9.jpg",
        "http://b.hiphotos.baidu.com/image/pic/item/279759ee3d6d55fbb3586c0168224f4a20a4dd7e.jpg",
        "http://img2.xkhouse.com/bbs/hfhouse/data/attachment/forum/corebbs/2009-11/2009113011534566298.jpg",
        "http://a.hiphotos.baidu.com/image/pic/item/e824b899a9014c087eb617650e7b02087af4f464.jpg",
        "http://c.hiphotos.baidu.com/image/pic/item/9c16fdfaaf51f3de1e296fa390eef01f3b29795a.jpg",
        "http://d.hiphotos.baidu.com/image/pic/item/b58f8c5494eef01f119945cbe2fe9925bc317d2a.jpg",
        "http://h.hiphotos.baidu.com/image/pic/item/902397dda144ad340668b847d4a20cf430ad851e.jpg",
        "http://b.hiphotos.baidu.com/image/pic/item/359b033b5bb5c9ea5c0e3c23d139b6003bf3b374.jpg",
        "http://a.hiphotos.baidu.com/image/pic/item/8d5494eef01f3a292d2472199d25bc315d607c7c.jpg",
        "http://b.hiphotos.baidu.com/image/pic/item/e824b899a9014c08878b2c4c0e7b02087af4f4a3.jpg",
        "http://g.hiphotos.baidu.com/image/pic/item/6d81800a19d8bc3e770bd00d868ba61ea9d345f2.jpg")


    /**
     * 获取随机rgb颜色值
     */
    fun randomColor(): Int {
        Random().run {
            //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
            val red = nextInt(190)
            val green = nextInt(190)
            val blue = nextInt(190)
            //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
            return Color.rgb(red, green, blue)
        }
    }

    /**
     * 获取随机一张图片路径
     */
    fun randomImage(): String {
        Random().run {
            val index = nextInt(IMAGE_URL3.size)
            return IMAGE_URL3[index]
        }
    }

}
