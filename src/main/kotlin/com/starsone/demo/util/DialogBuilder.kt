package com.starsone.demo.util

import com.jfoenix.controls.JFXAlert
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDialogLayout
import com.jfoenix.controls.JFXTextField
import com.starsone.icontext.MaterialDesignIconTextFactory
import com.sun.istack.internal.Nullable

import javafx.scene.control.Hyperlink
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Border
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Paint
import javafx.stage.Modality
import javafx.stage.Stage

/**
 * @author StarsOne
 * @date Create in  2019/6/2 0002 20:51
 */
class DialogBuilder(stage: Stage?, private var modality: Modality) {
    private var title: String? = null
    private var message: String? = null
    private var negativeBtn: JFXButton? = null
    private var positiveBtn: JFXButton? = null
    private var stage = stage as Stage
    private var negativeBtnPaint = Paint.valueOf("#747474")//否定按钮文字颜色，默认灰色
    private var positiveBtnPaint = Paint.valueOf("#0099ff")
    private var hyperlink: Hyperlink? = null
    private var textField: TextField? = null
    private var alert: JFXAlert<String>? = null
    private var onInputListener: ((text: String) -> Unit)? = null

    fun setTitle(title: String): DialogBuilder {
        this.title = title
        return this
    }

    fun setMessage(message: String): DialogBuilder {
        this.message = message
        return this
    }

    /**
     * 设置否定按钮文字和文字颜色
     *
     * @param negativeBtnText 文字
     * @param color           文字颜色 十六进制 #fafafa
     * @return
     */
    fun setNegativeBtn(negativeBtnText: String, color: String): DialogBuilder {
        return setNegativeBtn(negativeBtnText, null, color)
    }

    /**
     * 设置按钮文字和按钮文字颜色，按钮监听器和
     *
     * @param negativeBtnText
     * @param negativeBtnOnclickListener
     * @param color                      文字颜色 十六进制 #fafafa
     * @return
     */

    fun setNegativeBtn(negativeBtnText: String, @Nullable negativeBtnOnclickListener: OnClickListener? = null, color: String? = null): DialogBuilder {
        if (color != null) {
            this.negativeBtnPaint = Paint.valueOf(color)
        }
        this.negativeBtn?.isCancelButton = true
        return setNegativeBtn(negativeBtnText, negativeBtnOnclickListener)
    }

    /**
     * 设置按钮文字和点击监听器
     *
     * @param negativeBtnText            按钮文字
     * @param negativeBtnOnclickListener 点击监听器
     * @return
     */
    fun setNegativeBtn(negativeBtnText: String, @Nullable negativeBtnOnclickListener: OnClickListener?): DialogBuilder {

        negativeBtn = JFXButton(negativeBtnText)
        negativeBtn!!.isCancelButton = true
        negativeBtn!!.textFill = negativeBtnPaint
        negativeBtn!!.buttonType = JFXButton.ButtonType.FLAT
        negativeBtn!!.setOnAction { addEvent ->
            alert!!.hideWithAnimation()
            negativeBtnOnclickListener?.onClick()
        }
        return this
    }

    /**
     * 设置按钮文字和颜色
     *
     * @param positiveBtnText 文字
     * @param color           颜色 十六进制 #fafafa
     * @return
     */
    fun setPositiveBtn(positiveBtnText: String, color: String): DialogBuilder {
        return setPositiveBtn(positiveBtnText, null, color)
    }

    /**
     * 设置按钮文字，颜色和点击监听器
     *
     * @param positiveBtnText            文字
     * @param positiveBtnOnclickListener 点击监听器
     * @param color                      颜色 十六进制 #fafafa
     * @return
     */
    fun setPositiveBtn(positiveBtnText: String, @Nullable positiveBtnOnclickListener: OnClickListener? = null, color: String? = null): DialogBuilder {
        if (color != null) {
            this.positiveBtnPaint = Paint.valueOf(color)
        }
        return setPositiveBtn(positiveBtnText, positiveBtnOnclickListener)
    }

    /**
     * 设置按钮文字和监听器
     *
     * @param positiveBtnText            文字
     * @param positiveBtnOnclickListener 点击监听器
     * @return
     */
    fun setPositiveBtn(positiveBtnText: String, @Nullable positiveBtnOnclickListener: OnClickListener?): DialogBuilder {
        positiveBtn = JFXButton(positiveBtnText)
        positiveBtn!!.isDefaultButton = true
        positiveBtn!!.textFill = positiveBtnPaint
        positiveBtn!!.setOnAction { _ ->
            alert!!.hideWithAnimation()
            positiveBtnOnclickListener?.onClick()
        }
        return this
    }

    /**
     * 设置超链接（文件输出路径，网址跳转），会自动打开指定浏览器或者是资源管理器执行操作
     *
     * @param text 文件的路径，或者是网址，
     * @return
     */
    fun setHyperLink(text: String): DialogBuilder {
        hyperlink = Hyperlink(text)
        hyperlink!!.border = Border.EMPTY
        hyperlink!!.setOnMouseClicked {

        }
        return this
    }

    /**
     * 设置输入框
     */
    fun setTextFieldText(hintText:String,onInputListener: ((text: String) -> Unit)): DialogBuilder {

        val jfxtf = JFXTextField()
        jfxtf.promptText = hintText
        this.textField = jfxtf
        this.onInputListener = onInputListener
        return this
    }

    /**
     * 创建对话框并显示
     *
     * @return JFXAlert<String>
     */
    fun create(): JFXAlert<String> {
        alert = JFXAlert(stage)
        alert!!.initModality(modality)
        alert!!.isOverlayClose = false

        val layout = JFXDialogLayout()
        val title = Label(title)
        val closeBtn = MaterialDesignIconTextFactory.getIconText("close")
        closeBtn.setSize("20px")
        closeBtn.setOnMouseClicked {
            alert!!.hideWithAnimation()
        }
        AnchorPane.setLeftAnchor(title, 0.0)
        //设置关闭按钮显示在右上角
        AnchorPane.setRightAnchor(closeBtn, 0.0)
        AnchorPane.setBottomAnchor(closeBtn, 0.0)
        AnchorPane.setTopAnchor(closeBtn, 0.0)
        //添加到对话框头部
        layout.setHeading(AnchorPane(title, closeBtn))

        //添加hyperlink超链接文本或者是输入框
        when {
            hyperlink != null -> layout.setBody(HBox(Label(this.message), hyperlink))
            textField != null -> {
                layout.setBody(VBox(Label(this.message), textField))
                positiveBtn!!.setOnAction { _ ->
                    alert!!.result = textField!!.text
                    alert!!.hideWithAnimation()
                }
            }
            else -> layout.setBody(VBox(Label(this.message)))
        }
        title.isFocusTraversable = true
        //添加确定和取消按钮
        if (negativeBtn != null && positiveBtn != null) {
            layout.setActions(negativeBtn, positiveBtn)
        } else {
            if (negativeBtn != null) {
                layout.setActions(negativeBtn)
            } else if (positiveBtn != null) {
                layout.setActions(positiveBtn)
            }
        }

        alert!!.setContent(layout)
        if (textField != null) {
            alert!!.showAndWait()
            //不为空，则回调接口
            if (alert!!.result is String) {
                onInputListener?.invoke(alert!!.result)
            }

        } else {
            alert!!.showAndWait()
        }

        return alert as JFXAlert<String>
    }

    interface OnClickListener {
        fun onClick()
    }


}
