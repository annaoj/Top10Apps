package example.android.com.top10downloadingapps;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;


public class ParseApp {
    //variable to store data
    private String xmlData;
    //array to store multiple entries &
    // each entry of this array will have entries from Application class
    private ArrayList<Application> applications;


    public ArrayList<Application> getApplications() {
        return applications;
    }


    //generate a constructor

    public ParseApp(String xmlData) {
        this.xmlData = xmlData;
        applications = new ArrayList<Application>();

    }

    public boolean process() {
        boolean status = true;
        Application currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int evenType = xpp.getEventType();
            while (evenType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                switch (evenType) {
                    case XmlPullParser.START_TAG:
                        //Log.d("ParseApp","Starting tag for"+tagName);
                        if (tagName.equalsIgnoreCase("entry")) {
                            inEntry = true;
                            currentRecord = new Application();

                        }
                        break;

                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        // Log.d("ParseApp","Starting tag for"+tagName);
                        if (inEntry) {
                            if (tagName.equalsIgnoreCase("entry")) {
                                applications.add(currentRecord);
                                inEntry = false;
                            } else if (tagName.equalsIgnoreCase("name")) {
                                currentRecord.setName(textValue);
                            } else if (tagName.equalsIgnoreCase("artist")) {
                                currentRecord.setArtist(textValue);
                            } else if (tagName.equalsIgnoreCase("releaseDate")) {
                                currentRecord.setReleaseDate(textValue);
                            }
                        }
                        break;
                    default:
                        //nothing else

                }
                evenType = xpp.next();
            }

        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        for (Application app : applications) {
            Log.d("ParseApp", "*************");

        }
        return true;
    }
}

