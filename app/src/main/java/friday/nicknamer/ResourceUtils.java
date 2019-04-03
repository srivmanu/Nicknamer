package friday.nicknamer;

/**
 * Created by srivmanu on 07-Oct-18.
 * All versions of this code are expected to be inspection only.
 * If you are planning to Submit this to ply store, God bless your Soul.
 */

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResourceUtils {
    public static Map<String, String> getHashMapResource(Context c, int hashMapResId) {
        Map<String, String> map = null;
        XmlResourceParser parser = c.getResources().getXml(hashMapResId);

        String key = null, value = null;

        try {
            int eventType = parser.getEventType();

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_DOCUMENT) {
                            Log.d("utils", "Start document");
                        } else if (eventType == XmlPullParser.START_TAG) {
                            if (parser.getName().equals("map")) {
                                boolean isLinked = parser.getAttributeBooleanValue(null, "linked", false);

                                map = isLinked ? new LinkedHashMap<String, String>() : new HashMap<String, String>();
                            } else if (parser.getName().equals("entry")) {
                        key = parser.getAttributeValue(null, "key");

                        if (null == key) {
                            parser.close();
                            return null;
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (parser.getName().equals("entry")) {
                        map.put(key, value);
                        key = null;
                        value = null;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (null != key) {
                        value = parser.getText();
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return map;
    }
}