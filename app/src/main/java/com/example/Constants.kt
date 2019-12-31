package com.example

import android.Manifest

const val CLICK_IMAGE_REQUEST = 1

const val GET_IMAGE = 2

const val REQUEST_CODE_PERMISSIONS = 101

const val KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT"

const val MAX_NUMBER_REQUEST_PERMISSIONS = 2

val sPermissions: Array<String> = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)
const val API_BASE_URL = "https://api.github.com/"

const val GOOGLE_API_KEY ="AIzaSyC0CWqHrCdmIEzUu6INA5k0atcx4k_l1C0"

const val OXFORD_API = "a22127c3"

const val RC_SIGN = 9001

const val SUCCESS = 1

const val FAILURE =0

