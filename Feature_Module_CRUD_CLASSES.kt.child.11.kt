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
import ${PACKAGE_NAME}.R
import ${PACKAGE_NAME}.domain.model.${NAME}
import ${PACKAGE_NAME}.databinding.Fragment${NAME}ListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ${NAME}ListFragment : Fragment() {

    private var _binding: Fragment${NAME}ListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ${NAME}ListViewModel by viewModels()
    private lateinit var ${NAME.toLowerCase()}Adapter: ${NAME}Adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = Fragment${NAME}ListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupClickListeners()
        observeViewModel() // Consolidated observer
        initView()
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.load${NAME}s(isForcedRefresh = true)
        }
    }

    private fun initView() {
        binding.header.tvHeaderTitle.setText("${NAME}s")
        binding.header.btnBack.setImageResource(R.drawable.ic_menu)
        // Add click listener for back/menu button if it's interactive
        // binding.header.btnBack.setOnClickListener { /* Handle menu or back */ }
    }

    private fun setupRecyclerView() {
        ${NAME.toLowerCase()}Adapter = ${NAME}Adapter(
            onItemClick = { ${NAME.toLowerCase()} -> viewModel.on${NAME}Clicked(${NAME.toLowerCase()}) },
            onDeleteItemClick = { ${NAME.toLowerCase()} -> showDeleteConfirmationDialog(${NAME.toLowerCase()}) }
        )
        binding.recyclerView.apply {
            adapter = ${NAME.toLowerCase()}Adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupClickListeners() {
        binding.fabAdd.setOnClickListener {
            viewModel.onAdd${NAME}Clicked()
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

                // Observe ${NAME.toLowerCase()}s list
                launch {
                    viewModel.${NAME.toLowerCase()}List.collectLatest { ${NAME.toLowerCase()}s ->
                        ${NAME.toLowerCase()}Adapter.submitList(${NAME.toLowerCase()}s)
                        binding.textViewEmptyList.isVisible = ${NAME.toLowerCase()}s.isEmpty() && !viewModel.isLoading.value
                        binding.recyclerView.isVisible = ${NAME.toLowerCase()}s.isNotEmpty()
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

                // Observe navigation to Add${NAME}
                launch {
                    viewModel.navigateToAdd${NAME}Event.collect {
                        val action = ${NAME}ListFragmentDirections.${NAME.toLowerCase()}ListToAddEdit${NAME}(null) // ${NAME.toLowerCase()}Id is null for new
                        findNavController().navigate(action)
                    }
                }

                // Observe navigation to Edit${NAME}
                launch {
                    viewModel.navigateToEdit${NAME}Event.collect { ${NAME.toLowerCase()}Id ->
                        val action = ${NAME}ListFragmentDirections.${NAME.toLowerCase()}ListToAddEdit${NAME}(${NAME.toLowerCase()}Id)
                        findNavController().navigate(action)
                    }
                }
            }
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.load${NAME}s(isForcedRefresh = true)
        }
    }

    private fun showDeleteConfirmationDialog(${NAME.toLowerCase()}: ${NAME}) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete ${NAME}")
            .setMessage("Are you sure you want to delete '${${NAME.toLowerCase()}.firstName}'?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.delete${NAME}(${NAME.toLowerCase()})
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