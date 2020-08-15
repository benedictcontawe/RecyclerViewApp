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
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.CopyOnWriteArrayList

class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
        private const val SyncContacts = 0
        private const val SyncNames = 1
        private const val SyncPhotos = 2
        private const val SyncNumbers = 3
        private const val SyncEmails = 4
        private const val SortContacts = 5
        private const val DeleteContact = 6
        private val contactsProvider : ContactsProvider by lazy(LazyThreadSafetyMode.NONE, initializer = {
            ContactsProvider()
        })
        private val itemProcessList : MutableList<Int> by lazy(LazyThreadSafetyMode.NONE, initializer = {
            //mutableListOf<Int>()
            CopyOnWriteArrayList<Int>()
        })
        private val itemContactList : MutableList<ContactModel> by lazy(LazyThreadSafetyMode.NONE, initializer = {
            //mutableListOf<ContactModel>()
            //ArrayList<ContactModel>()
            CopyOnWriteArrayList<ContactModel>()
        })
    }

    private val liveStandBy : MutableLiveData<Boolean> by lazy(LazyThreadSafetyMode.NONE) {
        MutableLiveData<Boolean>()
    }

    private val liveContactList : MutableLiveData<List<ContactModel>> by lazy(LazyThreadSafetyMode.NONE) {
        MutableLiveData<List<ContactModel>>()
    }

    constructor(application: Application) : super(application) {
        Log.d(TAG, "constructor()")
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
            Log.i(TAG, "ID ${it.id} Name ${it.name} Photo ${it.photo} viewType ${it.viewType}")
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

    private fun addContact(contactsIDList : List<Long>) {
        loop@ for (index in 0 until contactsIDList.size step 1) {
            Log.d(TAG,"$index Adding ${contactsIDList.get(index)}")
            val condition : Boolean = contactsIDList.get(index) != itemContactList.get(index).id
            if (condition) {
                contactsProvider.getContact(getApplication(),contactsIDList.get(index).toString())?.let {
                    newContact -> Log.d(TAG,"Added ${newContact.id} ${newContact.name}")
                    itemContactList.add(index, newContact)
                }
            }
            if (isSameSize() && isSameId() || index == contactsIDList.size -1) {
                break@loop
            }
        }
    }

    public fun updateContact(item : ContactViewHolderModel) : Intent {
        val contactUri : Uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, item.id)
        return contactsProvider.updateContact(contactUri)
    }

    private fun deleteContact(item : ContactModel) { Log.d(TAG,"deleteContact(${item})")
        itemContactList.remove(item)
    }

    private fun deleteContact(contactsIDList : List<Long>) {
        postLiveStandBy(DeleteContact,false)
        loop@ for (index in itemContactList.size - 1 downTo 0  step 1) {
            Log.d(TAG,"$index Deleting ${itemContactList.get(index).id} ${itemContactList.get(index).name}")
            val condition : Boolean = contactsIDList.filter { ID -> itemContactList.get(index).id == ID }.none()
            if (condition) { Log.d(TAG,"Deleted ${itemContactList.get(index).id} ${itemContactList.get(index).name}")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { Log.d(TAG,"Build.VERSION.SDK_INT >= Build.VERSION_CODES.N")
                    itemContactList.removeIf { it.id == itemContactList.get(index).id }
                } else { Log.d(TAG,"Build.VERSION.SDK_INT < Build.VERSION_CODES.N")
                    itemContactList.removeAll(itemContactList.filter { it.id == itemContactList.get(index).id })
                }
            }
            if (isSameSize() && isSameId() || index == 0) {
                break@loop
            }
        }
        postLiveStandBy(DeleteContact,true)
        postLiveContact()
    }

    public fun deleteContact(item : ContactViewHolderModel, position : Int) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute {
            postLiveStandBy(DeleteContact,false)
            when {
                contactsProvider.deleteContact(getApplication(), item.id.toString()) > 0 -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Log.d(TAG,"Build.VERSION.SDK_INT >= Build.VERSION_CODES.N")
                        itemContactList.removeIf { it.id == item.id }
                    } else {
                        Log.d(TAG,"Build.VERSION.SDK_INT < Build.VERSION_CODES.N")
                        itemContactList.removeAll(itemContactList.filter { it.id == item.id })
                    }
                }
                else -> {
                    Log.e(TAG,"Error Deleting")
                }
            }
            postLiveStandBy(DeleteContact,true)
            postLiveContact()
        }
    }

    public fun syncContacts() { Log.d(TAG, "syncContacts()")
        if(!isProcessing(SyncContacts)) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute { Log.d(TAG, "syncContacts() Processing")
                postLiveStandBy(SyncContacts,false)
                val oldSize : Int = itemContactList.size
                val newSize : Int = contactsProvider.getContactCount(getApplication())
                val contactsIDList : List<Long>
                Log.d(TAG, "Old ${oldSize}")
                Log.d(TAG, "New ${newSize}")
                when {
                    itemContactList.isEmpty() -> { Log.d(TAG, "Get All Contacts")
                        //region Initialize Contacts
                        itemContactList.addAll(contactsProvider.getContacts(getApplication()))
                        //endregion
                    }
                    itemContactList.isNotEmpty() && oldSize < newSize -> { Log.d(TAG, "New ${newSize - oldSize} Added Contacts")
                        //region Add New Contacts
                        contactsIDList = contactsProvider.getListID(getApplication())
                        addContact(contactsIDList)
                        //endregion
                    }
                    itemContactList.isNotEmpty() && oldSize > newSize -> { Log.d(TAG, "New ${oldSize - newSize} Deleted Contacts")
                        //region Delete Old Contacts
                        contactsIDList = contactsProvider.getListID(getApplication())
                        deleteContact(contactsIDList)
                        //endregion
                    }
                    itemContactList.isNotEmpty() && oldSize == newSize && isSameId() -> {
                        Log.d(TAG, "Same ${oldSize} Size Contacts")
                    }
                    itemContactList.isNotEmpty() && oldSize == newSize && !isSameId() -> {
                        Log.d(TAG, "Same ${oldSize} Size Contacts Not Same Id")
                        contactsIDList = contactsProvider.getListID(getApplication())
                        //region Delete Old Contact
                        Log.d(TAG, "New ${1} Deleted Contacts")
                        deleteContact(contactsIDList)
                        //endregion
                        //region Add New Contact
                        Log.d(TAG, "New ${1} Added Contacts")
                        addContact(contactsIDList)
                        //endregion
                    }
                    else -> { Log.d(TAG, "else") }
                }
                postLiveStandBy(SyncContacts,true)
                postLiveContact()
                Log.d(TAG, "syncContacts() Done")
            }
        }
    }

    public fun syncNames() { Log.d(TAG,"syncNames()")
        if(!isProcessing(SyncNames)) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute { Log.d(TAG, "syncNames() Processing")
                postLiveStandBy(SyncNames, false)
                when {
                    itemContactList.isEmpty() -> {
                        Log.d(TAG, "Names is Empty")
                    }
                    itemContactList.isNotEmpty() && isSameName() -> {
                        Log.d(TAG, "Same Names")
                    }
                    else -> {
                        Log.d(TAG, "Not Same Names Now Syncing. . .")
                        contactsProvider.getContactNames(getApplication()).map { updatedContact ->
                            val condition: Boolean = itemContactList.filter { filteredContact ->
                                filteredContact.id == updatedContact.id &&
                                        filteredContact.name.equals(updatedContact.name, false)
                            }.none()
                            if (condition) {
                                Log.d(TAG, "Name Not Synced ${updatedContact.id} ${updatedContact.name} Now Syncing. . .")
                                itemContactList.filter { oldContact -> oldContact.id == updatedContact.id }.map { it.name = updatedContact.name }
                            } else {
                                Log.d(TAG, "Name Synced ${updatedContact.id} ${updatedContact.name}")
                            }
                        }
                    }
                }
                postLiveStandBy(SyncNames, true)
                postLiveContact()
                Log.d(TAG, "syncNames() Done")
            }
        }
    }

    public fun syncPhotos() { Log.d(TAG,"syncPhotos()")
        if(!isProcessing(SyncPhotos)) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute { Log.d(TAG, "syncPhotos() Processing")
                postLiveStandBy(SyncPhotos, false)
                when {
                    itemContactList.isEmpty() -> {
                        Log.d(TAG, "Photos is Empty")
                    }
                    itemContactList.isNotEmpty() && isSamePhoto() -> {
                        Log.d(TAG, "Same Photos")
                    }
                    else -> {
                        Log.d(TAG, "Not Same Photos Now Syncing. . .")
                        contactsProvider.getContactPhotos(getApplication()).map { updatedContact ->
                            val condition: Boolean = itemContactList.filter { filteredContact ->
                                filteredContact.id == updatedContact.id &&
                                        filteredContact.photo.equals(updatedContact.photo, false)
                            }.none()
                            if (condition) {
                                Log.d(TAG, "Photo Not Synced ${updatedContact.id} ${updatedContact.photo} Now Syncing. . .")
                                itemContactList.filter { oldContact -> oldContact.id == updatedContact.id }.map { it.photo = updatedContact.photo }
                            } else {
                                Log.d(TAG, "Photo Synced ${updatedContact.id} ${updatedContact.name}")
                            }
                        }
                    }
                }
                postLiveStandBy(SyncPhotos, true)
                postLiveContact()
                Log.d(TAG, "syncPhotos() Done")
            }
        }
    }

    public fun syncNumbers() { Log.d(TAG,"syncNumbers()")
        if(!isProcessing(SyncNumbers)) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute { Log.d(TAG, "syncNumbers() Processing")
                postLiveStandBy(SyncNumbers, false)
                when {
                    itemContactList.isEmpty() -> {
                        Log.d(TAG, "Numbers is Empty")
                    }
                    itemContactList.isNotEmpty() && isSameNumber() -> {
                        Log.d(TAG, "Same Numbers")
                    }
                    else -> {
                        Log.d(TAG, "Not Same Numbers Now Syncing. . .")
                        contactsProvider.getContactNumbers(getApplication()).map { updatedContact ->
                            val condition: Boolean = itemContactList.filter { filteredContact ->
                                filteredContact.id == updatedContact.id &&
                                        filteredContact.numbers.equals(updatedContact.numbers)
                            }.none()
                            if (condition) {
                                Log.d(TAG, "Numbers Not Synced ${updatedContact.id} ${updatedContact.numbers} Now Syncing. . .")
                                itemContactList.filter { oldContact -> oldContact.id == updatedContact.id }.map {
                                    it.numbers = updatedContact.numbers
                                }
                            } else {
                                Log.d(TAG, "Numbers Synced ${updatedContact.id} ${updatedContact.numbers}")
                            }
                        }
                    }
                }
                postLiveStandBy(SyncNumbers, true)
                postLiveContact()
                Log.d(TAG, "syncNumbers() Done")
            }
        }
    }

    public fun syncEmails() { Log.d(TAG,"syncEmails()")
        if(!isProcessing(SyncEmails)) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute { Log.d(TAG, "syncEmails() Processing")
                postLiveStandBy(SyncEmails, false)
                when {
                    itemContactList.isEmpty() -> {
                        Log.d(TAG, "Emails is Empty")
                    }
                    itemContactList.isNotEmpty() && isSameEmails() -> {
                        Log.d(TAG, "Same Emails")
                    }
                    else -> {
                        Log.d(TAG, "Not Same Emails Now Syncing. . .")
                        contactsProvider.getContactEmails(getApplication()).map { updatedContact ->
                            val condition: Boolean = itemContactList.filter { filteredContact ->
                                filteredContact.id == updatedContact.id &&
                                        filteredContact.emails.equals(updatedContact.emails)
                            }.none()
                            if (condition) {
                                Log.d(TAG, "Emails Not Synced ${updatedContact.id} ${updatedContact.emails} Now Syncing. . .")
                                itemContactList.filter { oldContact -> oldContact.id == updatedContact.id }.map {
                                    it.emails = updatedContact.emails
                                }
                            } else {
                                Log.d(TAG, "Emails Synced ${updatedContact.id} ${updatedContact.numbers}")
                            }
                        }
                    }
                }
                postLiveStandBy(SyncEmails, true)
                postLiveContact()
                Log.d(TAG, "syncEmails() Done")
            }
        }
    }

    public fun sortContacts() { Log.d(TAG,"sortContacts()")
        if(!isProcessing(SortContacts)) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute { Log.d(TAG, "sortContacts() Processing")
                postLiveStandBy(SortContacts, false)
                if (itemContactList.isNotEmpty()) {
                    contactsProvider.getListID(getApplication()).mapIndexed { index, id ->
                        Log.d(TAG, "sortContacts() $index $id ${contactsProvider.getListID(getApplication()).indexOf(id)}  ${itemContactList.get(index).id}")
                        when {
                            id == itemContactList.get(index).id -> { Log.d(TAG, "sortContacts() Same Index $index") }
                            contactsProvider.getListID(getApplication()).indexOf(itemContactList.get(index).id) > -1 -> { Log.d(TAG, "sortContacts() Not Same Index $index")
                                val movingItem : ContactModel = itemContactList.get(index)
                                Log.d(TAG, "movingItem ${movingItem.id} ${movingItem.name} ${contactsProvider.getListID(getApplication()).indexOf(movingItem.id)}")
                                deleteContact(movingItem)
                                itemContactList.add(contactsProvider.getListID(getApplication()).indexOf(movingItem.id), movingItem)
                            }
                            contactsProvider.getListID(getApplication()).indexOf(itemContactList.get(index).id) == -1 -> {
                                deleteContact(itemContactList.get(index))
                            }
                            else -> {  }
                        }
                    }
                }
                postLiveStandBy(SortContacts, true)
                postLiveContact()
                Log.d(TAG, "sortContacts() Done")
            }
        }
    }

    private fun isSameSize() : Boolean {
        return itemContactList.size == contactsProvider.getContactCount(getApplication())
    }

    private fun isSameId() : Boolean { Log.d(TAG,"isSameId() Processing")
        contactsProvider.getListID(getApplication()).map { updatedContactID ->
            val condition : Boolean = itemContactList.filter { filteredContact ->
                filteredContact.id == updatedContactID
            }.none()
            Log.i(TAG,"isSameName() ${updatedContactID} ${condition}")
            if (condition) { Log.d(TAG,"isSameId() false")
                return false
            }
        }
        Log.d(TAG,"isSameId() true")
        return true
    }

    private fun isSameName() : Boolean { Log.d(TAG,"isSameName() Processing")
        /*
        contactsProvider.getMapNames(getApplication()).map { updatedName ->
            val condition : Boolean = itemContactList.filter { filteredContact ->
                filteredContact.id == updatedName.key &&
                filteredContact.name.equals(updatedName.value,false)
            }.none()
            Log.i(TAG,"isSameName() id ${updatedName.key} name ${updatedName.value}")
            if (condition) { Log.d(TAG,"isSameName() false")
                return false
            }
        }
        */
        contactsProvider.getContactNames(getApplication()).map { updatedContact ->
            val condition : Boolean = itemContactList.filter { filteredContact ->
                filteredContact.id == updatedContact.id &&
                filteredContact.name.equals(updatedContact.name,false)
            }.none()
            Log.i(TAG,"isSameName() ${updatedContact.id} ${updatedContact.name}")
            if (condition) { Log.d(TAG,"isSameName() false")
                return false
            }
        }
        Log.d(TAG,"isSameName() true")
        return true
    }

    private fun isSamePhoto() : Boolean { Log.d(TAG,"isSamePhoto() Processing")
        contactsProvider.getContactPhotos(getApplication()).map { updatedContact ->
            val condition : Boolean = itemContactList.filter { filteredContact ->
                filteredContact.id == updatedContact.id &&
                filteredContact.photo.equals(updatedContact.photo,false)
            }.none()
            Log.i(TAG,"isSamePhoto() ${updatedContact.id} ${updatedContact.photo}")
            if (condition) { Log.d(TAG,"isSamePhoto() false")
                return false
            }
        }
        Log.d(TAG,"isSamePhoto() true")
        return true
    }

    private fun isSameNumber() : Boolean { Log.d(TAG,"isSameNumber() Processing")
        contactsProvider.getContactNumbers(getApplication()).map { updatedContact ->
            val condition : Boolean = itemContactList.filter { filteredContact ->
                filteredContact.id == updatedContact.id &&
                filteredContact.numbers.equals(updatedContact.numbers)
            }.none()
            Log.i(TAG,"isSameNumber() ${updatedContact.id} ${updatedContact.numbers}")
            if (condition) { Log.d(TAG,"isSameNumber() false")
                return false
            }
        }
        Log.d(TAG,"isSameNumber() true")
        return true
    }

    private fun isSameEmails() : Boolean { Log.d(TAG,"isSameEmails() Processing")
        contactsProvider.getContactEmails(getApplication()).map { updatedContact ->
            val condition : Boolean = itemContactList.filter { filteredContact ->
                filteredContact.id == updatedContact.id &&
                filteredContact.emails.equals(updatedContact.emails)
            }.none()
            Log.i(TAG,"isSameEmails() ${updatedContact.id} ${updatedContact.emails}")
            if (condition) {Log.d(TAG,"isSameEmails() true")
                return false
            }
        }
        Log.d(TAG,"isSameEmails() true")
        return true
    }

    private fun isProcessing(ProgressID : Int) : Boolean {
        return itemProcessList.filter { it == ProgressID }.isNotEmpty()
    }

    private fun postLiveStandBy(ProgressID : Int, isDone : Boolean) { Log.d(TAG,"postLiveStandBy($ProgressID,$isDone)")
        when {
            isDone == false && itemProcessList.filter { it == ProgressID }.isEmpty() -> {
                itemProcessList.add(ProgressID) //itemProgressList.distinct()
            }
            isDone == true && itemProcessList.filter { it == ProgressID }.isNotEmpty() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                Log.d(TAG, "Build.VERSION.SDK_INT >= Build.VERSION_CODES.N")
                itemProcessList.removeIf { it == ProgressID }
            }
            isDone == true && itemProcessList.filter { it == ProgressID }.isNotEmpty() && Build.VERSION.SDK_INT < Build.VERSION_CODES.N -> {
                Log.d(TAG, "Build.VERSION.SDK_INT < Build.VERSION_CODES.N")
                itemProcessList.remove(ProgressID)
            }
        }
        Log.i(TAG, "itemProgressList ${itemProcessList.map { it }}")
        liveStandBy.postValue(itemProcessList.isNotEmpty())
    }

    public fun observeLiveStandBy() : LiveData<Boolean> {
        return liveStandBy
    }

    private fun postLiveContact() { Log.d(TAG,"postLiveContact()")
        if (itemProcessList.none()) { Log.d(TAG,"postLiveContact() itemProcessList.none()")
            liveContactList.postValue(itemContactList)
        }
    }

    public fun observeLiveContact() : LiveData<List<ContactViewHolderModel>> {
        return Transformations.map(liveContactList) { contactList ->
            convertContacts(contactList)
        }
    }
}