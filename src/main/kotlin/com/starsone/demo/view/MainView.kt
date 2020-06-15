package com.starsone.demo.view

import ZipUtils
import com.starsone.demo.util.DocUtils
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import kfoenix.jfxbutton
import kfoenix.jfxtextfield
import tornadofx.*
import java.io.File

class MainView : BaseView() {
    private val myDataModel by inject<MyViewModel>()

    val partDataList = arrayListOf<PartData>()

    override var root = vbox(10) {
        setPrefSize(800.0, 600.0)

        menubar {
            menu("帮助") {
                item("关于") {
                    action {
                        find(AboutView::class).openModal()
                    }
                }
            }
        }
        val fxRecyclerView = FxRecyclerView<PartData, PartView>()

        hbox(10) {
            jfxbutton("添加新大章节") {
                action {
                    fxRecyclerView.add(PartData("", listOf()))
                }
            }
            jfxbutton("生成静态资源压缩包") {
                action {
                    //生成js css的资源文件
                    val stream = resources.stream("/templates.zip")
                    val file = File("templates.zip")
                    file.writeBytes(stream.readBytes())
                    //解压文件
                    ZipUtils.unZip(file, "doc")
                    //获得数据源
                    val list = fxRecyclerView.adapter?.itemViewList?.map { it.getPartData() }
                    if (list != null) {
                        val docData = DocCatalogue(myDataModel.docTile.value, list,myDataModel.githubUrl.value,myDataModel.giteeUrl.value)
                        //解析md文件生成hhtml文件并将html文件放在指定路径
                        val url = resources.url("/templates")
                        val templatesDir = File(url.toURI())
                        DocUtils.outputHtmlFile(docData,templatesDir)
                    }
                }
            }
            jfxbutton("测试资源") {
                action {


                }
            }
        }


        val adapter = object : RVAdapter<PartData, PartView>(partDataList) {
            override fun onCreateView(): PartView {
                return PartView()
            }

            override fun onBindData(itemView: PartView, bean: PartData, position: Int) {

            }

            override fun onClick(itemView: PartView, position: Int) {
            }

            override fun onRightClick(itemView: PartView, position: Int) {

            }

        }
        fxRecyclerView.adapter = adapter

        jfxtextfield(myDataModel.docTile, "文档标题")
        jfxtextfield(myDataModel.giteeUrl, "Gitee项目地址")
        jfxtextfield(myDataModel.githubUrl, "Github项目地址")

        this += fxRecyclerView

    }
}

class MyViewModel : ViewModel() {
    val docTile = bind { SimpleStringProperty() }
    val giteeUrl = bind { SimpleStringProperty() }
    val githubUrl = bind { SimpleStringProperty() }
    val partDataList = bind { SimpleListProperty<PartData>(observableList()) }
}

/*
整篇文档的标题
 */
class DocCatalogue(val title: String, val partDataList: List<PartData>,var githubUrl:String="",var giteeUrl:String="")

/*
每一部分的标题及其下面的各章节列表
 */
class PartData(val title: String, val chapterDataList: List<ChapterData>, var index: Int = 0)

/**
 * 各章节的标题及路径
 */
class ChapterData(val title: String, var filePath: String, var index: Int = 0)
