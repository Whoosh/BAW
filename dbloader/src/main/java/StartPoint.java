import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

public class StartPoint {
    public static void main(String[] args) {
        new GenericGroovyApplicationContext("context/config.groovy");
    }
}
