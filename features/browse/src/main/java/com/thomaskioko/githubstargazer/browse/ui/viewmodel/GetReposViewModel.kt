package com.thomaskioko.githubstargazer.browse.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.githubstargazer.browse.domain.interactor.GetRepoListInteractor
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GetReposViewModel @ViewModelInject constructor(
    private val interactor: GetRepoListInteractor
) : ViewModel() {

    private val _repoListMutableStateFlow: MutableStateFlow<ViewState<List<RepoViewDataModel>>> =
        MutableStateFlow(ViewState.loading())
    val repoList: SharedFlow<ViewState<List<RepoViewDataModel>>> get() = _repoListMutableStateFlow

    val repoMutableStateFlow: MutableStateFlow<ViewState<RepoViewDataModel>> =
        MutableStateFlow(ViewState.loading())

    val repoUpdateMutableStateFlow: MutableStateFlow<ViewState<RepoViewDataModel>> =
        MutableStateFlow(ViewState.loading())

    fun getRepoList(connectivityAvailable: Boolean) {
        interactor(connectivityAvailable)
            .onEach { _repoListMutableStateFlow.emit(it) }
            .launchIn(viewModelScope)
    }
}
