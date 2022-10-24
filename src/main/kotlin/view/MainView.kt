package com.marcfearby.view

import com.marcfearby.Styles
import com.marcfearby.controller.MainController
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.ButtonType
import javafx.scene.control.ComboBox
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import tornadofx.*

class MainView : View("Hello TornadoFX") {

    private val mainController: MainController by inject()
    private var splitCombo: ComboBox<Int> by singleAssign()
    private var slider: Slider by singleAssign()
    var billAmount: TextField by singleAssign()

    override val root = vbox {
        addClass(Styles.vBox)
        alignment = Pos.TOP_CENTER

        label("Total per person") {
            addClass(Styles.heading)
        }

        label() {
            addClass(Styles.heading)
            textProperty().bind(
                Bindings.concat("$", Bindings.format("%.2f", mainController.totalPerPerson))
            )
        }

        form {
            fieldset(labelPosition = Orientation.HORIZONTAL) {
                field("Bill Amount") {
                    maxWidth = 190.0
                    billAmount = textfield()
                    billAmount.filterInput {
                        it.controlNewText.isDouble() || it.controlNewText.isInt()
                    }
                    billAmount.setOnKeyPressed {
                        if (it.code == KeyCode.ENTER) {
                            validateField()
                        }
                    }
                }
                field("Split by") {
                    splitCombo = combobox(values = (1..10).toList()) {
                        prefWidth = 135.0
                        value = 1
                    }
                    splitCombo.valueProperty().onChange {
                        validateField()
                    }
                }
                field {
                    hbox(spacing = 23.0) {
                        label("Total Tip")
                        label().textProperty().bind(
                            Bindings.concat("$", Bindings.format("%.2f", mainController.tipPercentageAmount))
                        )
                    }
                }
                field {
                    hbox(spacing = 5.0) {
                        label("Tip %")
                        slider = slider(min = 0, max = 100, orientation = Orientation.HORIZONTAL)
                        slider.valueProperty().onChange {
                            validateField()
                        }
                        label().textProperty().bind(
                            Bindings.concat(mainController.sliderPercentageAmount, "%")
                        )
                    }
                }
            }
        }

    }

    private fun validateField() {
        if (billAmount.text.toString().isEmpty()) {
            error(header = "Error", content = "Empty field not allowed", buttons = arrayOf(ButtonType.OK))
        } else {
            mainController.calculate(
                SimpleDoubleProperty(billAmount.text.toDouble()),
                SimpleIntegerProperty(splitCombo.value),
                SimpleIntegerProperty(slider.value.toInt())
            )
        }
    }
}