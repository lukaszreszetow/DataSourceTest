package net.practi.datasourceloop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking
import net.practi.datasourceloop.database.OrderDatabase
import net.practi.datasourceloop.database.entity.OrderEntity
import net.practi.datasourceloop.list.OrdersAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var db: OrderDatabase
    lateinit var pagedListBuilder: PagedListBuilder
    private val ordersAdapter = OrdersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv.adapter = ordersAdapter

        db = Room.databaseBuilder(applicationContext, OrderDatabase::class.java, "order_database").build()
        addDataToDb()
        initializePagedListBuilder()
        initializeButtons()
        observeOrders()
        changeTypeQuickly()
    }

    private fun changeTypeQuickly() {
        pagedListBuilder.changeType(DataSourceType.CUSTOM)
        pagedListBuilder.changeType(DataSourceType.NORMAL)
        pagedListBuilder.changeType(DataSourceType.CUSTOM)
        pagedListBuilder.changeType(DataSourceType.NORMAL)
        pagedListBuilder.changeType(DataSourceType.CUSTOM)
    }

    private fun observeOrders() {
        pagedListBuilder.filteredOrdersLive.observeForever {
            Log.d("MainActivity", "Received paged list with ${it.size} orders")
            ordersAdapter.submitList(it)
        }
    }

    private fun addDataToDb() {
        runBlocking {
            db.ordersDao().addOrUpdateAll(DUMMY_ORDERS)
        }
    }

    private fun initializePagedListBuilder() {
        pagedListBuilder = PagedListBuilder(db.ordersDao())
    }

    private fun initializeButtons() {
        normalDataSource.setOnClickListener {
            pagedListBuilder.changeType(DataSourceType.NORMAL)
        }

        customDataSource.setOnClickListener {
            pagedListBuilder.changeType(DataSourceType.CUSTOM)
        }
    }

    companion object {
        val DUMMY_ORDERS = (1..100).map { OrderEntity("id$it") }
    }
}
