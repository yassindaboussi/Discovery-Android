package tn.yassin.discovery.Utils.PasswordStrength

class StrengthRules() {

    fun anyDigit(password: String): Boolean {
        return password.any { c: Char ->  c.isDigit()}
    }

    fun anyUppercase(password: String): Boolean {
        return password.any{ c-> c.isUpperCase()}
    }

    fun anyLowerCase(password: String): Boolean{
        return password.any { c -> c.isLowerCase() }
    }


}