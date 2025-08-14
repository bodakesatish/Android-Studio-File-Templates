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
import ${PACKAGE_NAME}.databinding.FragmentAddOrUpdate${NAME}Binding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEdit${NAME}Fragment : Fragment() {

    private var _binding: FragmentAddOrUpdate${NAME}Binding? = null
    private val binding get() = _binding!!

    private val viewModel: AddEdit${NAME}ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOrUpdate${NAME}Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupClickListeners()
        observeViewModel()
    }
    
    private fun initView() {
        //binding.header.tvHeaderTitle.setText("${NAME}s")
        //binding.header.btnBack.setImageResource(R.drawable.ic_menu)
        // Add click listener for back/menu button if it's interactive
        // binding.header.btnBack.setOnClickListener { /* Handle menu or back */ }
    }

    private fun setupClickListeners() {
        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val phone = binding.editTextPhone.text.toString().trim()
            val address = binding.editTextAddress.text.toString().trim()
            viewModel.save${NAME}(
                name = name,
                email = email,
                phone = phone,
                address = address
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
                            editTextEmail.isEnabled = !isLoading
                            editTextPhone.isEnabled = !isLoading
                            editTextAddress.isEnabled = !isLoading
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
                    viewModel.${NAME.toLowerCase()}.collectLatest { ${NAME.toLowerCase()} ->
                        if (binding.editTextName.text.toString() != ${NAME.toLowerCase()}.firstName) {
                            binding.editTextName.setText(${NAME.toLowerCase()}.firstName)
                        }
                        if (binding.editTextEmail.text.toString() != (${NAME.toLowerCase()}.email ?: "")) {
                            binding.editTextEmail.setText(${NAME.toLowerCase()}.email ?: "")
                        }
                        if (binding.editTextPhone.text.toString() != (${NAME.toLowerCase()}.phoneNumber ?: "")) {
                            binding.editTextPhone.setText(${NAME.toLowerCase()}.phoneNumber ?: "")
                        }
                        if (binding.editTextAddress.text.toString() != (${NAME.toLowerCase()}.address ?: "")) {
                            binding.editTextAddress.setText(${NAME.toLowerCase()}.address ?: "")
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