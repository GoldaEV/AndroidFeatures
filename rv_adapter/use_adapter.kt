fun limits() = viewBinder<LimitsModel, RvItemInfoLimitsCardBinding>(
    binder = RvItemInfoLimitsCardBinding::bind,
    layoutResId = R.layout.rv_item_info_limits_card
) {
    bindView { item ->
            tvLabel.text = item.label
            tvValue.text = item.value
    }
}


val limitsAdapter by lazy {
    MultiBindingAdapter(
       limits()
    )
}
