package com.example.mycontacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.contacts.Contacts
import com.example.contacts.DatabaseHandler
import kotlinx.android.synthetic.main.activity_contact.*

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val context = this
        var db = DatabaseHandler(context)

        val id:Int?= intent.getIntExtra("id",0)
        val nameText = intent.getStringExtra("name")
        val phoneNumberText = intent.getStringExtra("phoneNumber")
        val phoneTypeText = intent.getStringExtra("phoneType")
        val favorite:Int? = intent.getIntExtra("favorite", 0)

        title = nameText

        val name:TextView = findViewById(R.id.name)
        val phoneNumber:TextView = findViewById(R.id.phoneNumber)
        val phoneType:TextView = findViewById(R.id.phoneType)

        name.text = nameText
        phoneNumber.text = phoneNumberText
        phoneType.text = phoneTypeText

        if (favorite == 1){
            contact_bottom_nav_add.visibility = View.GONE
            contact_bottom_nav_rem.visibility = View.VISIBLE
        }
        else{
            contact_bottom_nav_add.visibility = View.VISIBLE
            contact_bottom_nav_rem.visibility = View.GONE
        }


        contact_bottom_nav_add.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.add -> {
                    if (id != null && favorite != null) {

                        var contact = Contacts(id, nameText,phoneNumberText,phoneTypeText, favorite)

                        db.addFavorite(contact)

                        contact_bottom_nav_add.visibility = View.GONE
                        contact_bottom_nav_rem.visibility = View.VISIBLE
                    }
                }
                R.id.edit -> {
                    val intent = Intent(this, UpdateActivity::class.java)

                    intent.putExtra("id", id)
                    intent.putExtra("name", nameText)
                    intent.putExtra("phoneNumber", phoneNumberText)
                    intent.putExtra("phoneType", phoneTypeText)
                    intent.putExtra("favorite", favorite)

                    startActivity(intent)
                }
                R.id.delete -> {
                    if (id != null) {
                        db.deleteData(id)

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            true
        }

        contact_bottom_nav_rem.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.rem ->{
                    if (id != null && favorite != null) {

                        var contact = Contacts(id, nameText,phoneNumberText,phoneTypeText, favorite)

                        db.removeFavorite(contact)

                        contact_bottom_nav_add.visibility = View.VISIBLE
                        contact_bottom_nav_rem.visibility = View.GONE

                    }
                }
                R.id.edit -> {
                    val intent = Intent(this, UpdateActivity::class.java)

                    intent.putExtra("id", id)
                    intent.putExtra("name", nameText)
                    intent.putExtra("phoneNumber", phoneNumberText)
                    intent.putExtra("phoneType", phoneTypeText)
                    intent.putExtra("favorite", favorite)

                    startActivity(intent)
                }
                R.id.delete -> {
                    if (id != null) {
                        db.deleteData(id)

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            true
        }

        callBtn.setOnClickListener {
            val intent = Intent(this, CallActivity::class.java)
            intent.putExtra("phoneNumber", phoneNumberText)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
