package com.cristianomadeira.paginateduserlist.data.dataSource.local

import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.appmattus.kotlinfixture.decorator.nullability.NeverNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.nullabilityStrategy
import com.appmattus.kotlinfixture.decorator.optional.NeverOptionalStrategy
import com.appmattus.kotlinfixture.decorator.optional.optionalStrategy
import com.appmattus.kotlinfixture.kotlinFixture
import com.cristianomadeira.paginateduserlist.data.dataSource.local.UserLocalDataSource
import com.cristianomadeira.paginateduserlist.data.dataSource.local.UserLocalDataSourceImpl
import com.cristianomadeira.paginateduserlist.data.dataSource.local.dao.UserDao
import com.cristianomadeira.paginateduserlist.data.dataSource.local.database.AppDatabase
import com.cristianomadeira.paginateduserlist.data.dataSource.local.mapper.UserEntityToModelMapper
import com.cristianomadeira.paginateduserlist.data.dataSource.local.mapper.UserModelToEntityMapper
import com.cristianomadeira.paginateduserlist.data.model.UserModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserLocalDataSourceTest {
    private val fixture = kotlinFixture {
        nullabilityStrategy(NeverNullStrategy)
        optionalStrategy(NeverOptionalStrategy)
    }

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var userLocalDataSource: UserLocalDataSource

    @Before
    fun setupDatabase() {
        database = inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = database.userDao()

        userLocalDataSource = UserLocalDataSourceImpl(
            database = database,
            userEntityToModelMapper = UserEntityToModelMapper(),
            userModelToEntityMapper = UserModelToEntityMapper()
        )
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun test_insert_users() = runTest {
        val expectedUsers: List<UserModel> = listOf(fixture(), fixture(), fixture())

        userLocalDataSource.insertUsers(expectedUsers)
        val users =  userLocalDataSource.getPaginatedUsers(page = 1)

        users.forEachIndexed { i, user ->
            assertThat(user).isEqualTo(expectedUsers[i])
        }
    }

    @Test
    fun test_delete_users() = runTest {
        userLocalDataSource.insertUsers(listOf(fixture(), fixture()))
        userLocalDataSource.deleteUsers()
        val users = userLocalDataSource.getPaginatedUsers(page = 1)

        assertThat(users).isEmpty()
    }
}