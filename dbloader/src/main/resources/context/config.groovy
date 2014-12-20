package context

import logic.utils.htmlunit.SuppressWebClientWarningBeansContextListener
import logic.query.impl.InactiveJSCountOnPageResolverImpl
import logic.query.impl.WebPageImpl
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

beans {

    xmlns context: "http://www.springframework.org/schema/context"
    context.'component-scan'('base-package': "logic")
    context.'component-scan'('base-package': "java_configs")

    webPageService(WebPageImpl) { jsLoadSleepMs = 500 }

    webClientWarningSuppresser(SuppressWebClientWarningBeansContextListener) { enabled = true }

    threadPoolExecuter(ThreadPoolTaskExecutor) {
        corePoolSize = 30
        keepAliveSeconds = 86400
    }

    activeJSCountOnPageResolverService(InactiveJSCountOnPageResolverImpl) {
        rules = new HashMap<String, Integer>() {
            {
                put("(http://pp.163.com/pp/#p=)[-0-9]+&.+=[-0-9]+&.+=[-0-9]+&page=[-0-9]+", 1)
                put("http://pp.163.com/.+/pp/[0-9]+.html", 0)
            }
        }
    }
}
