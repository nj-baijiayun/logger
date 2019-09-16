package com.nj.baijiayun.logger.printer

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.priter
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/29 5:32 PM
 * @change
 * @time
 * @describe
 */
interface IPrinter {

    fun print(priority: Int, tag: String?, msg: String?)

    companion object {
        val DEFAULT_TAG = "Logger"
    }
}
