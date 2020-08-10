package com.example.recyclerviewapp

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log

class ContactsProvider {

    companion object {
        private final val TAG = ContactsProvider::class.java.simpleName
        //region ContactsContract
        private final const val MimeType = ContactsContract.Data.MIMETYPE
        private  final const val InsertContentType = ContactsContract.RawContacts.CONTENT_TYPE
        //endregion
        //region ContactsContract.Contacts
        private final const val ContactID = ContactsContract.Contacts._ID
        private final const val DisplayName = ContactsContract.Contacts.DISPLAY_NAME
        private final const val ContactsPhoto = ContactsContract.Contacts.PHOTO_URI
        private final val ContactsContentUri = ContactsContract.Contacts.CONTENT_URI
        private final const val SortName = ContactsContract.Contacts.SORT_KEY_PRIMARY
        private final const val SortId = ContactsContract.Contacts.Entity.RAW_CONTACT_ID + " ASC"
        //endregion
        //region ContactsContract.Intents.Insert
        private  final const val InsertEmail = ContactsContract.Intents.Insert.EMAIL
        private  final const val InsertPhone = ContactsContract.Intents.Insert.PHONE
        private  final const val InsertAction = ContactsContract.Intents.Insert.ACTION
        private  final const val InsertPhoneType = ContactsContract.Intents.Insert.PHONE_TYPE
        //endregion
        //region ContactsContract.CommonDataKinds.Email
        private final const val EmailData = ContactsContract.CommonDataKinds.Email.DATA
        private final const val EmailType = ContactsContract.CommonDataKinds.Email.TYPE
        private final const val EmailLabel = ContactsContract.CommonDataKinds.Email.LABEL
        private final const val EmailAddress = ContactsContract.CommonDataKinds.Email.ADDRESS
        private final val EmailContentUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI
        private final const val EmailName = ContactsContract.CommonDataKinds.Email.DISPLAY_NAME
        private  final const val EmailTypeWork = ContactsContract.CommonDataKinds.Email.TYPE_WORK
        private final const val EmailContactID = ContactsContract.CommonDataKinds.Email.CONTACT_ID
        private final const val EmailContentItemType = ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
        //endregion
        //region ContactsContract.CommonDataKinds.Phone
        private final const val PhoneType = ContactsContract.CommonDataKinds.Phone.TYPE
        private  final const val InsertEmailType = ContactsContract.Intents.Insert.EMAIL_TYPE
        private final const val PhoneLabel = ContactsContract.CommonDataKinds.Phone.LABEL
        private final const val PhoneNumber = ContactsContract.CommonDataKinds.Phone.NUMBER
        private final const val PhonePhoto = ContactsContract.CommonDataKinds.Phone.PHOTO_URI
        private final val PhoneContentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        private  final const val PhoneTypeWork = ContactsContract.CommonDataKinds.Phone.TYPE_WORK
        private final const val PhoneContactID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        private final const val PhoneContentItemType = ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        //endregion
        private final val Projection : Array<String> = arrayOf(DisplayName, PhoneNumber, PhonePhoto, MimeType, PhoneType, PhoneLabel, PhoneContactID)
    }

    private fun getPhoto(cursor : Cursor) : String? {
        return try {
            cursor.getString(cursor.getColumnIndexOrThrow(ContactsPhoto))
        } catch (ex : Exception) { ex.printStackTrace()
            Log.e(TAG, "getPhoto() Exception : ${ex.message}")
            null
        }
    }

    private fun getPhones(context : Context, contentResolver : ContentResolver, cursor : Cursor, id : String) : MutableMap<String,String> {
        val numbers : MutableMap<String,String> = mutableMapOf<String, String>()
        if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
            val numberCursor : Cursor? = contentResolver.query(PhoneContentUri, null, "$PhoneContactID = ?", arrayOf(id), SortName)
            while (numberCursor?.moveToNext() == true) {
                val mimeType : String = numberCursor.getString(numberCursor.getColumnIndex(MimeType))
                val phoneNumber : String? = getPhoneNumber(numberCursor, mimeType)
                val phoneType : Int = numberCursor.getInt(numberCursor.getColumnIndex(PhoneType))
                val phoneLabel : String? = getPhoneLabel(context, numberCursor, mimeType, phoneType)
                phoneNumber?.let { number ->
                    numbers.set(number.getNumbersOnly(), phoneLabel?:"Nil")
                }
            }
            if (numberCursor?.moveToNext() == false) {
                numberCursor.close()
            }
        }
        return numbers
    }

    private fun getPhoneLabel(context : Context, cursor : Cursor, mimeType : String, phoneType : Int) : String? {
        val typeLabel = ContactsContract.CommonDataKinds.Phone.getTypeLabel(context.getResources(), phoneType, "")
        Log.d(TAG,"getPhoneLabel() typeLabel $typeLabel")
        return when {
            mimeType.equals(PhoneContentItemType) && typeLabel.isNotBlank() && phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM -> {
                cursor.getString(cursor.getColumnIndex(PhoneLabel))
            }
            mimeType.equals(PhoneContentItemType) && typeLabel.isNotBlank() && phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> {
                "Mobile"
            }
            mimeType.equals(PhoneContentItemType) && typeLabel.isNotBlank() -> {
                typeLabel.toString()
            }
            else -> {
                null
            }
        }
    }

    private fun getPhoneNumber(cursor : Cursor, mimeType : String ) : String? {
        return if (mimeType.equals(PhoneContentItemType)) {
            cursor.getString(cursor.getColumnIndex(PhoneNumber))
        } else {
            null
        }
    }

    private fun getEmails(context : Context, contentResolver : ContentResolver, cursor : Cursor, id : String) : MutableMap<String,String> {
        val emails : MutableMap<String,String> = mutableMapOf<String, String>()
        val emailCursor : Cursor? = contentResolver.query(EmailContentUri, null, "$EmailContactID = ?", arrayOf(id.toString()), null)
        while (emailCursor?.moveToNext() == true) {
            val mimeType : String = emailCursor.getString(emailCursor.getColumnIndex(MimeType))
            val emailValue : String = emailCursor.getString(emailCursor.getColumnIndex(EmailData))
            val emailType : Int = emailCursor.getInt(emailCursor.getColumnIndex(EmailType))
            val emailLabel : String? = getEmailLabel(context, emailCursor, mimeType, emailType)
            emails.set(emailValue.remove_(), emailLabel?:"Nil")
        }
        if (emailCursor?.moveToNext() == false) {
            emailCursor.close()
        }
        return emails
    }

    private fun getEmailLabel(context : Context, cursor : Cursor, mimeType : String, emailType : Int) : String? {
        val typeLabel = ContactsContract.CommonDataKinds.Email.getTypeLabel(context.getResources(), emailType, "")
        Log.d(TAG,"getEmailLabel() typeLabel $typeLabel")
        return when {
            mimeType.equals(EmailContentItemType) && typeLabel.isNotBlank() && emailType == ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM -> {
                cursor.getString(cursor.getColumnIndex(EmailLabel))
            }
            mimeType.equals(EmailContentItemType) && typeLabel.isNotBlank() -> {
                typeLabel.toString()
            } else -> {
                null
            }
        }
    }

    public fun addContact() : Intent { Log.d(TAG, "addContact()")
        return Intent(InsertAction)
                .setType(InsertContentType)
                //.putExtra(InsertEmail, "xxx@xxx.com")
                //.putExtra(InsertEmailType, EmailTypeWork)
                //.putExtra(InsertPhone, "000")
                //.putExtra(InsertPhoneType, PhoneTypeWork)
    }

    public fun updateContact(contactUri : Uri) : Intent { Log.d(TAG, "updateContact()")
        return Intent(Intent.ACTION_EDIT, contactUri)
                .setData(contactUri)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("finishActivityOnSaveCompleted", true)
    }

    public fun deleteContact(context : Context, contactId : String) : Int { Log.d(TAG, "deleteContact()")
        val uri : Uri  = Uri.withAppendedPath(ContactsContentUri, contactId)
        val deleted : Int = context.getContentResolver().delete(uri,null,null)
        return deleted
    }

    public fun getContactCount(context : Context) : Int { Log.d(TAG, "getContactCount()")
        var contactsSize : Int = 0
        val contentResolver : ContentResolver
        var cursor : Cursor? = null
        try {
            contentResolver = context.getContentResolver()
            cursor = contentResolver.query(ContactsContentUri, null, null, null, SortName)
            while (cursor?.moveToNext() == true) {
                //val id : Long = cursor.getLong(cursor.getColumnIndex(ContactID))
                //val name : String = cursor.getString(cursor.getColumnIndex(DisplayName))
                //Log.d(TAG, "ID $id Name $name")
                contactsSize++
            }
        } catch (ex : Exception) { ex.printStackTrace()
            Log.e(TAG, "getContactCount() Exception : ${ex.message}")
        } catch (ex : IllegalArgumentException) { ex.printStackTrace()
            Log.e(TAG, "getContactCount() IllegalArgumentException : ${ex.message}")
        } finally {
            cursor?.close()
        }
        Log.i(TAG,"$contactsSize")
        return contactsSize
    }

    public fun getContact(context : Context, contactId : String) : ContactModel? {
        Log.d(TAG, "getContact()")
        var contact : ContactModel? = null
        val contentResolver : ContentResolver
        var cursor : Cursor? = null
        try {
            contentResolver = context.getContentResolver()
            cursor = contentResolver.query(ContactsContentUri, null, ContactID + " = ?", arrayOf(contactId), null)
            while (cursor?.moveToNext() == true) {
                val id : Long = cursor.getLong(cursor.getColumnIndex(ContactID))
                val name : String = cursor.getString(cursor.getColumnIndex(DisplayName))
                val photo : String = getPhoto(cursor)?:""
                val numbers : MutableMap<String,String> = getPhones(context, contentResolver, cursor, id.toString())
                val emails : MutableMap<String,String> = getEmails(context, contentResolver, cursor, id.toString())
                Log.d(TAG, "ID $id Name $name Photo $photo")
                contact = ContactModel(id, name, photo, numbers, emails)
            }
        } catch (ex : Exception) { ex.printStackTrace()
            Log.e(TAG, "getContact() Exception : ${ex.message}")
        } catch (ex : IllegalArgumentException) { ex.printStackTrace()
            Log.e(TAG, "getContact() IllegalArgumentException : ${ex.message}")
        } finally {
            cursor?.close()
        }
        return contact
    }

    public fun getContactsID(context : Context) : List<Long> { Log.d(TAG, "getContactsID()")
        val contactsIDList : MutableList<Long> = mutableListOf()
        val contentResolver : ContentResolver
        var cursor : Cursor? = null
        try {
            contentResolver = context.getContentResolver()
            cursor = contentResolver.query(ContactsContentUri, null, null, null, SortName)
            while (cursor?.moveToNext() == true) {
                val id : Long = cursor.getLong(cursor.getColumnIndex(ContactID))
                Log.d(TAG, "ID $id")
                contactsIDList.add(id)
            }
        } catch (ex : Exception) { ex.printStackTrace()
            Log.e(TAG, "getContactsID() Exception : ${ex.message}")
        } catch (ex : IllegalArgumentException) { ex.printStackTrace()
            Log.e(TAG, "getContactsID() IllegalArgumentException : ${ex.message}")
        } finally {
            cursor?.close()
        }
        contactsIDList.map { id -> Log.i(TAG, "ID ${id}") }
        return when {
            contactsIDList.isEmpty() -> {
                emptyList()
            }
            contactsIDList.isNotEmpty() -> {
                contactsIDList.distinct()
                contactsIDList
            }
            else -> {
                emptyList()
            }
        }
    }

    public fun getContactsName(context : Context) : List<ContactModel> { Log.d(TAG, "getContactsName()")
        val contactsList : MutableList<ContactModel> = mutableListOf()
        val contentResolver : ContentResolver
        var cursor : Cursor? = null
        try {
            contentResolver = context.getContentResolver()
            cursor = contentResolver.query(ContactsContentUri, null, null, null, SortName)
            while (cursor?.moveToNext() == true) {
                val id : Long = cursor.getLong(cursor.getColumnIndex(ContactID))
                val name : String = cursor.getString(cursor.getColumnIndex(DisplayName))
                val photo : String = ""
                val numbers : MutableMap<String,String> = mutableMapOf<String, String>()
                val emails : MutableMap<String,String> = mutableMapOf<String, String>()
                Log.d(TAG, "ID $id Name $name Photo $photo numbers $numbers emails $emails")
                contactsList.add(ContactModel(id, name, photo, numbers, emails))
            }
        } catch (ex : Exception) { ex.printStackTrace()
            Log.e(TAG, "getContactsName() Exception : ${ex.message}")
        } catch (ex : IllegalArgumentException) { ex.printStackTrace()
            Log.e(TAG, "getContactsName() IllegalArgumentException : ${ex.message}")
        } finally {
            cursor?.close()
        }
        contactsList.map {
            Log.i(TAG, "ID ${it.id} Name ${it.name} Photo ${it.photo} Numbers ${it.numbers} Emails ${it.emails}")
        }
        return emptyList()
    }

    public fun getContactsPhoto(context : Context) : List<ContactModel> { Log.d(TAG, "getContactsName()")
        val contactsList : MutableList<ContactModel> = mutableListOf()
        val contentResolver : ContentResolver
        var cursor : Cursor? = null
        try {
            contentResolver = context.getContentResolver()
            cursor = contentResolver.query(ContactsContentUri, null, null, null, SortName)
            while (cursor?.moveToNext() == true) {
                val id : Long = cursor.getLong(cursor.getColumnIndex(ContactID))
                val name : String = ""
                val photo : String = getPhoto(cursor)?:""
                val numbers : MutableMap<String,String> = mutableMapOf<String, String>()
                val emails : MutableMap<String,String> = mutableMapOf<String, String>()
                Log.d(TAG, "ID $id Name $name Photo $photo numbers $numbers emails $emails")
                contactsList.add(ContactModel(id, name, photo, numbers, emails))
            }
        } catch (ex : Exception) { ex.printStackTrace()
            Log.e(TAG, "getContactsName() Exception : ${ex.message}")
        } catch (ex : IllegalArgumentException) { ex.printStackTrace()
            Log.e(TAG, "getContactsName() IllegalArgumentException : ${ex.message}")
        } finally {
            cursor?.close()
        }
        contactsList.map {
            Log.i(TAG, "ID ${it.id} Name ${it.name} Photo ${it.photo} Numbers ${it.numbers} Emails ${it.emails}")
        }
        return emptyList()
    }

    public fun getContacts(context : Context) : List<ContactModel> { Log.d(TAG, "getContacts()")
        val contactsList : MutableList<ContactModel> = mutableListOf()
        val contentResolver : ContentResolver
        var cursor : Cursor? = null
        try {
            contentResolver = context.getContentResolver()
            cursor = contentResolver.query(ContactsContentUri, null, null, null, SortName)
            while (cursor?.moveToNext() == true) {
                val id : Long = cursor.getLong(cursor.getColumnIndex(ContactID))
                val name : String = cursor.getString(cursor.getColumnIndex(DisplayName))
                val photo : String = getPhoto(cursor)?:""
                val numbers : MutableMap<String,String> = getPhones(context, contentResolver, cursor, id.toString())
                val emails : MutableMap<String,String> = getEmails(context, contentResolver, cursor, id.toString())
                Log.d(TAG, "ID $id Name $name Photo $photo numbers $numbers emails $emails")
                contactsList.add(ContactModel(id, name, photo, numbers, emails))
            }
        } catch (ex : Exception) { ex.printStackTrace()
            Log.e(TAG, "getContacts() Exception : ${ex.message}")
        } catch (ex : IllegalArgumentException) { ex.printStackTrace()
            Log.e(TAG, "getContacts() IllegalArgumentException : ${ex.message}")
        } finally {
            cursor?.close()
        }
        contactsList.map {
            Log.i(TAG, "ID ${it.id} Name ${it.name} Photo ${it.photo} Numbers ${it.numbers} Emails ${it.emails}")
        }
        return when {
            contactsList.isEmpty() -> {
                emptyList()
            }
            contactsList.isNotEmpty() -> {
                contactsList.distinct()
                contactsList
            }
            else -> {
                emptyList()
            }
        }
    }
}