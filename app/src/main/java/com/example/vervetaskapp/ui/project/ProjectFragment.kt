package com.example.vervetaskapp.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vervetaskapp.R
import com.example.vervetaskapp.adapter.TaskAdapter
import com.example.vervetaskapp.databinding.FragmentProjectBinding
import com.example.vervetaskapp.utils.alert.showAlertAddTask
import com.example.vervetaskapp.utils.visibleOrBone
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProjectFragment : Fragment() {

    private var _binding: FragmentProjectBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProjectViewModel by viewModel()
    private val args: ProjectFragmentArgs by navArgs()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectBinding.inflate(inflater, container, false)
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

    private fun FragmentProjectBinding.initView() {
        args.project.run {
            with(viewModel) {
                loadTasks(id)
                recyclerTasks.run {
                    setHasFixedSize(true)
                    taskAdapter = TaskAdapter { task ->
                        findNavController().navigate(ProjectFragmentDirections.viewTaskDetails(task.id))
                    }.also { adapter = it }

                    addItemDecoration(
                        DividerItemDecoration(
                            context, (layoutManager as LinearLayoutManager).orientation
                        )
                    )
                }
                fab.setOnClickListener {
                    showAlertAddTask(requireContext(), id) { addTask(it) }
                }
                tvProject.text = getString(R.string.tasks_of, title)
            }
        }
    }

    private fun FragmentProjectBinding.subscribe() {
        with(viewModel) {
            tasks.observe(viewLifecycleOwner) { tasks ->
                taskAdapter.submitList(tasks)
                tvNoItems.visibleOrBone(tasks.isEmpty())
            }
        }
    }
}