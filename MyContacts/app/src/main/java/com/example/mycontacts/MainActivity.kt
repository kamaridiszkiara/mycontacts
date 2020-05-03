package com.example.mycontacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import androidx.core.view.get
import com.example.contacts.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Contacts"

        val context = this
        var db = DatabaseHandler(context)

        main_bottom_nav.selectedItemId = R.id.contacts

        main_bottom_nav.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.contacts -> {
                }
                R.id.favorites -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                }
            }
            true
        }

        newButton.setOnClickListener{
            val intent = Intent(this, NewContactActivity::class.java)
            startActivity(intent)
        }

        val contactsListView:ListView = findViewById(R.id.contactsList)
        val data = db.readData()
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        contactsListView.adapter = adapter

         contactsListView.setOnItemClickListener { _, _, position, _ ->
            val selected:Contacts = contactsListView.getItemAtPosition(position) as Contacts

            val intent = Intent(this, ContactActivity::class.java)
             intent.putExtra("id", selected.id)
             intent.putExtra("name", selected.name)
             intent.putExtra("phoneNumber", selected.phoneNumber)
             intent.putExtra("phoneType", selected.phoneType.toString())
             intent.putExtra("favorite", selected.favorite)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
