package com.me.spinner.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.me.spinner.R
import com.me.spinner.views.NiceSpinner
import java.util.*

/**
 * @author dysen
 * dy.sen@qq.com     1/28/21 13:08 PM
 *
 * Info：
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intView()
    }

    private fun intView() {
        val niceSpinner = findViewById<View>(R.id.nice_spinner) as NiceSpinner
        val dataset: List<Int> = LinkedList(Arrays.asList(3, 5, 15, 30))
        //        final List<String> dataset = Arrays.asList("One", "Two", "Three", "Four", "Five");
        niceSpinner.attachDataSource(dataset)
        niceSpinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                Toast.makeText(this@MainActivity, l.toString() + "your select item :" + dataset[i], Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
    }
}