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
        |   "marks" : [ 2, 3 ]
        |}""".trimMargin()

    @Test
    fun serialization() {
        val student = Student("Marius", 23, listOf(2, 3))
        val jsonString = serializeStudent(student)
        assertEquals(text, jsonString, true)
    }

    @Test
    fun deserialization() {
        val student = Student("Marius", 23, listOf(2, 3))

        val actual = deserializeStudent(text)
        assertEquals(student, actual)
    }
}