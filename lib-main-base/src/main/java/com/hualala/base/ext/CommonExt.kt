package com.hualala.base.ext

import android.content.res.Resources
import android.support.design.widget.TabLayout
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.COMPLEX_UNIT_SP
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.hualala.base.R
import com.hualala.base.common.BaseApplication
import com.hualala.base.data.protocol.BaseRespone
import com.hualala.base.rx.BaseDisposableObserver
import com.hualala.base.rx.BaseFunction
import com.hualala.base.rx.BaseFunctionBoolean
import com.hualala.base.rx.BasePageFunction
import com.hualala.base.utils.GlideUtils
import com.hualala.base.widgets.DefaultTextWatcher
import com.kennyc.view.MultiStateView
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

//Kotlin通用扩展

/*
    扩展Observable执行
*/
fun <T> Observable<T>.execute(baseDisposableObserver: BaseDisposableObserver<T>, lifecycleProvider: LifecycleProvider<*>) {
    this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribe(baseDisposableObserver)
}

/*
    扩展点击事件
    listener
*/
fun View.onClick(listener: View.OnClickListener) {
    this.setOnClickListener(listener)
}

/*
    扩展点击事件，参数为方法
    lamdb
*/
fun View.onClick(method: () -> Unit) {
    this.setOnClickListener { method() }
}


/*
    扩展Button可用性
*/
fun Button.enable(et: EditText, method: () -> Boolean) {
    val btn = this
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}

fun ImageView.enable(et: EditText, method: () -> Boolean) {
    val imv = this
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (method()){
                imv.visibility = View.VISIBLE
            } else {
                imv.visibility = View.INVISIBLE
            }

        }
    })
}

/*
    ImageView加载网络图片
 */
fun ImageView.loadUrl(url: String) {
    GlideUtils.loadUrlImage(context, url, this)
}

/*
    多状态视图开始加载
 */
fun MultiStateView.startLoading(){
    viewState = MultiStateView.VIEW_STATE_LOADING
    //val loadingView = getView(MultiStateView.VIEW_STATE_LOADING)
    //val animBackground = loadingView!!.find<View>(R.id.loading_anim_view).background
    //(animBackground as AnimationDrawable).start()
}

fun Long.timeTosecond(): String {
    return (this / 1000).toString()
}

/*
    金额转换成带有2位小数的金额
 */
fun String?.formatDecimal(): String {
    var amount = "0.00"
    val mDecimalFormat = DecimalFormat("0.00")
    if (this.isNullOrEmpty().not()){
        try {
            val fmoney = this?.toFloat()
            amount = mDecimalFormat.format(fmoney)
        } catch (e: NumberFormatException){
        }
    }
    return amount
}

/*
    将秒数转成时间格式 精简格式 2018-09-12
 */
fun String?.formatStreamlineDate(): String{
    var result = ""
    this?.let {
        val date = Date()
        val format = SimpleDateFormat("yyyy-MM-dd")
        try {
            date.time = this.toLong() * 1000
            result = format.format(date)
        } catch (e: java.lang.NumberFormatException){
        }

    }
    return result
}


/*
    将calendar日期转秒
 */
fun Calendar.getSomeDay(days: Int): String {
    val calendar = this
    calendar.add(Calendar.DATE, days)
    return SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
}

/*
    将时间格式2018-9-10 03:00:00转成秒
 */
fun String.convertSecond(): String{
    val second = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this).time / 1000
    return  second.toString()
}

/*
    将秒数转成时间格式 精简格式 2018-09-12 12:00:12
 */
fun String?.formatYearMonth(): String{
    var result = ""
    this?.let {
        val date = Date()
        val format = SimpleDateFormat("yyyy-MM")
        try {
            date.time = this.toLong() * 1000
            result = format.format(date)
        } catch (e: java.lang.NumberFormatException){
        }

    }
    return result
}

/*
    将秒数转成时间格式 精简格式 2018-09-12 12:00:12
 */
fun String?.formatFullDate(): String{
    var result = ""
    this?.let {
        val date = Date()
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            date.time = this.toLong() * 1000
            result = format.format(date)
        } catch (e: java.lang.NumberFormatException){
        }

    }
    return result
}

fun String?.daysBetween(end: String?): Int{
    var start = this
    if (start.isNullOrEmpty() or end.isNullOrEmpty()){
        return -1
    }
    val dates = (start!!.toLong() - end!!.toLong()) / (3600 * 24)
    return dates.toInt()
}

fun getStarToday(): String{
    return "${SimpleDateFormat("yyyy-MM-dd").format(Date())} 00:00:00".convertSecond()
}

fun getEndToday(): String{
    return "${SimpleDateFormat("yyyy-MM-dd").format(Date())} 23:59:59".convertSecond()
}

fun getPreviouslyDate(days: Int): String{
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, days)
    val previous = calendar.time
    val date = "${SimpleDateFormat("yyyy-MM-dd").format(previous)} 00:00:00"
    return date.convertSecond()
}

/*
    是否是微信渠道
 */
fun String?.isWeiXinChannel(): Boolean {
    var result = false
    this?.let {
        if (this.startsWith("WEIXIN")){
            return true
        }
    }

    return result
}

/*
    是否是微信渠道
 */
fun String?.isAlipayChannel(): Boolean {
    var result = false
    this?.let {
        if (this.startsWith("ALIPAY")){
            return true
        }
    }

    return result
}

fun String?.isVerifyPhoneNumber(): Boolean {
    return Pattern.compile("[1][345789]\\d{9}").matcher(this).matches()
}

/*
    判断小数是否大于0
    0:  等于0
    1:  大于0
    -1: 小于0
 */
fun String?.comparisonSize(): Int{
    return BigDecimal(this).compareTo(BigDecimal.ZERO)
}

/*
    扩展数据转换
*/
fun <T> Observable<BaseRespone<T>>.convert(): Observable<T> {
    return this.flatMap(BaseFunction())
}

fun <T> Observable<BaseRespone<T>>.convertPaged(): Observable<BaseRespone<T>> {
    return this.flatMap(BasePageFunction())
}

/*
    扩展Boolean类型数据转换
*/
fun <T> Observable<BaseRespone<T>>.convertBoolean(): Observable<Boolean> {
    return this.flatMap(BaseFunctionBoolean())
}

/*
    修改TabLayout宽度
 */
fun TabLayout.setIndicator(leftDip: Int, rightDip: Int) {
    try {
        val tabStrip = this.javaClass.getDeclaredField("mTabStrip")
        tabStrip.isAccessible = true
        var llTab: LinearLayout? = null
        llTab = tabStrip.get(this) as LinearLayout
        val left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip.toFloat(), Resources.getSystem().displayMetrics).toInt()
        val right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip.toFloat(), Resources.getSystem().displayMetrics).toInt()

        for (i in 0 until llTab.childCount) {
            val child = llTab.getChildAt(i)
            child.setPadding(0, 0, 0, 0)
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            params.leftMargin = left
            params.rightMargin = right
            child.layoutParams = params
            child.invalidate()
        }
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: NullPointerException) {
        e.printStackTrace()
    }

}

fun dip2px(dpValue: Float): Int {
    val scale = BaseApplication.context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}


fun BottomNavigationBar.setBottomNavigationItem(space: Int, imgLen: Int, textSize: Int) {
    val barClass = this.javaClass
    val fields = barClass.declaredFields
    for (i in fields.indices) {
        val field = fields[i]
        field.isAccessible = true
        if (field.name == "mTabContainer") {
            try {
                //反射得到 mTabContainer
                val mTabContainer = field.get(this) as LinearLayout
                for (j in 0 until mTabContainer.childCount) {
                    //获取到容器内的各个Tab
                    val view = mTabContainer.getChildAt(j)
                    //获取到Tab内的各个显示控件
                    var params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(56f))
                    val container = view.findViewById<View>(R.id.fixed_bottom_navigation_container) as FrameLayout
                    container.layoutParams = params
                    container.setPadding(dip2px(12f), dip2px(0f), dip2px(12f), dip2px(0f))

                    //获取到Tab内的文字控件
                    val labelView = view.findViewById<View>(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title) as TextView
                    //计算文字的高度DP值并设置，setTextSize为设置文字正方形的对角线长度，所以：文字高度（总内容高度减去间距和图片高度）*根号2即为对角线长度，此处用DP值，设置该值即可。
                    labelView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize.toFloat())
                    labelView.includeFontPadding = false
                    labelView.setPadding(0, 0, 0, dip2px((20 - textSize - space / 2).toFloat()))

                    //获取到Tab内的图像控件
                    val iconView = view.findViewById<View>(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_icon) as ImageView
                    //设置图片参数，其中，MethodUtils.dip2px()：换算dp值
                    params = FrameLayout.LayoutParams(dip2px(imgLen.toFloat()), dip2px(imgLen.toFloat()))
                    params.setMargins(0, 0, 0, space / 2)
                    params.gravity = Gravity.CENTER or Gravity.CENTER_HORIZONTAL
                    iconView.layoutParams = params
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
    }
}

private val metrics = Resources.getSystem().displayMetrics

/**
 * 正常编码中一般只会用到 [dp]/[sp] 和 [px] ;
 * 其中[dp]/[sp] 会根据系统分辨率将输入的dp/sp值转换为对应的px
 * 而[px]只是返回自身，目的是表明自己是px值
 */
val Float.dp: Float      // [xxhdpi](360 -> 1080)
    get() = TypedValue.applyDimension(COMPLEX_UNIT_DIP, this, metrics)

val Int.dp: Int      // [xxhdpi](360 -> 1080)
    get() = TypedValue.applyDimension(COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()

val Float.sp: Float      // [xxhdpi](360 -> 1080)
    get() = TypedValue.applyDimension(COMPLEX_UNIT_SP, this, metrics)

val Int.sp: Int      // [xxhdpi](360 -> 1080)
    get() = TypedValue.applyDimension(COMPLEX_UNIT_SP, this.toFloat(), metrics).toInt()

val Number.px: Number      // [xxhdpi](360 -> 360)
    get() = this

/**
 * 在(可能存在的?)某些特殊情况会需要将px值转换为对应的dp/sp
 * 对应方法[Number.px2dp]/[Number.px2sp]
 */
val Number.px2dp: Int       // [xxhdpi](360 -> 120)
    get() = (this.toFloat() / metrics.density).toInt()

val Number.px2sp: Int       // [xxhdpi](360 -> 120)
    get() = (this.toFloat() / metrics.scaledDensity).toInt()
