package com.thomaskioko.stargazer.trending.interactor

import androidx.compose.ui.graphics.Color
import androidx.paging.PagingData
import com.thomaskioko.stargazer.db.model.RepoEntity
import com.thomaskioko.stargazer.trending.R
import com.thomaskioko.stargazers.common.model.RepoViewDataModel

internal object ViewMockData {

    fun makeRepoEntityList() = listOf(
        RepoEntity(
            id = 1L,
            name = "Square",
            description = "Some cool description about the app",
            userName = "ninja",
            userType = "Organisation",
            stargazersCount = 1200,
            forksCount = 1200,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900",
            language = "Kotlin",
            avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
            isBookmarked = false
        )
    )

    fun makePagingRepoViewDataModelList() = PagingData.from(
        listOf(
            RepoViewDataModel(
                repoId = 1L,
                repoName = "Square",
                description = "Some cool description about the app",
                userName = "ninja",
                userType = "Organisation",
                stargazersCount = "1.2k",
                forksCount = "1.2k",
                contributorsUrl = "",
                createdDate = "1/11/1900",
                updatedDate = "1/11/1900",
                language = "Kotlin",
                avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
                languageDrawable = R.drawable.ic_language_drawable,
                drawableColor = Color(0xFFF08E33),
                isBookmarked = false
            )
        )
    )

    fun makePagingRepoEntityModelList() = PagingData.from(
        listOf(
            RepoEntity(
                id = 1L,
                name = "Square",
                description = "Some cool description about the app",
                userName = "ninja",
                userType = "Organisation",
                stargazersCount = 1200,
                forksCount = 1200,
                contributorsUrl = "",
                createdDate = "1/11/1900",
                updatedDate = "1/11/1900",
                language = "Kotlin",
                avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
                isBookmarked = false
            )
        )
    )

}
