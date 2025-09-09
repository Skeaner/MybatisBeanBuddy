package com.github.skeaner.mybatisbeanbuddy.settings

import com.github.skeaner.mybatisbeanbuddy.model.SerializableType
import com.intellij.ui.JBColor
import com.intellij.util.xmlb.Converter
import com.intellij.util.xmlb.annotations.OptionTag
import java.awt.Color

interface SettingsState {
    var serializableType: String?
    var classNamePostfix: String?
    var useLombok: Boolean
    var commentLinkRegexp: String?
    var classNameRegexp: String?
    var classComment: String?
}

