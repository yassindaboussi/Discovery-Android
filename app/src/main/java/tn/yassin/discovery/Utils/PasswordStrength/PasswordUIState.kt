package tn.yassin.discovery.Utils.PasswordStrength

import android.graphics.Color

data class PasswordUIState(
    val strength: PasswordStrength = PasswordStrength.EMPTY,
    val color: Int = Color.GRAY,
    val score: Int = 0,
    val progressScore : Int = score * 33
)