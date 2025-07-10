package by.alerus.tms_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.alerus.tms_android.ui.theme.TMS_AndroidTheme
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMS_AndroidTheme {
                SearchAutoCompleteScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAutoCompleteScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val searchObservable = remember(searchQuery) {
        Observable.just(searchQuery)
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .filter { it.length >= 3 }
            .switchMap { query ->
                simulateServerRequest(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorResumeNext { throwable: Throwable ->
                        Observable.just(emptyList<String>())
                    }
            }
    }

    DisposableEffect(searchQuery) {
        val disposable = searchObservable
            .subscribe(
                { results ->
                    searchResults = results
                    isLoading = false
                    errorMessage = null
                },
                { error ->
                    errorMessage = error.message
                    isLoading = false
                    searchResults = emptyList()
                }
            )

        onDispose {
            disposable.dispose()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Поиск с автодополнением") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { newText ->
                        searchQuery = newText
                        isLoading = true
                        errorMessage = null
                    },
                    label = { Text("Поиск") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }

                errorMessage?.let {
                    Text(
                        text = "Ошибка: $it",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(searchResults.size) { index ->
                        Text(
                            text = searchResults[index],
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    )
}

private fun simulateServerRequest(query: String): Observable<List<String>> {
    return Observable.create { emitter ->
        try {
            Thread.sleep(500 + (Math.random() * 1000).toLong())

            // Рандомная ошибка. На всякий случай
            if (Math.random() < 0.1) {
                throw RuntimeException("Server error occurred")
            }

            val results = if (query.isBlank()) {
                emptyList()
            } else {
                List((1..5).random()) {
                    "$query result ${it + 1}"
                }
            }

            emitter.onNext(results)
            emitter.onComplete()
        } catch (e: Exception) {
            emitter.onError(e)
        }
    }
}