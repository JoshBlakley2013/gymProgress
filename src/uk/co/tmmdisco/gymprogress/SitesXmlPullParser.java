package uk.co.tmmdisco.gymprogress;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;

public class SitesXmlPullParser {

	static final String KEY_GYMNAST = "gymnast";
	static final String KEY_NAME = "name";
	static final String KEY_AGE = "age";
	static final String KEY_LEVEL = "level";
	static final String KEY_DISCIPLINEIMG = "disciplineImg";
	static final String KEY_DISCIPLINELINK = "disciplineLink";


	public static List<Gymnast> getGymnastsFromFile(Context ctx) {

		// List of gymnasts that we will return
		List<Gymnast> Gymnasts;
		Gymnasts = new ArrayList<Gymnast>();

		// temp holder for current gymnast while parsing
		Gymnast curGymnast = null;
		// temp holder for current text value while parsing
		String curText = "";

		try {
			// Get our factory and PullParser
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xpp = factory.newPullParser();

			// Open up InputStream and Reader of our file.
			FileInputStream fis = ctx.openFileInput("gymnasts.xml");
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

			// point the parser to our file.
			xpp.setInput(reader);

			// get initial eventType
			int eventType = xpp.getEventType();

			// Loop through pull events until we reach END_DOCUMENT
			while (eventType != XmlPullParser.END_DOCUMENT) {
				// Get the current tag
				String tagname = xpp.getName();

				// React to different event types appropriately
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (tagname.equalsIgnoreCase(KEY_GYMNAST)) {
						// If we are starting a new <Gymnast> block we need
						//a new Gymnast object to represent it
						curGymnast = new Gymnast();
					}
					break;

				case XmlPullParser.TEXT:
					//grab the current text so we can use it in END_TAG event
					curText = xpp.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagname.equalsIgnoreCase(KEY_GYMNAST)) {
						// if </Gymnast> then we are done with current Gymnast
						// add it to the list.
						Gymnasts.add(curGymnast);
					} else if (tagname.equalsIgnoreCase(KEY_NAME)) {
						// if </name> use setName() on curGymnast
						curGymnast.setName(curText);
					} else if (tagname.equalsIgnoreCase(KEY_AGE)) {
						// if </age> use setLink() on curGymnast
						curGymnast.setAge(curText);
					}else if(tagname.equalsIgnoreCase(KEY_LEVEL)){
						//if </level> use setLink() on curGymnast
						curGymnast.setLevel(curText);
					} else if (tagname.equalsIgnoreCase(KEY_DISCIPLINEIMG)) {
						// if </discipline> use setAbout() on curGymnast
						curGymnast.setDisciplineImg(curText);
					} else if(tagname.equalsIgnoreCase(KEY_DISCIPLINELINK)){
						//if </disciplineLionk> use set Discipline Links() on curGymnast
						curGymnast.setDisciplineLink(curText);
					}
					break;

				default:
					break;
				}
				//move on to next iteration
				eventType = xpp.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return the populated list.
		return Gymnasts;
	}
}
