package com.example.mycontacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.contacts.Contacts
import com.example.contacts.DatabaseHandler
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_new_contact.*
import kotlinx.android.synthetic.main.activity_new_contact.cancelBtn
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val context = this
        var db = DatabaseHandler(context)

        val id:Int?= intent.getIntExtra("id",0)
        val nameText = intent.getStringExtra("name")
        val phoneNumberText = intent.getStringExtra("phoneNumber")
        val phoneTypeText = intent.getStringExtra("phoneType")
        val favorite:Int? = intent.getIntExtra("favorite", 0)

        val name:EditText = findViewById(R.id.name)
        val phoneNumber:EditText = findViewById(R.id.phoneNumber)
        val phoneTypes:RadioGroup = findViewById(R.id.phoneTypes)
        val mobile:RadioButton = findViewById(R.id.mobile)
        val home:RadioButton = findViewById(R.id.home)
        val work:RadioButton = findViewById(R.id.work)

        if (phoneTypeText == "Mobile"){
            phoneTypes.check(mobile.id)
        }
        else if (phoneTypeText == "Home"){
            phoneTypes.check(home.id)
        }
        else {
            phoneTypes.check(work.id)
        }

        name.setText(nameText)
        phoneNumber.setText(phoneNumberText)

        var type:String? = phoneTypeText

        phoneTypes.setOnCheckedChangeListener { _, checkedId ->
            if(checkedId == R.id.mobile){
                type = mobile.text as String?
            }
            else if (checkedId == R.id.work){
                type = work.text as String?
            }
            else{
                type = home.text as String?
            }
        }

        saveBtn.setOnClickListener{
            var radioId: Int = phoneTypes.checkedRadioButtonId

            if (name.text.toString() != nameText || phoneNumber.text.toString() != phoneNumberText || phoneTypeText != type){
                if (name.text.toString().isNotEmpty() && phoneNumber.text.toString().isNotEmpty() && radioId > 0 && id != null && favorite != null) {
                    var contacts = Contacts(id, name.text.toString(), phoneNumber.text.toString(), type.toString(), favorite)

                    db.updateData(contacts)

                    val intent = Intent(this, ContactActivity::class.java)
                    intent.putExtra("id", id)
                    intent.putExtra("name", name.text.toString())
                    intent.putExtra("phoneNumber", phoneNumber.text.toString())
                    intent.putExtra("phoneType", type.toString())
                    intent.putExtra("favorite", favorite)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(context, "Please fill all data's ", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                finish()
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