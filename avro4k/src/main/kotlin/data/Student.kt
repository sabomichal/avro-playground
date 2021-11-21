package data

import com.github.avrokotlin.avro4k.AvroName
import com.github.avrokotlin.avro4k.AvroNamespace
import kotlinx.serialization.Serializable

@AvroName("Student")
@AvroNamespace("com.students")
@Serializable
data class Student(val name: String, val age: Int)