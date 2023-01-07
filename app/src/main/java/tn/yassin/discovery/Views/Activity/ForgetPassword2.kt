package tn.yassin.discovery.Views.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chaos.view.PinView
import tn.yassin.discovery.Utils.*
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.CustomToast
import tn.yassin.discovery.Utils.ReadyFunction
import tn.yassin.discovery.ViewModel.UserViewModel


class ForgetPassword2 : AppCompatActivity() {
    lateinit var txtTimer: TextView
    lateinit var pinView: PinView
    private lateinit var btnSubmit: Button
    private lateinit var btnGoToLogin: TextView
    private lateinit var btnResendCode: TextView
    private lateinit var AllOfPageOfForget: RelativeLayout
    //
    val ReadyFunction = ReadyFunction()
    ///
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var MySharedPref: SharedPreferences
    private lateinit var EmailForgot : String
    ///
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forget_pass2)

        initView()
        CLickToHidKeyBoard()
        PinCodeDesign()
        ResendCode()
        VerifCode()
        GoToLogin()
        EmailForgot = MySharedPref.getString(EMAILFORGET, null).toString()
    }

    fun PinCodeDesign() {
        pinView.setItemCount(4);
        pinView.setCursorVisible(true);
        pinView.setHideLineWhenFilled(false);
        pinView.setPasswordHidden(false);
        pinView.setItemBackgroundColor(Color.parseColor("#F7F7F7"));
    }

    fun VerifCode() {
        btnSubmit.setOnClickListener {
            if(pinView.length()==4)
            {
               viewModel.VerifCodeForgot(EmailForgot,pinView.text.toString(),this,this)
            }
            else
            {
                CustomToast(this, "Enter The code!","RED").show()
            }
        }
    }



    fun ResendCode() {
        btnResendCode.setOnClickListener {

            viewModel.SendCodeForgot(EmailForgot, this,this)
/*            val timer = object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    btnResendCode.setClickable(false)
                    txtTimer.setText("Resend in : " + millisUntilFinished / 1000)
                }
                override fun onFinish() {
                    txtTimer.setText("Try Now!")
                    btnResendCode.setClickable(true)
                }
            }
            timer.start()*/
        }
    }

    fun initView() {
        btnSubmit = findViewById(R.id.btnForgetSubmit)
        txtTimer = findViewById(R.id.txtTimer)
        btnResendCode = findViewById(R.id.btnResendCode)
        AllOfPageOfForget = findViewById(R.id.AllPageOfForget2)
        //
        pinView = findViewById(R.id.firstPinView);
        //
        MySharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //
        btnGoToLogin = findViewById(R.id.btnGoToLogin3)
    }

    fun CLickToHidKeyBoard() {
        AllOfPageOfForget.setOnClickListener {
            ReadyFunction.hideKeyboard(pinView, this@ForgetPassword2)
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