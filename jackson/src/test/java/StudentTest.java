import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import org.apache.avro.Schema;
import org.junit.jupiter.api.Test;

public class StudentTest {
    private static final String TEXT = "{\n" +
            "  \"name\": {\n" +
            "    \"value\": \"Marius\"\n" +
            "  },\n" +
            "  \"age\": {\n" +
            "    \"value\": 23\n" +
            "  }\n" +
            "}";

    private static AvroSchema schema() {
        Schema raw = new Schema.Parser().setValidate(true).parse("{\n" +
                "  \"type\" : \"record\",\n" +
                "  \"name\" : \"Student\",\n" +
                "  \"fields\" : [ {\n" +
                "    \"name\" : \"name\",\n" +
                "    \"type\" : {\n" +
                "      \"type\" : \"record\",\n" +
                "      \"name\" : \"Name\",\n" +
                "      \"fields\" : [ {\n" +
                "        \"name\" : \"value\",\n" +
                "        \"type\" : \"string\"\n" +
                "      } ]\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\" : \"age\",\n" +
                "    \"type\" : {\n" +
                "      \"type\" : \"record\",\n" +
                "      \"name\" : \"Age\",\n" +
                "      \"fields\" : [ {\n" +
                "        \"name\" : \"value\",\n" +
                "        \"type\" : \"int\"\n" +
                "      } ]\n" +
                "    }\n" +
                "  } ]\n" +
                "}");
        return new AvroSchema(raw);
    }

    class MyFormat extends AvroSchema {

        public MyFormat(Schema asch) {
            super(asch);
        }

        @Override
        public String getSchemaType() {
            return "JSON";
        }
    }

    @Test
    void serializeAvro() throws JsonProcessingException {
        Student student = Student.builder()
                .name(Name.of("Marius"))
                .age(Age.of(23))
                .build();
        AvroMapper mapper = new AvroMapper();
        byte[] avroData = mapper.writer(schema())
                .writeValueAsBytes(student);
        System.out.println(new String(avroData));
    }

    @Test
    void serializeJson() throws JsonProcessingException {
        Student student = Student.builder()
                .name(Name.of("Marius"))
                .age(Age.of(23))
                .build();
        ObjectMapper om = new ObjectMapper();
        String text = om.writer(schema()).writeValueAsString(student);
        System.out.println(text);
    }
}
