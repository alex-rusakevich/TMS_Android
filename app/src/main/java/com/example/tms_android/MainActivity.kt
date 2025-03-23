package com.example.tms_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import android.view.View
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var textInputName : TextInputEditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textInputName = findViewById(R.id.textInputName)

        Log.d("MYLOG", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("MYLOG", "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MYLOG", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MYLOG", "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MYLOG", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MYLOG", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MYLOG", "onPause")
    }

    fun launchSecondActivity(view: View) {
        val intent = Intent(this, SecondActivity::class.java)
        val name = textInputName.getText().toString().trim()
        intent.putExtra("name", name)
        startActivity(intent)
    }
}