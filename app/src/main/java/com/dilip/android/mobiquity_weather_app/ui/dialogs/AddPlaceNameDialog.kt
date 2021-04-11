package com.dilip.android.mobiquity_weather_app.ui.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.dilip.android.mobiquity_weather_app.R
import kotlinx.android.synthetic.main.add_place_name_dialog.*
import kotlinx.android.synthetic.main.add_place_name_dialog.view.*

class AddPlaceNameDialog(
    context: Context,
    private val onCtaClickListener: ((String) -> Unit)? = null
) : AlertDialog(context) {

    private lateinit var dialogView: View

    init {
        init(context)
    }

    private fun init(context: Context) {
        val inflater = LayoutInflater.from(context)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 20)
        window!!.setBackgroundDrawable(inset)
        dialogView = inflater.inflate(R.layout.add_place_name_dialog, null)
        setView(dialogView)
        setCancelable(true)
    }

    override fun show() {
        initLayout()
        super.show()
    }

    private fun initLayout() {
        dialogView.btnSave.setOnClickListener {
            if (sign_in_username.text.toString().isEmpty()){
                etTextInput.error = "Name should not be empty"
                return@setOnClickListener
            }
            this.dismiss()
            onCtaClickListener?.invoke(sign_in_username.text.toString())
        }
    }
}
