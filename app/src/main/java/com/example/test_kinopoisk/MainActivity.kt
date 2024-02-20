package com.example.test_kinopoisk

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.findViewById<View>(android.R.id.content).rootView

        //Панель инструментов.
        //Установите панель инструментов в качестве панели действий для этого окна действий
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        val container = findViewById<DrawerLayout>(R.id.container)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        /*
        Настройка панели инструментов. Панель инструментов требуется настроить так, чтобы текст на ней
         содержал метку текущей цели в графе навигации и чтобы на ней выводилась кнопка Up
         Для этого можно построить объект AppBarConfiguration,
          основанный на графе навигации и связать его с панелью инструментов
          */
        val builder = AppBarConfiguration.Builder(navController.graph)
        //Связываем выдвижную панель с панелью инструментов
        //добавляется значок выдвижной панели
        builder.setOpenableLayout(container)
//        val appBarConfiguration = builder.build()
        //Вариант без кнопки Up
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_searching, R.id.navigation_profile
            )
        )

        //Скрыть навигационную панель в приветственном фрагменте
        val onBoarding = R.id.navigation_onboarding
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == onBoarding) {
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }

        toolbar.setupWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
