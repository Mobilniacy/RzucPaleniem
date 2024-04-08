package com.mobilniacy.rzucpaleniem.ui.loading_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mobilniacy.rzucpaleniem.R
import com.mobilniacy.rzucpaleniem.databinding.FragmentSelectionScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class SelectionScreenFragment : Fragment() {
    private var _binding: FragmentSelectionScreenBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val selectionScreenViewModel =
//            ViewModelProvider(this)[SelectionScreenViewModel::class.java]

        _binding = FragmentSelectionScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        // PO CO TO JEST?
//        val textView: TextView = binding.textViewSelectionTopText
//        selectionScreenViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            var loginBtn = requireView().findViewById<TextView>(R.id.textViewSelectionLogin)
            loginBtn.setOnClickListener() {
                requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_selectionScreen_to_loginScreen)
            }

            var registerBtn = requireView().findViewById<TextView>(R.id.textViewSelectionRegister)
            registerBtn.setOnClickListener() {
                requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_selectionScreen_to_registerScreen)
            }

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


    }