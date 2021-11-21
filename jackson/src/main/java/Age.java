import lombok.Value;

@Value(staticConstructor = "of")
public class Age {
    int value;
}
