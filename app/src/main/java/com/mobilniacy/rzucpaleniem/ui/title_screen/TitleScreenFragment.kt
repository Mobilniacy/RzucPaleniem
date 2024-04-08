package com.mobilniacy.rzucpaleniem.ui.title_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobilniacy.rzucpaleniem.R
import com.mobilniacy.rzucpaleniem.databinding.FragmentTitleScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        // PO CO TO JEST?
        val textView: TextView = binding.textTitleScreen
        titleScreenViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Pokazanie dolnego van bar, ponieważ jest ukrywany przez login/register
        val navView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.visibility = View.VISIBLE

        // Dla paska narzędzi (Toolbar)
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}