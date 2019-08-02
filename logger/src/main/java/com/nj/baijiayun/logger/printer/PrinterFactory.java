package com.nj.baijiayun.logger.printer;

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.printer
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/29 5:42 PM
 * @change
 * @time
 * @describe
 */
public class PrinterFactory {

    private static final IPrinter DEFAULT_PRINTER = new LogcatPrinter();

    public static IPrinter getDefaultPrinter() {
        return DEFAULT_PRINTER;
    }

    public static IPrinter getPrinter(String tag){
        return new TagPrinter(tag);
    }
}
