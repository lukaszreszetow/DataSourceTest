package net.practi.datasourceloop.database

import androidx.room.Dao
import androidx.room.Query
import androidx.paging.DataSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import net.practi.datasourceloop.database.entity.OrderEntity

@Dao
interface OrdersDao {

    @Query("SELECT * FROM orders LIMIT :take OFFSET :skip")
    fun getOrderEntitiesCustomPaging(skip: Int, take: Int): List<OrderEntity>

    @Query("SELECT * FROM orders")
    fun getOrderEntities(): DataSource.Factory<Int, OrderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrUpdateAll(orderEntities: List<OrderEntity>)
}