package com.poliana.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author David Gilmore
 * @date 11/27/13
 */
public class TimeUtils {

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

    public static int[] congressToYears(int congress) {
        int[] years = new int[2];
        //1st congress was 1789, they last two years
        years[0] = (congress*2) + 1787;
        years[1] = years[0] + 1;
        return years;
    }

    public static int nearestTermStart(int timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis((long)timestamp*1000);
        int year = cal.get(cal.YEAR);
        //All sessions begin on odd year, it this year isn't odd, roll back one
        if (year%2==0)
            year -= 1;
        //Set the last term start to January 1st at midnight
        //to ensure inclusion of all official start times
        cal.set(year,1,3,0,0,0);
        return (int)cal.getTimeInMillis()/1000;
    }

    public static int oneYear() {
        return 31556900;
    }

    public static int termBeginning(int timestamp) {
        int thisTerm = nearestTermStart(timestamp);
        return thisTerm-oneYear()*4;
    }

    public static int[] termRanges(int congress) {
        int[] years = congressToYears(congress);
        Calendar cal = Calendar.getInstance();
        cal.set(years[0],1,3,0,0,0);
        int begin = nearestTermStart((int)cal.getTimeInMillis()/1000);
        int[] timestamps = new int[2];
        timestamps[0] = begin;
        cal.set(years[1]+1,1,3,0,0,0);
        timestamps[1] = (int)cal.getTimeInMillis()/1000;
        return timestamps;
    }
}
