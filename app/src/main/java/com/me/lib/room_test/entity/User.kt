package com.me.lib.room_test.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author dysen
 * dy.sen@qq.com     12/1/20 10:33 AM
 *

 * Info：数据类（表）
 */
// @Entity 标识这个类用于建表，表名(默认不写，则用类名小写作为表名)
@Entity(tableName = "user")
data class User(
    // 主键，是否自增长
//    @PrimaryKey(autoGenerate = true)
    @PrimaryKey
    @ColumnInfo(name = "id") var id: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "age", defaultValue = "18") var age: Int,
    @ColumnInfo var telPhone:String?,
    @ColumnInfo var addr :String?
)