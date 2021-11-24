import com.github.avrokotlin.avro4k.Avro
import com.github.avrokotlin.avro4k.io.AvroEncodeFormat
import data.Student
import java.io.ByteArrayOutputStream

fun main() {
    val schema = Avro.default.schema(Student.serializer())
    println(schema.toString(true))
}