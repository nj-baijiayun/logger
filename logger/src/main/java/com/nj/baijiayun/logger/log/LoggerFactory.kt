package com.nj.baijiayun.logger.log


import com.nj.baijiayun.logger.intercepter.OkHttpLogInterceptor
import com.nj.baijiayun.logger.printer.PrinterFactory
import com.nj.baijiayun.logger.strategy.PrettyFormatStrategy


/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.log
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/29 4:28 PM
 * @change
 * @time
 * @describe
 */
internal object LoggerFactory {
    fun getDefaultLogger(sTag: String): ILogger? {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(1)
                .logPrinter(PrinterFactory.getPrinter(sTag))
                .methodOffset(0)
                .build()
        return LoggerImpl(formatStrategy)
    }

    fun getOkHttpInterceptor(tag: String): OkHttpLogInterceptor {
        return OkHttpLogInterceptor(PrinterFactory.getPrinter(tag))
    }

}
