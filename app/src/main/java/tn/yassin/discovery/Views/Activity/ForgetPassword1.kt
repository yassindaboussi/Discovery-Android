package tn.yassin.discovery.Views.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.ReadyFunction
import tn.yassin.discovery.Utils.Validator
import tn.yassin.discovery.ViewModel.UserViewModel

class ForgetPassword1 : AppCompatActivity() {
    private lateinit var txtEmailLayout: TextInputLayout
    private lateinit var txtEmail: TextInputEditText
    private lateinit var btnSendCode: Button
    private lateinit var btnGoToLogin: TextView
    private lateinit var AllOfPageOfForget: RelativeLayout
    //
    val ReadyFunction = ReadyFunction()
    val Validator = Validator()
    ///
    private val viewModel by viewModels<UserViewModel>()
    ///
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forget_pass1)
        //
        intView()
        CLickToHidKeyBoard()
        SendCodeFoget()
        GoToLogin()
    }

    fun intView()
    {
        AllOfPageOfForget = findViewById(R.id.AllPageOfForget1)
        btnSendCode = findViewById(R.id.btnForgetContinuer)
        txtEmailLayout = findViewById(R.id.txtEmailLayoutForget)
        txtEmail = findViewById(R.id.txtEmailForget)
        btnGoToLogin = findViewById(R.id.btnGoToLogin2)
    }

    fun GoToLogin()
    {
        btnGoToLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java).apply {}
            startActivity(intent)
            finish()
        }
    }

    fun SendCodeFoget() {
        gettextwathcerlogin()
        btnSendCode.setOnClickListener {
            if (!Validator.VerifisEmpty(txtEmail,txtEmailLayout) or !Validator.ValidatFormatEmail(txtEmail,txtEmailLayout)) {
                println("Something is Not Correct!")
                return@setOnClickListener
            } else {
                println("let's Go!!!!!")
                viewModel.SendCodeForgot(txtEmail.text.toString().trim(), this,this@ForgetPassword1)
            }
        }
    }

    private fun gettextwathcerlogin() {
        txtEmail.addTextChangedListener(EmailtxtWatcher)
    }

    private val EmailtxtWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            Validator.VerifisEmpty(txtEmail,txtEmailLayout)
        }
    }

    fun CLickToHidKeyBoard() {
        AllOfPageOfForget.setOnClickListener {
            ReadyFunction.hideKeyboard(txtEmail, this@ForgetPassword1)
        }
    }


}