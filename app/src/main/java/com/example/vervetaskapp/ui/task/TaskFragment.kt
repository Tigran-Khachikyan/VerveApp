package com.example.vervetaskapp.ui.task

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vervetaskapp.R
import com.example.vervetaskapp.adapter.CommentAdapter
import com.example.vervetaskapp.databinding.FragmentTaskBinding
import com.example.vervetaskapp.utils.alert.showAlertAddComment
import com.example.vervetaskapp.utils.visibleOrBone
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat

class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TaskViewModel by viewModel()
    private val args: TaskFragmentArgs by navArgs()
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            initView()
            subscribe()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun FragmentTaskBinding.initView() {
        with(viewModel) {
            loadTaskDetails(args.taskId)
            etTitle.addTextChangedListener {
                it?.toString()?.let { input -> onTitleChanged(input) }
            }
            etDescription.addTextChangedListener {
                it?.toString()?.let { input -> onDescriptionChanged(input) }
            }
            cbInReview.setOnCheckedChangeListener { _, checked -> onReviewStateChanged(checked) }
            recComment.run {
                setHasFixedSize(false)
                commentAdapter = CommentAdapter()
                val dividerItemDecoration = DividerItemDecoration(
                    context, (layoutManager as LinearLayoutManager).orientation
                )
                addItemDecoration(dividerItemDecoration)
                adapter = commentAdapter
            }
            btnSubmit.setOnClickListener { onSubmit() }
            fabAddComment.setOnClickListener {
                showAlertAddComment(requireContext()) { addComment(it) }
            }
        }
    }

    private fun FragmentTaskBinding.subscribe() {
        with(viewModel) {
            savedTask.observe(viewLifecycleOwner) { task ->
                etTitle.setText(task.title)
                etDescription.setText(task.description)
                val dateTime = DateFormat.getDateTimeInstance().format(task.creationDate)
                tvDateTime.text = getString(R.string.created_at, dateTime)
                cbInReview.isChecked = task.inReview
            }
            isSubmitActivated.observe(viewLifecycleOwner) { active ->
                btnSubmit.visibleOrBone(active)
            }
            comments.observe(viewLifecycleOwner) { comments ->
                commentAdapter.submitList(comments)
            }
            message.observe(viewLifecycleOwner) { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).run {
                    setGravity(Gravity.CENTER, 0, 0)
                    show()
                }
            }
        }
    }
}