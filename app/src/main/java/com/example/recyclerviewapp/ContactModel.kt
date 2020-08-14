package com.example.recyclerviewapp

data class ContactModel (
        val id : Long,
        var name : String,
        var photo : String,
        var numbers : MutableMap<String,String>,
        var emails : MutableMap<String,String>
)