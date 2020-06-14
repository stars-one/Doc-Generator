import com.jfoenix.controls.JFXButton
import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.image.ImageView
import kfoenix.jfxbutton
import tornadofx.*

/**
 * 创建指定宽高的ImageView,单独指定[imgWidth]会生成正方形的图形
 */
fun EventTarget.imageview(url: String, imgWidth: Int, imgHeight: Int = 0, lazyload: Boolean = true, op: ImageView.() -> Unit = {}): ImageView {
    val img = imageview(url) {
        fitWidth = imgWidth.toDouble()
        fitHeight = if (imgHeight == 0) {
            imgWidth.toDouble()
        } else {
            imgHeight.toDouble()
        }
    }
    return opcr(this, img, op)
}
/**
 * 图标扁平按钮,单独设置[imgWidth]为正方形按钮
 */
fun EventTarget.jfxbutton(imgPath: String, imgWidth: Int, imgHeight: Int = 0, op: (JFXButton.() -> Unit) = {}): JFXButton {
    val button = jfxbutton {
        graphic = if (imgHeight == 0) {
            imageview(imgPath, imgWidth)
        } else {
            imageview(imgPath, imgWidth, imgHeight)
        }
        setOnMouseEntered {
            style {
                backgroundColor += c(0, 0, 0, 0.1)
                backgroundRadius += box(20.percent)
            }

        }
        setOnMouseExited {
            style {

            }
        }
    }
    return opcr(this, button, op)
}

/**
 * 圆形图标扁平按钮(鼠标滑过会有阴影)
 */
fun EventTarget.circlejfxbutton(icon: Node, op: (JFXButton.() -> Unit) = {}): JFXButton {
    val jfxbutton = jfxbutton {
        graphic = icon
        setOnMouseEntered {
            style {
                backgroundColor += c(0, 0, 0, 0.1)
                backgroundRadius += box(50.percent)
            }

        }
        setOnMouseExited {
            style {

            }
        }
    }
    return opcr(this,jfxbutton,op)
}