import com.github.avrokotlin.avro4k.Avro
import data.Serializer.deserializeStudent
import data.Serializer.serializeStudent
import data.Student
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert.assertEquals
import kotlin.test.assertEquals

class StudentTest {
    private val text = """{
        |   "name" : "Marius",
        |   "age" : 23,
        |   "marks" : [ 2, 3 ],
        |   "comments": null
        |}""".trimMargin()

    @Test
    fun serialization() {
        val student = Student("Marius", 23, listOf(2, 3), null)
        val jsonString = serializeStudent(student)
        assertEquals(text, jsonString, true)
    }

    @Test
    fun deserialization() {
        val student = Student("Marius", 23, listOf(2, 3), null)
        val actual = deserializeStudent(text)
        assertEquals(student, actual)
    }
}

class StudentSchemaTest {
    private val text = """
        {
          "type": "record",
          "name": "Student",
          "namespace": "com.students",
          "fields": [
            {
              "name": "name",
              "type": "string"
            },
            {
              "name": "age",
              "type": "int"
            },
            {
              "name": "marks",
              "type": {
                "type": "array",
                "items": "int"
              }
            },
            {
              "name": "comments",
              "type": [
                "null",
                "string"
              ],
              "default": null
            }
          ]
        }
    """.trimIndent()

    @Test
    fun schema() {
        val schema = Avro.default.schema(Student.serializer())
        val actual = schema.toString(true)
        println(actual)
        assertEquals(text, actual, true)
    }

}