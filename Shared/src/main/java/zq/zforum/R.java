package zq.zforum;

public class R<T> {
    public T info;
    public int status;

    public static int OK = 0;
    public static int ERROR = 1;

    public static <F> R<F> success(F info) {
        R<F> r = new R<>();
        r.status = OK;
        r.info = info;
        return r;
    }

    public static <F> R<F> error(F info) {
        R<F> r = new R<>();
        r.status = ERROR;
        r.info = info;
        return r;
    }

}
