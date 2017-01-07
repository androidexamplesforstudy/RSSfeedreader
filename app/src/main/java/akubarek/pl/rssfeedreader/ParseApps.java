package akubarek.pl.rssfeedreader;

import java.util.ArrayList;

/**
 * Created by BloodyFire on 07.01.2017.
 */

public class ParseApps {
    private static final String TAG = "ParseApps";
    private ArrayList <FeedEntry> apps;

    public ParseApps() {
        this.apps = new ArrayList<>();
    }

    public ArrayList<FeedEntry> getApps() {
        return apps;
    }
}
