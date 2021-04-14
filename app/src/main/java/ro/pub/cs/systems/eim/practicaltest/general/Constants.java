package ro.pub.cs.systems.eim.practicaltest.general;

public interface Constants {
    String LEFT_CLICKS = "leftClicks";
    String RIGHT_CLICKS = "rightClicks";
    String SUM = "sum";
    String PROCESSING_THREAD_TAG = "[Processing Thread]";
    int SECOND_ACTIVITY_REQUEST_CODE = 1;
    int THRESHOLD = 5;
    int SERVICE_STOPPED = 0;
    int SERVICE_STARTED = 1;

    final public static String[] actionTypes = {
            "ro.pub.cs.systems.eim.practicaltest01.arithmeticmean",
            "ro.pub.cs.systems.eim.practicaltest01.geometricmean"
    };

    final public static String BROADCAST_RECEIVER_EXTRA = "message";
    final public static String BROADCAST_RECEIVER_TAG = "[Message]";

}
