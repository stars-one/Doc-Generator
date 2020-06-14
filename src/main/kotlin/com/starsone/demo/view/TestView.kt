package com.starsone.demo.view

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class TestView : BaseView() {
    private val testController by inject<TestController>()
    private val dataModel by inject<DataModel>()

    override var root = vbox {
        setPrefSize(500.0,300.0)
        textfield(dataModel.username)
        textfield(dataModel.password)
        button{
            action{
                runAsync {
                    Thread.sleep(1000)
                    dataModel.username.value = "hello"
                }
            }
        }
        vbox{
            this+=ChapterView()
            this+=ChapterView()
            this+=ChapterView()
        }
    }

}
class TestController:Controller(){
    private val dataModel by inject<DataModel>()
    fun println() {
        println(dataModel.username.value)
        println(dataModel.password.value)
    }
}
class DataModel: ViewModel() {
    val username = bind { SimpleStringProperty() }
    val password = bind { SimpleStringProperty() }
    val remember = bind { SimpleBooleanProperty() }
}

