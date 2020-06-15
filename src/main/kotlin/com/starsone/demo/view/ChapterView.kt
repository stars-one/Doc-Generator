package com.starsone.demo.view

import circlejfxbutton
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import com.starsone.icontext.icontext
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
    var titleTf by singleAssign<JFXTextField>()
    var filePathTf by singleAssign<JFXTextField>()

    override val root = hbox(10) {
        titleTf = jfxtextfield("", "章节标题")
        filePathTf = jfxtextfield("", "md文件路径")
        deleteBtn = circlejfxbutton(icontext("delete", "30px", "red"))
    }

    fun setChapterData(chapterData: ChapterData) {
        titleTf.text=chapterData.title
        filePathTf.text=chapterData.filePath
    }

    fun genereateChapterData(): ChapterData {
        println(titleTf.text)
        return ChapterData(titleTf.text,filePathTf.text)
    }
}

