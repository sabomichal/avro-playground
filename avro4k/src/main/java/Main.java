import data.Student;

public class Main {
    public static void main(String[] args) {
        Student student = new Student("Marius", 12);
        String text = Serializer.serialize(student);
        System.out.println(text);
    }
}
