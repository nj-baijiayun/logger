package com.nj.baijiayun.logger.printer

import android.util.Log

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.printer
 * @author houyi QQ:1007362137
 * @time 2019-09-10 17:09
 * @describe 同时使用两种printer进行打印，使用何种printer根据设定的优先级来进行
 */
class MultiPrinter(val firstPrinter: IPrinter,
                   val secondPrinter: IPrinter,
                   val firstPriority: Int = Log.VERBOSE,
                   val secondPriority: Int = Log.VERBOSE) : IPrinter {


    override fun print(priority: Int, tag: String?, msg: String?) {
        if (priority >= firstPriority) {
            firstPrinter.print(priority, tag, msg)
        }
        if (priority >= secondPriority) {
            secondPrinter.print(priority, tag, msg)
        }
    }
}