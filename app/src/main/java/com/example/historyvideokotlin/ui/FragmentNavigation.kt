package com.example.historyvideokotlin.ui

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions

interface FragmentNavigation {
    fun clearBackStack()
    fun showDialogFragment(fragment: DialogFragment)
    fun pushFragment(fragment: Fragment, options: FragNavTransactionOptions)
    fun popFragment(depth: Int, options: FragNavTransactionOptions)
}