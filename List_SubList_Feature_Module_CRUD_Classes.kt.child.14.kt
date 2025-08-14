package ${PACKAGE_NAME}.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import ${PACKAGE_NAME}.databinding.FragmentAddOrUpdateSub${NAME}Binding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditSub${NAME}Fragment : Fragment() {

    private var _binding: FragmentAddOrUpdateSub${NAME}Binding? = null
    private val binding get() = _binding!!

    private val viewModel: AddEditSub${NAME}ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOrUpdateSub${NAME}Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupClickListeners()
        observeViewModel()
    }
    
    private fun initView() {
        binding.header.tvHeaderTitle.setText("Add/Update Sub${NAME}")
        // Add click listener for back/menu button if it's interactive
        // binding.header.btnBack.setOnClickListener { /* Handle menu or back */ }
    }

    private fun setupClickListeners() {
        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val price = binding.editTextPrice.text.toString().toDouble()
            viewModel.saveSub${NAME}(
                name = name,
                price = price
            )
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isLoading.collect { isLoading ->
                        binding.apply {
                            editTextName.isEnabled = !isLoading
                            editTextPrice.isEnabled = !isLoading
                            progressBar.isVisible = isLoading
                        }
                    }
                }

                launch {
                    viewModel.isSaveButtonEnabled.collect { isEnabled ->
                        binding.buttonSave.isEnabled = isEnabled
                    }
                }

                launch {
                    viewModel.sub${NAME}.collectLatest { sub${NAME} ->
                        if (binding.editTextName.text.toString() != sub${NAME}.name) {
                            binding.editTextName.setText(sub${NAME}.name)
                        }
                        if (binding.editTextPrice.text.toString() != sub${NAME}.price.toString()) {
                            binding.editTextPrice.setText(sub${NAME}.price.toString())
                        }
                    }
                }

                // Observe messages
                launch {
                    viewModel.snackBarMessage.collect { customMessage ->
                        customMessage?.let {
                            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                            viewModel.clearSnackBarMessage() // Clear after showing
                        }
                    }
                }

                // Observe navigation events
                launch {
                    viewModel.navigateBackEvent.collect { // Collects Unit
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}