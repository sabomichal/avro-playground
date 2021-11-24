package data

import com.github.avrokotlin.avro4k.Avro
import com.github.avrokotlin.avro4k.io.AvroDecodeFormat
import com.github.avrokotlin.avro4k.io.AvroEncodeFormat
import kotlinx.serialization.KSerializer
import org.apache.avro.Schema
import java.io.ByteArrayOutputStream

object Serializer {
    private val studentSerializer: KSerializer<Student> = Student.serializer()
    private val studentSchema: Schema = Avro.default.schema(studentSerializer)

    @JvmStatic
    fun serializeStudent(value:Student):String = serialize(value, studentSerializer)

    @JvmStatic
    fun deserializeStudent(value:String):Student = deserialize(value, studentSerializer, studentSchema)

    @JvmStatic
    private fun <T> serialize(value: T, serializer: KSerializer<T>): String {
        val buffer = ByteArrayOutputStream()
        Avro.default.openOutputStream(serializer) {
            encodeFormat = AvroEncodeFormat.Json
        }.to(buffer).write(value).close()

        return String(buffer.toByteArray())
    }

    @JvmStatic
    private fun <T> deserialize(value:String, serializer: KSerializer<T>, schema: Schema):T {
        return Avro.default.openInputStream(serializer) {
            decodeFormat = AvroDecodeFormat.Json(schema)
        }.from(value.toByteArray()).nextOrThrow()

    }
}