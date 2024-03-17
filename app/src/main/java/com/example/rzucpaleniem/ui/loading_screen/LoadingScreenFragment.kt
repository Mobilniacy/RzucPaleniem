package com.example.rzucpaleniem.ui.loading_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rzucpaleniem.R
import com.example.rzucpaleniem.databinding.FragmentLoadingScreenBinding
import com.example.rzucpaleniem.ui.loading_screen.LoadingScreenViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: zaimplementować
class LoadingScreenFragment : Fragment() {
    private var _binding: FragmentLoadingScreenBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loadingScreenViewModel =
            ViewModelProvider(this)[LoadingScreenViewModel::class.java]

        _binding = FragmentLoadingScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // PO CO TO JEST?
        val textView: TextView = binding.textView4
        loadingScreenViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Ukrycie dolnego van bar
        val navView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.visibility = View.GONE

        // Dla paska narzędzi (Toolbar)
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.hide()
    }

    //TODO: Zaimplementować wczytywanie danych

}