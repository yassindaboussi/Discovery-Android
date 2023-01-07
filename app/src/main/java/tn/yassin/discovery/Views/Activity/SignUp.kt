package tn.yassin.discovery.Views.Activity

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.PasswordStrength.StrengthCalculator
import tn.yassin.discovery.Utils.CustomToast
import tn.yassin.discovery.Utils.ReadyFunction
import tn.yassin.discovery.Utils.Validator
import tn.yassin.discovery.ViewModel.UserViewModel

class SignUp : AppCompatActivity() {
    private lateinit var btnGoToLogin: TextView
    //
    private lateinit var txtNameLayout: TextInputLayout
    private lateinit var txtEmailLayout: TextInputLayout
    private lateinit var txtPasswordLayout: TextInputLayout
    private lateinit var txtConfirmPasswordLayout: TextInputLayout
    //
    private lateinit var txtName: TextInputEditText
    private lateinit var txtEmail: TextInputEditText
    private lateinit var txtPwd: TextInputEditText
    private lateinit var txtConfirmPwd: TextInputEditText
    //
    private val strengthCalculator = StrengthCalculator()
    private lateinit var pbPasswordStrength: ProgressBar
    private lateinit var twPasswordStrength:TextView
    //
    private lateinit var btnSignUp:Button
    //
    private lateinit var AllOfPageSingUp: RelativeLayout
    //
    val ReadyFunction = ReadyFunction()
    val Validator = Validator()
    //
    private val viewModel by viewModels<UserViewModel>()
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
        //
        initView()
        GoToLogin()
        CLickToHidKeyBoard()
        PasswordCalculator()
        DoActionSingUp()
        //
        SetAnimation()
    }


    fun SetAnimation()
    {
        //Animation
        ReadyFunction.animationFadeInOut(txtNameLayout,800)
        ReadyFunction.animationFadeInOut(txtEmailLayout,1500)
        ReadyFunction.animationFadeInOut(txtPasswordLayout,1500)
        ReadyFunction.animationFadeInOut(txtConfirmPasswordLayout,1500)

        ReadyFunction.animationFadeIn(pbPasswordStrength,1500)
        ReadyFunction.animationFadeIn(btnSignUp,500)
        ReadyFunction.animationFadeIn(btnGoToLogin,600)
    }

    fun GoToLogin()
    {
        btnGoToLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java).apply {}
            startActivity(intent)
            finish()
        }
    }

    fun initView()
    {
        AllOfPageSingUp = findViewById(R.id.AllOfPageSingUp)
        //
        btnGoToLogin = findViewById(R.id.btnGoToLogin)
        pbPasswordStrength = findViewById(R.id.pb_password_strength)
        twPasswordStrength = findViewById(R.id.twPasswordStrength)
        //
        txtNameLayout = findViewById(R.id.txtNameLayout)
        txtEmailLayout = findViewById(R.id.txtEmailLayoutSignUp)
        txtPasswordLayout = findViewById(R.id.txtPasswordLayoutSignUp)
        txtConfirmPasswordLayout = findViewById(R.id.txtConfirmPasswordLayout)
        //
        txtPwd = findViewById(R.id.txtPwdSignUp)
        txtConfirmPwd = findViewById(R.id.txtConfirmPwd)
        txtName = findViewById(R.id.txtName)
        txtEmail = findViewById(R.id.txtEmailSignUp)
        //
        btnSignUp = findViewById(R.id.btnSignUp)
    }

    fun PasswordCalculator()
    {
        txtPwd.addTextChangedListener(strengthCalculator)
        lifecycleScope.launchWhenCreated {
            strengthCalculator.passwordUIState.collect{
                pbPasswordStrength.progress = it.progressScore
                pbPasswordStrength.progressTintList = ColorStateList.valueOf(it.color)
                twPasswordStrength.text = it.strength.name
                twPasswordStrength.setTextColor(ColorStateList.valueOf(it.color))
            }
        }
    }

    fun DoActionSingUp() {
         gettextwathcerSignUp()
         btnSignUp.setOnClickListener {
             if (!Validator.VerifisEmpty(txtName,txtNameLayout) or !Validator.VerifisEmpty(txtEmail,txtEmailLayout)
                 or !Validator.VerifisEmpty(txtPwd,txtPasswordLayout) or !Validator.VerifisEmpty(txtConfirmPwd,txtConfirmPasswordLayout)
                 or !Validator.ValidatFormatEmail(txtEmail,txtEmailLayout)
                 or !isEqualPassowrd() or !LowPassword() or !Validator.VerifLength(txtName,txtNameLayout,3)) {
                 println("Something is Not Correct!")
                 return@setOnClickListener
             } else {
                 println("let's Go!!!!!")
                 viewModel.Register(txtName,txtEmail,txtPwd,this,this@SignUp)
             }
         }
    }

    fun LowPassword(): Boolean {
        val Value: String = twPasswordStrength.text.toString()
        println("Valuuuuuuueeee ==>>>>> "+Value)
        if (Value.equals("EMPTY") || Value.equals("WEAK")) {
            txtPasswordLayout.error = "Weak Password!"
            return false
        }
        else {
            txtPasswordLayout.setError(null)
            txtPasswordLayout.setErrorEnabled(false)
            return true
        }
        return true
    }

    fun isEqualPassowrd(): Boolean {
        if (!txtPwd.text.toString().equals(txtConfirmPwd.text.toString())) {
            CustomToast(this, "The Password is Not the Same!","RED").show()
            return false
        }
        else {
            return true
        }
        return true
    }

  private fun gettextwathcerSignUp() {
        txtName.addTextChangedListener(NametxtWatcher)
        txtEmail.addTextChangedListener(EmailtxtWatcher)
        txtPwd.addTextChangedListener(PasswordtxtWatcher)
        txtConfirmPwd.addTextChangedListener(PasswordConfirmtxtWatcher)
    }

    private val NametxtWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            Validator.VerifisEmpty(txtName,txtNameLayout)
        }
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
    private val PasswordConfirmtxtWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            Validator.VerifisEmpty(txtConfirmPwd,txtConfirmPasswordLayout)
        }
    }

    fun CLickToHidKeyBoard() {
        AllOfPageSingUp.setOnClickListener {
            ReadyFunction.hideKeyboard(txtName, this)
        }
    }

}