package com.example.agenda.configuracion

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.agenda.Dao.MateriaDao
import com.example.agenda.Dao.NotaDao
import com.example.agenda.Dao.UsuarioDao
import com.example.agenda.model.Materia
import com.example.agenda.model.Nota
import com.example.agenda.model.Usuario

@Database(
    entities = [Usuario::class, Materia::class, Nota::class],
    version = 1
)
abstract class AgendaDB:RoomDatabase() {
    abstract val UsuarioDao: UsuarioDao
    abstract val MateriaDao: MateriaDao
    abstract val NotaDao: NotaDao

    companion object{
        const val DATABASE_NAME="DB_AGENDA"
    }
}