package com.example.mycontacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.contacts.Contacts
import com.example.contacts.DatabaseHandler
import kotlinx.android.synthetic.main.activity_new_contact.*

class NewContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        var type:String? = ""
        phoneTypes.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.mobile){
                type = mobile.text as String?
            }
            else if (checkedId == R.id.work){
                type = work.text as String?
            }
            else{
                type = home.text as String?
            }
        }

        val context = this
        var db = DatabaseHandler(context)

        addBtn.setOnClickListener {
            var radioId: Int = phoneTypes.checkedRadioButtonId
            if (name.text.toString().isNotEmpty() && phoneNumber.text.toString().isNotEmpty() && radioId > 0) {
                var contacts = Contacts(name.text.toString(), phoneNumber.text.toString(), type.toString(), 0)

                db.insertData(contacts)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(context, "Please fill all data's ", Toast.LENGTH_SHORT).show()
            }
        }

        cancelBtn.setOnClickListener {
           finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
