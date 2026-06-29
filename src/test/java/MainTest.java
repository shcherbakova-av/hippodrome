import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    
    @Disabled("testTimeWorkMain() Запускать вручную, чтобы не замедлять общий прогон тестов")
    @Test
    @Timeout(22)
    public void testTimeWorkMain() throws Exception {
        Main.main(new String[0]);
    }
}
