package com.github.skeaner.mybatisbeanbuddy

import com.github.skeaner.mybatisbeanbuddy.settings.SettingsState
import com.intellij.openapi.observable.properties.ObservableMutableProperty
import com.intellij.openapi.observable.util.bind
import com.intellij.openapi.observable.util.transform
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBRadioButton
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.layout.ComponentPredicate
import com.intellij.ui.layout.selected
import kotlin.reflect.KMutableProperty1


fun <T : SettingsState> Cell<JBCheckBox>.bindSelected(
    graphProperty: ObservableMutableProperty<T>,
    property: KMutableProperty1<T, Boolean>
) =
    bindSelected(with(graphProperty) {
        transform(
            { it.let(property::get) },
            { value -> get().also { property.set(it, value) } }
        )
    })

fun <T : SettingsState> Cell<JBRadioButton>.bindSelected(
    graphProperty: ObservableMutableProperty<T>,
    property: KMutableProperty1<T, String?>
) {
    applyToComponent {
        this.bind(graphProperty.transform(property).transform { text == it })
        this.addItemListener {
            if (it.stateChange == java.awt.event.ItemEvent.SELECTED) {
                graphProperty.set(graphProperty.get().apply { property.set(this, text) })
            }  // Update the property with the selected
        }
    }
}


fun <T : SettingsState> Cell<JBTextField>.bindText(
    graphProperty: ObservableMutableProperty<T>,
    property: KMutableProperty1<T, String?>
) =
    this.bindText(with(graphProperty) {
        this.transform(
            { it.let(property::get) ?: "" },
            { value -> this.get().also { property.set(it, value) } }
        )
    })


fun <T : SettingsState> ObservableMutableProperty<T>.createPredicate(property: KMutableProperty1<T, Boolean>) =
    object : ComponentPredicate() {

        private val observableProperty = transform(property)

        override fun invoke() = observableProperty.get()

        override fun addListener(listener: (Boolean) -> Unit) = observableProperty.afterChange(listener)
    }

fun <T> T?.or(other: T): T = this ?: other

fun <T> T?.or(block: () -> T): T = this ?: block()
