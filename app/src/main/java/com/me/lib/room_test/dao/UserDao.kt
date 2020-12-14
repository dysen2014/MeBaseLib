package com.me.lib.room_test.dao

import androidx.room.*
import com.dysen.lib.room_test.entity.User

/**
 * @author dysen
 * dy.sen@qq.com     12/1/20 10:30 AM
 *
 * Info：数据库操作类（DatabaseWrapper）
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = User::class)
    fun inSertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM user")
    fun getUser(): User

    @Query("SELECT * FROM user WHERE name = :name LIMIT 1")
    fun getUser(name: String): User
//
    @Query("SELECT age FROM user WHERE name = :name LIMIT 1")
    fun getUserAge(name: String): Int

}