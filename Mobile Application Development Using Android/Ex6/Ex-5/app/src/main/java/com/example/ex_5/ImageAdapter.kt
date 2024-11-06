import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.ex_5.DisplayActivity
import com.example.ex_5.MainActivity
import com.example.ex_5.R

class ImageAdapter(
    private val context: Context,
    private val imageUris: MutableList<Uri>
) : ArrayAdapter<Uri>(context, R.layout.list_item_image, imageUris) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_image, parent, false)

        // Set the image using Glide or any other library
        val imageView: ImageView = view.findViewById(R.id.imageView)
        Glide.with(context).load(imageUris[position]).into(imageView)

        imageView.setOnClickListener {
            val intent = Intent(context, DisplayActivity::class.java)
            intent.putExtra("imageUri", imageUris[position]) // Pass the URI of the selected image
            context.startActivity(intent)
        }

        // Handle the dropdown for delete
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
        val updateButton: Button = view.findViewById(R.id.updateButton)
        deleteButton.setOnClickListener {
            // Delete the image from the list and notify the adapter
            removeAt(position)
        }
        updateButton.setOnClickListener {
            (context as MainActivity).startImageUpdate(position)
        }


        return view
    }

    fun removeAt(position: Int) {
        imageUris.removeAt(position)
        notifyDataSetChanged()
    }
}
