package com.nj.baijiayun.logger.tag;

import android.support.annotation.Nullable;

import com.nj.baijiayun.logger.utils.Utils;

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.tag
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/30 9:39 AM
 * @change
 * @time
 * @describe
 */
public class TagMaker {
    private String tag;

    @Nullable
    public String formatTag(@Nullable String tag) {
        if (!Utils.isEmpty(tag) && !Utils.equals(this.tag, tag)) {
            return this.tag + "-" + tag;
        }
        return this.tag;
    }
}
