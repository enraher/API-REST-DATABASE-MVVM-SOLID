package com.enraher.githublist.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.enraher.githublist.ui.repos.RepoModels
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@SmallTest
class AppDatabaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var db: AppDatabase
    private lateinit var dao: ReposDao


    @Before
    fun setup() {
//        val mMockContext = ApplicationProvider.getApplicationContext<Context>()
//        db = Room.inMemoryDatabaseBuilder(mMockContext, AppDatabase::class.java).build()
        hiltRule.inject()
        dao = db.reposDao()
    }

    @After
    fun closeDb() {
        db.close()
    }


    @Test
    fun writeAndReadRepo() = runBlocking {
        val repo = RepoModels.RepoEntity(
            "1",
            "www.google.es",
            "Repo Mock",
            "This is a repo",
            true, "Google",
            "https://picsum.photos/200/300",
            "fullname/repoMock",
            "5",
            3,
            4,
            System.currentTimeMillis()
        )

        dao.insert(repo)
        val repoGot = dao.getRepo(repo.ownerLogin, repo.name)
        assertThat(repo == repoGot).isTrue()
    }

    @Test
    fun writeAndReadRepoList() = runBlocking {
        val repo1 = RepoModels.RepoEntity("1",
            "www.google.es",
            "Repo Mock",
            "This is a repo",
            true, "Google",
            "https://picsum.photos/200/300",
            "fullname/repoMock",
            "5",
            3,
            4,
            System.currentTimeMillis()
        )
        val repo2 = RepoModels.RepoEntity("2",
            "www.google.es",
            "Repo Mock",
            "This is a repo",
            true, "Google",
            "https://picsum.photos/200/300",
            "fullname/repoMock",
            "5",
            3,
            4,
            System.currentTimeMillis()
        )
        val repo3 = RepoModels.RepoEntity("3",
            "www.google.es",
            "Repo Mock",
            "This is a repo",
            true, "Google",
            "https://picsum.photos/200/300",
            "fullname/repoMock",
            "5",
            3,
            4,
            System.currentTimeMillis()
        )
        val repo4 = RepoModels.RepoEntity("4",
            "www.google.es",
            "Repo Mock",
            "This is a repo",
            true, "Google",
            "https://picsum.photos/200/300",
            "fullname/repoMock",
            "5",
            3,
            4,
            System.currentTimeMillis()
        )

        val repoList = arrayListOf(repo1, repo2, repo3, repo4)
        dao.insertAll(repoList)
        val reposGot = dao.getRepos(0, 4)
        assertThat(repoList == reposGot).isTrue()
    }

    @Test
    fun deleteRepo() = runBlocking {
        val repo = RepoModels.RepoEntity("1",
            "www.google.es",
            "Repo Mock",
            "This is a repo",
            true, "Google",
            "https://picsum.photos/200/300",
            "fullname/repoMock",
            "5",
            3,
            4,
            System.currentTimeMillis()
        )

        dao.insert(repo)
        dao.deleteRepo(repo.id)
        assertThat(dao.getRepos(0, 100).isEmpty()).isTrue()
    }

    @Test
    fun replaceReadRepo() = runBlocking {
        val repo = RepoModels.RepoEntity(
            "1",
            "www.google.es",
            "Repo Mock",
            "This is a repo",
            true, "Google",
            "https://picsum.photos/200/300",
            "fullname/repoMock",
            "5",
            3,
            4,
            System.currentTimeMillis()
        )

        dao.insert(repo)
        val repoUpdated = RepoModels.RepoEntity(
            "1",
            "www.google.es",
            "Repo Mock Updated",
            "This is a repo",
            true, "Google",
            "https://picsum.photos/200/300",
            "fullname/repoMock",
            "5",
            3,
            4,
            System.currentTimeMillis()
        )
        dao.insert(repoUpdated)
        val repoGot = dao.getRepo(repoUpdated.ownerLogin, repoUpdated.name)
        assertThat(repoGot?.name == "Repo Mock Updated").isTrue()
        assertThat(dao.countAll() == 1).isTrue()
    }
}