package com.starsone.demo.view

import com.jfoenix.controls.JFXTextField
import javafx.stage.FileChooser
import kfoenix.jfxbutton
import kfoenix.jfxtextfield
import tornadofx.*

/**
 *
 * @author StarsOne
 * @date Create in  2020/6/12 0012 15:08
 * @description
 *
 */
class PartView : View("My View") {

    var titleTf by singleAssign<JFXTextField>()

    val chapterDataList = arrayListOf<ChapterData>()

    lateinit var adapter:RVAdapter<ChapterData,ChapterView>

    override val root = vbox(10) {

        titleTf = jfxtextfield("", "大章节标题")
        val fxRecyclerView = FxRecyclerView<ChapterData,ChapterView>()

        adapter = object: RVAdapter<ChapterData,ChapterView>(chapterDataList){
            override fun onCreateView(): ChapterView {
                return ChapterView()
            }

            override fun onBindData(itemView: ChapterView, bean: ChapterData, position: Int) {
                itemView.setChapterData(bean)
                /*itemView.deleteBtn.setOnAction {
                    println("这是$position")
                    val genereateChapterData = itemView.genereateChapterData()
                    println("${genereateChapterData.title}+${genereateChapterData.filePath}")
                }*/
            }

            override fun onClick(itemView: ChapterView, position: Int) {
            }

            override fun onRightClick(itemView: ChapterView, position: Int) {
            }
        }
        fxRecyclerView.adapter =adapter

        jfxbutton("批量选择md文件"){
            isFocusTraversable = false
            setOnMouseEntered { style { backgroundColor += c(0, 0, 0, 0.1) } }
            setOnMouseExited { style {} }
            action{
                val files = chooseFile("批量选择md文件", arrayOf(FileChooser.ExtensionFilter("md文件","*.md")),FileChooserMode.Multi)
                val list =arrayListOf<ChapterData>()
                for (file in files) {
                    val title =file.nameWithoutExtension
                    val filePath = file.path
                    list.add(ChapterData(title,filePath))
                }
                fxRecyclerView.addList(list)
                chapterDataList.clear()
                chapterDataList.addAll(list)
            }
        }
        fxRecyclerView.setWidth(600.0)
        this+=fxRecyclerView
    }

    fun getPartData(): PartData {
        val chapterList = arrayListOf<ChapterData>()
        for (chapterView in adapter.itemViewList) {
            val genereateChapterData = chapterView.genereateChapterData()
            chapterList.add(genereateChapterData)
        }
        return PartData(titleTf.text,chapterList)
    }
}

