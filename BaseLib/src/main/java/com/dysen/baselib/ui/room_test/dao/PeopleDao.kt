package com.dysen.baselib.ui.room_test.dao

import androidx.room.*
import com.dysen.baselib.ui.room_test.entity.People

/**
 * dysen.
 * dy.sen@qq.com    10/23/20  9:39 AM

 * Info：数据库操作类（DatabaseWrapper）
 */
@Dao
interface PeopleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = People::class)
    fun insertPeople(people: People)

//    @Query("DELETE FROM people WHERE name=:name")
//    fun deletePeople(name: String): People

    @Delete
    fun deletePeople(people: People)

    @Update
    fun updatePeople(people: People)

    @Query("SELECT * FROM people")
    fun getPeople(): People

    @Query("SELECT * FROM people WHERE name= :name LIMIT 1")
    fun getPeople(name: String): People

    @Query("SELECT age FROM people WHERE name= :name LIMIT 1")
    fun getPeopleAge(name: String): Int
}