package com.cristianomadeira.paginateduserlist.data.dataSource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cristianomadeira.paginateduserlist.data.dataSource.local.dao.UserDao
import com.cristianomadeira.paginateduserlist.data.dataSource.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}