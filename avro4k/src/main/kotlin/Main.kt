import com.github.avrokotlin.avro4k.Avro
import com.github.avrokotlin.avro4k.io.AvroEncodeFormat
import data.Student
import java.io.ByteArrayOutputStream

fun main() {
    val schema = Avro.default.schema(Student.serializer())
    println(schema.toString(true))
}

object Serializer {
    val studentSchema = Avro.default.schema(Student.serializer())

    @JvmStatic
    fun serialize(value:Student): String {
        val baos = ByteArrayOutputStream()
        Avro.default.openOutputStream(Student.serializer()) {
            encodeFormat = AvroEncodeFormat.Json
        }.to(baos).write(value).close()
        return String(baos.toByteArray())
    }
}