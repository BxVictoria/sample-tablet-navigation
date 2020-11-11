package ru.dialog.tabletfragmentnavigation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
fun Fragment.hideKeyboard() {
    (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let { inputMethodManager ->
        val token = activity?.currentFocus?.windowToken ?: activity?.window?.decorView?.windowToken
        inputMethodManager.hideSoftInputFromWindow(token, 0)
    }
}

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
fun Fragment.toggleKeyboard() {
    (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

/**
 * @return true if keyboard visible after method running
 */
fun Fragment.showKeyboard(view: View): Boolean {
    val isKeyboardVisible = (activity
            ?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    return isKeyboardVisible ?: false
}