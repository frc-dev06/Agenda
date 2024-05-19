package com.example.agenda.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import com.example.agenda.R
import com.example.agenda.model.Nota

class NotasAdapter(
    private val mContext: Context,
    private val listaNotas: List<Nota>
    ): ArrayAdapter<Nota> (mContext, 0, listaNotas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layout= convertView
        val nota= listaNotas[position]

        if (layout==null){
            layout = LayoutInflater.from(mContext).inflate(R.layout.item_nota, parent, false)
        }

        val txtTitulo= layout!!.findViewById<TextView>(R.id.txtTituloItemNota)
        val txtFecha= layout.findViewById<TextView>(R.id.txtFechaItemNota)

        txtTitulo.setText(nota.titulo)
        txtFecha.setText(nota.fecha)

        return layout
    }
}