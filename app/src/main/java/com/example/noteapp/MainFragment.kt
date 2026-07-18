package com.example.noteapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * MainFragment - Фрагмент для MainActivity NoteApp
 */
class MainFragment : Fragment() {

    private lateinit var viewBinding: MainFragmentBinding // Будет инициализирован при необходимости
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Инициализация UI фрагмента
        return setupUI(inflater)
    }

    private fun setupUI(inflater: LayoutInflater): View {
        // Здесь будет код для настройки UI фрагмента
        
        println("MainFragment UI initialized")
        
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Инициализация навигации и событий
        setupNavigation()
        
        // Проверка состояния фрагмента
        checkFragmentState()
    }

    private fun setupNavigation() {
        // Здесь будет код для настройки навигации (Navigation Component)
        // Например: Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_notesListFragment)
        
        println("MainFragment navigation initialized")
    }

    private fun checkFragmentState() {
        // Проверка состояния фрагмента (например, восстановление данных)
        // Пока базовая логика отсутствует
        
        println("MainFragment state checked successfully")
    }

    override fun onResume() {
        super.onResume()
        // Обработка событий при возвращении в фокус
    }

    override fun onPause() {
        super.onPause()
        // Сохранение состояния перед паузой
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Очистка ресурсов перед завершением
    }
}