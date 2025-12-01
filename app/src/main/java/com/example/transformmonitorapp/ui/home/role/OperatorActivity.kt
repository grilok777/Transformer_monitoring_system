package com.example.transformmonitorapp.ui.home.role

import GenericViewModelFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.data.repository.impl.OperatorRepositoryImpl
import com.example.transformmonitorapp.views.models.OperatorViewModel
import kotlin.getValue

class OperatorActivity : RoleActivity() {

    val token: String by lazy { homeViewModel.loadToken() ?: "" }

    private lateinit var btnGetAll: Button
    private lateinit var btnGetById: Button
    private lateinit var btnGetAlerts: Button
    private lateinit var btnProcessError: Button
    private lateinit var inputId: EditText
    private lateinit var output: TextView

    private val viewModel: OperatorViewModel by viewModels {
        GenericViewModelFactory {
            OperatorViewModel(
                application,
                OperatorRepositoryImpl(applicationContext, token)
            )
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_operator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViews()
        observeViewModel()
        setupProfileView()
        setupListeners()
    }

    private fun bindViews() {
        btnGetAll = findViewById(R.id.btnGetAll)
        btnGetById = findViewById(R.id.btnGetById)
        btnGetAlerts = findViewById(R.id.btnGetAlerts)
        btnProcessError = findViewById(R.id.btnProcessError)
        inputId = findViewById(R.id.inputTransformerId)
        output = findViewById(R.id.tvOutput)
    }

    override fun customizeAsideMenu() {}

    private fun observeViewModel() {

        viewModel.transformers.observe(this) { list ->
            output.text = list.joinToString("\n\n")
        }

        viewModel.transformer.observe(this) { t ->
            output.text = t.toString()
        }

        viewModel.alerts.observe(this) { list ->
            output.text = list.joinToString("\n\n")
        }

        viewModel.message.observe(this) {
            output.text = it
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupListeners() {

        btnGetAll.setOnClickListener {
            println("=== UI: GetAll button clicked ===")
            viewModel.getAllTransformers()
        }

        btnGetById.setOnClickListener {
            val id = inputId.text.toString().toLongOrNull()
            if (id == null) {
                Toast.makeText(this, "Enter valid ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.getTransformerById(id)
        }

        btnGetAlerts.setOnClickListener {
            println("=== UI: GetById button clicked ===")
            val id = inputId.text.toString().toLongOrNull()
            if (id == null) {
                println("=== UI ERROR: invalid ID ===")
                return@setOnClickListener
            }
            viewModel.getTransformerAlerts(id)
        }

        btnProcessError.setOnClickListener {
            val id = inputId.text.toString().toLongOrNull()
            if (id == null) {
                Toast.makeText(this, "Enter valid ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.processError(id) {
                Toast.makeText(this, "Error processed!", Toast.LENGTH_SHORT).show()
            }
        }
    }


}