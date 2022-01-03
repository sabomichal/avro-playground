package data

import com.github.avrokotlin.avro4k.Avro
import com.github.avrokotlin.avro4k.AvroDefault
import com.github.avrokotlin.avro4k.AvroName
import com.github.avrokotlin.avro4k.AvroNamespace
import kotlinx.serialization.Serializable

@AvroName("Student")
@AvroNamespace("com.students")
@Serializable
data class Student(
    val name: String,
    val age: Int,
    val marks: List<Int>,
    @AvroDefault(Avro.NULL)
    val comments: String?
)

