package com.example.noteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import android.widget.Toast

/**
 * MainActivity - Обновленный каркас с Navigation Component для NoteApp
 */
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Инициализация UI и навигации
        setupUI()
        
        // Проверка состояния приложения
        checkAppState()
    }

    private fun setupUI() {
        // Устанавливаем основной layout с NavigationHostFragment
        setContentView(R.layout.activity_main)
        
        // Получаем NavHostFragment из layout
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        
        // Получаем NavController из NavHostFragment
        navController = navHostFragment.navController
        
        // Настраиваем Navigation UI (BottomNavigationView, если используется)
        setupNavigationUI()
        
        Toast.makeText(
            this,
            "NoteApp запущен с навигацией",
            Toast.LENGTH_SHORT
        ).show()
        
        println("MainActivity UI initialized with Navigation Component")
    }

    private fun setupNavigationUI() {
        // Здесь будет код для настройки BottomNavigationView или других элементов UI
        
        println("Navigation UI initialized")
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
    
    /**
     * Метод для навигации между фрагментами (будет вызываться из UI)
     */
    fun navigateToFragment(fragmentId: Int, arguments: Bundle? = null) {
        navController.navigate(fragmentId, arguments)
    }
}