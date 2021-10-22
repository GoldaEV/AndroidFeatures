class RVAdapter : RecyclerView.Adapter<RVAdapter.MyViewHolder>() {

    private var oldUserList = mutableListOf<User>()

    fun setData(newList: List<User>) {
        val difUtil = MyDifUtil(oldUserList, newList)
        val difUtilResult = DiffUtil.calculateDiff(difUtil)
        oldUserList.clear()
        oldUserList.addAll(newList)
        difUtilResult.dispatchUpdatesTo(this)
    }

    class MyViewHolder(val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tv1.text = oldUserList[position].name
        holder.binding.tv2.text = oldUserList[position].value.toString()
    }

    override fun getItemCount(): Int {
        return oldUserList.size
    }

}

class MyDifUtil(
    private val listOld: List<User>,
    private val listNew: List<User>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return listOld.size
    }

    override fun getNewListSize(): Int {
        return listNew.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return listNew[newItemPosition].name == listOld[oldItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return listNew[newItemPosition] == listOld[oldItemPosition]}
}
