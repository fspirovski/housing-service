package mk.ukim.finki.mpip.housing_service

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT
import com.google.android.material.bottomnavigation.BottomNavigationView
import mk.ukim.finki.mpip.housing_service.databinding.ActivityMainBinding
import mk.ukim.finki.mpip.housing_service.service.LocalStorageService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val localStorageService = LocalStorageService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        localStorageService
        if (!isLoggedIn()) {
            redirectToAuthActivity()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun isLoggedIn(): Boolean {
        print(localStorageService.getData("jwt", null))
        val token = localStorageService.getData("jwt", null) ?: return false

        return try {
            !JWT(token).isExpired(0)
        } catch (e: DecodeException) {
            false
        }
    }

    private fun redirectToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)

        startActivity(intent)
        finish()
    }
}