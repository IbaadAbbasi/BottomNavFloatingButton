package com.railway.ncs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kpk.eChallan.database.AppDao
import com.kpk.eChallan.database_tables.CitiesTable
import com.kpk.eChallan.database_tables.TicketsTable
import com.railway.ncs.database_tables.AllTrainsTable


@Database(entities = [CitiesTable::class, TicketsTable::class, AllTrainsTable::class],version = 1, exportSchema = false)
abstract class NCSDb: RoomDatabase() {
    abstract fun appDao(): AppDao
}