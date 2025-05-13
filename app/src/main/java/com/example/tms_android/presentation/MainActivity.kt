package com.example.tms_android.presentation

/*
Реализовать экран поиска по списку элементов с использованием Flow для обработки изменений текста,
1. На экране должны быть:
   - EditText для ввода поискового запроса.
   - ProgressBar для отображения загрузки.
   - RecyclerView для отображения результатов поиска.
   - TextView для отображения ошибок или "нет результатов".
2. Поиск должен срабатывать только если пользователь прекратил ввод на 300 мс и если введенный текст изменился
3. Для каждого поискового запроса запускается имитация сетевого поиска (например, задержка через delay(1000)), после чего возвращается результат — список строк, содержащих поисковый запрос.
4. Если пользователь вводит новый текст до завершения предыдущего поиска, старый поиск отменяется и начинается новый.
5. Если результат пуст — вывести "Ничего не найдено".
6. Если произошла ошибка (например, искусственно выбрасываемое исключение при определённом запросе) — отобразить сообщение об ошибке.
* */
import android.os.Bundle
import android.text.Editable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tms_android.data.SearchViewModel
import com.example.tms_android.databinding.ActivityMainBinding
import com.example.tms_android.presentation.adapters.SearchResultsAdapter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearchObservers()
        setupSearchInputListener()
    }

    private fun setupRecyclerView() {
        adapter = SearchResultsAdapter()
        binding.resultsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.resultsRecyclerView.adapter = adapter
    }

    private fun setupSearchObservers() {
        viewModel.searchResults.observe(this) { results ->
            adapter.submitList(results)
        }

        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        }

        viewModel.error.observe(this) { errorMessage ->
            binding.statusTextView.text = errorMessage
            binding.statusTextView.visibility = if (errorMessage != null) android.view.View.VISIBLE else android.view.View.GONE
        }
    }

    private fun setupSearchInputListener() {
        val searchFlow = callbackFlow {
            val textWatcher = object : android.text.TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    trySend(s?.toString() ?: "")
                }
            }

            binding.searchEditText.addTextChangedListener(textWatcher)

            awaitClose {
                binding.searchEditText.removeTextChangedListener(textWatcher)
            }
        }

        searchFlow
            .debounce(300)
            .distinctUntilChanged() // Только если текст изменился
            .onEach { query ->
                viewModel.performSearch(query.trim())
            }
            .launchIn(lifecycleScope)
    }
}