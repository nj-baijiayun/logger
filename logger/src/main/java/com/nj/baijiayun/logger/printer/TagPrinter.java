package com.nj.baijiayun.logger.printer;

import android.support.annotation.Nullable;

import com.nj.baijiayun.logger.tag.TagMaker;
import com.nj.baijiayun.logger.utils.Utils;

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.printer
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/30 9:31 AM
 * @change
 * @time
 * @describe
 */
public class TagPrinter implements IPrinter {
    private final LogcatPrinter printer;
    private final String tag;

    public TagPrinter(String tag) {
        this.printer = new LogcatPrinter();
        this.tag = tag;
    }

    @Override
    public void print(int priority, String onceOnlyTag, String msg) {
        printer.print(priority, formatTag(onceOnlyTag), msg);
    }

    @Nullable
    public String formatTag(@Nullable String tag) {
        if (!Utils.isEmpty(tag) && !Utils.equals(this.tag, tag)) {
            return this.tag + "-" + tag;
        }
        return this.tag;
    }
}
