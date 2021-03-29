package com.alecsander.poc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.alecsander.poc.model.Contact


class ContactAdapter(private val contacts : List<Contact>,
                     private val context: Context) : Adapter<ContactAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return contacts.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemName = itemView.findViewById<TextView>(R.id.contact_item_name)!!
        val itemContact = itemView.findViewById<TextView>(R.id.contact_item_contact)!!

        fun bindView(contact: Contact) {
            val name = itemView.findViewById<TextView>(R.id.contact_item_name)
            val email = itemView.findViewById<TextView>(R.id.contact_item_contact)

            name.text = contact.nameContact
            email.text = contact.email
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = contacts[position]
        holder?.let {
            it.itemName.text = note.nameContact
            it.itemContact.text = note.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false)
        return ViewHolder(view)
    }
}
