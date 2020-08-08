package com.example.recyclerviewapp

class ContactViewHolderModel {

    var id : Long
    var name : String
    var photo : String
    var numbers : MutableMap<String, String>

    var isSelf : Boolean
    var viewType : Int

    constructor(header : String, id : Long) {
        this.id = id
        name = header
        photo = ""
        numbers = mutableMapOf<String, String>("" to "")
        this.isSelf = false
        viewType = ContactAdapter.HeaderView
    }

    constructor(contact : ContactModel, isSelf : Boolean = false) {
        this.id = contact.id
        this.name = contact.name
        this.photo = contact.photo
        numbers = contact.numbers
        this.isSelf = isSelf
        viewType = ContactAdapter.DefaultView
    }
}
