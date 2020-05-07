package net.practi.datasourceloop.database

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import kotlinx.coroutines.runBlocking
import net.practi.datasourceloop.Order
import kotlin.random.Random

class CustomDataSource(private val ordersDao: OrdersDao) : PositionalDataSource<Order>() {

    var number: Int? = null

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Order>) {
        val result = loadBlocking(params.startPosition, params.loadSize)
        callback.onResult(result)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Order>) {
        val result = loadBlocking(0, params.pageSize)
        callback.onResult(result, 0)
    }

    private fun loadBlocking(startPosition: Int, loadSize: Int): List<Order> {
        return runBlocking {
            try {
                return@runBlocking ordersDao.getOrderEntitiesCustomPaging(startPosition, loadSize).map { it.toOrder() }
                    .filter { Random.nextBoolean() }
            } catch (e: Exception) {
                return@runBlocking emptyList<Order>()
            }
        }
    }

    class Factory(private val ordersDao: OrdersDao) : DataSource.Factory<Int, Order>() {
        private var lastDataSource: CustomDataSource? = null
        private var lastNumber: Int? = null

        override fun create(): DataSource<Int, Order> {
            Log.d("CustomDataSource", "Factory create function called")
            val newDataSource = CustomDataSource(ordersDao).apply {
                number = lastNumber
            }
            lastDataSource = newDataSource
            return newDataSource
        }

        fun setNumber(newNumber: Int) {
            lastNumber = newNumber
            invalidateLastDataSource()
        }

        private fun invalidateLastDataSource() {
            lastDataSource?.invalidate()
        }
    }

}