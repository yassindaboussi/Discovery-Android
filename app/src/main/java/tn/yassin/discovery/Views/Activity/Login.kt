package tn.yassin.discovery.Views.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import tn.yassin.discovery.Utils.*

import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.ReadyFunction
import tn.yassin.discovery.Utils.Validator
import tn.yassin.discovery.ViewModel.UserViewModel

class Login : AppCompatActivity() {
    private lateinit var txtForgetPassword: TextView
    private lateinit var btnGoToSignUp: TextView
    //
    private lateinit var txtEmailLayout: TextInputLayout
    private lateinit var txtPasswordLayout: TextInputLayout
    private lateinit var txtEmail: TextInputEditText
    private lateinit var txtPwd: TextInputEditText
    //
    private lateinit var cbRememberMe: CheckBox
    private lateinit var ProgressbarLogin: ProgressBar
    private lateinit var btnLogin: Button
    private lateinit var AllOfPageLogin: RelativeLayout
    //
    val ReadyFunction = ReadyFunction()
    val Validator = Validator()
    //
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var MySharedPref: SharedPreferences
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        //
        MySharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //
        initView();
        SetLastEmail()
        DoActionLogin()
        CLickToHidKeyBoard()
        GoToSignUp()
        GoToForget()
        //
        SetAnimation()
    }

    fun SetAnimation()
    {
        //Animation
        ReadyFunction.animationFadeInOut(txtEmailLayout,800)
        ReadyFunction.animationFadeInOut(txtPasswordLayout,1500)
        ReadyFunction.animationFadeInOut(cbRememberMe,1500)

        ReadyFunction.animationFadeIn(btnLogin,1600)
        ReadyFunction.animationFadeIn(txtForgetPassword,500)
        ReadyFunction.animationFadeIn(btnGoToSignUp,600)
    }

    fun SetLastEmail()
    {
        val LastEmail = MySharedPref.getString(EMAILUSER, null)
        txtEmail.setText(LastEmail)
    }

    fun initView() {
        btnLogin = findViewById(R.id.btnLogin)
        btnGoToSignUp = findViewById(R.id.btnGoToSignUp)
        txtForgetPassword = findViewById(R.id.txtForgetPassword)
        //
        txtEmailLayout = findViewById(R.id.txtEmailLayout)
        txtPasswordLayout = findViewById(R.id.txtPasswordLayout)
        txtEmail = findViewById(R.id.txtEmail)
        txtPwd = findViewById(R.id.txtPwd)
        //
        cbRememberMe = findViewById(R.id.comboRememberMe)
        ProgressbarLogin = findViewById(R.id.progressbar_login)
        AllOfPageLogin = findViewById(R.id.AllOfPageLogin)
    }

    fun GoToSignUp() {
        btnGoToSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java).apply {}
            startActivity(intent)
            finish()
        }
    }

    fun GoToForget() {
        txtForgetPassword.setOnClickListener {
            val intent = Intent(this, ForgetPassword1::class.java).apply {}
            startActivity(intent)
            finish()
        }
    }


    fun DoActionLogin() {

        gettextwathcerlogin()
        btnLogin.setOnClickListener {
            ReadyFunction.showView(ProgressbarLogin)
            if (!Validator.VerifisEmpty(txtEmail,txtEmailLayout) or !Validator.VerifisEmpty(txtPwd,txtPasswordLayout)
                or !Validator.ValidatFormatEmail(txtEmail,txtEmailLayout)) {
                println("Something is Not Correct!")
                ReadyFunction.hideView(ProgressbarLogin)
                return@setOnClickListener
            } else {
                println("let's Go!!!!!")
                //ReadyFunction.showView(ProgressbarLogin)
                //
                viewModel.login1(txtEmail, txtPwd, this, ProgressbarLogin, this,cbRememberMe)
                //viewModel.login2(txtEmailLogin, txtPasswordLogin)
                //
                /* viewModel.getUsers()
                println("aaaaaaaaaaa " + viewModel.getUsers()!!.value)
                Log.d(TAG, viewModel.getUsers()!!.value.toString())*/
            }
        }
    }

    fun CLickToHidKeyBoard() {
        AllOfPageLogin.setOnClickListener {
           ReadyFunction.hideKeyboard(txtEmail, this@Login)
        }
    }

    private fun gettextwathcerlogin() {
        txtEmail.addTextChangedListener(EmailtxtWatcher)
        txtPwd.addTextChangedListener(PasswordtxtWatcher)
    }

    private val EmailtxtWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            Validator.VerifisEmpty(txtEmail,txtEmailLayout)
        }
    }
    private val PasswordtxtWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            Validator.VerifisEmpty(txtPwd,txtPasswordLayout)
        }
    }




}