package com.example.noteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

/**
 * MainActivity - Базовый каркас для NoteApp (Android Notes App)
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Инициализация UI и навигации
        setupUI()
        
        // Проверка состояния приложения
        checkAppState()
    }

    private fun setupUI() {
        // Здесь будет код для настройки интерфейса
        
        // Устанавливаем основной layout
        setContentView(R.layout.activity_main)
        
        Toast.makeText(
            this,
            "NoteApp запущен",
            Toast.LENGTH_SHORT
        ).show()
        
        println("MainActivity UI initialized with activity_main layout")
    }

    private fun checkAppState() {
        // Проверка состояния приложения (например, восстановление из бэкапа)
        // Пока базовая логика отсутствует
        
        println("MainActivity state checked successfully")
    }

    override fun onResume() {
        super.onResume()
        // Обработка событий при возвращении в фокус
    }

    override fun onPause() {
        super.onPause()
        // Сохранение состояния перед паузой
    }

    override fun onDestroy() {
        super.onDestroy()
        // Очистка ресурсов перед завершением
    }
}