package com.softsolutions.fpap.ui.common

import android.view.View

interface OnItemViewClickListener<T> {
    fun onClick(view: View, item: T)
}