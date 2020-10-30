    val recyclerView: RecyclerView
    val recyclerAdapter: RecyclerView.Adapter

    
    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback: SwipeToDeleteCallback = object : SwipeToDeleteCallback(this) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                val position = viewHolder.adapterPosition
                val item = recyclerAdapter.getData()?.get(position)
                recyclerAdapter.removeItem(position)
                val snackbar = Snackbar.make(view, "Item was removed from the list.", Snackbar.LENGTH_LONG)
                view.setAction("UNDO") {
                    recyclerView.scrollToPosition(position)
                }
                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.show()
            }
        }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(recyclerView)
    }
