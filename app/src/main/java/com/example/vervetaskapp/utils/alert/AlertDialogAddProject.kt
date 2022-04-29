package com.example.vervetaskapp.utils.alert

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.vervetaskapp.R
import com.example.vervetaskapp.databinding.AlertDialogAddProjectBinding
import com.example.vervetaskapp.models.Project
import com.example.vervetaskapp.utils.activate

inline fun showAlertAddProject(
    context: Context,
    crossinline onSubmit: (Project) -> Unit
) {
    val view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_add_project, null)
    val builder = AlertDialog.Builder(context).setView(view)
    val dialog = builder.show()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    with(AlertDialogAddProjectBinding.bind(view)) {
        etTitle.addTextChangedListener {
            btnSubmit.activate(!it?.toString().isNullOrEmpty())
        }
        btnSubmit.run {
            activate(false)
            setOnClickListener {
                etTitle.text?.toString()?.let { onSubmit.invoke(Project(title = it)) }
                dialog.dismiss()
            }
        }
    }
}
