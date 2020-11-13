package ru.dialog.tabletfragmentnavigation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment

abstract class BaseDialogFragment : AppCompatDialogFragment() {
    abstract val layout: Int


    private var dialogProgress: AlertDialog? = null
    private var dialogLoadContinue: AlertDialog? = null

    protected lateinit var homeActivity: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(layout, null)

    fun onBackPressed() {
        backPressAction()
    }

    protected open fun hideProgress() {
        dialogProgress?.dismiss()
        dialogProgress = null
    }

    protected open fun hideContinueLoadDialog() {
        dialogLoadContinue?.dismiss()
        dialogLoadContinue = null
    }

    open fun backPressAction() {}

    fun toast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppCompatActivity) {
            homeActivity = context
        }
    }
}