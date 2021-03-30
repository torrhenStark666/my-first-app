package com.alecsander.poc.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Contact(val userId: String? = null, val nameContact: String? = null, val email : String? = null){

}