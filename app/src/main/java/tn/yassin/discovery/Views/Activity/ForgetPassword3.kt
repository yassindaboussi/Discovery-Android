package tn.yassin.discovery.Views.Activity

import android.content.Intent
import android.content.SharedPreferences
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
import tn.yassin.discovery.Utils.*
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.CustomToast
import tn.yassin.discovery.Utils.ReadyFunction
import tn.yassin.discovery.Utils.Validator
import tn.yassin.discovery.ViewModel.UserViewModel

class ForgetPassword3 : AppCompatActivity() {
    private lateinit var btnGoToLogin: TextView
    private lateinit var btnChangePass: Button
    private lateinit var AllOfPageOfForget: RelativeLayout
    private lateinit var txtPasswordLayout: TextInputLayout
    private lateinit var txtConfirmPasswordLayout: TextInputLayout

    //
    private lateinit var txtPwd: TextInputEditText
    private lateinit var txtConfirmPwd: TextInputEditText

    //
    val ReadyFunction = ReadyFunction()
    val Validator = Validator()

    //
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var MySharedPref: SharedPreferences
    private lateinit var EmailForgot: String
    private lateinit var CodeForgot: String

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forget_pass3)
        //
        initView()
        CLickToHidKeyBoard()
        //
        ChangePassword()
        GoToLogin()
        //
        EmailForgot = MySharedPref.getString(EMAILFORGET, null).toString()
        CodeForgot = MySharedPref.getString(CODEFORGET, null).toString()
    }

    fun initView() {
        btnChangePass = findViewById(R.id.btnChangePasswordForget)
        //
        txtPwd = findViewById(R.id.txtPwdForget)
        txtConfirmPwd = findViewById(R.id.txtConfirmPwdForget)
        txtPasswordLayout = findViewById(R.id.txtPasswordLayoutForget)
        txtConfirmPasswordLayout = findViewById(R.id.txtConfirmPasswordLayoutForget)
        //
        AllOfPageOfForget = findViewById(R.id.AllPageOfForget3)
        //
        MySharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //
        btnGoToLogin = findViewById(R.id.btnGoToLogin4)
    }

    fun ChangePassword() {
        gettextwathcerSignUp()
        btnChangePass.setOnClickListener {
            if (!Validator.VerifisEmpty(txtPwd, txtPasswordLayout)
                or !Validator.VerifisEmpty(txtConfirmPwd, txtConfirmPasswordLayout)
                or !isEqualPassowrd() or !Validator.VerifLength(txtPwd,txtPasswordLayout,5)
            ) {
                println("Something is Not Correct!")
                return@setOnClickListener
            } else {
                println("let's Go!!!!!")
                val Newpass = txtPwd.text.toString().trim()
                viewModel.ChangePasswordForgot(EmailForgot, CodeForgot, Newpass, this, this)
            }
        }
    }

    fun isEqualPassowrd(): Boolean {
        if (!txtPwd.text.toString().equals(txtConfirmPwd.text.toString())) {
            CustomToast(this, "The Password is Not the Same!", "RED").show()
            return false
        } else {
            return true
        }
        return true
    }

    private fun gettextwathcerSignUp() {
        txtPwd.addTextChangedListener(PasswordtxtWatcher)
        txtConfirmPwd.addTextChangedListener(PasswordConfirmtxtWatcher)
    }

    private val PasswordtxtWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            Validator.VerifisEmpty(txtPwd, txtPasswordLayout)
        }
    }
    private val PasswordConfirmtxtWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            Validator.VerifisEmpty(txtConfirmPwd, txtConfirmPasswordLayout)
        }
    }

    fun CLickToHidKeyBoard() {
        AllOfPageOfForget.setOnClickListener {
            ReadyFunction.hideKeyboard(txtPwd, this)
        }
    }
    fun GoToLogin()
    {
        btnGoToLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java).apply {}
            startActivity(intent)
            finish()
        }
    }
}