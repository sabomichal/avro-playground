import com.github.avrokotlin.avro4k.Avro
import kotlinx.serialization.Serializable

fun main() {
    val schema = Avro.default.schema(Student.serializer())
    println(schema.toString(true))
}

@Serializable
data class Student(val name: Name, val age: Age)

@Serializable
data class Name(val value:String)
@Serializable
data class Age(val value:Int)
