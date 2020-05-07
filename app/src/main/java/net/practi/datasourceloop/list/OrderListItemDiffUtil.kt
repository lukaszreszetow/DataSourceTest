package net.practi.datasourceloop.list

import androidx.recyclerview.widget.DiffUtil
import net.practi.datasourceloop.OrderWithNumber

object OrderListItemDiffUtil : DiffUtil.ItemCallback<OrderWithNumber>() {
    override fun areItemsTheSame(oldItem: OrderWithNumber, newItem: OrderWithNumber): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OrderWithNumber, newItem: OrderWithNumber): Boolean {
        return oldItem == newItem
    }
}