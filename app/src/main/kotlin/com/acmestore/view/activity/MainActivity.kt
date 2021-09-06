package com.acmestore.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acmestore.R
import com.acmestore.databinding.ActMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
    }

    // TODO create an item for shopping cart
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_search -> {
                Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }*/

}