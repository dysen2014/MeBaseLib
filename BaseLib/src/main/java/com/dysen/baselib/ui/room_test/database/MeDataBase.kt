package com.dysen.baselib.ui.room_test.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dysen.baselib.ui.room_test.dao.PeopleDao
import com.dysen.baselib.ui.room_test.dao.UserDao
import com.dysen.baselib.ui.room_test.entity.People
import com.dysen.baselib.ui.room_test.entity.User

/**
 * @author dysen
 * dy.sen@qq.com     12/1/20 10:49 AM
 *
 * Info：
 */
@Database(entities = [User::class, People::class], version = 2)
abstract class MeDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pepleDao(): PeopleDao

    companion object {

        @Volatile
        private var INSTANCE: MeDataBase? = null

        //    fun getDatabase(context :Context, name:String, scope:CoroutineScope):MeDataBase{
        fun getDatabase(context: Context, name: String): MeDataBase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    MeDataBase::class.java,
                    "${name}_database"
                ).allowMainThreadQueries()//允许在主线程中查询
                     .fallbackToDestructiveMigration() //升级数据库版本 清空数据库

//                .addCallback(UserDatabaseCallback(scope))
                    .build()
                INSTANCE = db
                db
            }
        }
    }
}