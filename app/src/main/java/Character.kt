import retrofit2.http.GET
import retrofit2.http.Query

data class Character(
    val id: Int? = null,
    val name: String,
    val image: String,
    val species: String,
    val type: String? = null,
    val gender: String? = null,
    val origin: Origin? = null,
    val location: Location? = null,
    val status: String? = null,
    val episode: Array<String>? = null,
    val url: String? = null,
    val created: String? = null
){
    fun getTypeRAndM(): Int{
        if (species == "Human"){
            return Image_type
        }else if(species == "Alien"){
            return Name_type
        }else{
            return Species_type
        }
    }
}
data class CharacterAPI( // итоговый класс в который потом получаем данные из запроса
    val info: Info? = null,
    val results: Array<Character>
)

data class Info(
    val count: Int? = null,
    val pages: Int? = null,
    val next: String? = null,
    val prev: String? = null
)

data class Origin(
    val name: String? = null,
    val url: String? = null
)

data class Location(
    val name: String? = null,
    val url: String? = null
)


interface RickAndMortyApiService{                // Интерфейс для API запроса
    @GET("/api/character/?")
    suspend fun getCharacters(
        @Query("page") page: Int
    ) : CharacterAPI
}

val Image_type = 1
val Name_type = 2
val Species_type = 3
