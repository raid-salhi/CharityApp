package com.example.adminpanel


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.adminpanel.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_need_help_request,R.id.navigation_emergency,R.id.navigation_donate,R.id.navigation_project,R.id.navigation_add)
        )
        binding.toolbar.setNavigationIconTint(resources.getColor(R.color.green_main))
        binding.toolbar.setBackgroundColor(getColor(R.color.white))
        binding.toolbar.setTitleTextColor(getColor(R.color.green_main))
        setSupportActionBar(binding.toolbar)
//        supportActionBar?.title=""
        val bottomNavView: BottomNavigationView = binding.bottomNavView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        bottomNavView.setupWithNavController(navController)
        bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_add -> {
                    navController.navigate(R.id.navigation_add)
                    binding.toolbar.title="Add Post"
                    true
                }
                R.id.nav_donate-> {
                    navController.navigate(R.id.navigation_donate)
                    binding.toolbar.title="Donations"
                    true
                }
                R.id.nav_projects -> {
                    navController.navigate(R.id.navigation_project)
                    binding.toolbar.title="Projects"
                    true
                }
                R.id.nav_emergency -> {
                    navController.navigate(R.id.navigation_emergency)
                    binding.toolbar.title="Emergency"
                    true
                }
                R.id.nav_needhelp -> {
                    navController.navigate(R.id.navigation_need_help_request)
                    binding.toolbar.title="Requests"
                    true
                }

                else -> {
                    false
                }
            }
        }


    }


}