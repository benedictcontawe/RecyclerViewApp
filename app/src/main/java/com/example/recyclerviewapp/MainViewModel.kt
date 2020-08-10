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
        Log.d(TAG,"addContactHeaders()")
        var currentIndex : Int = 0
        StringUtils.getLetters().map { alphabetHeader ->
            loop@ for (index in currentIndex until item.size) {
                val condition : Boolean = currentIndex < index &&
                    item[index].name.startsWith(alphabetHeader,ignoreCase = true) &&
                    item.filter { it.name.contentEquals(alphabetHeader) && it.viewType.equals(ContactAdapter.HeaderView) }.none()
                Log.d(TAG,"$alphabetHeader check")
                when {
                    condition -> {
                        Log.d(TAG,"$alphabetHeader index $index item ${item[index].name}")
                        currentIndex = index
                        item.add(index,ContactViewHolderModel(alphabetHeader, item.maxBy { it.id }?.id?.plus(1)?:RecyclerView.NO_ID))
                        break@loop
                    }
                    else -> { Log.d(TAG,"$alphabetHeader index $index none") }
                }
                Log.d(TAG,"$alphabetHeader end")
            }
        }
        Log.d(TAG,"Done Adding A to Z Header")
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

    public fun checkContacts() { Log.e(TAG, "checkContacts()")
        AsyncTask.execute {
            var oldSize : Int = itemContactList.size
            val newSize : Int = contactsProvider.getContactCount(getApplication())
            val contactsIDList : List<Long>
            Log.d(TAG, "Old ${oldSize}")
            Log.d(TAG, "New ${newSize}")
            when {
                itemContactList.isEmpty() -> { Log.e(TAG, "Get All Contacts")
                    //region Initialize Contacts
                    liveStandBy.postValue(true)
                    itemContactList.addAll(contactsProvider.getContacts(getApplication()))
                    liveContactList.postValue(itemContactList)
                    liveStandBy.postValue(false)
                    //endregion
                }
                itemContactList.isNotEmpty() && oldSize < newSize -> { Log.d(TAG, "New ${newSize - oldSize} Added Contacts")
                    //region Add New Contacts
                    liveStandBy.postValue(true)
                    contactsIDList = contactsProvider.getContactsID(getApplication())
                    loop@ for (index in 0 until contactsIDList.size step 1) {
                        Log.d(TAG,"$index Adding ${contactsIDList.get(index)}")
                        val condition : Boolean = contactsIDList.get(index) != itemContactList.get(index).id
                        if (condition) {
                            contactsProvider.getContact(getApplication(),contactsIDList.get(index).toString())?.let {
                                newContact -> Log.d(TAG,"Added ${newContact.id} ${newContact.name}")
                                itemContactList.add(index, newContact)
                            }
                            oldSize = itemContactList.size
                        }
                        if (oldSize == newSize) {
                            break@loop
                        }
                    }
                    liveContactList.postValue(itemContactList)
                    liveStandBy.postValue(false)
                    //endregion
                }
                itemContactList.isNotEmpty() && oldSize > newSize -> { Log.d(TAG, "New ${oldSize - newSize} Deleted Contacts")
                    //region Delete Old Contacts
                    liveStandBy.postValue(true)
                    contactsIDList = contactsProvider.getContactsID(getApplication())
                    loop@ for (index in itemContactList.size - 1 downTo 0  step 1) {
                        Log.d(TAG,"$index Deleting ${itemContactList.get(index).id} ${itemContactList.get(index).name}")
                        val condition : Boolean = contactsIDList.filter { ID -> itemContactList.get(index).id == ID }.none()
                        if (condition) { Log.d(TAG,"Deleted ${itemContactList.get(index).id} ${itemContactList.get(index).name}")
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { Log.d(TAG,"Build.VERSION.SDK_INT >= Build.VERSION_CODES.N")
                                itemContactList.removeIf { it.id == itemContactList.get(index).id }
                            } else { Log.d(TAG,"Build.VERSION.SDK_INT < Build.VERSION_CODES.N")
                                itemContactList.removeAll(itemContactList.filter { it.id == itemContactList.get(index).id })
                            }
                            oldSize = itemContactList.size
                        }
                        if (oldSize == newSize || index == 0) {
                            break@loop
                        }
                    }
                    liveContactList.postValue(itemContactList)
                    liveStandBy.postValue(false)
                    //endregion
                }
                itemContactList.isNotEmpty() && oldSize == newSize && isSameId() -> { Log.e(TAG, "Same ${oldSize} Size Contacts")
                    /*
                    newContacts.map { newContact ->
                        contactsProvider.getContact(getApplication(),newContact.id.toString())?.let { letContact ->
                            itemContactList.filter { filteredContact -> filteredContact.id == letContact.id
                            }.map { mapContact ->
                                Log.e(TAG, "Id ${mapContact.id} - ${letContact.id}")

                                Log.e(TAG, "Name ${mapContact.name} - ${letContact.name}")
                                if (!mapContact.name.equals(letContact.name,false)) {
                                    Log.e(TAG, "Name Not Synced")
                                } else {
                                    Log.e(TAG, "Name Synced")
                                }

                                Log.e(TAG, "Photo ${mapContact.photo} - ${letContact.photo}")
                                if (!mapContact.photo.equals(letContact.photo,false)) {
                                    Log.e(TAG, "Photo Not Synced")
                                } else {
                                    Log.e(TAG, "Photo Synced")
                                }

                                Log.e(TAG, "Numbers ${mapContact.numbers} - ${letContact.numbers}")
                                if (!mapContact.numbers.equals(letContact.numbers)) {
                                    Log.e(TAG, "Numbers Not Synced")
                                } else {
                                    Log.e(TAG, "Numbers Synced")
                                }

                                Log.e(TAG, "Emails ${mapContact.emails} - ${letContact.emails}")
                                if (!mapContact.emails.equals(letContact.emails)) {
                                    Log.e(TAG, "Emails Not Synced")
                                } else {
                                    Log.e(TAG, "Emails Synced")
                                }
                            }
                        }
                    }
                    */
                }
                itemContactList.isNotEmpty() && oldSize == newSize && !isSameId() -> { Log.e(TAG, "Same ${oldSize} Size Contacts Not Same Id")
                    liveStandBy.postValue(true)
                    contactsIDList = contactsProvider.getContactsID(getApplication())
                    //region Delete Old Contact
                    Log.d(TAG, "New ${1} Deleted Contacts")
                    loop@ for (index in itemContactList.size - 1 downTo 0  step 1) {
                        Log.d(TAG,"$index Deleting ${itemContactList.get(index).id} ${itemContactList.get(index).name}")
                        val condition : Boolean = contactsIDList.filter { ID -> itemContactList.get(index).id == ID }.none()
                        if (condition) { Log.d(TAG,"Deleted ${itemContactList.get(index).id} ${itemContactList.get(index).name}")
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { Log.d(TAG,"Build.VERSION.SDK_INT >= Build.VERSION_CODES.N")
                                itemContactList.removeIf { it.id == itemContactList.get(index).id }
                            } else { Log.d(TAG,"Build.VERSION.SDK_INT < Build.VERSION_CODES.N")
                                itemContactList.removeAll(itemContactList.filter { it.id == itemContactList.get(index).id })
                            }
                            oldSize = itemContactList.size
                            break@loop
                        }
                    }
                    //endregion
                    //region Add New Contact
                    Log.d(TAG, "New ${1} Added Contacts")
                    loop@ for (index in 0 until contactsIDList.size step 1) {
                        Log.d(TAG,"$index Adding ${contactsIDList.get(index)}")
                        val condition : Boolean = contactsIDList.get(index) != itemContactList.get(index).id
                        if (condition) {
                            contactsProvider.getContact(getApplication(),contactsIDList.get(index).toString())?.let {
                                newContact -> Log.d(TAG,"Added ${newContact.id} ${newContact.name}")
                                itemContactList.add(index, newContact)
                            }
                            oldSize = itemContactList.size
                            break@loop
                        }
                    }
                    //endregion
                    liveContactList.postValue(itemContactList)
                    liveStandBy.postValue(false)
                }
                else -> { Log.e(TAG, "else") }
            }
            Log.e(TAG, "checkContacts() Done")
        }
    }

    public fun syncNames() { Log.e(TAG,"syncNames()")
        AsyncTask.execute {
            when {
                itemContactList.isEmpty() -> {
                    Log.e(TAG,"Names is Empty")
                }
                itemContactList.isNotEmpty() && isSameName() -> {
                    Log.e(TAG,"Same Names")
                }
                else -> {
                    Log.e(TAG,"Not Same Names")
                    /*
                    contactsProvider.getContactsName(getApplication()).map { updatedContact ->
                        Log.e(TAG,"Names ${updatedContact.id} ${updatedContact.name}")
                    }
                    */
                }
            }
            Log.e(TAG,"syncNames() Done")
        }
    }

    public fun syncPhotos() { Log.e(TAG,"syncPhotos()")
        AsyncTask.execute {
            when {
                itemContactList.isEmpty() -> {
                    Log.e(TAG,"Photos is Empty")
                }
                itemContactList.isNotEmpty() && isSamePhoto() -> {
                    Log.e(TAG,"Same Photos")
                }
                else -> {
                    Log.e(TAG,"Not Same Photos")
                    /*
                    contactsProvider.getContactsPhoto(getApplication()).map { updatedContact ->
                        Log.e(TAG,"Photos ${updatedContact.id} ${updatedContact.photo}")
                    }
                    */
                }
            }
            Log.e(TAG,"syncPhotos() Done")
        }
    }

    private fun isSameId() : Boolean {
        contactsProvider.getContactsID(getApplication()).map { updatedContactID ->
            val condition : Boolean = itemContactList.filter { filteredContact ->
                filteredContact.id == updatedContactID
            }.none()
            if (condition) {
                return false
            }
        }
        return true
    }

    private fun isSameName() : Boolean {
        Log.e(TAG,"isSameName Processing")
        contactsProvider.getContactsName(getApplication()).map { updatedContact ->
            val condition : Boolean = itemContactList.filter { filteredContact ->
                filteredContact.id == updatedContact.id &&
                filteredContact.name.equals(updatedContact.name,false)
            }.none()
            Log.e(TAG,"isSameName ${updatedContact.id} ${updatedContact.name}")
            if (condition) {
                Log.e(TAG,"isSameName false")
                return false
            }
        }
        Log.e(TAG,"isSameName true")
        return true
    }

    private fun isSamePhoto() : Boolean {
        contactsProvider.getContactsPhoto(getApplication()).map { updatedContact ->
            val condition : Boolean = itemContactList.filter { filteredContact ->
                filteredContact.id == updatedContact.id &&
                filteredContact.photo.equals(updatedContact.photo,false)
            }.none()
            if (condition) {
                return false
            }
        }
        return true
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