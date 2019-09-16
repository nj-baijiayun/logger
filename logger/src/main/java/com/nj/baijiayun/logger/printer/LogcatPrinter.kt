package com.nj.baijiayun.logger.printer

import android.util.Log

import com.nj.baijiayun.logger.utils.Utils.checkNotNull


/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.priter
 * @describe 使用logcat打印log
 * @anthor houyi QQ:1007362137
 * @time 2019/4/29 5:31 PM
 */
internal class LogcatPrinter : IPrinter {

    /**
     * 使用logcat打印log，logcat单条log最长只有2048个字符，这2048个字符把tag等额外信息也包含在内了
     */
    override fun print(priority: Int, tag: String?, msg: String?) {
        checkNotNull(msg)

        val originLength = msg!!.length

        //当原始log小于1990个字符时直接打印
        if (originLength < MAX_LOG_LENGTH + 90) {
            printActual(priority, tag, msg)
            return
        }

        //log起始位置
        var start = 0
        //换行符字符数
        val separatorOffset = System.lineSeparator().length - 1
        //剩余未打印的字符数量
        var lastLength = msg.length
        //当原始log远大于最大字符长度时循环打印打印
        while (lastLength > MAX_LOG_LENGTH) {
            //找到从最大长度向前的第一个换行符号作为单条log终点
            val index = msg.substring(start, start + MAX_LOG_LENGTH).lastIndexOf(System.lineSeparator(), start + MAX_LOG_LENGTH - 100)
            val nextStart: Int = if (index <= 0) {
                start + MAX_LOG_LENGTH - separatorOffset
            } else {
                start + index - separatorOffset
            }
            var logString = msg.substring(start, nextStart)
            if (start != 0) {
                logString = "|$logString"
            }
            printActual(priority, tag, logString)
            start = nextStart
            lastLength = originLength - start
        }

        printActual(priority, tag, "|" + msg.substring(start))
    }

    private fun printActual(priority: Int, tag: String?, msg: String?): Int {
        return Log.println(priority, tag, msg)
    }

    companion object {
        /**
         * 单条log最大长度
         */
        val MAX_LOG_LENGTH = 1900
    }
}
