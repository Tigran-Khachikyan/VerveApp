package com.example.vervetaskapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vervetaskapp.adapter.ProjectAdapter
import com.example.vervetaskapp.databinding.FragmentDashboardBinding
import com.example.vervetaskapp.utils.alert.showAlertAddProject
import com.example.vervetaskapp.utils.visibleOrBone
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModel()
    private lateinit var projectAdapter: ProjectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
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

    private fun FragmentDashboardBinding.initView() {
        recyclerProjects.run {
            setHasFixedSize(true)
            projectAdapter = ProjectAdapter { project ->
                findNavController().navigate(
                    DashboardFragmentDirections.viewProjectDetails(project)
                )
            }
            val dividerItemDecoration = DividerItemDecoration(
                context, (layoutManager as LinearLayoutManager).orientation
            )
            addItemDecoration(dividerItemDecoration)
            adapter = projectAdapter
        }
        fab.setOnClickListener {
            showAlertAddProject(requireContext()) {
                viewModel.addProject(it)
            }
        }
    }

    private fun FragmentDashboardBinding.subscribe() {
        with(viewModel) {
            projects.observe(viewLifecycleOwner) { projects ->
                projectAdapter.submitList(projects)
                tvNoItems.visibleOrBone(projects.isEmpty())
            }
        }
    }

}