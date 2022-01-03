import data.Student;

import java.util.Arrays;

import static data.Serializer.serializeStudent;

public class Main {
    public static void main(String[] args) {
        Student student = new Student("Marius", 12, Arrays.asList(2, 6, 5), null);
        String text = serializeStudent(student);
        System.out.println(text);
    }
}
