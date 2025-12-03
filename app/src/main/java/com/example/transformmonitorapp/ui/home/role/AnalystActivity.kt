package com.example.transformmonitorapp.ui.home.role

import GenericViewModelFactory
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.data.repository.impl.AnalyticRepositoryImpl
import com.example.transformmonitorapp.views.models.AnalystViewModel
import kotlin.getValue

class AnalystActivity : RoleActivity() {

    val token: String by lazy { homeViewModel.loadToken() ?: "" }

    private lateinit var btnExportOne: Button
    private lateinit var btnExportRange: Button
    private lateinit var btnExportAll: Button
    private lateinit var btnAlerts: Button
    private lateinit var btnCriticalAlerts: Button
    private lateinit var btnLogs: Button
    private lateinit var tvOutput: TextView

    private val viewModel: AnalystViewModel by viewModels {
        GenericViewModelFactory {
            AnalystViewModel(
                application,
                AnalyticRepositoryImpl(applicationContext, token)
            ) }
    }

    override fun getLayoutId(): Int = R.layout.activity_analyst

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViews()
        observeViewModel()

        btnExportOne.setOnClickListener { showIdInputDialog(action = Action.EXPORT_ONE) }
        btnExportRange.setOnClickListener { showRangeDialog() }
        btnExportAll.setOnClickListener { viewModel.exportAllTransformers() }
        btnAlerts.setOnClickListener { viewModel.getAllAlerts() }
        btnCriticalAlerts.setOnClickListener { viewModel.getCriticalAlerts() }
        btnLogs.setOnClickListener { showIdInputDialog(action = Action.EXPORT_LOGS) }
    }

    override fun customizeAsideMenu() {

    }

    private fun bindViews() {
        btnExportOne = findViewById(R.id.btnExportOne)
        btnExportRange = findViewById(R.id.btnExportRange)
        btnExportAll = findViewById(R.id.btnExportAll)
        btnAlerts = findViewById(R.id.btnAlerts)
        btnCriticalAlerts = findViewById(R.id.btnCriticalAlerts)
        btnLogs = findViewById(R.id.btnLogs)
        tvOutput = findViewById(R.id.tvAdminOutput)
    }

    private fun observeViewModel() {
        viewModel.status.observe(this) { s ->
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
        }

        viewModel.transformerResult.observe(this) { t ->
            tvOutput.text = t?.toString() ?: "Результат пустий"
        }

        viewModel.transformerList.observe(this) { list ->
            tvOutput.text = list.joinToString("\n\n") { it.toString() }
        }

        viewModel.textResult.observe(this) { txt ->
            tvOutput.text = txt
        }
    }



    private fun showIdInputDialog(action: Action) {
        val ctx = this
        val etId = EditText(ctx).apply { hint = "ID"; inputType = android.text.InputType.TYPE_CLASS_NUMBER; setPadding(16,16,16,16) }
        AlertDialog.Builder(ctx)
            .setTitle(
                when(action) {
                    Action.EXPORT_ONE -> "Експорт трансформатора"
                    Action.EXPORT_LOGS -> "Експорт логів"
                }
            )
            .setView(etId)
            .setPositiveButton("OK") { _, _ ->
                try {
                    val id = etId.text.toString().toLong()
                    when(action) {
                        Action.EXPORT_ONE -> viewModel.exportTransformer(id)
                        Action.EXPORT_LOGS -> viewModel.exportLogs(id)
                    }
                } catch (_: Exception) {
                    Toast.makeText(this, "Невірний ID", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Скасувати", null)
            .show()
    }

    private fun showRangeDialog() {
        val ctx = this
        val layout = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16,16,16,16)
        }
        val etFrom = EditText(ctx).apply { hint = "From ID"; inputType = android.text.InputType.TYPE_CLASS_NUMBER }
        val etTo = EditText(ctx).apply { hint = "To ID"; inputType = android.text.InputType.TYPE_CLASS_NUMBER }
        layout.addView(etFrom)
        layout.addView(etTo)

        AlertDialog.Builder(ctx)
            .setTitle("Експорт діапазону (from..to)")
            .setView(layout)
            .setPositiveButton("Експорт") { _, _ ->
                try {
                    val from = etFrom.text.toString().toLong()
                    val to = etTo.text.toString().toLong()
                    viewModel.exportTransformersRange(from, to)
                } catch (_: Exception) {
                    Toast.makeText(this, "Невірні ID", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Скасувати", null)
            .show()
    }

    enum class Action { EXPORT_ONE, EXPORT_LOGS }
}
