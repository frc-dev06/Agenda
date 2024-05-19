package com.example.agenda.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.agenda.R
import com.example.agenda.model.Materia

class MateriasAdapter(
    private val mContext:Context,
    private val listaMaterias: List<Materia>
): ArrayAdapter<Materia>(mContext, 0,listaMaterias) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layout= convertView
        val materia= listaMaterias[position]

        if (layout==null){
            layout = LayoutInflater.from(mContext).inflate(R.layout.item_materia, parent, false)
        }

        val txtNombreMateria= layout!!.findViewById<TextView>(R.id.txtNombreMateriaItem)
        val txtNombreDocente= layout!!.findViewById<TextView>(R.id.txtNombreDocenteItem)

        txtNombreMateria.setText(materia.nombreMateria)
        txtNombreDocente.setText(materia.nombreDocente)

        return layout
    }
}