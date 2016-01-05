package huytranq.databasesecurity;

/**
 * Created by huytr on 04-01-2016.
 */
public abstract class Security {
    protected final static String key = "67837B6666C8143B";
    protected final static String initVector = "1BE488D7FE8D8558";

    public abstract String getName();
    public abstract String digest(String text);
}
