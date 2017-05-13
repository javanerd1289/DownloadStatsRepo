package extremecode.downloadstatsmodel;

/**
 * Created by Junaid Hassan on 10-May-17.
 */

public class MyListModel {
    public String date;
    public String deviceName;
    public String appName;
    public String id;
    public String nowTime;

    public MyListModel(String id, String appName, String date, String deviceName, String nowTime) {
        this.id = id;
        this.nowTime = nowTime;
        this.appName = appName;
        this.date = date;
        this.deviceName = deviceName;
    }
}
