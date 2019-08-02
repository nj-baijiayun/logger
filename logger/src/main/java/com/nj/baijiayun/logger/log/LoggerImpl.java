package com.nj.baijiayun.logger.log;


import com.nj.baijiayun.logger.strategy.Strategy;

import static com.nj.baijiayun.logger.utils.Utils.getStackTraceString;
import static com.nj.baijiayun.logger.utils.Utils.isEmpty;

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.log
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/29 5:50 PM
 * @change
 * @time
 * @describe
 */
class LoggerImpl implements ILogger {
    private final Strategy strategy;

    public LoggerImpl(Strategy formatStrategy) {
        strategy = formatStrategy;
    }

    @Override
    public void log(int priority, String message, Throwable t) {
        log(priority, null, message, t);
    }

    @Override
    public void log(int priority, String onceOnlyTag, String message, Throwable t) {
        if (t != null && message != null) {
            message += " : " + getStackTraceString(t);
        }
        if (t != null && message == null) {
            message = getStackTraceString(t);
        }
        if (isEmpty(message)) {
            message = "Empty/NULL log message";
        }
        strategy.log(priority, onceOnlyTag, message);
    }
}
