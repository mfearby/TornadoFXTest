package com.marcfearby

import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val vBox by cssclass()
    }

    init {

        vBox {
            padding = box(10.px)
            backgroundColor = multi(Color.CHARTREUSE)
        }

        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }
    }
}