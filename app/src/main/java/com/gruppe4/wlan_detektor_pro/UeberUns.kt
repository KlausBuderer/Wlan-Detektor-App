package com.gruppe4.wlan_detektor_pro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Über uns View
 * @author Klaus Buderer
 * @since 1.0.0
 */
class UeberUns : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ueber_uns, container, false)
    }
}