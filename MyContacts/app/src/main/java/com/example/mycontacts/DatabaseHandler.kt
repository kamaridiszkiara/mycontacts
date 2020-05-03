package com.example.contacts

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

const val databaseName = "mydb"
const val databaseVersion = 1
const val tableName = "contacts"
const val colId = "id"
const val colName = "name"
const val colPhoneNumber = "phonenumber"
const val colPhoneType = "phonetype"
const val colFavorite = "favorite"

class DatabaseHandler(var context:Context) : SQLiteOpenHelper(context, databaseName, null, databaseVersion){
        override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "create table " + tableName + "(" +
                colId + " integer primary key autoincrement, " +
                colName + " text," +
                colPhoneNumber + " text," +
                colPhoneType + " text, " +
                colFavorite + " integer)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertData (contacts: Contacts) {
        val db = this.writableDatabase

        var cv = ContentValues()
        cv.put(colName, contacts.name)
        cv.put(colPhoneNumber, contacts.phoneNumber)
        cv.put(colPhoneType, contacts.phoneType)
        cv.put(colFavorite, 0)

        var result = db.insert(tableName, null, cv)
        if (result == (-1).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
    }

    fun readData() : ArrayList<Contacts> {
        val db = this.readableDatabase

        var list: ArrayList<Contacts> = ArrayList()

        var query = "select * from $tableName order by name"
        var result = db.rawQuery(query, null)

        if (result.moveToFirst()){
            do {
                var contact = Contacts()
                contact.id = result.getString(result.getColumnIndex(colId)).toInt()
                contact.name = result.getString(result.getColumnIndex((colName)))
                contact.phoneNumber = result.getString(result.getColumnIndex(colPhoneNumber))
                contact.phoneType = result.getString(result.getColumnIndex(colPhoneType))
                contact.favorite = result.getString(result.getColumnIndex(colFavorite)).toInt()
                list.add(contact)
            }while (result.moveToNext())
        }

        result.close()

        db.close()

        return list
    }

    fun deleteData(id:Int) {
        val db = this.writableDatabase

        db.delete(tableName, "$colId=?", arrayOf(id.toString()))
        db.close()
    }

    fun updateData(contacts:Contacts) {
        val db = this.readableDatabase

        var cv = ContentValues()
        cv.put(colId, contacts.id)
        cv.put(colName, contacts.name)
        cv.put(colPhoneNumber, contacts.phoneNumber)
        cv.put(colPhoneType, contacts.phoneType)
        cv.put(colFavorite, contacts.favorite)

        db.update(tableName, cv, "$colId=?", arrayOf(contacts.id.toString()))
        db.close()
    }

    fun addFavorite(contacts:Contacts){
        val db = this.readableDatabase

        var cv = ContentValues()
        cv.put(colId, contacts.id)
        cv.put(colName, contacts.name)
        cv.put(colPhoneNumber, contacts.phoneNumber)
        cv.put(colPhoneType, contacts.phoneType)
        cv.put(colFavorite, 1)

        db.update(tableName, cv, "$colId=?", arrayOf(contacts.id.toString()))
        db.close()
    }

    fun removeFavorite(contacts:Contacts){
        val db = this.readableDatabase

        var cv = ContentValues()
        cv.put(colId, contacts.id)
        cv.put(colName, contacts.name)
        cv.put(colPhoneNumber, contacts.phoneNumber)
        cv.put(colPhoneType, contacts.phoneType)
        cv.put(colFavorite, 0)

        db.update(tableName, cv, "$colId=?", arrayOf(contacts.id.toString()))
        db.close()
    }

    fun readFavorites() : ArrayList<Contacts>{
        val db = this.readableDatabase

        var list: ArrayList<Contacts> = ArrayList()

        var query = "select * from $tableName where $colFavorite=1 order by name"
        var result = db.rawQuery(query, null)

        if (result.moveToFirst()){
            do {
                var contact = Contacts()
                contact.id = result.getString(result.getColumnIndex(colId)).toInt()
                contact.name = result.getString(result.getColumnIndex((colName)))
                contact.phoneNumber = result.getString(result.getColumnIndex(colPhoneNumber))
                contact.phoneType = result.getString(result.getColumnIndex(colPhoneType))
                contact.favorite = result.getString(result.getColumnIndex(colFavorite)).toInt()
                list.add(contact)
            }while (result.moveToNext())
        }

        result.close()

        db.close()

        return list
    }
}