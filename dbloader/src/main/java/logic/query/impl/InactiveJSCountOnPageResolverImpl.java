package logic.query.impl;

import logic.query.services.InactiveJSCountOnPageResolverService;

import java.util.HashMap;

public class InactiveJSCountOnPageResolverImpl implements InactiveJSCountOnPageResolverService {

    private HashMap<String, Integer> rules;

    @Override
    public int getCount(String url) {
        return rules.get(rules.keySet()
                .stream()
                .filter(url::matches)
                .findFirst()
                .get());
    }

    public void setRules(HashMap<String, Integer> rules) {
        this.rules = rules;
    }
}