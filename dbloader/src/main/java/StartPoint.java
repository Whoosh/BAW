import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartPoint {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("context.xml");
    }
}
