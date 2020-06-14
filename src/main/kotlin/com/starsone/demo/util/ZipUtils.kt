
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream


class ZipUtils {
    companion object {
        fun compress(file: File, outputZipFilePath: String) {
            println("压缩中...")
            //创建zip输出流
            val out = ZipOutputStream(FileOutputStream(outputZipFilePath))
            //创建缓冲输出流
            val bos = BufferedOutputStream(out)
            //调用函数
            compress(out, bos, file, file.name)
            bos.close()
            out.close()
            println("压缩完成")
        }

        fun compress(out: ZipOutputStream, bos: BufferedOutputStream, sourceFile: File, base: String) {
            //如果路径为目录（文件夹）
            if (sourceFile.isDirectory) {
                //取出文件夹中的文件（或子文件夹）
                val flist = sourceFile.listFiles()
                //如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
                if (flist.isEmpty()) {
                    println("$base/")
                    out.putNextEntry(ZipEntry("$base/"))
                } else {
                    //如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
                    for (i in flist.indices) {
                        compress(out, bos, flist[i], base + "/" + flist[i].name)
                    }
                }
            } else {
                //如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
                out.putNextEntry(ZipEntry(base))
                val fos = FileInputStream(sourceFile)
                val bis = BufferedInputStream(fos)
                bos.write(sourceFile.readBytes())
                bis.close()
                fos.close()
            }
        }

        fun compress(inputStream: InputStream) {
            val zos = ZipOutputStream(FileOutputStream(File("Q:\\Front Project\\tornadofx-guide-chinese\\tornadofx中文文档\\test1.zip")))
            zos.putNextEntry(ZipEntry("dir/"))
            zos.write(inputStream.readBytes())
            zos.closeEntry()
            zos.close()
        }

        fun unZip(sourceFilename: String, targetDir: String) {
            unZip(File(sourceFilename), targetDir)
        }

        /**
         * 将sourceFile解压到targetDir
         * @param sourceFile
         * @param targetDir
         * @throws RuntimeException
         */
        fun unZip(sourceFile: File, targetDir: String) {
            if (!sourceFile.exists()) {
                throw FileNotFoundException("cannot find the file = " + sourceFile.path)
            }
            val zipFile = ZipFile(sourceFile)
            val entries = zipFile.entries()
            while (entries.hasMoreElements()) {
                val entry = entries.nextElement()
                if (entry.isDirectory) {
                    val dirPath = targetDir + "/" + entry.name
                    createDirIfNotExist(dirPath)
                } else {
                    val targetFile = File(targetDir + "/" + entry.name)
                    createFileIfNotExist(targetFile)
                    val fos = FileOutputStream(targetFile)
                    fos.write(zipFile.getInputStream(entry).readBytes())
                    fos.close()
                }
            }
            //删除压缩包
            zipFile.close()
            sourceFile.delete()
        }

        fun createDirIfNotExist(path: String) {
            val file = File(path)
            createDirIfNotExist(file)
        }

        fun createDirIfNotExist(file: File) {
            if (!file.exists()) {
                file.mkdirs()
            }
        }

        fun createFileIfNotExist(file: File) {
            createParentDirIfNotExist(file)
            file.createNewFile()
        }

        fun createParentDirIfNotExist(file: File) {
            createDirIfNotExist(file.parentFile)
        }
    }
}



