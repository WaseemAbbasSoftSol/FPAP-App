package com.lmslsbe.ui.common

interface OnListItemClickListener <T>{
    fun onItemClick(item: T, pos: Int)
}