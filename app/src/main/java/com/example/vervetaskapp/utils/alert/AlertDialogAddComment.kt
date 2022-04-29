package com.example.vervetaskapp.utils.alert

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.vervetaskapp.R
import com.example.vervetaskapp.databinding.AlertDialogAddCommentBinding
import com.example.vervetaskapp.utils.activate

inline fun showAlertAddComment(
    context: Context,
    crossinline onSubmit: (String) -> Unit
) {
    val view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_add_comment, null)
    val builder = AlertDialog.Builder(context).setView(view)
    val dialog = builder.show()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    with(AlertDialogAddCommentBinding.bind(view)) {
        etComment.addTextChangedListener {
            btnSubmit.activate(!it?.toString().isNullOrEmpty())
        }
        btnSubmit.run {
            activate(false)
            setOnClickListener {
                etComment.text?.toString()?.let { onSubmit.invoke(it) }
                dialog.dismiss()
            }
        }
    }
}
