package com.example.transformmonitorapp.ui.home.role

import GenericViewModelFactory
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.data.repository.impl.OperatorRepositoryImpl
import com.example.transformmonitorapp.views.adapters.AlertAdapter
import com.example.transformmonitorapp.views.adapters.MenuAdapter
import com.example.transformmonitorapp.views.adapters.MenuItem
import com.example.transformmonitorapp.views.models.OperatorViewModel

class OperatorActivity : RoleActivity() {

    private val token: String by lazy { homeViewModel.loadToken() ?: "" }

    private val viewModel: OperatorViewModel by viewModels {
        GenericViewModelFactory {
            OperatorViewModel(application, OperatorRepositoryImpl(applicationContext, token))
        }
    }

    override fun getLayoutId(): Int = R.layout.layout_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        navigateToProfile()
    }

    override fun customizeAsideMenu() {
        val menuItems = listOf(
            MenuItem(
                titleRes = R.string.activity_operator_all,
                action = {
                    openAllTransformersPage()
                }
            ),
            MenuItem(
                titleRes = R.string.activity_operator_by_id,
                action = {
                    openByIdPage()
                }
            ),
            MenuItem(
                titleRes = R.string.activity_operator_alerts,
                action = {
                    openAlertsPage()
                }
            )
        )

        menuContainer.addView(
            RecyclerView(this).apply {
                layoutManager = LinearLayoutManager(this@OperatorActivity)
                adapter = MenuAdapter(menuItems)
            }
        )
    }

    private fun openAllTransformersPage() {
        header.tvTitle.setText(R.string.activity_operator_all)
        setContentLayout(R.layout.activity_operator_all_transformers)

        val tvOutput = findViewById<TextView>(R.id.tvOutput)

        viewModel.getAllTransformers()

        viewModel.transformers.observe(this) { list ->
            tvOutput.text = list.joinToString("\n\n")
        }
    }

    private fun openByIdPage() {
        header.tvTitle.setText(R.string.activity_operator_by_id)
        setContentLayout(R.layout.activity_operator_transformer_by_id)

        val inputId = findViewById<EditText>(R.id.etTransformerId)
        val btnGet = findViewById<Button>(R.id.btnLoad)
        val tvOutput = findViewById<TextView>(R.id.tvOutput)

        btnGet.setOnClickListener {
            val id = inputId.text.toString().toLongOrNull()
            if (id == null) {
                Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.getTransformerById(id)
        }

        viewModel.transformer.observe(this) { t ->
            tvOutput.text = t.toString()
        }
    }

    private fun openAlertsPage() {
        header.tvTitle.text = "Transformer Alerts"
        setContentLayout(R.layout.activity_operator_alerts_doing)

        val etId = findViewById<EditText>(R.id.etTransformerId)
        val btnLoad = findViewById<Button>(R.id.btnLoadAlerts)
        val rv = findViewById<RecyclerView>(R.id.rvAlerts)

        val adapter = AlertAdapter { alert ->
            viewModel.processError(alert.id) {
                Toast.makeText(this, "Alert resolved!", Toast.LENGTH_SHORT).show()
            }
        }

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        viewModel.alerts.observe(this) {
            adapter.setData(it)
        }

        btnLoad.setOnClickListener {
            val id = etId.text.toString().toLongOrNull()
            if (id == null) {
                Toast.makeText(this, "Enter valid ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.getTransformerAlerts(id)
        }
    }

    private fun observeViewModel() {
        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}

/*package com.example.transformmonitorapp.ui.home.role

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
        //setupProfileView()
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


}*/
