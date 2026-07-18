package com.example.noteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Базовая инициализация активности
        setContentView(R.layout.activity_main)
        
        // Здесь можно добавить базовую логику приложения
        // Например, Toast для подтверждения загрузки
        android.widget.Toast.makeText(
            this,
            "NoteApp запущен",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }
}