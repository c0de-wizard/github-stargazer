package com.thomaskioko.stargazer.repo_details.domain

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.core.ViewStateResult.Success
import com.thomaskioko.stargazer.repo_details.domain.model.UpdateObject
import com.thomaskioko.stargazer.repo_details.util.ViewMockData.makeRepoEntity
import com.thomaskioko.stargazer.repo_details.util.ViewMockData.makeRepoViewDataModel
import com.thomaskioko.stargazer.repository.api.GithubRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

internal class UpdateRepoBookmarkStateInteractorTest {

    private val repository: GithubRepository = mock()
    private val interactor = UpdateRepoBookmarkStateInteractor(repository)

    @Test
    fun `whenever updateRepoIsInvoked expectedDataIsReturned`() = runBlocking {
        whenever(repository.getRepoByIdFlow(1)) doReturn(flowOf(makeRepoEntity()))


        val result = interactor(UpdateObject(1, false)).toList()
        val expected = listOf(
            Success(makeRepoViewDataModel())
        )

        assertThat(result).isEqualTo(expected)
    }
}
