package com.dysen.baselib.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import com.dysen.baselib.utils.DateUtils.dateSimpleFormat
import com.dysen.baselib.utils.DateUtils.getNormalDateString
import com.dysen.baselib.utils.DateUtils.getOtherDay
import com.dysen.baselib.utils.DateUtils.getOtherFormat
import java.io.*
import java.util.*

/**
 * Created by dysen on 2017/9/14.
 */
class FileUtils private constructor() {
    /**
     * 根据对象获取属性名
     *
     * @param o
     * @return
     */
    fun getFiledName(o: Any): List<String> {
        val fields = o.javaClass.declaredFields
        val fieldNames: MutableList<String> = ArrayList()
        for (i in fields.indices) {
            fieldNames.add(fields[i].name)
            // fields[i].getType();
        }
        return fieldNames
    }

    /**
     * 根据属性值获取值
     *
     * @param fieldName
     * @param o
     * @return
     */
    fun getFieldValueByName(fieldName: String, o: Any): Any? {
        return try {
            val firstLetter = fieldName.substring(0, 1).toUpperCase(Locale.CHINA)
            val getter = "get" + firstLetter + fieldName.substring(1)
            val method = o.javaClass.getMethod(getter, *arrayOf())
            method.invoke(o)
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private var isSaveSucc = false

        /**
         * 分隔符.
         */
        const val FILE_EXTENSION_SEPARATOR = "."

        /**
         * "/"
         */
        val SEP = File.separator

        /**
         * SD卡根目录
         */
        val SDPATH = Environment
            .getExternalStorageDirectory().toString() + File.separator
        /**
         * 读取文件的内容
         *
         * @param filePath    文件目录
         * @param charsetName 字符编码
         * @return String字符串
         */
        /**
         * 读取文件的内容
         * <br></br>
         * 默认utf-8编码
         *
         * @param filePath 文件路径
         * @return 字符串
         * @throws IOException
         */
        @JvmOverloads
        @Throws(IOException::class)
        fun readFile(filePath: String?, charsetName: String? = "utf-8"): String? {
            var charsetName = charsetName
            if (TextUtils.isEmpty(filePath)) return null
            if (TextUtils.isEmpty(charsetName)) charsetName = "utf-8"
            val file = File(filePath)
            val fileContent = StringBuilder("")
            if (file == null || !file.isFile) return null
            var reader: BufferedReader? = null
            return try {
                val `is` = InputStreamReader(
                    FileInputStream(
                        file
                    ), charsetName
                )
                reader = BufferedReader(`is`)
                var line: String? = null
                while (reader.readLine().also { line = it } != null) {
                    if (fileContent.toString() != "") {
                        fileContent.append("\r\n")
                    }
                    fileContent.append(line)
                }
                fileContent.toString()
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        /**
         * 读取文本文件到List字符串集合中
         *
         * @param filePath    文件目录
         * @param charsetName 字符编码
         * @return 文件不存在返回null，否则返回字符串集合
         */
        /**
         * 读取文本文件到List字符串集合中(默认utf-8编码)
         *
         * @param filePath 文件目录
         * @return 文件不存在返回null，否则返回字符串集合
         * @throws IOException
         */
        @JvmOverloads
        @Throws(IOException::class)
        fun readFileToList(
            filePath: String?,
            charsetName: String? = "utf-8"
        ): List<String?>? {
            var charsetName = charsetName
            if (TextUtils.isEmpty(filePath)) return null
            if (TextUtils.isEmpty(charsetName)) charsetName = "utf-8"
            val file = File(filePath)
            val fileContent: MutableList<String?> = ArrayList()
            if (file == null || !file.isFile) {
                return null
            }
            var reader: BufferedReader? = null
            return try {
                val `is` = InputStreamReader(
                    FileInputStream(
                        file
                    ), charsetName
                )
                reader = BufferedReader(`is`)
                var line: String? = null
                while (reader.readLine().also { line = it } != null) {
                    fileContent.add(line)
                }
                fileContent
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        /**
         * 向文件中写入数据
         *
         * @param filePath 文件目录
         * @param content  要写入的内容
         * @param append   如果为 true，则将数据写入文件末尾处，而不是写入文件开始处
         * @return 写入成功返回true， 写入失败返回false
         * @throws IOException
         */
        @Throws(IOException::class)
        fun writeFile(
            filePath: String?, content: String?,
            append: Boolean
        ): Boolean {
            if (TextUtils.isEmpty(filePath)) return false
            if (TextUtils.isEmpty(content)) return false
            var fileWriter: FileWriter? = null
            return try {
                createFile(filePath)
                fileWriter = FileWriter(filePath, append)
                fileWriter.write(content)
                fileWriter.flush()
                true
            } finally {
                if (fileWriter != null) {
                    try {
                        fileWriter.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        /**
         * 向文件中写入数据
         *
         * @param filePath 文件目录
         * @param stream   字节输入流
         * @param append   如果为 true，则将数据写入文件末尾处；
         * 为false时，清空原来的数据，从头开始写
         * @return 写入成功返回true，否则返回false
         * @throws IOException
         */
        /**
         * 向文件中写入数据<br></br>
         * 默认在文件开始处重新写入数据
         *
         * @param filePath 文件目录
         * @param stream   字节输入流
         * @return 写入成功返回true，否则返回false
         * @throws IOException
         */
        @JvmOverloads
        @Throws(IOException::class)
        fun writeFile(
            filePath: String?, stream: InputStream?,
            append: Boolean = false
        ): Boolean {
            if (TextUtils.isEmpty(filePath)) throw NullPointerException("filePath is Empty")
            if (stream == null) throw NullPointerException("InputStream is null")
            return writeFile(
                File(filePath), stream,
                append
            )
        }
        /**
         * 向文件中写入数据
         *
         * @param file   指定文件
         * @param stream 字节输入流
         * @param append 为true时，在文件开始处重新写入数据；
         * 为false时，清空原来的数据，从头开始写
         * @return 写入成功返回true，否则返回false
         * @throws IOException
         */
        /**
         * 向文件中写入数据
         * 默认在文件开始处重新写入数据
         *
         * @param file   指定文件
         * @param stream 字节输入流
         * @return 写入成功返回true，否则返回false
         * @throws IOException
         */
        @JvmOverloads
        @Throws(IOException::class)
        fun writeFile(
            file: File?, stream: InputStream,
            append: Boolean = false
        ): Boolean {
            if (file == null) throw NullPointerException("file = null")
            var out: OutputStream? = null
            return try {
                createFile(file.absolutePath)
                out = FileOutputStream(file, append)
                val data = ByteArray(1024)
                var length = -1
                while (stream.read(data).also { length = it } != -1) {
                    out.write(data, 0, length)
                }
                out.flush()
                true
            } finally {
                if (out != null) {
                    try {
                        out.close()
                        stream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        /**
         * 复制文件
         *
         * @param sourceFilePath 源文件目录（要复制的文件目录）
         * @param destFilePath   目标文件目录（复制后的文件目录）
         * @return 复制文件成功返回true，否则返回false
         * @throws IOException
         */
        @Throws(IOException::class)
        fun copyFile(sourceFilePath: String?, destFilePath: String?): Boolean {
            var inputStream: InputStream? = null
            inputStream = FileInputStream(sourceFilePath)
            return writeFile(destFilePath, inputStream)
        }

        /**
         * 获取某个目录下的文件名
         *
         * @param dirPath    目录
         * @param fileFilter 过滤器
         * @return 某个目录下的所有文件名
         */
        fun getFileNameList(
            dirPath: String?,
            fileFilter: FilenameFilter?
        ): List<String> {
            if (fileFilter == null) return getFileNameList(dirPath)
            if (TextUtils.isEmpty(dirPath)) return emptyList()
            val dir = File(dirPath)
            val files = dir.listFiles(fileFilter) ?: return emptyList()
            val conList: MutableList<String> = ArrayList()
            for (file in files) {
                if (file.isFile) conList.add(file.name)
            }
            return conList
        }

        /**
         * 获取某个目录下的文件名
         *
         * @param dirPath 目录
         * @return 某个目录下的所有文件名
         */
        fun getFileNameList(dirPath: String?): List<String> {
            if (TextUtils.isEmpty(dirPath)) return emptyList()
            val dir = File(dirPath)
            val files = dir.listFiles() ?: return emptyList()
            val conList: MutableList<String> = ArrayList()
            for (file in files) {
                if (file.isFile) conList.add(file.name)
            }
            return conList
        }

        /**
         * 获取某个目录下的指定扩展名的文件名称
         *
         * @param dirPath 目录
         * @return 某个目录下的所有文件名
         */
        fun getFileNameList(
            dirPath: String?,
            extension: String
        ): List<String> {
            if (TextUtils.isEmpty(dirPath)) return emptyList()
            val dir = File(dirPath)
            val files = dir.listFiles { dir, filename -> if (filename.indexOf(".$extension") > 0) true else false }
                ?: return emptyList()
            val conList: MutableList<String> = ArrayList()
            for (file in files) {
                if (file.isFile) conList.add(file.name)
            }
            return conList
        }

        /**
         * 获得文件的扩展名
         *
         * @param filePath 文件路径
         * @return 如果没有扩展名，返回""
         */
        fun getFileExtension(filePath: String): String {
            if (TextUtils.isEmpty(filePath)) {
                return filePath
            }
            val extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR)
            val filePosi = filePath.lastIndexOf(File.separator)
            if (extenPosi == -1) {
                return ""
            }
            return if (filePosi >= extenPosi) "" else filePath.substring(extenPosi + 1)
        }

        /**
         * 创建文件
         *
         * @param path 文件的绝对路径
         * @return
         */
        fun createFile(path: String?): Boolean {
            return if (TextUtils.isEmpty(path)) false else createFile(File(path))
        }

        /**
         * 创建文件
         *
         * @param file
         * @return 创建成功返回true
         */
        fun createFile(file: File?): Boolean {
            if (file == null || !makeDirs(getFolderName(file.absolutePath))) return false
            return if (!file.exists()) try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                false
            } else false
        }

        /**
         * 创建目录（可以是多个）
         *
         * @param filePath 目录路径
         * @return 如果路径为空时，返回false；如果目录创建成功，则返回true，否则返回false
         */
        fun makeDirs(filePath: String?): Boolean {
            if (TextUtils.isEmpty(filePath)) {
                return false
            }
            val folder = File(filePath)
            return if (folder.exists() && folder.isDirectory) true else folder
                .mkdirs()
        }

        /**
         * 创建目录（可以是多个）
         *
         * @param dir 目录
         * @return 如果目录创建成功，则返回true，否则返回false
         */
        fun makeDirs(dir: File?): Boolean {
            if (dir == null) return false
            return if (dir.exists() && dir.isDirectory) true else dir.mkdirs()
        }

        /**
         * 判断文件是否存在
         *
         * @param filePath 文件路径
         * @return 如果路径为空或者为空白字符串，就返回false；如果文件存在，且是文件，
         * 就返回true；如果不是文件或者不存在，则返回false
         */
        fun isFileExist(filePath: String?): Boolean {
            if (TextUtils.isEmpty(filePath)) {
                return false
            }
            val file = File(filePath)
            return file.exists() && file.isFile
        }

        /**
         * 获得不带扩展名的文件名称
         *
         * @param filePath 文件路径
         * @return
         */
        fun getFileNameWithoutExtension(filePath: String): String {
            if (TextUtils.isEmpty(filePath)) {
                return filePath
            }
            val extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR)
            val filePosi = filePath.lastIndexOf(File.separator)
            if (filePosi == -1) {
                return if (extenPosi == -1) filePath else filePath.substring(
                    0,
                    extenPosi
                )
            }
            if (extenPosi == -1) {
                return filePath.substring(filePosi + 1)
            }
            return if (filePosi < extenPosi) filePath.substring(
                filePosi + 1,
                extenPosi
            ) else filePath.substring(filePosi + 1)
        }

        /**
         * 获得文件名
         *
         * @param filePath 文件路径
         * @return 如果路径为空或空串，返回路径名；不为空时，返回文件名
         */
        fun getFileName(filePath: String): String {
            if (TextUtils.isEmpty(filePath)) {
                return filePath
            }
            val filePosi = filePath.lastIndexOf(File.separator)
            return if (filePosi == -1) filePath else filePath.substring(filePosi + 1)
        }

        /**
         * 获得所在目录名称
         *
         * @param filePath 文件的绝对路径
         * @return 如果路径为空或空串，返回路径名；不为空时，如果为根目录，返回"";
         * 如果不是根目录，返回所在目录名称，格式如：C:/Windows/Boot
         */
        fun getFolderName(filePath: String): String {
            if (TextUtils.isEmpty(filePath)) {
                return filePath
            }
            val filePosi = filePath.lastIndexOf(File.separator)
            return if (filePosi == -1) "" else filePath.substring(0, filePosi)
        }

        /**
         * 判断目录是否存在
         *
         * @param directoryPath 目录路径
         * @return 如果路径为空或空白字符串，返回false；如果目录存在且，确实是目录文件夹，
         * 返回true；如果不是文件夹或者不存在，则返回false
         */
        fun isFolderExist(directoryPath: String?): Boolean {
            if (TextUtils.isEmpty(directoryPath)) {
                return false
            }
            val dire = File(directoryPath)
            return dire.exists() && dire.isDirectory
        }

        /**
         * 删除指定文件或指定目录内的所有文件
         *
         * @param path 文件或目录的绝对路径
         * @return 路径为空或空白字符串，false；文件不存在，返回true；文件删除返回true；
         * 文件删除异常返回false
         */
        fun deleteFile(path: String?): Boolean {
            return if (TextUtils.isEmpty(path)) {
                false
            } else deleteFile(File(path))
        }

        /**
         * 删除指定文件或指定目录内的所有文件
         *
         * @param file
         * @return 路径为空或空白字符串，返回true；文件不存在，返回true；文件删除返回true；
         * 文件删除异常返回false
         */
        fun deleteFile(file: File?): Boolean {
            if (file == null) throw NullPointerException("file is null")
            if (!file.exists()) {
                return true
            }
            if (file.isFile) {
                return file.delete()
            }
            if (!file.isDirectory) {
                return false
            }
            val files = file.listFiles() ?: return true
            for (f in files) {
                if (f.isFile) {
                    f.delete()
                } else if (f.isDirectory) {
                    deleteFile(f.absolutePath)
                }
            }
            return file.delete()
        }

        /**
         * 删除指定目录中特定的文件
         *
         * @param dir
         * @param filter
         */
        fun delete(dir: String?, filter: FilenameFilter?) {
            if (TextUtils.isEmpty(dir)) return
            val file = File(dir)
            if (!file.exists()) return
            if (file.isFile) file.delete()
            if (!file.isDirectory) return
            var lists: Array<File>? = null
            lists = if (filter != null) file.listFiles(filter) else file.listFiles()
            if (lists == null) return
            for (f in lists) {
                if (f.isFile) {
                    f.delete()
                }
            }
        }

        /**
         * 获得文件或文件夹的大小
         *
         * @param path 文件或目录的绝对路径
         * @return 返回当前目录的大小 ，注：当文件不存在，为空，或者为空白字符串，返回 -1
         */
        fun getFileSize(path: String?): Long {
            if (TextUtils.isEmpty(path)) {
                return -1
            }
            val file = File(path)
            return if (file.exists() && file.isFile) file.length() else -1
        }

        /**
         * 获取assets里文件的内容
         *
         * @param params fileName, charsetName
         * @return
         */
        fun get4Assets(context: Context, fileName: String?, vararg params: String?): String {
            val manager = context.resources.assets
            try {
                val inputStream = manager.open(fileName!!)
                val isr = InputStreamReader(
                    inputStream,
                    if (params.size > 0) params[0] else "UTF-8"
                )
                val br = BufferedReader(isr)
                val sb = StringBuilder()
                var length: String
                while (br.readLine().also { length = it } != null) {
                    sb.append(
                        """
    $length
    
    """.trimIndent()
                    )
                }
                //关流
                br.close()
                isr.close()
                inputStream.close()
                return sb.toString()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return ""
        }

        /**
         * 获取SD卡的路径
         *
         * @param newDir 新建的文件夹
         * @return
         */
        fun getSDdir(newDir: String): File {
            val dirPath = Environment.getExternalStorageDirectory().toString() + File.separator + newDir + File.separator
            //建立文件下载的目录
            val dir = File(dirPath)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            return dir
        }

        fun getPath(fileName: String): String {
            return Environment.getExternalStorageDirectory().toString() + File.separator + fileName + File.separator
        }

        /**
         * 保存方法
         *
         * @param bitmap
         * @return
         */
        fun saveBitmap(bitmap: Bitmap, f: File): Boolean {
            if (f.exists()) {
                f.delete()
            }
            try {
                val out = FileOutputStream(f)
                isSaveSucc = bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                out.flush()
                out.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return isSaveSucc
        }

        /**
         * 复制单个文件
         *
         * @param oldPath String 原文件路径 如：c:/fqf.txt
         * @param newPath String 复制后路径 如：f:/fqf.txt
         * @return boolean
         */
        fun copyFile2(oldPath: String?, newPath: String?) {
            try {
                var bytesum = 0
                var byteread = 0
                val oldfile = File(oldPath)
                if (oldfile.exists()) { // 文件存在时
                    val inStream: InputStream = FileInputStream(oldPath) // 读入原文件
                    val fs = FileOutputStream(newPath)
                    val buffer = ByteArray(14440)
                    while (inStream.read(buffer).also { byteread = it } != -1) {
                        bytesum += byteread // 字节数 文件大小
                        fs.write(buffer, 0, byteread)
                    }
                    inStream.close()
                }
            } catch (e: Exception) {
                println("复制单个文件操作出错")
                e.printStackTrace()
            }
        }

        /**
         * 刪除文件或文件夹
         */
        fun deleteFileByFile(file: File) {
            if (file.exists()) {
                if (file.isFile) {
                    file.delete()
                } else if (file.isDirectory) {
                    val files = file.listFiles()
                    for (i in files.indices) {
                        deleteFileByFile(files[i])
                    }
                }
                file.delete()
            }
        }

        /**
         * 取得文件夹大小
         *
         * @param f
         * @return
         * @throws Exception
         */
        fun getFileSize(f: File): Long {
            var size: Long = 0
            val flist = f.listFiles()
            if (flist != null) {
                for (i in flist.indices) {
                    size = if (flist[i].isDirectory) {
                        size + getFileSize(flist[i])
                    } else {
                        size + flist[i].length()
                    }
                }
            }
            return size
        }

        /**
         * Created by hutian on 2018/1/26.
         * 读取Assets目录下的文件
         *
         * @Info
         */
        fun readFromAssets(context: Context, fileName: String?, vararg params: String?): String {
            var content = ""
            try {
                val `is` = context.assets.open(fileName!!)
                content = AnalysisInputStream(`is`, (if (params.size > 0) params[0] else "UTF-8")!!)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            //        content = content.replace(",", "\n");
            return content
        }

        /**
         * Created by hutian on 2018/1/26.
         * 解析输入的字节流
         *
         * @Info
         */
        @Throws(Exception::class)
        private fun AnalysisInputStream(iso: InputStream, vararg params: String): String {
            val ireader = InputStreamReader(iso, if (params.size > 0) params[0] else "UTF-8")
            val bd = BufferedReader(ireader)
            val sbr = StringBuffer("")
            var str: String?
            while (bd.readLine().also { str = it } != null) {
                sbr.append(str)
                sbr.append(",") //每一行接“,”
                //sbr.append("\n");
            }
            return sbr.toString()
        }

        fun getArryString(str: String, offset: Int): Array<String?>? {
            var str = str
            if (offset <= 0) {
                return null
            }
            str = str.replace("\"", "")
            //"AG","AG:安提瓜和巴布达",
            val Alllist = Arrays.asList(*str.split(",".toRegex()).toTypedArray())
            val Namelist = ArrayList<String>()
            Log.d("hut", "读取前面部分的是：==$Alllist")
            val count = Alllist.size
            //获取偶数位字符串
            var i = 0
            while (i < count) {

                //存取“AG:安提瓜和巴布达”
                if (Alllist[i + 1] == "CN:中国") Namelist.add(0, Alllist[i + 1]) else Namelist.add(Alllist[i + 1])
                i += offset
            }
            var arry: Array<String?>? = arrayOfNulls(Namelist.size)
            arry = Namelist.toArray(arry)
            return arry
        }

        /**
         * Created by hutian on 2018/3/05
         */
        fun writeFile(fileName: String, content: String) {
            val file = File(getPath(fileName))
            if (!file.exists()) {
                try {
                    file.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (TextUtils.isEmpty(content)) {
                return
            }
            var out: OutputStream? = null
            try {
                out = FileOutputStream(file)
                out.write(content.toByteArray())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (out != null) {
                    try {
                        out.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        fun writeFile(fileName: String, data: ByteArray?) {
            val file = File(getPath(fileName))
            if (!file.exists()) {
                try {
                    file.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (data == null) {
                return
            }
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(fileName)
                fos.write(data, 0, data.size)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        /**
         * 保存文件
         *
         * @param context  设备上下文
         * @param fileName 文件名
         * @param content  内容
         */
        fun save(context: Context, fileName: String?, content: String) {
            var outStream: FileOutputStream? = null
            try {
                outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
                outStream.write(content.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    outStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        /**
         * 读取数据
         *
         * @param context  设备上下文
         * @param filename 文件名
         * @return 内容
         */
        fun read(context: Context, filename: String?): String {
//        if (!checkFileExist(filename))
//            return "";
            var inStream: FileInputStream? = null
            var outStream: ByteArrayOutputStream? = null
            //        LogUtils.i(context+"---------------------------"+filename);
            return try {
                inStream = context.openFileInput(filename)
                outStream = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var len = 0
                while (inStream.read(buffer).also { len = it } != -1) outStream.write(buffer, 0, len)
                val data = outStream.toByteArray()
                String(data)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            } finally {
                try {
                    inStream?.close()
                    outStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        /**
         * 追加模式保存文件
         *
         * @param context  设备上下文
         * @param filename 文件名
         * @param content  内容
         */
        fun saveAppend(context: Context, filename: String?, content: String) {
            var outStream: FileOutputStream? = null
            try {
                outStream = context.openFileOutput(filename, Context.MODE_APPEND)
                outStream.write(content.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    outStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        fun checkDirectory(file: File?) {
            if (file == null) return
            if (!file.parentFile.exists()) checkDirectory(file.parentFile)
            file.mkdir()
        }

        /**
         * 检查文件是否存在
         *
         * @param path 文件的绝对路径
         * @return 如果文件不存在返回false，否则返回true。 PS: 如果参数为空，则返回true
         */
        fun checkFileExist(path: String?): Boolean {
            if (path == null || path.isEmpty()) return true
            val file = File(path)
            return file.exists()
        }

        /**
         * 判断SD卡是否可用
         *
         * @return SD卡可用返回true
         */
        fun hasSdcard(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }

        /**
         * 获取当前目录下所有文件
         * @param path
         * @return
         */
        fun getDirFiles(path: String?): List<File> {
//        String path = "/data/data/com.integrated.edu.kpad/files";
            val file = File(path)
            val files = file.listFiles()
            return Arrays.asList(*files)
        }

        /**
         * 去除文件后缀名(同类型文件)
         * 或者取 . 前面的
         * @param files
         */
        fun clrFileSuffix(files: List<File>, suffixName: String?): List<String> {
            val lists: MutableList<String> = ArrayList()
            for (file in files) {
                Log.i("路径：", file.name + "===" + file.absolutePath)
                if (!TextUtils.isEmpty(file.name)) {
//                int indexOf = file.getName().indexOf(".txt");
                    val indexOf = file.name.indexOf(suffixName!!)
                    if (indexOf > -1) lists.add(file.name.substring(0, indexOf))
                }
            }
            return lists
        }

        /**
         * 通过uri获取文件的绝对路径
         *
         * @param context
         * @param uri
         * @return
         */
        fun getRealFilePath(context: Context, uri: Uri?): String? {
            if (null == uri) return null
            val scheme = uri.scheme
            var data: String? = null
            if (scheme == null) data = uri.path else if (ContentResolver.SCHEME_FILE == scheme) {
                data = uri.path
            } else if (ContentResolver.SCHEME_CONTENT == scheme) {
                val cursor = context.contentResolver.query(uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                        if (index > -1) {
                            data = cursor.getString(index)
                        }
                    }
                    cursor.close()
                }
            }
            return data
        }

        /**
         * 文件删除
         *
         * @param autoClearDay 文件保存天数
         */
        fun autoClear(dirPath: String?, autoClearDay: Int) {
            delete(dirPath, FilenameFilter { file, filename ->
                var s = getFileNameWithoutExtension(filename)
                val day = if (autoClearDay < 0) autoClearDay else -1 * autoClearDay
                val date = getOtherDay(day)
                if (s.contains("Screenshot_")) {
                    s = s.substring(s.indexOf("_") + 1, s.indexOf("-"))
                    val ss = dateSimpleFormat(getOtherFormat(day), DateUtils.SHORT_DATE_FORMAT)
                    return@FilenameFilter ss.compareTo(s) >= 0
                }
                if (FormatUtil.isNumeric(s)) {
                    val newStr = getNormalDateString(java.lang.Long.valueOf(s)) //把毫秒数转成 "yyyy-MM-dd 格式
                    date.compareTo(newStr) >= 0
                } else false
            })
        }
    }

    init {
        throw Error("￣﹏￣")
    }
}