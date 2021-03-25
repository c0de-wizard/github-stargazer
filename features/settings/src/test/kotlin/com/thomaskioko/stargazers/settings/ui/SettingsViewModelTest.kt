package com.thomaskioko.stargazers.settings.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazers.settings.domain.SettingsManager
import com.thomaskioko.stargazers.settings.domain.UiTheme
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.rules.TestRule


internal class SettingsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val settingsManager: SettingsManager = mock {
        on { getUiModeFlow() } doReturn flowOf(ViewStateResult.Success(UiTheme.DARK))
    }

    private val viewModel = SettingsViewModel(settingsManager, testCoroutineDispatcher)

    @Test
    fun givenThemeIsSet_verify_CorrectValueIsEmitted() = runBlocking {

        viewModel.actionState.test {
            viewModel.dispatchAction(SettingsActions.LoadTheme)

            assertEquals(expectItem(), SettingsViewState.Loading)
            assertEquals(expectItem(), SettingsViewState.ThemeLoaded(UiTheme.DARK))
        }
    }

    @Test
    fun givenThemeIsUpdated_verify_updateFunctionIsInvoked() = runBlocking {

        viewModel.actionState.test {
            viewModel.dispatchAction(SettingsActions.UpdateTheme(UiTheme.LIGHT))

            assertEquals(expectItem(), SettingsViewState.Loading)
        }

        verify(settingsManager).setUiMode(UiTheme.LIGHT)
    }

    @Test
    fun givenAnErrorHappens_verifyErrorStateIsReturned() = runBlocking {
        val errorMessage = "Something went wrong!"

        whenever(settingsManager.getUiModeFlow()).thenReturn(flowOf(ViewStateResult.Error(errorMessage)))

        viewModel.actionState.test {
            viewModel.dispatchAction(SettingsActions.LoadTheme)

            assertEquals(expectItem(), SettingsViewState.Loading)
            assertEquals(expectItem(), SettingsViewState.Error(errorMessage))
        }
    }

}