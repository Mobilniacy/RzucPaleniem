import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.mobilniacy.rzucpaleniem.R

class ProductListAdapter(context: Context, private val resource: Int, private val productList: List<Product>) :
    ArrayAdapter<Product>(context, resource, productList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(resource, parent, false)
        }

        val currentProduct = productList[position]

        // Ustawienie danych produktu w widoku
        val productNameTextView: TextView = listItemView!!.findViewById(R.id.productNameTextView)
        val productPriceTextView: TextView = listItemView.findViewById(R.id.productPriceTextView)
        val productImageView: ImageView = listItemView.findViewById(R.id.productImageView)

        productNameTextView.text = currentProduct.name
        productPriceTextView.text = currentProduct.price
        productImageView.setImageResource(currentProduct.imageResId)

        return listItemView
    }
}