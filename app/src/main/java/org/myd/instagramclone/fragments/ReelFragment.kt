package org.myd.instagramclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.myd.instagramclone.R
import org.myd.instagramclone.databinding.FragmentReelBinding

class ReelFragment : Fragment() {
    private lateinit var binding: FragmentReelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReelBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {

    }
}