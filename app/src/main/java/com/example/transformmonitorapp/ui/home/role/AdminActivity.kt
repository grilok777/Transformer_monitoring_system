package com.example.transformmonitorapp.ui.home.role

import GenericViewModelFactory
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import com.example.transformmonitorapp.views.models.AdminViewModel

import android.os.PersistableBundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.data.network.ApiServiceProvider
import com.example.transformmonitorapp.data.repository.impl.AdminRepositoryImpl
import com.example.transformmonitorapp.data.repository.impl.CreatorRepositoryImpl
import com.example.transformmonitorapp.domain.dto.request.TransformerRequest
import com.example.transformmonitorapp.views.models.CreatorViewModel

class AdminActivity : RoleActivity() {

    val token: String by lazy { homeViewModel.loadToken() ?: "" }

    private lateinit var btnCreate: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDeactivate: Button
    private lateinit var btnExportOne: Button
    private lateinit var btnExportRange: Button
    private lateinit var btnExportAll: Button
    private lateinit var btnAlerts: Button
    private lateinit var btnCriticalAlerts: Button
    private lateinit var btnLogs: Button
    private lateinit var tvOutput: TextView

    private val viewModel: AdminViewModel by viewModels {
        GenericViewModelFactory {
            AdminViewModel(
                application,
                AdminRepositoryImpl(applicationContext, token)) }
    }

    override fun getLayoutId(): Int = R.layout.activity_admin

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViews()
        observeViewModel()


        btnCreate.setOnClickListener { showCreateDialog() }
        btnUpdate.setOnClickListener { showUpdateDialog() }
        btnDeactivate.setOnClickListener { showIdInputDialog(action = Action.DEACTIVATE) }
        btnExportOne.setOnClickListener { showIdInputDialog(action = Action.EXPORT_ONE) }
        btnExportRange.setOnClickListener { showRangeDialog() }
        btnExportAll.setOnClickListener { viewModel.exportAllTransformers() }
        btnAlerts.setOnClickListener { viewModel.getAllAlerts() }
        btnCriticalAlerts.setOnClickListener { viewModel.getCriticalAlerts() }
        btnLogs.setOnClickListener { showIdInputDialog(action = Action.EXPORT_LOGS) }
    }

    override fun customizeAsideMenu() {

    }

    override fun navigateToProfile() {

    }

    private fun bindViews() {
        btnCreate = findViewById(R.id.btnCreate)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDeactivate = findViewById(R.id.btnDeactivate)
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

    private fun showCreateDialog() {
        val ctx = this
        val layout = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
        }

        val etManufacturer = EditText(ctx).apply { hint = "Manufacturer" }
        val etModel = EditText(ctx).apply { hint = "Model type" }
        val etPower = EditText(ctx).apply { hint = "Rated power (kVA)"; inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL }
        val etPrimary = EditText(ctx).apply { hint = "Primary voltage (kV)"; inputType = android.text.InputType.TYPE_CLASS_NUMBER }
        val etSecondary = EditText(ctx).apply { hint = "Secondary voltage (kV)"; inputType = android.text.InputType.TYPE_CLASS_NUMBER }
        val etFreq = EditText(ctx).apply { hint = "Frequency (Hz)"; inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL }

        layout.addView(etManufacturer)
        layout.addView(etModel)
        layout.addView(etPower)
        layout.addView(etPrimary)
        layout.addView(etSecondary)
        layout.addView(etFreq)

        AlertDialog.Builder(ctx)
            .setTitle("Створити трансформатор")
            .setView(layout)
            .setPositiveButton("Створити") { _, _ ->
                try {
                    val req = TransformerRequest(
                        manufacturer = etManufacturer.text.toString(),
                        modelType = etModel.text.toString(),
                        ratedPowerKVA = etPower.text.toString().toDouble(),
                        primaryVoltageKV = etPrimary.text.toString().toInt(),
                        secondaryVoltageKV = etSecondary.text.toString().toInt(),
                        frequencyHz = etFreq.text.toString().toDouble()
                    )
                    viewModel.createTransformer(req)
                } catch (e: Exception) {
                    Toast.makeText(this, "Невірні дані: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Скасувати", null)
            .show()
    }

    private fun showUpdateDialog() {
        val ctx = this
        val layout = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
        }

        val etId = EditText(ctx).apply { hint = "ID (Long)"; inputType = android.text.InputType.TYPE_CLASS_NUMBER }
        val etManufacturer = EditText(ctx).apply { hint = "Manufacturer" }
        val etModel = EditText(ctx).apply { hint = "Model type" }
        val etPower = EditText(ctx).apply { hint = "Rated power (kVA)"; inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL }
        val etPrimary = EditText(ctx).apply { hint = "Primary voltage (kV)"; inputType = android.text.InputType.TYPE_CLASS_NUMBER }
        val etSecondary = EditText(ctx).apply { hint = "Secondary voltage (kV)"; inputType = android.text.InputType.TYPE_CLASS_NUMBER }
        val etFreq = EditText(ctx).apply { hint = "Frequency (Hz)"; inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL }
        val cbCondition = CheckBox(ctx).apply { text = "Condition (OK=true)"; isChecked = true }
        val cbRemote = CheckBox(ctx).apply { text = "Remote monitoring enabled"; isChecked = true }

        layout.addView(etId)
        layout.addView(etManufacturer)
        layout.addView(etModel)
        layout.addView(etPower)
        layout.addView(etPrimary)
        layout.addView(etSecondary)
        layout.addView(etFreq)
        layout.addView(cbCondition)
        layout.addView(cbRemote)

        AlertDialog.Builder(ctx)
            .setTitle("Оновити трансформатор")
            .setView(layout)
            .setPositiveButton("Оновити") { _, _ ->
                try {
                    val id = etId.text.toString().toLong()
                    val req = TransformerRequest(
                        manufacturer = etManufacturer.text.toString(),
                        modelType = etModel.text.toString(),
                        ratedPowerKVA = etPower.text.toString().toDouble(),
                        primaryVoltageKV = etPrimary.text.toString().toInt(),
                        secondaryVoltageKV = etSecondary.text.toString().toInt(),
                        frequencyHz = etFreq.text.toString().toDouble(),
                        transformerCondition = cbCondition.isChecked,
                        remoteMonitoring = cbRemote.isChecked
                    )
                    viewModel.updateTransformer(id, req)
                } catch (e: Exception) {
                    Toast.makeText(this, "Невірні дані: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Скасувати", null)
            .show()
    }

    private fun showIdInputDialog(action: Action) {
        val ctx = this
        val etId = EditText(ctx).apply { hint = "ID"; inputType = android.text.InputType.TYPE_CLASS_NUMBER; setPadding(16,16,16,16) }
        AlertDialog.Builder(ctx)
            .setTitle(
                when(action) {
                    Action.DEACTIVATE -> "Деактивувати трансформатор"
                    Action.EXPORT_ONE -> "Експорт трансформатора"
                    Action.EXPORT_LOGS -> "Експорт логів"
                    else -> "ID"
                }
            )
            .setView(etId)
            .setPositiveButton("OK") { _, _ ->
                try {
                    val id = etId.text.toString().toLong()
                    when(action) {
                        Action.DEACTIVATE -> viewModel.deactivateTransformer(id)
                        Action.EXPORT_ONE -> viewModel.exportTransformer(id)
                        Action.EXPORT_LOGS -> viewModel.exportLogs(id)
                        else -> {}
                    }
                } catch (e: Exception) {
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
                } catch (e: Exception) {
                    Toast.makeText(this, "Невірні ID", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Скасувати", null)
            .show()
    }

    enum class Action { DEACTIVATE, EXPORT_ONE, EXPORT_LOGS }


}
