package com.enraher.githublist.util

import com.enraher.githublist.ui.repos.RepoModels

object RepoFactory {

    private val repo1 = RepoModels.Repo("1",
        "www.google.es",
        "Repo Mock",
        "This is a repo",
        true, "Google",
        "https://picsum.photos/200/300")
    private val repo2 = RepoModels.Repo("2",
        "www.google.es",
        "Repo Mock",
        "This is a repo",
        true, "Google",
        "https://picsum.photos/200/300")
    private val repo3 = RepoModels.Repo("3",
        "www.google.es",
        "Repo Mock",
        "This is a repo",
        true, "Google",
        "https://picsum.photos/200/300")
    private val repo4 = RepoModels.Repo("4",
        "www.google.es",
        "Repo Mock",
        "This is a repo",
        true, "Google",
        "https://picsum.photos/200/300")

    val repoList = arrayListOf(repo1, repo2, repo3, repo4)

//    private val counter = AtomicInteger(0)
//    fun createRepo(): RepoModels.Repo {
//        val id = counter.incrementAndGet()
//        return RepoModels.Repo(
//            name = "name_$id",
//            url = "www.google.es",
//            id = id.toString(),
//            description = "description $id",
//            fork = Random.nextBoolean(),
//            ownerLogin = "Owner login $id",
//            ownerAvatar = "https://picsum.photos/200/300"
//        )
//    }
}