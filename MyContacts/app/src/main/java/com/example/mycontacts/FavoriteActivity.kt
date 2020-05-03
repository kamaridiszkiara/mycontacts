package com.example.mycontacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import androidx.core.view.get
import com.example.contacts.*
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val context = this
        var db = DatabaseHandler(context)

        main_bottom_nav.selectedItemId = R.id.favorites

        main_bottom_nav.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.contacts -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                }
                R.id.favorites -> {
                }
            }
            true
        }

        val favoritesListView : ListView = findViewById(R.id.favoritesList)
        val favorites = db.readFavorites()
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, favorites)
        favoritesListView.adapter = adapter

        favoritesListView.setOnItemClickListener { _, _, position, _ ->
            val selected:Contacts = favoritesListView.getItemAtPosition(position) as Contacts

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
