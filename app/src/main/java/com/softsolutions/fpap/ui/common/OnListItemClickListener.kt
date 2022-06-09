package com.softsolutions.fpap.ui.common

interface OnListItemClickListener <T>{
    fun onItemClick(item: T, pos: Int)
}