package logic.query.services;

public interface InactiveJSCountOnPageResolverService {

    default int getCount(String url) {
        return 0;
    }
}
