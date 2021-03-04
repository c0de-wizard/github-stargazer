package com.thomaskioko.githubstargazer.repository.api

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.repository.TestCoroutineExecutionThread
import com.thomaskioko.githubstargazer.repository.util.MockData.makeRepoEntityList
import com.thomaskioko.githubstargazer.repository.util.MockData.makeRepoResponseList
import com.thomaskioko.stargazer.repository.api.GithubRepository
import com.thomaskioko.stargazer.repository.api.service.GitHubService
import com.thomaskioko.stargazer.repository.db.GithubDatabase
import com.thomaskioko.stargazer.repository.db.dao.RepoDao
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GithubRepositoryTest {

    private val repoDao:RepoDao = mock {
        on { getReposFlow() } doReturn flowOf(emptyList())
    }

    private val database : GithubDatabase = mock {
        on { repoDao() } doReturn repoDao
    }
    private val service : GitHubService = mock(GitHubService::class.java)

    private val executionThread = mock(TestCoroutineExecutionThread::class.java)

    private  var repository = GithubRepository(service, database, executionThread)


    @Test
    fun `givenDeviceIsConnected verify data isLoadedFrom Remote`() = runBlocking {
        whenever(service.getRepositories()).thenReturn(makeRepoResponseList())

        // TODO:: Replace with Turbine Test
        val repos = repository.getRepositoryList(true).toList()
        val expected = listOf(makeRepoEntityList())

        verify(service).getRepositories()
        verify(database.repoDao()).insertRepos(makeRepoEntityList())
        verify(database.repoDao(), times(2)).getReposFlow()

        assertThat(repos.size).isEqualTo(expected.size)
    }

    @Test
    fun `givenDeviceIsNotConnected verify data isLoadedFrom Database`() = runBlocking {

        whenever(repoDao.getReposFlow()).thenReturn(flowOf(makeRepoEntityList()))

        val repos = repository.getRepositoryList(false).toList()
        val expected = listOf(makeRepoEntityList())

        verify(service, never()).getRepositories()
        verify(database.repoDao(), times(2)).getReposFlow()

        assertThat(repos).isEqualTo(expected)
    }

    @Test
    fun `givenRepoId verify data isLoadedFrom Database`() = runBlocking {
        val expected = makeRepoEntityList()[0]

        whenever(repoDao.getRepoById(expected.repoId)).thenReturn(expected)

        val repoEntity = repository.getRepoById(expected.repoId)

        verify(database.repoDao()).getRepoById(expected.repoId)

        assertThat(repoEntity).isEqualTo(expected)
    }

    @Test
    fun `wheneverGetBookmarkedRepos verify data isLoadedFrom Database`() = runBlocking {

        whenever(repoDao.getBookmarkedRepos()).thenReturn(makeRepoEntityList())

        val repoEntity = repository.getBookmarkedRepos()

        verify(database.repoDao()).getBookmarkedRepos()

        assertThat(repoEntity).isEqualTo(makeRepoEntityList())
    }

    @Test
    fun `wheneverUpdateRepo verify data isLoadedFrom Database`() = runBlocking {
        val entity = makeRepoEntityList()[0]

        whenever(repoDao.setBookmarkStatus(1, 1)).thenReturn(Unit)
        whenever(repoDao.getRepoById(1)).thenReturn(entity)

        repoDao.insertRepo(entity)

        repository.updateRepoBookMarkStatus(1, entity.repoId)

        val repoEntity = repository.getRepoById(entity.repoId)

        verify(database.repoDao()).getRepoById(entity.repoId)

        assertThat(repoEntity).isEqualTo(entity)
        assertThat(repoEntity.isBookmarked).isEqualTo(entity.isBookmarked)
    }
}
