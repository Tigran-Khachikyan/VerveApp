package com.example.vervetaskapp.utils.alert

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.vervetaskapp.R
import com.example.vervetaskapp.databinding.AlertDialogAddTaskBinding
import com.example.vervetaskapp.models.Task
import com.example.vervetaskapp.utils.activate
import java.util.*

inline fun showAlertAddTask(
    context: Context,
    projectId: Int,
    crossinline onSubmit: (Task) -> Unit
) {
    val view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_add_task, null)
    val builder = AlertDialog.Builder(context).setView(view)
    val dialog = builder.show()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    with(AlertDialogAddTaskBinding.bind(view)) {
        etTitle.addTextChangedListener {
            btnSubmit.activate(!it?.toString().isNullOrEmpty())
        }
        btnSubmit.run {
            activate(false)
            setOnClickListener {
                etTitle.text?.toString()?.let { title ->
                    onSubmit.invoke(
                        Task(
                            title = title,
                            description = etDescription.text?.toString(),
                            creationDate = Calendar.getInstance().time,
                            comments = etComment.text?.toString()
                                ?.let { if (it.isNotEmpty()) listOf(it) else null },
                            inReview = cbInReview.isChecked,
                            projectId = projectId
                        )
                    )
                }
                dialog.dismiss()
            }
        }
    }
}
