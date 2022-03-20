package by.exmple_project.utils.recycler

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

fun RecyclerView.configure(
    adapter: MultiBindingAdapter,
    block: RecyclerViewConfig.() -> Unit = {}
) {
    RecyclerViewConfig(context).also {
        block(it)
        layoutManager = it.layoutManager
        this.adapter = adapter
        it.itemDecorations.forEach { addItemDecoration(it) }
        it.itemTouchHelper?.attachToRecyclerView(this)
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> RecyclerView.getItems() = (adapter as? MultiBindingAdapter)?.items as? List<T>

fun RecyclerViewConfig.linearLayout(
    block: LinearLayoutManager.() -> Unit = {}
) { layoutManager = LinearLayoutManager(context).apply(block) }

fun RecyclerViewConfig.gridLayout(
    spansCount: Int = 1,
    block: GridLayoutManager.() -> Unit = {}
) { layoutManager = GridLayoutManager(context, spansCount).apply(block) }

fun RecyclerViewConfig.staggeredGridLayout(
    spansCount: Int = 1,
    orientation: Int = StaggeredGridLayoutManager.VERTICAL,
    block: StaggeredGridLayoutManager.() -> Unit = {}
) {
    layoutManager = StaggeredGridLayoutManager(spansCount, orientation).apply(block)
}

