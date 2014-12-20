package logic.utils.strings;

public interface OnStringFinder {
    // TODO add perform
    default String firstBetween(Object src, String start, String end) {
        String source = src.toString();
        source = source.substring(source.indexOf(start) + start.length());
        source = source.substring(0, source.indexOf(end));
        return source;
    }

    default String firstBetween(String s, String start, String end) {
        final String src = s.substring(s.indexOf(start) + start.length());
        return src.substring(0, src.indexOf(end));
    }

    default String lastBetween(String s, String start, String end) {
        final String src = s.substring(0, s.lastIndexOf(end));
        return src.substring(src.lastIndexOf(start) + 1);
    }
}
