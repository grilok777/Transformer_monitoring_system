package com.example.transformmonitorapp.ui.home.role

import GenericViewModelFactory
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.data.repository.impl.AdminRepositoryImpl
import com.example.transformmonitorapp.domain.dto.request.TransformerRequest
import com.example.transformmonitorapp.views.adapters.AdminAlertAdapter
import com.example.transformmonitorapp.views.adapters.MenuAdapter
import com.example.transformmonitorapp.views.adapters.MenuItem
import com.example.transformmonitorapp.views.models.AdminViewModel

class AdminActivity : RoleActivity() {

    private val token: String by lazy { homeViewModel.loadToken() ?: "" }

    private val alertsAdapter = AdminAlertAdapter()
    private val viewModel: AdminViewModel by viewModels {
        GenericViewModelFactory {
            AdminViewModel(application, AdminRepositoryImpl(applicationContext, token))
        }
    }

    override fun getLayoutId(): Int = R.layout.layout_profile

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        navigateToProfile()
    }

    private fun observeViewModel() {
        // 1. Обробка СТАТУСУ (Помилки або успіх)
        viewModel.status.observe(this) { msg ->
            // Показуємо Toast, щоб користувач точно побачив
            if (msg.isNotBlank()) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

                // Також дублюємо в текстове поле, якщо воно є на екрані
                val currentTv = findViewById<TextView>(R.id.tvOutput)
                // Пишемо в текст, тільки якщо це схоже на помилку або якщо поле ще пусте
                if (currentTv != null && (msg.contains("Помилка") || currentTv.text == "Інформація з'явиться тут")) {
                    currentTv.text = msg
                }
            }
        }

        // 2. Обробка ОДНОГО трансформатора
        viewModel.transformerResult.observe(this) { t ->
            val currentTv = findViewById<TextView>(R.id.tvOutput)
            if (currentTv != null && t != null) {
                currentTv.text = t.toString()
            }
        }

        // 3. Обробка СПИСКУ трансформаторів (Range / All)
        viewModel.transformerList.observe(this) { list ->
            val currentTv = findViewById<TextView>(R.id.tvOutput)

            if (currentTv != null) {
                if (!list.isNullOrEmpty()) {
                    // Якщо є дані — виводимо список красиво
                    currentTv.text = list.joinToString("\n\n") { it.toString() }
                } else {
                    // Якщо список прийшов порожнім (але це не очищення)
                    // Перевіряємо, чи ми не робили clearState (зазвичай там null або пустий список)
                    // Тут можна нічого не писати або написати "Список порожній"
                    currentTv.text = "Список порожній"
                }
            }
        }

        // 4. Обробка ЛОГІВ (TextResult)
        viewModel.textResult.observe(this) { txt ->
            val currentTv = findViewById<TextView>(R.id.tvOutput)
            if (currentTv != null && txt.isNotBlank()) {
                currentTv.text = txt
            }
        }

        // 5. Обробка ПОПЕРЕДЖЕНЬ (Alerts)
        // Тут особлива логіка: оновлюємо Адаптер + пишемо кількість у текст
        viewModel.alerts.observe(this) { list ->
            // Оновлюємо таблицю (RecyclerView)
            alertsAdapter.setData(list)

            // Оновлюємо текст статусу
            val currentTv = findViewById<TextView>(R.id.tvOutput)
            if (currentTv != null) {
                if (list.isNullOrEmpty()) {
                    currentTv.text = "Записів не знайдено"
                } else {
                    currentTv.text = "Завантажено записів: ${list.size}"
                }
            }
        }
    }

    // ================================
    // ASIDE MENU
    // ================================
    override fun customizeAsideMenu() {
        val menuItems = listOf(
            MenuItem(R.string.admin_create) {
                handleMenuAction(R.string.admin_create, R.layout.activity_admin_create) { setupCreatePage() }
            },
            MenuItem(R.string.admin_update) {
                handleMenuAction(R.string.admin_update, R.layout.activity_admin_update) { setupUpdatePage() }
            },
            MenuItem(R.string.admin_deactivate) {
                handleMenuAction(R.string.admin_deactivate, R.layout.activity_admin_deactivate) { setupDeactivatePage() }
            },
            MenuItem(R.string.admin_export_one) {
                handleMenuAction(R.string.admin_export_one, R.layout.activity_admin_export_one) { setupExportOnePage() }
            },
            MenuItem(R.string.admin_export_range) {
                handleMenuAction(R.string.admin_export_range, R.layout.activity_admin_export_range) { setupRangePage() }
            },
            MenuItem(R.string.admin_export_all) {
                handleMenuAction(R.string.admin_export_all, R.layout.activity_admin_export_all) { setupExportAllPage() }
            },
            MenuItem(R.string.admin_alerts) {
                handleMenuAction(R.string.admin_alerts, R.layout.activity_admin_alerts) { setupAlertsPage() }
            },
            MenuItem(R.string.admin_critical_alerts) {
                handleMenuAction(R.string.admin_critical_alerts, R.layout.activity_admin_critical_alerts) { setupCriticalAlertsPage() }
            },
            MenuItem(R.string.admin_logs) {
                handleMenuAction(R.string.admin_logs, R.layout.activity_admin_logs) { setupLogsPage() }
            }
        )

        menuContainer.addView(
            RecyclerView(this).apply {
                layoutManager = LinearLayoutManager(this@AdminActivity)
                adapter = MenuAdapter(menuItems)
            }
        )
    }

    private fun handleMenuAction(titleRes: Int, layoutRes: Int, extra: (() -> Unit)? = null) {
        viewModel.clearState()

        header.tvTitle.setText(titleRes)
        setContentLayout(layoutRes)
        extra?.invoke()
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    // ================================
    // PAGES
    // ================================

    // 1 — CREATE
    private fun setupCreatePage() {
        val man = findViewById<EditText>(R.id.etManufacturer)
        val model = findViewById<EditText>(R.id.etModelType)
        val power = findViewById<EditText>(R.id.etPower)
        val primary = findViewById<EditText>(R.id.etPrimary)
        val secondary = findViewById<EditText>(R.id.etSecondary)
        val freq = findViewById<EditText>(R.id.etFrequency)
        val btn = findViewById<Button>(R.id.btnCreate)

        btn?.setOnClickListener {
            val req = TransformerRequest(
                manufacturer = man.text.toString(),
                modelType = model.text.toString(),
                ratedPowerKVA = power.text.toString().toDoubleOrNull() ?: 0.0,
                primaryVoltageKV = primary.text.toString().toIntOrNull() ?: 0,
                secondaryVoltageKV = secondary.text.toString().toIntOrNull() ?: 0,
                frequencyHz = freq.text.toString().toDoubleOrNull() ?: 0.0
            )
            viewModel.createTransformer(req)
        }
    }

    // 2 — UPDATE
    private fun setupUpdatePage() {
        val id = findViewById<EditText>(R.id.etId)
        val man = findViewById<EditText>(R.id.etManufacturer)
        val model = findViewById<EditText>(R.id.etModelType)
        val power = findViewById<EditText>(R.id.etPower)
        val primary = findViewById<EditText>(R.id.etPrimary)
        val secondary = findViewById<EditText>(R.id.etSecondary)
        val freq = findViewById<EditText>(R.id.etFrequency)
        val cond = findViewById<CheckBox>(R.id.cbCondition)
        val remote = findViewById<CheckBox>(R.id.cbRemote)
        val btn = findViewById<Button>(R.id.btnUpdate)

        btn?.setOnClickListener {
            val longId = id.text.toString().toLongOrNull()
            if (longId == null) {
                Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val req = TransformerRequest(
                manufacturer = man.text.toString(),
                modelType = model.text.toString(),
                ratedPowerKVA = power.text.toString().toDoubleOrNull() ?: 0.0,
                primaryVoltageKV = primary.text.toString().toIntOrNull() ?: 0,
                secondaryVoltageKV = secondary.text.toString().toIntOrNull() ?: 0,
                frequencyHz = freq.text.toString().toDoubleOrNull() ?: 0.0,
                transformerCondition = cond.isChecked,
                remoteMonitoring = remote.isChecked
            )

            viewModel.updateTransformer(longId, req)
        }
    }

    // 3 — DEACTIVATE
    private fun setupDeactivatePage() {
        val id = findViewById<EditText>(R.id.etId)
        val btn = findViewById<Button>(R.id.btnDeactivate)

        btn?.setOnClickListener {
            val longId = id.text.toString().toLongOrNull() ?: return@setOnClickListener
            viewModel.deactivateTransformer(longId)
        }
    }

    // 4 — EXPORT ONE
    private fun setupExportOnePage() {
        val id = findViewById<EditText>(R.id.etId)
        val btn = findViewById<Button>(R.id.btnLoad)

        // Більше ніяких observe() тут!

        btn?.setOnClickListener {
            val longId = id.text.toString().toLongOrNull()
            if (longId == null) {
                Toast.makeText(this, "Введіть ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Ставимо текст "Завантаження...", результат прийде через observeViewModel
            findViewById<TextView>(R.id.tvOutput)?.text = "Завантаження..."
            viewModel.exportTransformer(longId)
        }
    }

    // 5 — EXPORT RANGE
    private fun setupRangePage() {
        val from = findViewById<EditText>(R.id.etFrom)
        val to = findViewById<EditText>(R.id.etTo)
        val btn = findViewById<Button>(R.id.btnLoadRange)

        btn?.setOnClickListener {
            val f = from.text.toString().toLongOrNull() ?: return@setOnClickListener
            val t = to.text.toString().toLongOrNull() ?: return@setOnClickListener

            findViewById<TextView>(R.id.tvOutput)?.text = "Завантаження..."
            viewModel.exportTransformersRange(f, t)
        }
    }

    // 6 — EXPORT ALL
    private fun setupExportAllPage() {
        val btn = findViewById<Button>(R.id.btnLoadAll)

        btn?.setOnClickListener {
            findViewById<TextView>(R.id.tvOutput)?.text = "Завантаження..."
            viewModel.exportAllTransformers()
        }
    }

    // 7 — ALL ALERTS
    private fun setupAlertsPage() {
        val rv = findViewById<RecyclerView>(R.id.rvAlerts)
        val btn = findViewById<Button>(R.id.btnLoad)

        // Налаштовуємо RecyclerView, використовуючи наш глобальний адаптер
        rv?.layoutManager = LinearLayoutManager(this)
        rv?.adapter = alertsAdapter

        // Візуально очищаємо список перед новим запитом (щоб не миготіли старі дані)
        alertsAdapter.setData(emptyList())

        btn?.setOnClickListener {
            findViewById<TextView>(R.id.tvOutput)?.text = "Завантаження..."
            viewModel.getAllAlerts()
        }
    }

    // 8 — CRITICAL ALERTS
    private fun setupCriticalAlertsPage() {
        val rv = findViewById<RecyclerView>(R.id.rvAlerts)
        val btn = findViewById<Button>(R.id.btnLoad)

        // Те саме: підключаємо адаптер
        rv?.layoutManager = LinearLayoutManager(this)
        rv?.adapter = alertsAdapter
        alertsAdapter.setData(emptyList())

        btn?.setOnClickListener {
            findViewById<TextView>(R.id.tvOutput)?.text = "Завантаження..."
            viewModel.getCriticalAlerts()
        }
    }

    // 9 — LOGS
    private fun setupLogsPage() {
        val id = findViewById<EditText>(R.id.etId)
        val btn = findViewById<Button>(R.id.btnLoadLogs)

        btn?.setOnClickListener {
            val longId = id.text.toString().toLongOrNull() ?: return@setOnClickListener

            findViewById<TextView>(R.id.tvOutput)?.text = "Завантаження логів..."
            viewModel.exportLogs(longId)
        }
    }
}