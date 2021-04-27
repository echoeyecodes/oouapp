package com.echoeyecodes.scrub.customviews

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.RelativeLayout
import com.echoeyecodes.scrub.R
import com.echoeyecodes.scrub.callbacks.CustomEditTextCallBack
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CustomEditText(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputEditText: TextInputEditText
    private val formEditTextListener: CustomEditTextCallBack = context as CustomEditTextCallBack
    private lateinit var form_type: String

    private fun init(context: Context, attributeSet: AttributeSet) {
        inflate(context, R.layout.layout_edit_text, this)
        val typedArray = context.theme.obtainStyledAttributes(attributeSet, R.styleable.CustomEditText, 0, 0)
        initViews()
        try {
            val name = typedArray.getString(R.styleable.CustomEditText_text)
            form_type = typedArray.getString(R.styleable.CustomEditText_form_type)!!
            val hint = typedArray.getString(R.styleable.CustomEditText_hint)
            val type = typedArray.getInt(R.styleable.CustomEditText_inputType, 0)
            val isSecure = typedArray.getBoolean(R.styleable.CustomEditText_isSecure, false)
            setText(name)
            setHint(hint)
            setInputType(type)
            setEndIconMode(isSecure)
        } finally {
            run { typedArray.recycle() }
        }
    }

    private fun refreshLayout() {
        invalidate()
        requestLayout()
    }

    private fun setEndIconMode(value: Boolean) {
        textInputLayout.endIconMode = if (value) TextInputLayout.END_ICON_PASSWORD_TOGGLE else TextInputLayout.END_ICON_NONE
    }

    private fun initViews() {
        textInputLayout = findViewById(R.id.form_text_input_layout)
        textInputEditText = findViewById(R.id.form_text_input)
        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                formEditTextListener.onTextInput(form_type, s.toString())
            }
        })
        textInputEditText.onFocusChangeListener = OnFocusChangeListener { v: View?, hasFocus: Boolean -> formEditTextListener.onFocused(form_type, hasFocus) }
    }

    fun setHint(charSequence: CharSequence?) {
        textInputLayout.hint = charSequence
        refreshLayout()
    }

    fun setText(charSequence: CharSequence?) {
        textInputEditText.setText(charSequence)
        refreshLayout()
    }

    fun setError(enable: Boolean, message: String?) {
        textInputLayout.isErrorEnabled = enable
        textInputLayout.error = message
        refreshLayout()
    }

    private fun setInputType(type: Int) {
        when (type) {
            0 -> textInputEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
            1 -> textInputEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
            2 -> textInputEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            3 -> textInputEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        refreshLayout()
    }

    init {
        init(context, attrs)
    }
}