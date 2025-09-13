package com.example.app_moviles.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.app_moviles.R
import com.example.app_moviles.entities.Contact

class ContactAdapter(val data: List<Contact>): RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ContactViewHolder {
        // esta metodo define que layout se va a usar
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_concat, parent, false);

        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder,position: Int) {
        // se comparta como una activid
        val view = holder.itemView
        val item = data[position]

        val tvName= view.findViewById<TextView>(R.id.tvName)
        val tvPhone = view.findViewById<TextView>(R.id.tvPhone)

        tvName.text = item.name
        tvPhone.text = item.phone

        view.setOnClickListener {
            Toast.makeText(view.context, "Hola ${item.name}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}