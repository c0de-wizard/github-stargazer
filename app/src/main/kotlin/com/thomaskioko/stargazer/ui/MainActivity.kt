package com.thomaskioko.stargazer.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.thomaskioko.stargazer.R
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.injection.annotations.MainDispatcher
import com.thomaskioko.stargazer.core.network.FlowNetworkObserver
import com.thomaskioko.stargazer.databinding.ActivityMainBinding
import com.thomaskioko.stargazers.settings.domain.SettingsManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navControllerProvider: Provider<NavController>

    @Inject
    lateinit var settingsManager: SettingsManager


    @Inject
    lateinit var flowNetworkObserver: FlowNetworkObserver

    @Inject
    @MainDispatcher
    lateinit var mainDispatcher: CoroutineDispatcher

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val navController: NavController
        get() = navControllerProvider.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        flowNetworkObserver.observeInternetConnection()
            .onEach {
                //TODO:: Show snackBar
                Timber.d("Device Connection Status: $it")
            }
            .launchIn(CoroutineScope(mainDispatcher))

        binding.bottomNavigation.setupWithNavController(navController)

        // Hide bottom nav on screens which don't require it
        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.repoListFragment, R.id.trendingListFragment, R.id.bookmarkListFragment ->
                        binding.bottomNavigation.visibility =
                            View.VISIBLE
                    else -> binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }

    private fun setTheme() {
        val job = GlobalScope.launch(context = mainDispatcher) {
            settingsManager.getUiModeFlow()
                .collect {
                    when (it) {
                        is ViewStateResult.Error -> {
                            Timber.e(it.message)
                        }
                        is ViewStateResult.Loading -> {
                        }
                        is ViewStateResult.Success -> when (it.data) {
                            MODE_NIGHT_NO -> setDefaultNightMode(MODE_NIGHT_NO)
                            MODE_NIGHT_YES -> setDefaultNightMode(MODE_NIGHT_YES)
                        }
                    }
                }
        }
        job.cancel()
    }

    override fun onBackPressed() {
        if (!onSupportNavigateUp()) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()
}
