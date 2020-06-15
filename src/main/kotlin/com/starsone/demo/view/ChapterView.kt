package com.starsone.demo.view

import com.jfoenix.controls.JFXButton
import javafx.geometry.Pos
import javafx.scene.control.TextField
import kfoenix.jfxtextfield
import tornadofx.*

/**
 *
 * @author StarsOne
 * @date Create in  2020/6/12 0012 15:08
 * @description
 *
 */
class ChapterView : View("My View") {

    var deleteBtn by singleAssign<JFXButton>()
    var titleTf by singleAssign<TextField>()
    var filePathTf by singleAssign<TextField>()

    override val root = hbox(10) {
        alignment = Pos.CENTER
        text("章节标题:")
        titleTf = jfxtextfield("", "章节标题")
        text("md文件路径:")
        filePathTf = jfxtextfield("", "md文件路径")
//        deleteBtn = circlejfxbutton(icontext("delete", "30px", "red"))
    }

    fun setChapterData(chapterData: ChapterData) {
        titleTf.text = chapterData.title
        filePathTf.text = chapterData.filePath
    }

    fun genereateChapterData(): ChapterData {
        println(titleTf.text)
        return ChapterData(titleTf.text, filePathTf.text)
    }
}

