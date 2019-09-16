package com.nj.baijiayun.logger.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.util.Log.*
import java.io.PrintWriter
import java.io.StringWriter
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Date

import com.nj.baijiayun.logger.log.Logger
import java.lang.reflect.Field

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.utils
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/29 5:48 PM
 * @change
 * @time
 * @describe
 */
object Utils {

    @JvmField
    val lineSeparator = System.lineSeparator()

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    @JvmStatic
    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.length == 0
    }

    /**
     * Returns true if a and b are equal, including if they are both null.
     *
     * *Note: In platform versions 1.1 and earlier, this method only worked well if
     * both the arguments were instances of String.*
     *
     * @param a first CharSequence to check
     * @param b second CharSequence to check
     * @return true if a and b are equal
     *
     *
     * NOTE: Logic slightly change due to strict policy on CI -
     * "Inner assignments should be avoided"
     */
    @JvmStatic
    fun equals(a: CharSequence?, b: CharSequence?): Boolean {
        if (a === b) return true
        if (a != null && b != null) {
            val length = a.length
            if (length == b.length) {
                if (a is String && b is String) {
                    return a == b
                } else {
                    for (i in 0 until length) {
                        if (a[i] != b[i]) return false
                    }
                    return true
                }
            }
        }
        return false
    }

    /**
     * Copied from "android.util.Log.getStackTraceString()" in order to avoid usage of Android stack
     * in unit tests.
     *
     * @return Stack trace in form of String
     */
    @JvmStatic
    fun getStackTraceString(tr: Throwable?): String {
        if (tr == null) {
            return ""
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        var t = tr
        while (t != null) {
            if (t is UnknownHostException) {
                return ""
            }
            t = t.cause
        }

        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    @JvmStatic
    fun logLevel(value: Int): String {
        when (value) {
            VERBOSE -> return "VERBOSE"
            DEBUG -> return "DEBUG"
            INFO -> return "INFO"
            WARN -> return "WARN"
            ERROR -> return "ERROR"
            ASSERT -> return "ASSERT"
            else -> return "UNKNOWN"
        }
    }

    @JvmStatic
    fun toString(`object`: Any?): String {
        if (`object` == null) {
            return "null"
        }
        if (!`object`.javaClass.isArray) {
            return `object`.toString()
        }
        if (`object` is BooleanArray) {
            return Arrays.toString(`object` as BooleanArray?)
        }
        if (`object` is ByteArray) {
            return Arrays.toString(`object` as ByteArray?)
        }
        if (`object` is CharArray) {
            return Arrays.toString(`object` as CharArray?)
        }
        if (`object` is ShortArray) {
            return Arrays.toString(`object` as ShortArray?)
        }
        if (`object` is IntArray) {
            return Arrays.toString(`object` as IntArray?)
        }
        if (`object` is LongArray) {
            return Arrays.toString(`object` as LongArray?)
        }
        if (`object` is FloatArray) {
            return Arrays.toString(`object` as FloatArray?)
        }
        if (`object` is DoubleArray) {
            return Arrays.toString(`object` as DoubleArray?)
        }
        return if (`object` is Array<*>) {
            Arrays.deepToString(`object` as Array<Any>?)
        } else "Couldn't find a correct type for the object"
    }

    @JvmStatic
    fun <T> checkNotNull(obj: T?): T {
        if (obj == null) {
            throw NullPointerException()
        }
        return obj
    }


    @JvmStatic
    fun dataTime(date: Date): String {
        return SimpleDateFormat("yyyy-MM-dd").format(date)
    }

    @JvmStatic
    fun getDeviceInfo(context: Context): String {
        val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        val mapInfo = HashMap<String, String>();
        if (info != null) {
            mapInfo["versionName"] = info.versionName
            mapInfo["versionCode"] = info.versionCode.toString()
        }
        val declaredFields = Build::class.java.declaredFields
        for (declaredField in declaredFields) {
            declaredField.isAccessible = true;
            mapInfo[declaredField.name] = declaredField.get(null).toString();
        }
        return mapInfo.toString()
    }
}// Hidden constructor.
