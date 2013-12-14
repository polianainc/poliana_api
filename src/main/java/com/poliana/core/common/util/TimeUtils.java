package com.poliana.core.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author David Gilmore
 * @date 11/27/13
 */
public class TimeUtils {

    /**
     *
     * @param dateString
     * @return
     */
    public static int getTimestamp(String dateString) {
        if (dateString == null)
            return 0;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat();
            //TODO: not a robust solution, works for all encountered dates though
            if (dateString.length() <= 10) {
                formatter.applyPattern("yyyy-MM-dd");
            }
            else {
                formatter.applyPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
            }
            Date date = formatter.parse(dateString);
            long time = date.getTime();
            return (int) (time/1000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * @param congress
     * @return
     */
    public static int[] congressToYears(int congress) {
        int[] years = new int[2];
        //1st congress was 1789, they last two years
        years[0] = (congress*2) + 1787;
        years[1] = years[0] + 1;
        return years;
    }

    /**
     *
     * @param timestamp
     * @return
     */
    public static int nearestTermStart(int timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis((long)timestamp*1000);
        int year = cal.get(cal.YEAR);
        //All sessions begin on odd year, it this year isn't odd, roll back one
        if (year%2==0)
            year -= 1;
        //Set the last term start to January 1st at midnight
        //to ensure inclusion of all official start times
        cal.set(year,0,3,0,0,0);
        return (int)(cal.getTimeInMillis()/1000);
    }

    /**
     *
     * @return
     */
    public static int oneYear() {
        return 31556900;
    }

    /**
     *
     * @param timestamp
     * @return
     */
    public static int termBeginning(int timestamp) {
        int thisTerm = nearestTermStart(timestamp);
        return thisTerm-oneYear()*4;
    }

    /**
     *
     * @param timestamp
     * @return
     */
    public static int timestampToCongress(int timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis((long)timestamp*1000);
        int year = cal.get(cal.YEAR);
        return (year-1787)/2;
    }

    /**
     *
     * @param congress
     * @return
     */
    public static int[] yearTimestamps(int congress) {

        Calendar cal = Calendar.getInstance();

        //Get the corresponding years for a given congress
        int[] years = congressToYears(congress);

        //Set the begin timestamp to january 1 at 12am of the first year for this session
        cal.set(years[0],0,1,0,0,0);
        long begin = cal.getTimeInMillis();

        //Set the begin timestamp to December 31 at 11:59am of the second year of this session
        cal.set(years[1]-1,12,31,0,0,0);
        long end = cal.getTimeInMillis();

        int[] timestamps = new int[2];

        //Convert the milisecond timestamps to seconds
        timestamps[0] = (int)(begin/1000);
        timestamps[1] = (int)(end/1000);

        return timestamps;
    }
}
