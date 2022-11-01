package com.lmslsbe.utils


const val APP_TAG = "Fpap"
const val KEY_USER_OBJECT = "fpap_user"
const val KEY_USER_MEDIUM = "medium"
const val KEY_COUNTRY_CODE_NAME = "pk"
const val MEMBER_SIGNUP_SUCCESSFULLY = "Member signup successfully"
const val MEMBER_SIGNIN_SUCCESSFULLY = "Member signin successfully"
const val PASSWORD_SEND_TO_EMAIL = "Your Account Password send To Your Email"
const val MEMBER_UPDATED = "Member Update successfully"
const val LANGUAGE_UPDATED_SUCCESSFULLY = "Update Language Medium Successfully!"

const val REQUEST_IMAGE = 1839
const val REQUEST_VIDEO = 1843
const val REQUEST_FILE = 1863
const val REQUEST_ALL = 1893


enum class Medium(val medium:String){
   English("English Medium"),
    Urdu("Urdu Medium")
}