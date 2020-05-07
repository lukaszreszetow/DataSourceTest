package net.practi.datasourceloop.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import net.practi.datasourceloop.Order
import net.practi.datasourceloop.OrderWithNumber

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey
    val id: String,
    val number: Int? = null
) {

    constructor(order: Order) : this(
        id = order.id,
        number = null
    )

    fun toOrder() = Order(id = id)

    fun toOrderWithNumber() = OrderWithNumber(id = id, number = null)
}