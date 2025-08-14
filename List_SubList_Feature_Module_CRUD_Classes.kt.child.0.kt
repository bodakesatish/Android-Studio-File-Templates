package ${PACKAGE_NAME}.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
//import ${PACKAGE_NAME}.domain.model.Sub${NAME}
import ${PACKAGE_NAME}.databinding.FragmentSub${NAME}ListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Sub${NAME}ListFragment : Fragment() {

    private var _binding: FragmentSub${NAME}ListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: Sub${NAME}ListViewModel by viewModels()
    private lateinit var sub${NAME}Adapter: Sub${NAME}Adapter

            override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSub${NAME}ListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupClickListeners()
        observeViewModel() // Consolidated observer
        initView()
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadSub${NAME}s(isForcedRefresh = true)
        }
    }

    private fun initView() {
        binding.header.tvHeaderTitle.setText("Sub${NAME}s")
        // Add click listener for back/menu button if it's interactive
        // binding.header.btnBack.setOnClickListener { /* Handle menu or back */ }
    }

    private fun setupRecyclerView() {
        sub${NAME}Adapter = Sub${NAME}Adapter(
                onItemClick = { sub${NAME} -> viewModel.onSub${NAME}Clicked(sub${NAME}) },
        onDeleteItemClick = { sub${NAME} -> showDeleteConfirmationDialog(sub${NAME}) }
        )
        binding.recyclerView.apply {
            adapter = sub${NAME}Adapter
                layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupClickListeners() {
        binding.fabAdd.setOnClickListener {
            viewModel.onAddSub${NAME}Clicked()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Observe isLoading state
                launch {
                    viewModel.isLoading.collect { isLoading ->
                        if (!binding.swipeRefreshLayout.isRefreshing) {
                            binding.progressBar.isVisible = isLoading
                        }
                        if (!isLoading) {
                            binding.swipeRefreshLayout.isRefreshing = false
                        }
                    }
                }

                // Observe categorys list
                launch {
                    viewModel.sub${NAME}List.collectLatest { sub${NAME}s ->
                    sub${NAME}Adapter.submitList(sub${NAME}s)
                    binding.textViewEmptyList.isVisible = sub${NAME}s.isEmpty() && !viewModel.isLoading.value
                    binding.recyclerView.isVisible = sub${NAME}s.isNotEmpty()
                }
                }

                // Observe general error messages
                launch {
                    viewModel.snackBarMessage.collect { errorMessage ->
                        errorMessage?.let {
                            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                            viewModel.clearSnackBarMessage() // Acknowledge error shown
                        }
                    }
                }

                // Observe navigation to AddSub${NAME}
                launch {
                    viewModel.navigateToAddSub${NAME}Event.collect { pairOfStrings ->
                    val categoryId = pairOfStrings.first
                    val sub${NAME}Id = pairOfStrings.second
                    val action = Sub${NAME}ListFragmentDirections.sub${NAME}ListToAddEditSub${NAME}(categoryId, sub${NAME}Id) // categoryId is null for new
                    findNavController().navigate(action)
                }
                }

                // Observe navigation to EditSub${NAME}
                launch {
                    viewModel.navigateToEditSub${NAME}Event.collect { pairOfStrings ->
                    val categoryId = pairOfStrings.first
                    val sub${NAME}Id = pairOfStrings.second
                    val action = Sub${NAME}ListFragmentDirections.sub${NAME}ListToAddEditSub${NAME}(categoryId, sub${NAME}Id)
                    findNavController().navigate(action)
                }
                }
            }
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.loadSub${NAME}s(isForcedRefresh = true)
        }
    }

    private fun showDeleteConfirmationDialog(sub${NAME}: Sub${NAME}) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Sub${NAME}")
            .setMessage("Are you sure you want to delete "+sub${NAME}.name+" ?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteSub${NAME}(sub${NAME})
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }
}