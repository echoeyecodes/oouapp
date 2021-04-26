package com.echoeyecodes.ign.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.echoeyecodes.dobby.utils.AndroidUtilities
import com.echoeyecodes.ign.MainActivity
import com.echoeyecodes.ign.R
import com.echoeyecodes.ign.api.constants.ApiState
import com.echoeyecodes.ign.callbacks.CustomEditTextCallBack
import com.echoeyecodes.ign.viewmodels.SignUpViewModel
import com.echoeyecodes.ign.customviews.CustomEditText
import com.echoeyecodes.ign.fragments.LoadingDialogFragment
import com.google.android.material.button.MaterialButton

class SignUpActivity : AppCompatActivity(), CustomEditTextCallBack{

    private lateinit var matricNumber:CustomEditText
    private lateinit var viewModel:SignUpViewModel
    private lateinit var password:CustomEditText
    private lateinit var loadingFragment: LoadingDialogFragment
    private lateinit var loginBtn: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        loginBtn = findViewById(R.id.btn)
        matricNumber = findViewById(R.id.matric_number)
        password = findViewById(R.id.password)
        loadingFragment = LoadingDialogFragment.newInstance()

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        viewModel.errorObserver.observe(this,  {
            if(it.key == ""){
                getView("matricnumber")?.setError(false, "");
                getView("password")?.setError(false, "");
            }else{
                getView(it.key)?.setError(true, it.message);
            }
        })

        viewModel.getStatus().observe(this, {
            when(it){
                is ApiState.Loading ->  loadingFragment.show(supportFragmentManager, LoadingDialogFragment.TAG)
                is ApiState.Complete -> {
                    dismissLoadingContainer()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is ApiState.Error ->{
                    dismissLoadingContainer()
                    AndroidUtilities.showToastMessage(this, it.data)
                }
            }
        })

        loginBtn.setOnClickListener { viewModel.submitForm() }
    }

    private fun dismissLoadingContainer(){
        if(loadingFragment.isVisible){
            loadingFragment.dismiss()
        }
    }


    override fun onTextInput(type: String, value: String) {
        if(this::viewModel.isInitialized){
            if(type == "matricnumber"){
                viewModel.matricnumber = value
            }else{
                viewModel.password = value
            }
            viewModel.validateFields()
        }
    }

    private fun getView(key: String): CustomEditText? {
        return when (key) {
            "matricnumber" -> matricNumber
            "password" -> password
            else -> null
        }
    }

    override fun onFocused(key: String, isFocused: Boolean) {
        if (!isFocused) {
            viewModel.validateFields()
        }
    }
}