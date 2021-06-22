package com.mimdal.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.mimdal.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var activityBinding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        setSupportActionBar(activityBinding!!.toolbar as Toolbar)




        activityBinding!!.fab.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_listFragment_to_addFragment)

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        activityBinding = null
    }


}