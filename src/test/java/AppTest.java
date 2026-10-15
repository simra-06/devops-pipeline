import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    @Test
    void testMessage() {
        assertEquals(
            "End-to-End DevOps Pipeline Successful!",
            App.message()
        );
    }
}