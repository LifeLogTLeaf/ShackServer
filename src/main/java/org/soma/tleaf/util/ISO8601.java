package org.soma.tleaf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Helper class for handling ISO 8601 strings of the following format:
 * "2008-03-01T13:00:00+01:00". It also supports parsing the "Z" timezone.
 */
public final class ISO8601 {
	
	final public static String FAR_FAR_AWAY = "5014-01-01T00:00:00Z";
	final public static String LONG_LONG_AGO = "1800-01-01T00:00:00Z";
	
    /** Transform Calendar to ISO 8601 string. */
    public static String fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /** Get current date and time formatted as ISO 8601 string. */
    public static String now() {
        return fromCalendar(GregorianCalendar.getInstance());
    }

    /** Transform ISO 8601 string to Calendar. 
     * @throws ParseException */
    public static Calendar toCalendar(final String iso8601string) throws ParseException {
        
    	Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601string.replace("Z", "+00:00");
        Date date;
        
        try {
            s = s.substring(0, 22) + s.substring(23);  // to get rid of the ":"
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(s);
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new ParseException("String is not in ISO8601 format", 0);
        }
        calendar.setTime(date);
        return calendar;
    }
    
    /** Compares Two ISO8601 String 
     * @throws ParseException */
    public static boolean isFirstEarlier ( final String first, final String second ) throws ParseException {
    	
    	Calendar firstCal; Calendar secondCal;
		try {
			firstCal = toCalendar( first );
	    	secondCal = toCalendar( second );
		} catch (ParseException e) {
			e.printStackTrace();
			throw new ParseException("String is not in ISO8601 format", 1);
		}
    	
    	// Calendar.compareTo returns int value indicating how much is different between
    	return ( firstCal.compareTo( secondCal ) < 0 );
    }
}
