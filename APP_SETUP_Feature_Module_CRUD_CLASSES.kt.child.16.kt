package ${PACKAGE_NAME}.app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ${PACKAGE_NAME}.databinding.Activity${NAME}Binding
import dagger.hilt.android.AndroidEntryPoint
import ${PACKAGE_NAME}.R

@AndroidEntryPoint
class ${NAME}Activity : AppCompatActivity() {

    private lateinit var binding: Activity${NAME}Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = Activity${NAME}Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}