package com.example.qrcodescanner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.qrcodescanner.scanned_history.ScannedHistoryFragment
import com.example.qrcodescanner.scanner.QrScannerFragment

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
                ScannedHistoryFragment.newInstance()
            }

            2 -> {
                ScannedHistoryFragment.newInstance()
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