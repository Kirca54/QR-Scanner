package com.example.qrcodescanner.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.qrcodescanner.ui.scanned_history.ScannedHistoryFragment
import com.example.qrcodescanner.ui.scanner.QrScannerFragment

/**
 * Developed by Happy on 6/7/19
 */
class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                QrScannerFragment.newInstance()
            }

            1 -> {
                ScannedHistoryFragment.newInstance(ScannedHistoryFragment.ResultListType.ALL_RESULT)
            }

            2 -> {
                ScannedHistoryFragment.newInstance(ScannedHistoryFragment.ResultListType.FAVOURITE_RESULT)
            }

            else -> {
                QrScannerFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }
}