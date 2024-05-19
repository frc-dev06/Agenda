package com.example.agenda.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "materias",
    foreignKeys = [ForeignKey(entity = Usuario::class,
        parentColumns = ["cedulaUsuario"],
        childColumns = ["cedulaUsuario"],
        onDelete = ForeignKey.CASCADE)]
)
data class Materia(
    @PrimaryKey(autoGenerate = true) val materiaId: Int=0,
    val nombreMateria: String,
    val nombreDocente: String,
    val cedulaUsuario: String
):Serializable