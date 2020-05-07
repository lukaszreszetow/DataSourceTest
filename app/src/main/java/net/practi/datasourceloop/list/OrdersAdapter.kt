package net.practi.datasourceloop.list

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.order_item_view.view.*
import net.practi.datasourceloop.OrderWithNumber

class OrdersAdapter : PagedListAdapter<OrderWithNumber, RecyclerView.ViewHolder>(OrderListItemDiffUtil) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            val view = (holder.itemView as OrderView)
            view.number.text = item.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolderCreator(OrderView(parent.context))
}

class ViewHolderCreator(itemView: View) : RecyclerView.ViewHolder(itemView)

