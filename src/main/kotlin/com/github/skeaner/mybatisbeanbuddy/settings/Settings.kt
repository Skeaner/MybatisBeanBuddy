package com.github.skeaner.mybatisbeanbuddy.settings

import com.github.skeaner.mybatisbeanbuddy.model.SerializableType
import com.intellij.openapi.components.*
import com.intellij.util.xmlb.Converter
import com.intellij.util.xmlb.annotations.OptionTag

@Service(Service.Level.PROJECT)
@State(name = "MyBatisBeanBuddySettings", storages = [Storage("mybatis-bean-buddy.xml")])
class Settings : SettingsState, BaseState(), PersistentStateComponent<Settings> {

    @get:OptionTag
    override var serializableType: String? by string(SerializableType.Serializable.name)

    @get:OptionTag
    override var classNamePostfix: String? by string("Dto")

    @get:OptionTag
    override var useLombok: Boolean by property(true)

    @get:OptionTag
    override var commentLinkRegexp: String? by string("DTO for (?:the )?\\{@link (?<entity>.*)\\}")

    @get:OptionTag
    override var classNameRegexp: String? by string("(?<entity>.*)(?:Dto|DTO|VO|Vo|View|Request|Response|Browse|Edit|Query)")

    @get:OptionTag
    override var classComment: String? by string("DTO for {@link \${ENTITY_FQN}}")

    override fun getState() = this

    override fun loadState(state: Settings) = copyFrom(state)


    private class SerializableTypeConverter : Converter<SerializableType>() {

        override fun fromString(value: String) = SerializableType.valueOf(value)

        override fun toString(value: SerializableType) = value.toString()
    }
}
