package com.github.skeaner.mybatisbeanbuddy.model

import com.intellij.util.xml.Convert
import com.intellij.util.xml.ConvertContext
import com.intellij.util.xmlb.Converter

enum class SerializableType  {

    Serializable,SerializableWithVersionUid, NoSerializable;


}