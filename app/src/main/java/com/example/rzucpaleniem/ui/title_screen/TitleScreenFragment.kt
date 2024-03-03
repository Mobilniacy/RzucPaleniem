package com.example.rzucpaleniem.ui.title_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rzucpaleniem.databinding.FragmentTitleScreenBinding

class TitleScreenFragment : Fragment() {

    private var _binding: FragmentTitleScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val titleScreenViewModel =
            ViewModelProvider(this).get(TitleScreenViewModel::class.java)

        _binding = FragmentTitleScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textTitleScreen
        titleScreenViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}