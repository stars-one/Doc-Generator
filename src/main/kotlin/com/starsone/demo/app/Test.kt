package com.starsone.demo.app

import org.commonmark.Extension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.ext.task.list.items.TaskListItemsExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer


/**
 *
 * @author StarsOne
 * @date Create in  2020/6/9 0009 20:42
 * @description
 *
 */
fun main(args: Array<String>) {
    //第一部分
    //0.序章 part1/0.html
//    val chacpterData = ChapterData("0.序章", "part1/0.html")
//    ZipUtils.unZip(File("Q:\\JavaFxProject\\LanzouDownloader\\out\\test1.zip"),"Q:\\JavaFxProject\\LanzouDownloader\\out")

    val extensions = arrayListOf<Extension>(TablesExtension.create(),TaskListItemsExtension.create())
    val parser = Parser.builder()
            .extensions(extensions)
            .build()
    val renderer = HtmlRenderer.builder()
            .extensions(extensions)
            .build()
    val document = parser.parse("# 标题1\n" +
            "## 标题2\n" +
            "### 标题3\n" +
            "\n" +
            "**无序列表：**\n" +
            "\n" +
            "- 第一部分\n" +
            "- 第二\n" +
            "- 第三\n" +
            "\n" +
            "**有序列表**\n" +
            "\n" +
            "1. 第一部分\n" +
            "2. 第二\n" +
            "3. 第三\n" +
            "\n" +
            "**TODO list:**\n" +
            "- [x] 添加功能\n" +
            "- [ ] 删除功能\n" +
            "- [ ] 二维码扫描功能\n" +
            "- [ ] 宿舍\n" +
            "\n" +
            "\n" +
            "**代码块**\n" +
            "\n" +
            "图中需要什么`File`对象\n" +
            "```\n" +
            "val hello  =\"test\"\n" +
            "```\n" +
            "这是*斜体*的使用\n" +
            "\n" +
            "引用:\n" +
            "> Kotlin由xx发布,在198989djkjxn,n间空房间看的借款方控件库相机开发快捷快递nnmxkklisjsm,快捷快递就看发接口接口的一下了楼接口接口的颈康胶囊小看就看\n" +
            "\n" +
            "|标题1\t|表土2\t|\n" +
            "|--\t\t|--\t\t|\n" +
            "|测试\t|宿舍\t|\n" +
            "|是\t\t|实现\t|\n" +
            "\n")
    println(renderer.render(document))  // "<p>This is <em>Sparta</em></p>\n"
}

