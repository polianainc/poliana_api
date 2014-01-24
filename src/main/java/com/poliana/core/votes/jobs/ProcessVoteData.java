package com.poliana.core.votes.jobs;

import com.mongodb.*;
import com.poliana.core.legislators.Legislator;
import com.poliana.core.legislators.LegislatorService;
import com.poliana.core.time.TimeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author David Gilmore
 * @date 1/21/14
 */
@Component
public class ProcessVoteData {

    private TimeService timeService;

    private DB mongoDb;
    private LegislatorService legislatorService;

    private static final Logger logger = Logger.getLogger(ProcessVoteData.class);


    public ProcessVoteData() {

        this.timeService = new TimeService();
    }

    /**
     * Scheduled job that cleans vote data nightly at 12am. The cleaning process involves the following:
     *
     * Process Voter information:
     */
    @Scheduled(cron = "0 0 12 ? * ?")
    public void voteJob() {


    }

    public void runPythonScraper() {


    }

    /**
     *      Each List of voters (Yea, Nay, Not Voting, Present) need to have bioguide_id fields added as well
     *      as first_name and last_name fields if they are not already present.
     *
     *      Some of the votes are recorded as Aye, No, Not Voting, Present - these need to be updated to Yea, Nay
     *      to maintain consistent POJO mappings
     */
    public void processVoteData() {

        DBCollection coll = mongoDb.getCollection("votes");

        DBCursor cursor = coll.find();
        cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);

        while ( cursor.hasNext() ) {

            DBObject obj = cursor.next();

            logger.info("Processing vote " + obj.get("vote_id"));

            Date date = processDate(obj);

            processUpdatedAt(obj);
            processRecordModified(obj);

            DBObject voters = processVoters((DBObject) obj.get("votes"), date);

            obj.removeField("votes");
            obj.put("votes", voters);

            DBObject q = new BasicDBObject("_id", obj.get("_id"));

            coll.update(q, obj);
        }
    }

    /**
     * Sometimes vote lists are Aye, and No, account for/rename those instances and then process individual votes
     * @param voters
     * @param date
     */
    private DBObject processVoters(DBObject voters, Date date) {

        BasicDBList yeaList = (BasicDBList) voters.get("Yea");
        BasicDBList nayList = (BasicDBList) voters.get("Nay");
        BasicDBList notVotingList = (BasicDBList) voters.get("Not Voting");
        BasicDBList presentList = (BasicDBList) voters.get("Present");

        if (yeaList == null)
            yeaList = (BasicDBList) voters.get("Aye");

        yeaList = processVotes(yeaList, date);

        if (nayList == null)
            nayList = (BasicDBList) voters.get("No");

        nayList = processVotes(nayList, date);


        notVotingList = processVotes(notVotingList, date);
        presentList= processVotes(presentList, date);


        voters = new BasicDBObject();

        voters.put("Yea", yeaList);
        voters.put("Nay", nayList);
        voters.put("Not Voting", notVotingList);
        voters.put("Present", presentList);

        return voters;
    }

    /**
     * Insert bioguide_id, first_name and last_name using Redis when said fields aren't present
     * @param votes
     */
    private BasicDBList processVotes(BasicDBList votes, Date date) {

        if (votes != null) {

            BasicDBObject[] votesArray;
            BasicDBList newVotes = new BasicDBList();

            try {

                votesArray = votes.toArray(new BasicDBObject[0]);

                for(BasicDBObject voter : votesArray) {

                    voter = processVoter(voter, date);
                    newVotes.add(voter);
                }
            }
            catch (Exception e) {}

            return newVotes;

        }

        return null;
    }

    /**
     * Add a bioguide_id field to all vote objects if it is not already present
     * @param voter
     * @return
     */
    private BasicDBObject processVoter(BasicDBObject voter, Date date) {

        long timestamp = date.getTime() / 1000;

        String id = (String) voter.get("id");

        String bioguideId = (String) voter.get("bioguide_id");
        String thomasId = (String) voter.get("thomas_id");
        String firstName = (String) voter.get("first_name");
        String lastName = (String) voter.get("last_name");

        Legislator legislator = null;

        if (id != null) {
            try {
                legislator = legislatorService.getCachedLegislatorByIdTimestamp(id, timestamp);
            }
            catch (NullPointerException e) {}
        }
        else if (thomasId != null) {
            try {
                legislator = legislatorService.getCachedLegislatorByIdTimestamp(thomasId, timestamp);
            }
            catch (NullPointerException e) {}
        }

        if (legislator != null) {

            if (bioguideId == null)
                voter.append("bioguide_id", legislator.getBioguideId());

            if (firstName == null)
                voter.append("first_name", legislator.getFirstName());

            if (lastName == null)
                voter.append("last_name", legislator.getLastName());
        }

        return voter;

    }

    /**
     * Update the date field with a Date object
     * @param obj
     * @return
     */
    private Date processDate(DBObject obj) {

        Date date;
        try {
            date = (Date) obj.get("date");
        }
        catch (Exception e) {

            date = new Date(timeService.getTimestamp((String)obj.get("date")) * 1000);
            obj.removeField("date");
            obj.put("date", date);
        }

        return date;
    }

    /**
     * Update the updated_at field with a Date object
     * @param obj
     * @return
     */
    private Date processUpdatedAt(DBObject obj) {

        Date date;
        try {
            date = (Date) obj.get("updated_at");
        }
        catch (Exception e) {

            date = new Date(timeService.getTimestamp((String)obj.get("updated_at")) * 1000);
            obj.removeField("updated_at");
            obj.put("updated_at", date);
        }

        return date;
    }

    /**
     * Update the record_modified field with a Date object
     * @param obj
     * @return
     */
    private Date processRecordModified(DBObject obj) {

        Date date;
        try {
            date = (Date) obj.get("record_modified");
        }
        catch (Exception e) {

            date = new Date(timeService.getTimestamp((String)obj.get("record_modified")) * 1000);
            obj.removeField("record_modified");
            obj.put("record_modified", date);
        }

        return date;
    }

    @Autowired
    public void setLegislatorService(LegislatorService legislatorService) {
        this.legislatorService = legislatorService;
    }

    @Autowired
    public void setMongoDb(DB mongoDb) {
        this.mongoDb = mongoDb;
    }
}
