package net.practi.datasourceloop.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.practi.datasourceloop.database.entity.OrderEntity

@Database(
    entities = [OrderEntity::class],
    version = 1,
    exportSchema = true
)

abstract class OrderDatabase : RoomDatabase() {
    abstract fun ordersDao(): OrdersDao
}

