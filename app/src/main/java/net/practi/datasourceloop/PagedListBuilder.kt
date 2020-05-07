package net.practi.datasourceloop

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import net.practi.datasourceloop.database.DataSourceProvider
import net.practi.datasourceloop.database.OrdersDao
import kotlin.random.Random

class PagedListBuilder(private val ordersDao: OrdersDao) {

    private val _typeSelectorLive = MutableLiveData(DataSourceType.NORMAL)
    private val typeSelectorLive: LiveData<DataSourceType> = _typeSelectorLive

    private val dataSource = DataSourceProvider(ordersDao, typeSelectorLive).dataSourceLive

    val filteredOrdersLive: LiveData<PagedList<OrderWithNumber>> =
        Transformations.switchMap(dataSource) { dataSource ->
            buildLivePagedList(dataSource.map {
                OrderWithNumber(it.id, Random.nextInt())
            })
        }

    private fun buildLivePagedList(dataSource: DataSource.Factory<Int, OrderWithNumber>): LiveData<PagedList<OrderWithNumber>> =
        LivePagedListBuilder(
            dataSource, PagedList.Config.Builder()
                .setPageSize(pagingSize)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pagingSize * 2)
                .build()
        ).build()

    fun changeType(dataSourceType: DataSourceType) {
        _typeSelectorLive.postValue(dataSourceType)
    }

    companion object {
        const val pagingSize = 10
    }

}