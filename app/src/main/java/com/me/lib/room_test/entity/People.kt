package com.me.lib.room_test.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * dysen.
 * dy.sen@qq.com    10/23/20  9:36 AM

 * Info：数据类（表）
 */
// @Entity 标识这个类用于建表，表名(默认不写，则用类名小写作为表名)
@Entity(tableName = "people")
data class People(
    // 主键，是否自增长
//    @PrimaryKey(autoGenerate = true)
    @PrimaryKey
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "age", defaultValue = "18") var age: Int,
    @ColumnInfo var address: String?
)