package com.mobilniacy.rzucpaleniem.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobilniacy.rzucpaleniem.MainActivity
import com.mobilniacy.rzucpaleniem.R
import com.mobilniacy.rzucpaleniem.databinding.FragmentSettingsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // PO CO TO JEST?
//        val textView: TextView = binding.textSettings
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageButton: ImageButton = (activity as MainActivity).findViewById(R.id.imageButtonRzuc)
        imageButton.setOnClickListener {
            openWebsite("https://jakrzucicpalenie.pl/")
        }
    }

    private fun openWebsite(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}