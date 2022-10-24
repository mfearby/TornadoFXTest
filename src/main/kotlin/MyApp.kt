package com.marcfearby

import com.marcfearby.view.MainView
import javafx.stage.Stage
import tornadofx.*

class MyApp: App(MainView::class, Styles::class) {

    // useful if "Live Views" is ticked in Dev Options under configurations
    init {
        reloadStylesheetsOnFocus()
        reloadViewsOnFocus()
    }

    override fun start(stage: Stage) {
        with(stage) {
            width = 250.0
            height = 300.0
        }
        super.start(stage)
    }
}