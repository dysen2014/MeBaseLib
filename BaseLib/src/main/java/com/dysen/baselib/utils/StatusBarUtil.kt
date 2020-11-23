package com.dysen.baselib.utils

import android.R
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.drawerlayout.widget.DrawerLayout

/**
 * @package com.dysen.commom_library.utils
 * @email dy.sen@qq.com
 * created by dysen on 2019/2/15 - 下午5:48
 * @info
 */
object StatusBarUtil {
    const val DEFAULT_STATUS_BAR_ALPHA = 112
    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    fun setColor(activity: Activity, color: Int) {
        setColor(activity, color, DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    fun setColor(activity: Activity, color: Int, statusBarAlpha: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window
                .addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = calculateStatusColor(color, statusBarAlpha)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 生成一个状态栏大小的矩形
            val statusView =
                createStatusBarView(activity, color, statusBarAlpha)
            // 添加 statusView 到布局中
            val decorView = activity.window.decorView as ViewGroup
            decorView.addView(statusView)
            setRootView(activity)
        }
    }

    /**
     * 设置状态栏纯色 不加半透明效果
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    fun setColorNoTranslucent(activity: Activity, color: Int) {
        setColor(activity, color, 0)
    }

    /**
     * 设置状态栏颜色(5.0以下无半透明效果,不建议使用)
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    fun setColorDiff(activity: Activity, color: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // 生成一个状态栏大小的矩形
        val statusView = createStatusBarView(activity, color)
        // 添加 statusView 到布局中
        val decorView = activity.window.decorView as ViewGroup
        decorView.addView(statusView)
        setRootView(activity)
    }

    /**
     * 使状态栏半透明
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    fun setTranslucent(activity: Activity) {
        setTranslucent(activity, DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 使状态栏半透明
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity       需要设置的activity
     * @param statusBarAlpha 状态栏透明度
     */
    fun setTranslucent(activity: Activity, statusBarAlpha: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        setTransparent(activity)
        addTranslucentView(activity, statusBarAlpha)
    }

    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    fun setTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        transparentStatusBar(activity)
        setRootView(activity)
    }

    /**
     * 使状态栏透明(5.0以上半透明效果,不建议使用)
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    fun setTranslucentDiff(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 设置状态栏透明
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            setRootView(activity)
        }
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    fun setColorForDrawerLayout(
        activity: Activity,
        drawerLayout: DrawerLayout,
        color: Int
    ) {
        setColorForDrawerLayout(
            activity,
            drawerLayout,
            color,
            DEFAULT_STATUS_BAR_ALPHA
        )
    }

    /**
     * 为DrawerLayout 布局设置状态栏颜色,纯色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    fun setColorNoTranslucentForDrawerLayout(
        activity: Activity,
        drawerLayout: DrawerLayout,
        color: Int
    ) {
        setColorForDrawerLayout(activity, drawerLayout, color, 0)
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色
     *
     * @param activity       需要设置的activity
     * @param drawerLayout   DrawerLayout
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    fun setColorForDrawerLayout(
        activity: Activity,
        drawerLayout: DrawerLayout,
        color: Int,
        statusBarAlpha: Int
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window
                .addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        // 生成一个状态栏大小的矩形
        val statusBarView = createStatusBarView(activity, color)
        // 添加 statusBarView 到布局中
        val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
        contentLayout.addView(statusBarView, 0)
        // 内容布局不是 LinearLayout 时,设置padding top
        if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1)
                .setPadding(0, getStatusBarHeight(activity), 0, 0)
        }
        // 设置属性
        val drawer = drawerLayout.getChildAt(1) as ViewGroup
        drawerLayout.fitsSystemWindows = false
        contentLayout.fitsSystemWindows = false
        contentLayout.clipToPadding = true
        drawer.fitsSystemWindows = false
        addTranslucentView(activity, statusBarAlpha)
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色(5.0以下无半透明效果,不建议使用)
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    fun setColorForDrawerLayoutDiff(
        activity: Activity,
        drawerLayout: DrawerLayout,
        color: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 生成一个状态栏大小的矩形
            val statusBarView =
                createStatusBarView(activity, color)
            // 添加 statusBarView 到布局中
            val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
            contentLayout.addView(statusBarView, 0)
            // 内容布局不是 LinearLayout 时,设置padding top
            if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
                contentLayout.getChildAt(1)
                    .setPadding(0, getStatusBarHeight(activity), 0, 0)
            }
            // 设置属性
            val drawer = drawerLayout.getChildAt(1) as ViewGroup
            drawerLayout.fitsSystemWindows = false
            contentLayout.fitsSystemWindows = false
            contentLayout.clipToPadding = true
            drawer.fitsSystemWindows = false
        }
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    fun setTranslucentForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout) {
        setTranslucentForDrawerLayout(
            activity,
            drawerLayout,
            DEFAULT_STATUS_BAR_ALPHA
        )
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    fun setTranslucentForDrawerLayout(
        activity: Activity,
        drawerLayout: DrawerLayout,
        statusBarAlpha: Int
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        setTransparentForDrawerLayout(activity, drawerLayout)
        addTranslucentView(activity, statusBarAlpha)
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    fun setTransparentForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window
                .addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
        // 内容布局不是 LinearLayout 时,设置padding top
        if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1)
                .setPadding(0, getStatusBarHeight(activity), 0, 0)
        }
        // 设置属性
        val drawer = drawerLayout.getChildAt(1) as ViewGroup
        drawerLayout.fitsSystemWindows = false
        contentLayout.fitsSystemWindows = false
        contentLayout.clipToPadding = true
        drawer.fitsSystemWindows = false
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明(5.0以上半透明效果,不建议使用)
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    fun setTranslucentForDrawerLayoutDiff(
        activity: Activity,
        drawerLayout: DrawerLayout
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 设置状态栏透明
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 设置内容布局属性
            val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
            contentLayout.fitsSystemWindows = true
            contentLayout.clipToPadding = true
            // 设置抽屉布局属性
            val vg = drawerLayout.getChildAt(1) as ViewGroup
            vg.fitsSystemWindows = false
            // 设置 DrawerLayout 属性
            drawerLayout.fitsSystemWindows = false
        }
    }

    /**
     * 添加半透明矩形条
     *
     * @param activity       需要设置的 activity
     * @param statusBarAlpha 透明值
     */
    private fun addTranslucentView(activity: Activity, statusBarAlpha: Int) {
        val contentView =
            activity.findViewById<View>(R.id.content) as ViewGroup
        // 移除半透明矩形,以免叠加
        if (contentView.childCount > 1) {
            contentView.removeViewAt(1)
        }
        contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha))
    }

    /**
     * 生成一个和状态栏大小相同的彩色矩形条
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private fun createStatusBarView(
        activity: Activity,
        color: Int
    ): View { // 绘制一个和状态栏一样高的矩形
        val statusBarView = View(activity)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getStatusBarHeight(activity)
        )
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(color)
        return statusBarView
    }

    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    透明值
     * @return 状态栏矩形条
     */
    private fun createStatusBarView(
        activity: Activity,
        color: Int,
        alpha: Int
    ): View { // 绘制一个和状态栏一样高的矩形
        val statusBarView = View(activity)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getStatusBarHeight(activity)
        )
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha))
        return statusBarView
    }

    /**
     * 设置根布局参数
     */
    private fun setRootView(activity: Activity) {
        val rootView =
            (activity.findViewById<View>(R.id.content) as ViewGroup).getChildAt(
                0
            ) as ViewGroup
                ?: return
        rootView.fitsSystemWindows = true
        rootView.clipToPadding = true
    }

    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun transparentStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window
                .addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 创建半透明矩形 View
     *
     * @param alpha 透明值
     * @return 半透明 View
     */
    private fun createTranslucentStatusBarView(
        activity: Activity,
        alpha: Int
    ): View { // 绘制一个和状态栏一样高的矩形
        val statusBarView = View(activity)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getStatusBarHeight(activity)
        )
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
        return statusBarView
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private fun getStatusBarHeight(context: Context): Int { // 获得状态栏高度
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private fun calculateStatusColor(color: Int, alpha: Int): Int {
        val a = 1 - alpha / 255f
        var red = color shr 16 and 0xff
        var green = color shr 8 and 0xff
        var blue = color and 0xff
        red = (red * a + 0.5).toInt()
        green = (green * a + 0.5).toInt()
        blue = (blue * a + 0.5).toInt()
        return 0xff shl 24 or (red shl 16) or (green shl 8) or blue
    }
}