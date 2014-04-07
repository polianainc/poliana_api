package com.poliana.core.time;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * This class contains functions for commonly needed date transformations in the Poliana API
 * @author David Gilmore
 * @date 11/27/13
 */
@Service
public class TimeService {

    public static final String CURRENT_CONGRESS = "113";
    public static final String CURRENT_YEAR = "2014";

    /**
     *
     * @param dateString
     * @return
     */
    public long getTimestamp(String dateString) {

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

            return time/1000L;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Return a CongressYears object for a given congress. It contains the years of a congressional cycle.
     * @param congress
     * @return
     */
    public CongressYears congressToYears(int congress) {

        CongressYears years = new CongressYears();

        //1st congress was 1789, they last two years
        years.setYearOne((congress*2) + 1787);

        //Simply set the second year to an increment of the first
        years.setYearTwo(years.getYearOne() + 1);

        return years;
    }

    /**
     *
     * @param timestamp
     * @return
     */
    public static long nearestTermStart(long timestamp) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp*1000);

        long year = cal.get(cal.YEAR);

        //All sessions begin on odd year, it this year isn't odd, roll back one
        if (year%2==0)
            year -= 1;

        //Set the last term start to January 1st at midnight
        //to ensure inclusion of all official start times
        cal.set((int)year,0,3,0,0,0);
        return (cal.getTimeInMillis()/1000);
    }

    /**
     * Return the number of seconds in a year.
     * Leap years could cause some problems with this method and methods that it depends on.
     * @return
     */

    public long getOneYear() {
        return 31556900;
    }

    /**
     * Get the timestamp of the beginning of the term for which the given timestamp corresponds.
     * @param timestamp
     * @return
     */
    public long termBeginning(long timestamp) {

        long thisTerm = nearestTermStart(timestamp);
        return thisTerm - getOneYear()*4;
    }

    /**
     * Get the congress for which a given timestamp corresponds.
     * @param timestamp
     * @return
     */
    public int getCongressByTimestamp(long timestamp) {

        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(timestamp*1000);
        int year = cal.get(cal.YEAR);

        return (year-1787)/2;
    }

    /**
     *
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public Integer[] getCongressionalCyclesByTimeRange(long beginTimestamp, long endTimestamp) {

        HashSet<Integer> cycleSet = new HashSet<>();

        Calendar begin = Calendar.getInstance();
        begin.setTimeInMillis(beginTimestamp*1000);

        int currentYear = begin.get(begin.YEAR);

        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(endTimestamp*1000);

        int endYear = end.get(end.YEAR);


        while(currentYear <= endYear) {
            int cycle = getYearToCongress(currentYear);
            cycleSet.add(cycle);
            currentYear += 1;
        }

        Object[] cycleObjects = cycleSet.toArray();
        Integer[] cycles = Arrays.copyOf(cycleObjects, cycleObjects.length, Integer[].class);
        return cycles;
    }

    /**
     * Return timestamps for January 1st of the first year of a the congressional cycle and December 31st of the second.
     * @param congress
     * @return
     */
    public CongressTimestamps getCongressTimestamps(int congress) {

        Calendar cal = Calendar.getInstance();

        //Get the corresponding years for a given congress
        CongressYears years = congressToYears(congress);

        //Set the begin timestamp to january 1 at 12am of the first year for this session
        cal.set(years.getYearOne(), 0, 1, 0, 0, 0);
        long begin = cal.getTimeInMillis();

        //Set the begin timestamp to December 31 at 11:59am of the second year of this session
        cal.set(years.getYearTwo(), 11, 31, 0, 0, 0);
        long end = cal.getTimeInMillis();

        CongressTimestamps timestamps = new CongressTimestamps();

        //Convert the milisecond timestamps to seconds
        timestamps.setBegin(begin/1000);
        timestamps.setEnd(end/1000);

        return timestamps;
    }

    /**
     * Given a year, return the corresponding congressional cycle
     * @param year
     * @return
     */
    public int getYearToCongress(int year) {

        return (year - 1787) / 2;
    }

    public String getNumberSuffix(int n) {
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }

    /**
     * Simply return the unix timestamp of now as a string
     * @return
     */
    public String getTimeNow() {

        Calendar cal = Calendar.getInstance();
        long time = cal.getTimeInMillis()/1000;

        return "" + time;
    }
}
