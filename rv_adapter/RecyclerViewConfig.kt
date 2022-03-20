package by.bank.bsb.mobile.utils.recycler

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewConfig(
    internal val context: Context
) {

    var itemDecorations: MutableList<RecyclerView.ItemDecoration> = mutableListOf()
    var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
    var itemTouchHelper: ItemTouchHelper? = null

}