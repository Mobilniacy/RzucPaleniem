package com.mobilniacy.rzucpaleniem.ui.stats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.mobilniacy.rzucpaleniem.MainActivity
import com.mobilniacy.rzucpaleniem.R
import com.mobilniacy.rzucpaleniem.databinding.FragmentStatsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var barChart: BarChart
    private lateinit var barData: BarData
    private lateinit var barDataSet: BarDataSet
    private lateinit var barEntriesList: ArrayList<BarEntry>
    private var resultList = mutableListOf(0f,0f,0f,0f,0f,0f,0f)
    private var labels = mutableListOf("","","","","","","")
    private var currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Obsługa spinnera do wyboru dni tygodni lub miesięcy
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

        // Rejestrujemy obiekt nasłuchujący zmiany wyboru dla spinnera
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Pobieramy wybraną opcję z spinnera
                val wybranaOpcja = parent?.getItemAtPosition(position).toString()
                // Wykonujemy odpowiednie działania na podstawie wyboru użytkownika
                // Na przykład, możemy wyświetlić wybraną opcję w logach


                Log.d("Spinner", "Wybrana opcja: $wybranaOpcja")

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ta metoda zostanie wywołana, gdy nic nie będzie wybrane w spinnerze
            }
        }




        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aktualizujDane()
    }

    private fun aktualizujDane() {

        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1 // Miesiące są indeksowane od 1
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        binding.buttonNext.text = "${day+1}-${month}-${year}"
        binding.buttonPrevious.text = "${day-1}-${month}-${year}"

        CoroutineScope(Dispatchers.Main).launch {
            pobierzZDnia(-6)
            delay(300)
            pobierzZDnia(-5)
            delay(300)
            pobierzZDnia(-4)
            delay(300)
            pobierzZDnia(-3)
            delay(300)
            pobierzZDnia(-2)
            delay(300)
            pobierzZDnia(-1)
            delay(300)
            pobierzZDnia(0)
            delay(300)
//            Toast.makeText(context, resultList[6].toString(), Toast.LENGTH_SHORT).show()

            // Inicjalizacja wykresu
            barChart = BarChart(requireContext())
            barChart.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            // Dodanie wykresu do RelativeLayout
            val layoutWykresu = view?.findViewById<RelativeLayout>(R.id.layoutWykresu)
            layoutWykresu?.addView(barChart)

            // Inicjalizacja danych dla wykresu
            barEntriesList = ArrayList()


            // Przykładowe dane - możesz je zastąpić rzeczywistymi danymi!!!!!!!!!!!!!!
            val entries = resultList


//            val entries = listOf(5f, 10f, 8f, 12f, 6f, 9f, 7f)
            for (i in entries.indices) {
                barEntriesList.add(BarEntry(i.toFloat(), entries[i]))
            }

            // Inicjalizacja zestawu danych dla wykresu
            barDataSet = BarDataSet(barEntriesList, "Ilość wypalonych papierosów")

            // Konfiguracja wyglądu wykresu
            barDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

            // Inicjalizacja danych dla wykresu
            barData = BarData(barDataSet)

            // Ustawienie danych dla wykresu
            barChart.data = barData

            // Konfiguracja osi X

            val xAxis = barChart.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 1f

            // Konfiguracja osi Y
            val leftAxis = barChart.axisLeft
            leftAxis.setDrawGridLines(true)
            leftAxis.granularity = 1f
        }






//        Toast.makeText(context, resultList.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun pobierzZDnia(dzien: Int){
        // Pobranie zmiennej z Firestore
        val currentUserUID = (activity as MainActivity).auth.currentUser?.uid
        var suma: Int = 0

        // Rozdzielenie daty na osobne zmienne - rok, miesiąc, dzień
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1 // Miesiące są indeksowane od 1
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val desiredDay = day+dzien // Dzień, dla którego chcesz pobrać dane
        val docRef = (activity as MainActivity).db.collection("users").document(currentUserUID.toString()).collection("products")
            .document("CAMEL").collection("stats")
            .document(year.toString()).collection(month.toString())
            .document(desiredDay.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    if (document.exists()) {
                        // Jeśli dokument istnieje, pobierz zmienną
                        val data = document.data
                        val zmienna = data?.get("smoked").toString()
                        val zmienna2 = data?.get("name").toString()
                        Log.d("TAG", "Zmienna: $zmienna")
//                        Toast.makeText(context, zmienna, Toast.LENGTH_SHORT).show()
                        resultList[dzien+6] += zmienna.toFloat()
                        labels[dzien+6] = zmienna2

                    } else {
                        Log.d("TAG", "Dokument nie istnieje")
                    }
                } else {
                    Log.d("TAG", "Błąd pobierania dokumentu")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Błąd pobierania dokumentu: $exception")
            }

        val docRef2 = (activity as MainActivity).db.collection("users").document(currentUserUID.toString()).collection("products")
            .document("L&M LINK BLUE").collection("stats")
            .document(year.toString()).collection(month.toString())
            .document(desiredDay.toString())
        docRef2.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    if (document.exists()) {
                        // Jeśli dokument istnieje, pobierz zmienną
                        val data = document.data
                        val zmienna = data?.get("smoked").toString()
                        val zmienna2 = data?.get("name").toString()
                        Log.d("TAG", "Zmienna: $zmienna")
//                        Toast.makeText(context, zmienna, Toast.LENGTH_SHORT).show()
                        resultList[dzien+6] += zmienna.toFloat()
                        labels[dzien+6] = zmienna2

                    } else {
                        Log.d("TAG", "Dokument nie istnieje")
                    }
                } else {
                    Log.d("TAG", "Błąd pobierania dokumentu")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Błąd pobierania dokumentu: $exception")
            }
        val docRef3 = (activity as MainActivity).db.collection("users").document(currentUserUID.toString()).collection("products")
            .document("MARLBORO GOLD").collection("stats")
            .document(year.toString()).collection(month.toString())
            .document(desiredDay.toString())
        docRef3.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    if (document.exists()) {
                        // Jeśli dokument istnieje, pobierz zmienną
                        val data = document.data
                        val zmienna = data?.get("smoked").toString()
                        val zmienna2 = data?.get("name").toString()
                        Log.d("TAG", "Zmienna: $zmienna")
//                        Toast.makeText(context, zmienna, Toast.LENGTH_SHORT).show()
                        resultList[dzien+6] += zmienna.toFloat()
                        labels[dzien+6] = zmienna2

                    } else {
                        Log.d("TAG", "Dokument nie istnieje")
                    }
                } else {
                    Log.d("TAG", "Błąd pobierania dokumentu")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Błąd pobierania dokumentu: $exception")
            }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}