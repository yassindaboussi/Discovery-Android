package tn.yassin.discovery.Utils

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import tn.yassin.discovery.Utils.emailRegex


class Validator {
    fun VerifisEmpty(txtName: TextInputEditText, txtNameLayout: TextInputLayout): Boolean {
        val Value: String = txtName.text.toString().trim()
        if (Value.isEmpty()) {
            txtNameLayout.error = "Must Not Be Empty!"
            return false
        } else {
            txtNameLayout.setError(null)
            txtNameLayout.setErrorEnabled(false)
            return true
        }
        return true
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    fun ValidatFormatEmail(txtEmail: TextInputEditText, txtEmailLayout: TextInputLayout): Boolean {
        val Email: String = txtEmail.text.toString().trim()
        if (!(emailRegex.matcher(Email).matches())) {
            txtEmailLayout.error = "Email Malformed!"
            return false
        } else {
            txtEmailLayout.setError(null)
            txtEmailLayout.setErrorEnabled(false)
            return true
        }
        return true
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    fun VerifLength(txtName: TextInputEditText, txtNameLayout: TextInputLayout,MinValue:Int): Boolean {
        val Value: String = txtName.text.toString().trim()
        if (Value.length<MinValue) {
            txtNameLayout.error = "Must Not Be More Then "+MinValue
            return false
        } else {
            txtNameLayout.setError(null)
            txtNameLayout.setErrorEnabled(false)
            return true
        }
        return true
    }
}