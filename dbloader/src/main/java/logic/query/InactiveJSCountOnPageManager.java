package logic.query;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

@Component
public class InactiveJSCountOnPageManager {

    @Resource(name = "js_count_rule")
    private HashMap<String, Integer> rules;

    public int getCount(String url) {
        return rules.get(rules.keySet()
                .stream()
                .filter(url::matches)
                .findFirst()
                .get());
    }
}