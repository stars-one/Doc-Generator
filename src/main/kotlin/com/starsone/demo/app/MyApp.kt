package com.starsone.demo.app

import com.google.gson.Gson
import com.starsone.demo.model.DescData
import com.starsone.demo.view.MainView
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*

class MyApp : App(MainView::class, Styles::class) {
    //全局消息存放
    private val model = AppModel()

    override fun start(stage: Stage) {
        super.start(stage)
        //存放model
        setInScope(model, scope)
        //设置图标
        stage.icons += Image(model.descData.icon)
    }
}

/**
 * 全局的消息存放
 */
class AppModel : ViewModel() {
    val descData = Gson().fromJson(resources.text("/desc.json"), DescData::class.java)

}