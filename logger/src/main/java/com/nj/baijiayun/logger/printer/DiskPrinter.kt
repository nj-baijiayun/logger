package com.nj.baijiayun.logger.printer

import android.os.Environment
import com.nj.baijiayun.logger.log.Logger
import com.nj.baijiayun.logger.utils.Utils
import java.io.*
import java.util.*

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.printer
 * @author houyi QQ:1007362137
 * @time 2019-09-10 16:44
 * @describe 文件错误日志，除了错误信息外还会打印部分系统信息
 */
class DiskPrinter : IPrinter {
    private val logFile: File = File(Logger.sAppContext.getExternalFilesDir("Log"), Utils.dataTime(Date()) + ".log")
    private val deviceInfo: String = Utils.getDeviceInfo(Logger.sAppContext)

    override fun print(priority: Int, tag: String?, msg: String?) {
        Logger.d("print crash")
        val sb = StringBuffer()
        sb.append("------------------crash----------------------")
        sb.append("\r\n")
        sb.append(msg)
        sb.append("\r\n")
        sb.append("----------------deviceInfo-------------------")
        sb.append("\r\n")
        sb.append(deviceInfo)
        sb.append("\r\n")
        sb.append("-------------------end-----------------------")
        sb.append("\r\n")
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            if (!logFile.parentFile.exists()) {
                logFile.parentFile.mkdirs()
            }
            logFile.createNewFile();
            val fw = FileWriter(logFile, true)
            val bw = BufferedWriter(fw)
            //写入相关Log到文件
            bw.write(sb.toString())
            bw.newLine()
            bw.close()
            fw.close()
            Logger.d("print crash finish")

        }
    }


}