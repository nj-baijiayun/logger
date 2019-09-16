package com.nj.baijiayun.logger.log

import android.content.Context
import android.os.Looper
import android.support.annotation.MainThread
import android.util.Log

import com.nj.baijiayun.logger.intercepter.OkHttpLogInterceptor
import com.nj.baijiayun.logger.printer.FilePrinter
import com.nj.baijiayun.logger.utils.Utils.getStackTraceString
import com.nj.baijiayun.logger.utils.Utils.isEmpty

object Logger {

    lateinit var sAppContext: Context
    private var filePrinter: FilePrinter? = null
    //==============常量================//
    private var sIsFileLogEnable: Boolean = true
    /**
     * 默认tag
     */
    val DEFAULT_FULL_LOG_TAG = "[Logger]"
    /**
     * 最大日志优先级【日志优先级为最大等级，所有日志都不打印】
     */
    private val MAX_LOG_PRIORITY = Log.ASSERT
    /**
     * 最小日志优先级【日志优先级为最小等级，所有日志都打印】
     */
    val MIN_LOG_PRIORITY = Log.VERBOSE

    //==============属性================//
    /**
     * 默认的日志记录为Logcat
     */
    private var sILogger: ILogger? = null

    private var sFullTag = DEFAULT_FULL_LOG_TAG
    /**
     * 是否打开log
     */
    private var sIsEnable = false
    /**
     * 日志打印优先级
     */
    private var sLogPriority = MAX_LOG_PRIORITY
    private var isInit: Boolean = false
    private var crashLoggerHandler: CrashLoggerHandler? = null

    //==============属性设置================//

    /**
     * 设置日志记录者的接口
     *
     * @param logger
     */
    private var logger: ILogger?
        get() {
            if (!isInit) {
                throw IllegalStateException("you should init Logger with Logger.init() method before " + "any Log method invoke! ")
            }
            return sILogger
        }
        set(logger) {
            sILogger = logger
        }

    @JvmStatic
    val okHttpInterceptor: OkHttpLogInterceptor
        get() {
            if (!isInit) {
                throw IllegalStateException("you should init Logger with Logger.init() method before " + "any Log method invoke! ")
            }
            return LoggerFactory.getOkHttpInterceptor(sFullTag)
        }

    /**
     * 设置日志的tag
     *
     * @param tag
     */
    @Deprecated("请使用init方法设置tag")
    @JvmStatic
    fun setTag(tag: String) {
        sFullTag = tag
    }

    /**
     * 设置是否是调试模式
     *
     * @param isEnable
     */
    @Deprecated("请使用init方法设置是否支持调试模式")
    @JvmStatic
    fun setEnable(isEnable: Boolean) {
        sIsEnable = isEnable
    }

    /**
     * 设置打印日志的等级（只打印改等级以上的日志）
     *
     * @param priority
     */
    @JvmStatic
    fun setPriority(priority: Int) {
        sLogPriority = priority
    }

    //=============打印方法===============//

    /**
     * 打印任何（所有）信息
     *
     * @param msg
     */
    @JvmStatic
    fun v(msg: String) {
        if (enableLog(Log.VERBOSE)) {
            logger!!.log(Log.VERBOSE, sFullTag, msg, null)
        }
    }

    /**
     * 初始化Logger
     */
    @Deprecated("请使用其他多参数的初始化方法")
    @MainThread
    @JvmStatic
    fun init(context: Context) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw IllegalThreadStateException("you should init Logger in the main thread!")
        }
        if (sILogger == null) {
            isInit = true
            sILogger = LoggerFactory.getDefaultLogger(sFullTag)
            crashLoggerHandler = CrashLoggerHandler()
            crashLoggerHandler!!.init()
        }
    }

    /**
     * 初始化Logger
     *
     * @param tag 日志tag
     * @param logEnable  logcat日志开关
     * @param fileLogEnable 文件错误日志开关
     */
    @MainThread
    @JvmStatic
    fun init(context: Context,tag: String = "[Logger]", logEnable: Boolean = false, fileLogEnable: Boolean = true) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw IllegalThreadStateException("you should init Logger in the main thread!")
        }
        sAppContext = context
        sFullTag = tag
        sIsEnable = logEnable
        sIsFileLogEnable = fileLogEnable
        if (sILogger == null) {
            isInit = true
            sILogger = LoggerFactory.getDefaultLogger(sFullTag)
            crashLoggerHandler = CrashLoggerHandler()
            crashLoggerHandler!!.init()
        }
        if (fileLogEnable) {
            filePrinter = FilePrinter()
        }
    }


    /**
     * 打印任何（所有）信息
     *
     * @param tag
     * @param msg
     */
    @JvmStatic
    fun vTag(tag: String, msg: String) {
        if (enableLog(Log.VERBOSE)) {
            logger!!.log(Log.VERBOSE, tag, msg, null)
        }
    }

    /**
     * 打印调试信息
     *
     * @param msg
     */
    @JvmStatic
    fun d(msg: String) {
        if (enableLog(Log.DEBUG)) {
            logger!!.log(Log.DEBUG, msg, null)
        }
    }

    /**
     * 打印调试信息
     *
     * @param tag
     * @param msg
     */
    @JvmStatic
    fun dTag(tag: String, msg: String) {
        if (enableLog(Log.DEBUG)) {
            logger!!.log(Log.DEBUG, tag, msg, null)
        }
    }

    /**
     * 打印提示性的信息
     *
     * @param msg
     */
    @JvmStatic
    fun i(msg: String) {
        if (enableLog(Log.INFO)) {
            logger!!.log(Log.INFO, msg, null)
        }
    }

    /**
     * 打印提示性的信息
     *
     * @param tag
     * @param msg
     */
    @JvmStatic
    fun iTag(tag: String, msg: String) {
        if (enableLog(Log.INFO)) {
            logger!!.log(Log.INFO, tag, msg, null)
        }
    }

    /**
     * 打印warning警告信息
     *
     * @param msg
     */
    @JvmStatic
    fun w(msg: String) {
        if (enableLog(Log.WARN)) {
            logger!!.log(Log.WARN, msg, null)
        }
    }

    /**
     * 打印warning警告信息
     *
     * @param tag
     * @param msg
     */
    @JvmStatic
    fun wTag(tag: String, msg: String) {
        if (enableLog(Log.WARN)) {
            logger!!.log(Log.WARN, tag, msg, null)
        }
    }

    /**
     * 打印出错信息
     *
     * @param msg
     */
    @JvmStatic
    fun e(msg: String) {
        eTag(null, msg, null)
    }

    /**
     * 打印出错信息
     *
     * @param tag
     * @param msg
     */
    @JvmStatic
    fun eTag(tag: String, msg: String) {
        eTag(tag, msg, null)

    }

    /**
     * 打印出错堆栈信息
     *
     * @param t
     */
    @JvmStatic
    fun e(t: Throwable) {
        eTag(null, null, t)
    }


    /**
     * 打印出错堆栈信息
     *
     * @param tag
     * @param t
     */
    @JvmStatic
    fun eTag(tag: String, t: Throwable) {
        eTag(tag, null, t)
    }

    /**
     * 打印出错堆栈信息
     *
     * @param msg
     * @param t
     */
    @JvmStatic
    fun e(msg: String, t: Throwable) {
        eTag(null, msg, t)
    }

    /**
     * 打印出错堆栈信息
     *
     * @param tag
     * @param msg
     * @param t
     */
    @JvmStatic
    fun eTag(tag: String?, msg: String?, t: Throwable?) {
        if (enableLog(Log.ERROR)) {
            logger!!.log(Log.ERROR, tag, msg, t)
        }
        var message = msg
        if (sIsFileLogEnable) {
            if (t != null && msg != null) {
                message += " : " + getStackTraceString(t)
            }
            if (t != null && msg == null) {
                message = getStackTraceString(t)
            }
            if (isEmpty(message)) {
                message = "Empty/NULL log message"
            }
            d(message!!)
            filePrinter?.print(Log.ERROR,tag,message)
        }
    }

    /**
     * 能否打印
     *
     * @param logPriority
     * @return
     */
    private fun enableLog(logPriority: Int): Boolean {
        return logger != null && sIsEnable && logPriority >= sLogPriority
    }
}