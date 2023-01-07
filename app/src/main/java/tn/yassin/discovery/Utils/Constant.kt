package tn.yassin.discovery.Utils

import java.util.regex.Pattern

//
const val PREF_NAME = "PREF_DISCOVERY"
//////////////////////////////////////////// Partie Login
const val IDUSER = "ID"
const val NAMEUSER = "NAME"
const val EMAILUSER = "EMAIL"
const val BIOUSER = "BIO"
const val AVATARUSER = "AVATAR"
const val TOKENUSER = "TOKEN"
const val ROLEUSER = "TOKEN"
const val RememberEmail="RememberEmail"
const val RememberPassword="RememberPassword"
/////////////////////////////////////////////
const val EMAILFORGET = "EMAILFORGET"
const val CODEFORGET  = "CODEFORGET"























//
val emailRegex = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )



