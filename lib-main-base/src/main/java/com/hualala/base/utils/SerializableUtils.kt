package com.hualala.base.utils

import android.content.Context
import java.io.*

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 序列化和反序列化操作类
 */

object SerializableUtils {
    /**
     * 序列化数据
     *
     * @param context
     * @param fileName
     * @param obj
     * @throws IOException
     */
    @Throws(IOException::class)
    fun serializeData(context: Context, fileName: String, obj: Any) {
        if (obj !is Serializable && obj !is Externalizable) {
            throw InvalidClassException("Object must be serialized!")
        }
        val fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        val ostream = ObjectOutputStream(fos)
        ostream.writeObject(obj)
        ostream.flush()
        ostream.close()
        fos.close()
    }

    /**
     * 反序列化数据
     *
     * @param context
     * @param fileName
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @Throws(ClassNotFoundException::class, IOException::class)
    fun deserializeData(context: Context, fileName: String): Any {
        val fis = context.openFileInput(fileName)
        val s = ObjectInputStream(fis)
        val obj = s.readObject()
        s.close()
        fis.close()
        return obj
    }

}
