package com.cristianomadeira.paginateduserlist.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cristianomadeira.paginateduserlist.data.dataSource.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE page = :page ORDER BY id ASC, page ASC")
    fun getUsers(page: Int): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<UserEntity>)

    @Query("DELETE FROM user")
    fun deleteUsers()
}