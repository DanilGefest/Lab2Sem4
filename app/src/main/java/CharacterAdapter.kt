import android.content.Context
import android.icu.text.Transliterator.Position
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab2sem4.R
import com.squareup.picasso.Picasso

class CharacterAdapter(private val context: Context, private val List: Array<Character>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {                 // при создании определяем какой это вьюхзолдер и передает в него те элементы которыек долженл
       if (viewType == Image_type){
           val view = LayoutInflater.from(context).inflate(R.layout.layout_1, parent, false)
       return ImageViewHolder(view)
       }
        if (viewType == Name_type){
            val view = LayoutInflater.from(context).inflate(R.layout.layout_2, parent, false)
            return NameViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_3, parent, false)
            return SpeciesViewHolder(view)
        }

    }

    override fun getItemViewType(position: Int): Int { //определяет тип холдера
        return List[position].getTypeRAndM()
    }

    override fun getItemCount(): Int {// определяет кол-во холдеров
         return List.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {  // заполняет холдер
        val data = List[position]
        if (holder.itemViewType == Image_type){
            (holder as ImageViewHolder).bind(data)
        }
        if (holder.itemViewType == Name_type){
            (holder as NameViewHolder).bind(data)
        }
        if (holder.itemViewType == Species_type){
            (holder as SpeciesViewHolder).bind(data)
        }
    }
}

class ImageViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val image = view.findViewById<ImageView>(R.id.imageView)
    fun bind(character: Character) {                                    //bind нужен для удобного заполеннеия view holder
        Picasso.get().load(character.image).into(image)
    }
}

class NameViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val name = view.findViewById<TextView>(R.id.textViewAlien)
    fun bind(character: Character) {
        name.text = character.name
    }
}

class SpeciesViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val species = view.findViewById<TextView>(R.id.textViewSpecies)
    fun bind(character: Character) {
        species.text = character.species
    }
}