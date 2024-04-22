package com.mobilniacy.rzucpaleniem.ui.title_screen

import Product
import ProductListAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobilniacy.rzucpaleniem.MainActivity
import com.mobilniacy.rzucpaleniem.R
import com.mobilniacy.rzucpaleniem.databinding.FragmentTitleScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar


class TitleScreenFragment : Fragment() {

    private var _binding: FragmentTitleScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var myButton: Button
    private lateinit var expandTextView: TextView
    private lateinit var productsListView: ListView
    private lateinit var productAdapter: ProductListAdapter

    private lateinit var plus_button: ImageButton
    private lateinit var counter: TextView
    private var count = 0
    private lateinit var oneMinuteText: TextView
    private var zmienionyProdukt: Boolean = false


    private val textList = listOf(
        "Palenie papierosów jest najczęstszą przyczyną raka płuc, który jest jedną z najbardziej śmiercionośnych form raka. " +
                "Unikanie palenia znacznie zmniejsza ryzyko zachorowania na tę chorobę.",
        "Dym papierosowy zawiera wiele toksycznych substancji, które podrażniają płuca i oskrzela, prowadząc do zapalenia oskrzeli, " +
                "przewlekłej obturacji dróg oddechowych (POChP) oraz astmy.",
        "Palenie papierosów zwiększa ryzyko chorób serca i naczyń krwionośnych, takich jak choroba wieńcowa, zawał serca, udar mózgu i miażdżyca.",
        "Nikotyna, substancja zawarta w papierosach, działa na mózg, wywołując uzależnienie fizyczne i psychiczne. Rzucenie palenia może spowodować objawy odstawienia, " +
                "takie jak drażliwość, lęk, trudności z koncentracją i silne pragnienie papierosów.",
        "Palenie w obecności osób niepalących może prowadzić do narażenia ich na dym bierzący, co zwiększa ryzyko zachorowania na choroby układu oddechowego i sercowo-naczyniowego")


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




        oneMinuteText= binding.textViewRandomMessage
        var tekstDoWyswietlenia = "Czy wiedziałeś że... \n" + textList.random()
        oneMinuteText.text = tekstDoWyswietlenia
        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        sleep(30000)
                        activity?.runOnUiThread() {

                            var tekstDoWyswietlenia = "Czy wiedziałeś że... \n" + textList.random()
                            oneMinuteText.text = tekstDoWyswietlenia

                        }
                    }
                } catch (e: InterruptedException) {
                }
            }
        }


        thread.start()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        myButton = requireView().findViewById(R.id.buttonxd)
//        myButton.setOnClickListener {
//            if (zmienionyProdukt == true){
//                updateLicznik()
//                Toast.makeText(context, "Button clicked!", Toast.LENGTH_SHORT).show()
//            }
//
//        }

        updateUserName()


        val myButton: Button = (activity as MainActivity).findViewById(R.id.buttonLogout)

        // Dodaj listener do przycisku
        myButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {

                try {
                    // Wylogowanie użytkownika
                    (activity as MainActivity).auth.signOut()
                } catch (e: Exception) {
                    //nie wylogowany
                }
                // Wprowadzenie opóźnienia na 3 sekundy
                delay(2000)
                requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                    .navigate(R.id.action_logout)
            }

        }

        // Initialize views using the binding object
        plus_button = binding.plusButton
        counter = binding.licznik

        // Set initial count
        counter.text = "0"


        // Set OnClickListener for the plusButton
        plus_button.setOnClickListener {
            incrementCounter()
        }
        plus_button.isClickable = false
        // Inicjalizacja widoków
        expandTextView = view.findViewById(R.id.expandTextView)
        productsListView = view.findViewById(R.id.productsListView)

        // Inicjalizacja adaptera i listy produktów
        val productList = generateProductList() // funkcja generująca listę produktów
        productAdapter = ProductListAdapter(requireContext(), R.layout.product_item_layout, productList)
        productsListView.adapter = productAdapter

        // Obsługa kliknięcia na belkę rozwijającą
        expandTextView.setOnClickListener {
            if (productsListView.visibility == View.VISIBLE) {
                productsListView.visibility = View.GONE
                counter.visibility = View.VISIBLE
                plus_button.visibility = View.VISIBLE
                binding.textView6.visibility = View.VISIBLE
                binding.imageView4.visibility = View.VISIBLE
//                binding.viewCounterBottom.visibility = View.VISIBLE
                binding.viewCounterTop.visibility = View.VISIBLE
                expandTextView.text = "Rozwiń aby wybrać produkt"
            } else {
                productsListView.visibility = View.VISIBLE
                counter.visibility = View.GONE
                plus_button.visibility = View.GONE
                binding.textView6.visibility = View.GONE
                binding.imageView4.visibility = View.GONE
//                binding.viewCounterBottom.visibility = View.GONE
                binding.viewCounterTop.visibility = View.GONE

                expandTextView.text = "Wybierz produkt"
            }
        }

        // Obsługa kliknięcia na element listy
        productsListView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedProduct = productList[position]
                zmienionyProdukt=true
                expandTextView.text = selectedProduct.name
                updateLicznik()
                productsListView.visibility = View.GONE
                counter.visibility = View.VISIBLE
                plus_button.visibility = View.VISIBLE
                binding.textView6.visibility = View.VISIBLE
                binding.imageView4.visibility = View.VISIBLE
//                binding.viewCounterBottom.visibility = View.VISIBLE
                binding.viewCounterTop.visibility = View.VISIBLE
                plus_button.isClickable = true
            }
    }

    private fun updateUserName() {
        val currentUserUID = (activity as MainActivity).auth.currentUser?.uid
        var nowyText = "Jesteś zalogowany jako: "
        binding.textView2.text = nowyText
        (activity as MainActivity).db.collection("users").document(currentUserUID.toString())
            .get().addOnSuccessListener { document ->
            if (document != null) {
                if (document.exists()) {
                    // Jeśli dokument istnieje, pobierz zmienną
                    val data = document.data
                    val zmienna = data?.get("login").toString()
                    Log.d("TAG", "Zmienna: $zmienna")
                    nowyText += zmienna
                    binding.textView2.text = nowyText

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

    private fun incrementCounter() {
        val nowyStan = counter.text.toString().toInt()+1
        counter.text = nowyStan.toString()
        updateFirestore()
    }

    private fun updateLicznik() {
        // Pobranie zmiennej z Firestore
        val currentUserUID = (activity as MainActivity).auth.currentUser?.uid
        val selectedProduct = binding.expandTextView.text.toString()

        // Rozdzielenie daty na osobne zmienne - rok, miesiąc, dzień
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1 // Miesiące są indeksowane od 1
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        val docRef = (activity as MainActivity).db.collection("users").document(currentUserUID.toString()).collection("products")
            .document(selectedProduct).collection("stats")
            .document(year.toString()).collection(month.toString())
            .document(day.toString())

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    if (document.exists()) {
                        // Jeśli dokument istnieje, pobierz zmienną
                        val data = document.data
                        val zmienna = data?.get("smoked").toString()
                        Log.d("TAG", "Zmienna: $zmienna")

                        if (zmienna.toInt() != counter.text.toString().toInt()){
                            Log.d("TAG", "Zmieniam counter")
                            counter.text = zmienna
                        }

                    } else {
                        Log.d("TAG", "Dokument nie istnieje")
                        val sevenDays: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                        var dayName: String = "null"

                        when (sevenDays) {
                            Calendar.SUNDAY -> {dayName = "ND"}
                            Calendar.MONDAY -> {dayName = "PN"}
                            Calendar.TUESDAY -> {dayName = "WT"}
                            Calendar.WEDNESDAY -> {dayName = "ŚR"}
                            Calendar.THURSDAY -> {dayName = "CZ"}
                            Calendar.FRIDAY -> {dayName = "PT"}
                            Calendar.SATURDAY -> {dayName = "SO"}
                        }
                        val dane = hashMapOf(
                            "name" to dayName,

                            "smoked" to counter.text.toString().toInt(),
                        )
                        (activity as MainActivity).db.collection("users")
                            .document(currentUserUID.toString())
                            .collection("products")
                            .document(selectedProduct.toString())
                            .collection("stats")
                            .document(year.toString())
                            .collection(month.toString())
                            .document(day.toString())
                            .set(dane)
                            .addOnSuccessListener {
                                Log.d("Firestore", "Document successfully written!")
                            }
                            .addOnFailureListener { e ->
                                Log.w("Firestore", "Error writing document", e)
                            }


                        counter.text = "0"
                    }
                } else {
                    Log.d("TAG", "Błąd pobierania dokumentu")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Błąd pobierania dokumentu: $exception")
            }
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
    private fun updateFirestore(){

        val currentUserUID = (activity as MainActivity).auth.currentUser?.uid
        val selectedProduct = binding.expandTextView.text.toString()

        // Rozdzielenie daty na osobne zmienne - rok, miesiąc, dzień
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1 // Miesiące są indeksowane od 1
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val sevenDays: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        var dayName: String = "null"

        when (sevenDays) {
            Calendar.SUNDAY -> {dayName = "ND"}
            Calendar.MONDAY -> {dayName = "PN"}
            Calendar.TUESDAY -> {dayName = "WT"}
            Calendar.WEDNESDAY -> {dayName = "ŚR"}
            Calendar.THURSDAY -> {dayName = "CZ"}
            Calendar.FRIDAY -> {dayName = "PT"}
            Calendar.SATURDAY -> {dayName = "SO"}
        }

//        Toast.makeText(
//            context,
//            "Updatuje firestore",
//            Toast.LENGTH_SHORT,
//        ).show()


        val dane = hashMapOf(
            "name" to dayName,

            "smoked" to counter.text.toString().toInt(),
        )

        // Add a new document with a generated ID
        (activity as MainActivity).db.collection("users")
            .document(currentUserUID.toString())
            .collection("products")
            .document(selectedProduct.toString())
            .collection("stats")
            .document(year.toString())
            .collection(month.toString())
            .document(day.toString())
            .set(dane)
            .addOnSuccessListener {
                Log.d("Firestore", "Document successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error writing document", e)
            }
    }

    private fun generateProductList(): List<Product> {
        val productList = mutableListOf<Product>()
        productList.add(Product("CAMEL", "14.99", R.drawable.camel))
        productList.add(Product("L&M LINK BLUE", "15.99", R.drawable.lm))
        productList.add(Product("MARLBORO GOLD", "19.99", R.drawable.marbolo))
        return productList
    }
}