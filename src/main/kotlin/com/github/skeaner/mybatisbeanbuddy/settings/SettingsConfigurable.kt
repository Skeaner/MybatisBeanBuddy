package com.github.skeaner.mybatisbeanbuddy.settings

import com.github.skeaner.mybatisbeanbuddy.MyBundle.message
import com.github.skeaner.mybatisbeanbuddy.bindSelected
import com.github.skeaner.mybatisbeanbuddy.bindText
import com.github.skeaner.mybatisbeanbuddy.model.SerializableType
import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.openapi.options.BoundSearchableConfigurable
import com.intellij.openapi.options.Configurable.NoScroll
import com.intellij.openapi.project.Project
import com.intellij.ui.dsl.builder.*
import com.jetbrains.rd.util.string.PrettyPrinter
import com.jetbrains.rd.util.string.println

class SettingsConfigurable(val project: Project) : BoundSearchableConfigurable(
    helpTopic = "MybatisBeanBuddy",
    _id = "MybatisBeanBuddy",
    displayName = "MybatisBeanBuddy",
), NoScroll {

    companion object {
        const val ID = "com.github.skeaner.mybatisbeanbuddy.settings.SettingsConfigurable"
    }

    private val settings = project.getService(Settings::class.java)

    private val propertyGraph = PropertyGraph()
    private val settingsProperty = propertyGraph.lazyProperty { Settings().apply { copyFrom(settings) } }


    override fun createPanel() =
        panel {
            buttonsGroup{
                row ("Serializable type:") {
                    for (value in SerializableType.values()) {
                        radioButton(value.name).bindSelected(settingsProperty, Settings::serializableType)
                    }
                }
            }
            row("Class name postfix:") {
                textField().align(Align.FILL).bindText(settingsProperty, Settings::classNamePostfix)
            }
            row("Use lombok:") {
                checkBox("Add lombok Annotation").bindSelected(settingsProperty, Settings::useLombok)
            }
            row("Comment link regexp:") {
                textField().align(Align.FILL).bindText(settingsProperty, Settings::commentLinkRegexp)
            }
            row("Class name regexp:") {
                textField().align(Align.FILL).bindText(settingsProperty, Settings::classNameRegexp)
            }
            row("Class comment:") {
                textField().align(Align.FILL).bindText(settingsProperty, Settings::classComment)
            }

        }

    override fun getId() = ID

    override fun getDisplayName() = message("projectName")

    override fun isModified() = settingsProperty.get() != settings

    override fun apply() {
        settings.copyFrom(settingsProperty.get())
//        project.messageBus.syncPublisher(SettingsListener.TOPIC).settingsChanged()
    }

    override fun reset() {
        settingsProperty.set(settingsProperty.get().apply {
            copyFrom(settings)
        })
    }

}
