package tn.yassin.discovery.Utils.PasswordStrength
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StrengthCalculator() : TextWatcher{

    private val rules = StrengthRules()
    private var _score = 0
    private val _passwordState : MutableStateFlow<PasswordUIState> = MutableStateFlow(
        PasswordUIState()
    )
    val passwordUIState : StateFlow<PasswordUIState> get() = _passwordState


    private fun calculateStrength(password: String) {
        _score = if (password.length in 1..4){
            1
        }else if(password.length in 5..8 || password.length>=8){
            if ((rules.anyUppercase(password) == rules.anyDigit(password) && rules.anyDigit(password) == rules.anyLowerCase(password))){
                3
            }else 2
        }else{
            0
        }
        measureStrength()
    }

    private fun measureStrength(){
        when(_score){
            1 -> {
                _passwordState.value = PasswordUIState(PasswordStrength.WEAK, Color.RED, 1)
            }
            2 -> {
                _passwordState.value = PasswordUIState(PasswordStrength.MEDIUM, Color.parseColor("#F4CF00"), 2)
            }
            3 -> {
                _passwordState.value = PasswordUIState(PasswordStrength.HIGH, Color.GREEN, 3)
            }
            else ->{
                _passwordState.value = PasswordUIState(PasswordStrength.EMPTY, Color.GRAY, 0)
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        calculateStrength(p0.toString())
    }

    override fun afterTextChanged(p0: Editable?) {
    }
}