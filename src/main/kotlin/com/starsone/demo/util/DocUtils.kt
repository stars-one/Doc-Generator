package com.starsone.demo.util

import com.starsone.demo.view.DocCatalogue
import com.starsone.demo.view.PartData
import freemarker.template.Configuration
import freemarker.template.ObjectWrapper
import org.commonmark.Extension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.ext.task.list.items.TaskListItemsExtension
import org.commonmark.node.Link
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.AttributeProvider
import org.commonmark.renderer.html.HtmlRenderer
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

/**
 *
 * @author StarsOne
 * @date Create in  2020/6/12 0012 22:11
 * @description
 *
 */
class DocUtils {
    companion object {

        fun outputHtmlFile(dataDoc: DocCatalogue) {
            // step1 创建freeMarker配置实例
            val configuration = Configuration()
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(File("Q:\\JavaWebProject\\DocumentGenerator\\src\\main\\resources\\templates"))

            // step3 数据模型
            dealMdFiles(dataDoc.partDataList)
            //处理索引
            var sum = 0
            dataDoc.partDataList.forEachIndexed { index, partData ->
                partData.index = index
                partData.chapterDataList.forEach { chapterData ->
                    chapterData.index = sum
                    sum++
                }
            }
            // step4 加载模版文件
            val template = configuration.getTemplate("index.ftl","utf-8")
            // step5 生成数据
            val docFile = File("doc/index.html")
            val out = BufferedWriter(OutputStreamWriter(FileOutputStream(docFile),"utf-8"))
            // step6 输出文件
            template.process(dataDoc, out, ObjectWrapper.BEANS_WRAPPER)
        }

        private fun dealMdFiles(partDataList: List<PartData>) {
            partDataList.forEachIndexed { index, partData ->
                val partPath = "part" + index + 1
                File("doc/$partPath").mkdirs()
                for (chapterData in partData.chapterDataList) {
                    val htmlFile = md2Html(chapterData.filePath)
                    //需要复制文件到指定路径
                    // step1 创建freeMarker配置实例
                    val configuration = Configuration()
                    // step2 获取模版路径
                    configuration.setDirectoryForTemplateLoading(File("Q:\\JavaWebProject\\DocumentGenerator\\src\\main\\resources\\templates"))
                    val content = htmlFile.readText()

                    val contentHtmlFile = File("doc/$partPath/${htmlFile.name}")

                    val template = configuration.getTemplate("content.ftl")
                    val out = BufferedWriter(OutputStreamWriter(FileOutputStream(contentHtmlFile)))
                    val map =hashMapOf("content" to content)
                    template.process(map, out)

                    htmlFile.delete()
                    chapterData.filePath = partPath + "/" + htmlFile.name
                }
            }
        }

        /**
         * 解析单独的md文件为html文件
         */
        private fun md2Html(mdFilePath: String): File {
            val mdFile = File(mdFilePath)
            val outputFile = File(mdFile.nameWithoutExtension + ".html")
            val extensions = arrayListOf<Extension>(TablesExtension.create(), TaskListItemsExtension.create())
            val parser = Parser.builder()
                    .extensions(extensions)
                    .build()
            val renderer = HtmlRenderer.builder()
                    .extensions(extensions)
                    .attributeProviderFactory { MdAttributeProvider() }
                    .escapeHtml(true)
                    .build()
            val document = parser.parse(mdFile.readText())
            val result = renderer.render(document)
            outputFile.writeText(result)
            return outputFile
        }
    }
}
class MdAttributeProvider : AttributeProvider{
    override fun setAttributes(node: Node?, tagName: String?, attributeMap: MutableMap<String, String>?) {
        //标题
        if (tagName=="h1" || tagName=="h2" || tagName=="h3") {
            attributeMap!!["class"]="mdui-text-color-theme"
        }
        //链接
        if (node is Link) {
            attributeMap!!["target"]="_blank"
        }
    }
}