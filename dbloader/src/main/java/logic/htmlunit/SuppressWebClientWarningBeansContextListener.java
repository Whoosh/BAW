package logic.htmlunit;

import com.gargoylesoftware.htmlunit.WebClient;
import logic.query.InactiveJSCountOnPageManager;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;

public class SuppressWebClientWarningBeansContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigurableBeanFactory configurableBeanFactory;
    private boolean enabled;
    private final Function<BeanDefinition, Class<?>> beanDefinitionInToClass;

    public SuppressWebClientWarningBeansContextListener() {
        beanDefinitionInToClass = beanDefinition -> {
            try {
                return Class.forName(beanDefinition.getBeanClassName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        };
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!enabled) return;
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Optional<Class<?>> first = Arrays.stream(beanDefinitionNames)
                .map(configurableBeanFactory::getMergedBeanDefinition)
                .map(beanDefinitionInToClass)
                .filter(this::checkFields)
                .findFirst();
        if (first.isPresent()) {
            LogFactory.releaseAll();
            java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
            java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
            LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        }
    }

    private boolean checkFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.getType().equals(InactiveJSCountOnPageManager.class))
                .findFirst()
                .isPresent();
    }
}
