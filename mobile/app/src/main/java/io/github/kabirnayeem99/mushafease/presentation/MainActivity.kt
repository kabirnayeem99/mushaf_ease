package io.github.kabirnayeem99.mushafease.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import io.github.kabirnayeem99.mushafease.data.service.database.MushafDatabase
import io.github.kabirnayeem99.mushafease.presentation.ui.theme.MushafEaseTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
        enableEdgeToEdge()
        setContent {
            MushafEaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            val db = MushafDatabase.getDatabase(this@MainActivity)
            db.surahDao().getAllSurahs().collect { allSurahs ->
                Log.d(TAG, "loadData: allSurahs: $allSurahs")
            }
            db.ayahDao().getAyahsFromPage(1).collect { ayat ->
                Log.d(TAG, "loadData: ayat: $ayat")
            }
        }
    }
}

private const val TAG = "MainActivity"

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MushafEaseTheme {
        Greeting("Android")
    }
}