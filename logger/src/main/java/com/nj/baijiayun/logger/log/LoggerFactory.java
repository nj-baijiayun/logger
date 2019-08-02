package com.nj.baijiayun.logger.log;


import com.nj.baijiayun.logger.intercepter.OkHttpLogInterceptor;
import com.nj.baijiayun.logger.printer.PrinterFactory;
import com.nj.baijiayun.logger.printer.TagPrinter;
import com.nj.baijiayun.logger.strategy.PrettyFormatStrategy;
import com.nj.baijiayun.logger.strategy.Strategy;


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
class LoggerFactory {
    public static ILogger getDefaultLogger(String sTag) {
        Strategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(1)
                .logPrinter(PrinterFactory.getPrinter(sTag))
                .methodOffset(0)
                .build();
        return new LoggerImpl(formatStrategy);
    }

    public static OkHttpLogInterceptor getOkHttpInterceptor(String tag) {
        return new OkHttpLogInterceptor(PrinterFactory.getPrinter(tag));
    }
}
