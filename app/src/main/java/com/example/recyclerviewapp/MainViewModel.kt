package com.example.recyclerviewapp

import android.app.Application
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.RecyclerView

class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    private val contactsProvider : ContactsProvider
    private val liveStandBy : MutableLiveData<Boolean>
    private val itemContactList : MutableList<ContactModel>
    private val liveContactList : MutableLiveData<List<ContactModel>>

    constructor(application: Application) : super(application) {
        contactsProvider = ContactsProvider()
        liveStandBy = MutableLiveData<Boolean>()
        itemContactList = mutableListOf<ContactModel>()
        liveContactList = MutableLiveData<List<ContactModel>>()
    }
    //region Transformations Map
    private fun convertContacts(contacts : List<ContactModel>) : List<ContactViewHolderModel> {
        val value : MutableList<ContactViewHolderModel> = mutableListOf<ContactViewHolderModel>()
        contacts.map {
            value.add(ContactViewHolderModel(it))
        }
        addContactAmpersandHeader(value)
        addContactNumericalHeaders(value)
        addContactLettersHeaders(value)
        value.distinctBy { it.id }
        value.map {
            Log.i(TAG, "ID ${it.id} Name ${it.name} Photo ${it.photo} Numbers ${it.numbers} viewType ${it.viewType}")
        }
        return value
    }

    private fun addContactAmpersandHeader(item : MutableList<ContactViewHolderModel>) {
        //region check if it has Speacial Characters Before Adding AMP Header
        Log.d(TAG,"addAmpersandHeader*()")
        loop@ for (index in 0 until item.size) {
            val condition : Boolean =
                BooleanUtils.hasSpecialCharacter(item[index].name.substring(0,1)) &&
                item.filter { BooleanUtils.hasSpecialCharacter(it.name.substring(0,1)) && it.viewType.equals(ContactAdapter.HeaderView) }.none()
            Log.d(TAG,"& check")
            when {
                condition -> { Log.d(TAG,"& index $index item ${item[index].name}")
                    item.add(index,ContactViewHolderModel("&", item.maxBy { it.id }?.id?.plus(1)?: RecyclerView.NO_ID))
                    break@loop
                }
                else -> { Log.d(TAG,"& index $index none") }
            }
            Log.d(TAG,"& end")
        }
        Log.d(TAG,"Done Adding & Header")
        //endregion
    }

    private fun addContactNumericalHeaders(item : MutableList<ContactViewHolderModel>) {
        //region check if it has 0-9 Number Before Adding Number Header
        Log.d(TAG,"Adding # Header")
        loop@ for (index in 0 until item.size) {
            val condition : Boolean =
                item[index].name.substring(0,1).isDigitsOnly() &&
                item.filter { it.name.substring(0,1).isDigitsOnly() && it.viewType.equals(ContactAdapter.HeaderView) }.none()
            Log.d(TAG,"# check")
            when {
                condition -> {
                    Log.d(TAG,"# index $index item ${item[index].name}")
                    item.add(index,ContactViewHolderModel("#", item.maxBy { it.id }?.id?.plus(1)?:RecyclerView.NO_ID))
                    break@loop
                }
                else -> { Log.d(TAG,"# index $index none") }
            }
            Log.d(TAG,"# end")
        }
        Log.d(TAG,"Done Adding # Header")
        //endregion
    }

    private fun addContactLettersHeaders(item : MutableList<ContactViewHolderModel>) {
        //region check if it has A-Z Characters Before Adding A-Z Header
        Log.e(TAG,"addContactHeaders()")
        var currentIndex : Int = 0
        StringUtils.getLetters().map { alphabetHeader ->
            loop@ for (index in currentIndex until item.size) {
                val condition : Boolean = currentIndex < index &&
                    item[index].name.startsWith(alphabetHeader,ignoreCase = true) &&
                    item.filter { it.name.contentEquals(alphabetHeader) && it.viewType.equals(ContactAdapter.HeaderView) }.none()
                Log.e(TAG,"$alphabetHeader check")
                when {
                    condition -> {
                        Log.e(TAG,"$alphabetHeader index $index item ${item[index].name}")
                        currentIndex = index
                        item.add(index,ContactViewHolderModel(alphabetHeader, item.maxBy { it.id }?.id?.plus(1)?:RecyclerView.NO_ID))
                        break@loop
                    }
                    else -> { Log.e(TAG,"$alphabetHeader index $index none") }
                }
                Log.e(TAG,"$alphabetHeader end")
            }
        }
        Log.e(TAG,"Done Adding A to Z Header")
        //endregion
    }
    //endregion
    public fun addContact() : Intent {
        return contactsProvider.addContact()
    }

    public fun updateContact(item : ContactViewHolderModel) : Intent {
        val contactUri : Uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, item.id)
        return contactsProvider.updateContact(contactUri)
    }

    public fun deleteContact(item : ContactViewHolderModel, position : Int) {
        AsyncTask.execute {
            liveStandBy.postValue(true)
            when {
                contactsProvider.deleteContact(getApplication(), item.id.toString()) > 0 -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Log.d(TAG,"Build.VERSION.SDK_INT >= Build.VERSION_CODES.N")
                        itemContactList.removeIf { it.id == item.id }
                    } else {
                        Log.d(TAG,"Build.VERSION.SDK_INT < Build.VERSION_CODES.N")
                        itemContactList.removeAll(itemContactList.filter { it.id == item.id })
                    }
                    liveContactList.postValue(itemContactList)

                }
                else -> {
                    Log.e(TAG,"Error Deleting")
                }
            }
            liveStandBy.postValue(false)
        }
    }

    public fun checkContacts() {
        //TODO: Refresh Updated Contacts
    }

    public fun getContacts() { Log.d(TAG, "getContacts()")
        AsyncTask.execute {
            liveStandBy.postValue(true)
            itemContactList.addAll(
                contactsProvider.getContacts(getApplication())
            )
            liveContactList.postValue(itemContactList)
            liveStandBy.postValue(false)
        }
    }

    public fun observeLiveStandBy() : LiveData<Boolean> {
        return liveStandBy
    }

    public fun observeLiveContact() : LiveData<List<ContactViewHolderModel>> {
        return Transformations.map(liveContactList) { contactList ->
            convertContacts(contactList)
        }
    }
}