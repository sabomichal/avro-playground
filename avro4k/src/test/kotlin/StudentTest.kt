import com.github.avrokotlin.avro4k.Avro
import com.github.avrokotlin.avro4k.io.AvroDecodeFormat
import com.github.avrokotlin.avro4k.io.AvroEncodeFormat
import data.Student
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import kotlin.test.assertEquals

class StudentTest {
    private val text = """{
    |  "name" : {
    |    "value" : "Marius"
    |  },
    |  "age" : {
    |    "value" : 23
    |  }
    |}""".trimMargin()

    @Test
    fun serialization() {
        val student = Student("Marius", 23)

        val baos = ByteArrayOutputStream()
        Avro.default.openOutputStream(Student.serializer()) {
            encodeFormat = AvroEncodeFormat.Json
        }.to(baos).write(student).close()
        val jsonString = String(baos.toByteArray())
        assertEquals(text, jsonString)
    }

    @Test
    fun deserialization() {
        val student = Student("Marius", 23)

        val schema = Avro.default.schema(Student.serializer())
        val actual = Avro.default.openInputStream(Student.serializer()) {
            decodeFormat = AvroDecodeFormat.Json(schema)
        }.from(text.toByteArray()).nextOrThrow()
        assertEquals(student, actual)
    }
}