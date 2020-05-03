package com.example.contacts

public class Contacts {
    var id:Int = 0
    var name:String = ""
    var phoneNumber:String = ""
    var phoneType:String = ""
    var favorite:Int = 0

    constructor(id:Int?, name:String, phoneNumber:String, phoneType:String, favorite: Int){
        this.id = id!!
        this.name = name
        this.phoneNumber = phoneNumber
        this.phoneType = phoneType
        this.favorite = favorite
    }

    constructor(name:String, phoneNumber:String, phoneType:String, favorite:Int){
        this.name = name
        this.phoneNumber = phoneNumber
        this.phoneType = phoneType
        this.favorite = favorite
    }

    constructor(){

    }

    override fun toString(): String {
        return name.toString()
    }
}