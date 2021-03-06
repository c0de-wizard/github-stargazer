package com.thomaskioko.stargazer.api.service

import com.thomaskioko.stargazer.api.model.RepoResponse
import com.thomaskioko.stargazer.api.model.RepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {

    @GET("orgs/square/repos")
    suspend fun getRepositories(): List<RepoResponse>

    @GET("search/repositories")
    suspend fun getTrendingRepositories(
        @Query("page") page: Int,
        @Query("q") language: String = "android+language:kotlin",
        @Query("created") created: String = "2021-01-27", //TODO:: Pass current date
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
    ): RepositoriesResponse

    @GET("search/repositories?sort=stars")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): RepositoriesResponse
}
