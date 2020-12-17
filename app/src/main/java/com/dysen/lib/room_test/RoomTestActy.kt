package com.dysen.lib.room_test

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.dysen.baselib.base.BaseActivity
import com.dysen.lib.R
import com.dysen.lib.room_test.dao.PeopleDao
import com.dysen.lib.room_test.dao.UserDao
import com.dysen.lib.room_test.database.MeDataBase
import com.dysen.lib.room_test.entity.People
import com.dysen.lib.room_test.entity.User
import kotlinx.android.synthetic.main.activity_room_test.*

class RoomTestActy : BaseActivity() {
    
    var userDao: UserDao?=null
    var peopleDao: PeopleDao?=null

    override fun layoutId(): Int = R.layout.activity_room_test

    override fun initView(savedInstanceState: Bundle?) {
        userDao = MeDataBase.getDatabase(this, "user").userDao()
        peopleDao = MeDataBase.getDatabase(this, "people").pepleDao()

        val permissions = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 1)
        }
        
        initClick()
    }

    private fun initClick() {
        var user = User(1,"sendy", 31, "17020099956", "利和广场")
        var people = People("sendy", 31, "广东中山丽江")

        btnInsert.setOnClickListener {
            for (i :Int in 1..100){
                user = User(i.toLong(),"sendy$i", i, "17020099956", "保利拉菲-${i}号")
                userDao?.inSertUser(user)

                people = People("sendy-$i", i, "广东中山丽江-${i}号")
                peopleDao?.insertPeople(people)
            }
            queryData()
        }
        btnDel.setOnClickListener {
            userDao?.deleteUser(user)
            queryData()
            user = User(1,"sendy", 31, "17020099956", "广东明湖豪庭")
        }
        btnUpdate.setOnClickListener {
            user.age = 35
            userDao?.updateUser(user)
            queryData()
        }
        btnQuery.setOnClickListener {
            queryData()
        }
    }

    private fun queryData() {
        val p = userDao?.getUser()
        tvContent.text = p.toString()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == 1) {
            Log.d(
                TAG,
                "onRequestPermissionsResult: $requestCode"
            )
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult-需要开启存储权限")
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults)
    }
}