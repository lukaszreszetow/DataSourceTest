package net.practi.datasourceloop.list

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import net.practi.datasourceloop.R

class OrderView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    init {
        inflate(context, R.layout.order_item_view, this)
    }
}