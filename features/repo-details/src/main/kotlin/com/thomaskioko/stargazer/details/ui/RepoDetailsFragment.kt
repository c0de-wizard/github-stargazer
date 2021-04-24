package com.thomaskioko.stargazer.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.thomaskioko.stargazer.core.factory.create
import com.thomaskioko.stargazer.details.ui.DetailAction.BackPressed
import com.thomaskioko.stargazer.details.ui.DetailAction.UpdateRepo
import com.thomaskioko.stargazer.details.ui.compose.RepoDetailScreen
import com.thomaskioko.stargazer.details.ui.viewmodel.RepoDetailsViewModel
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class RepoDetailsFragment : Fragment() {

    @Inject
    lateinit var factory: RepoDetailsViewModel.Factory

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    private val getRepoViewModel: RepoDetailsViewModel by viewModels {
        create(factory, screenNavigator)
    }
    private val args: RepoDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                StargazerTheme {
                    RepoDetailScreen(
                        viewModel = getRepoViewModel,
                        onRepoBookMarked = { getRepoViewModel.dispatchAction(UpdateRepo(it)) },
                        onBackPressed = { getRepoViewModel.dispatchAction(BackPressed) }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRepoViewModel.dispatchAction(DetailAction.LoadRepo(args.repoId))

    }
}
