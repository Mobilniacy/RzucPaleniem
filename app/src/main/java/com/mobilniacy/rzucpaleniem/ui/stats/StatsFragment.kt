package com.mobilniacy.rzucpaleniem.ui.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.mobilniacy.rzucpaleniem.R
import com.mobilniacy.rzucpaleniem.databinding.FragmentStatsBinding

class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Obsługa spinnera do wyboru dni tygodni lub miesiecy
        val spinner: Spinner = root.findViewById(R.id.lista_rozwijana)
        // Tworzymy ArrayAdapter z wykorzystaniem spinnera oraz listy day_week_month.
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.day_week_month,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Określamy jakiego layoutu użyć gdy lista się pojawia
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Przydzielamy adapter do spinnera
            spinner.adapter = adapter
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}