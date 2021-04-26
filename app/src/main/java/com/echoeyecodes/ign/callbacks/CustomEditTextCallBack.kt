package com.echoeyecodes.ign.callbacks

interface CustomEditTextCallBack {
        fun onTextInput(type: String, value: String)
        fun onFocused(key: String, isFocused: Boolean)
}