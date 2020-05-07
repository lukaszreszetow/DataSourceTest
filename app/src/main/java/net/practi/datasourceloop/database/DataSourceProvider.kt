package net.practi.datasourceloop.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import net.practi.datasourceloop.DataSourceType
import net.practi.datasourceloop.Order
import net.practi.datasourceloop.database.entity.OrderEntity
import kotlin.random.Random

class DataSourceProvider(
    private val ordersDao: OrdersDao,
    private val dataSourceSelectorLive: LiveData<DataSourceType>
) {

    private val customDataSource = CustomDataSource.Factory(ordersDao)

    val dataSourceLive: LiveData<DataSource.Factory<Int, Order>> =
        Transformations.map(dataSourceSelectorLive) {
            getCorrectDataSource(it)
        }

    private fun getCorrectDataSource(type: DataSourceType): DataSource.Factory<Int, Order> {
        return when (type) {
            DataSourceType.NORMAL -> ordersDao.getOrderEntities().map { it.toOrder() }
            DataSourceType.CUSTOM -> {
                customDataSource.setNumber(Random.nextInt())
                customDataSource
            }
        }
    }
}