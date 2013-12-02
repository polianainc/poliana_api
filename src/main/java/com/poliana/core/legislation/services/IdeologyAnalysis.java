package com.poliana.core.legislation.services;

import com.poliana.core.common.db.MahoutUtil;
import com.poliana.core.common.util.TimeUtils;
import com.poliana.core.entities.entities.Legislator;
import com.poliana.core.entities.repositories.EntitiesMongoRepo;
import com.poliana.core.entities.services.LegislatorService;
import com.poliana.core.legislation.entities.Sponsorship;
import com.poliana.core.legislation.repositories.BillHadoopRepo;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorization;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDPlusPlusFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author David Gilmore
 * @date 11/28/13
 */
@Service
public class IdeologyAnalysis {

    @Autowired
    private MahoutUtil mahoutUtil;

    @Autowired
    private EntitiesMongoRepo entitiesMongoRepo;

    @Autowired
    private BillHadoopRepo billHadoopRepo;

    @Autowired
    private LegislatorService legislatorService;

    public Matrix getSponsorshipMatrix(String chamber, int congress) {
        List<Sponsorship> sponsorships = billHadoopRepo.getSponsorships(chamber,congress);

        int[] timeRanges = TimeUtils.termRanges(congress);
        Iterator<Legislator> legislators = entitiesMongoRepo.getLegislators(chamber, timeRanges[0],timeRanges[1]);
        HashMap<String,Integer> legislatorMap = getLegislatorIndexMap(legislators,chamber);


        return null;
    }

    private double[][] constructMatrix(List<Sponsorship> sponsorships, HashMap<String,Integer> legislatorMap) {
        int size = legislatorMap.size();
        double[][] matrix = new double[size][size];
//        int i =
        for (Sponsorship sponsorship: sponsorships) {
        }
        return null;
    }

    private HashMap<String,Integer> getLegislatorIndexMap(Iterator<Legislator> legislators, String chamber) {
        int mapSize;
        if (chamber.contains("h"))
            mapSize = 450;
        else
            mapSize = 115;

        HashMap<String,Integer> legislatorMap = new HashMap<>(mapSize);
        Integer index = new Integer(0);
        while (legislators.hasNext()) {
            Legislator legislator = legislators.next();
            legislatorMap.put(legislator.getId(),index.intValue());
            index++;
        }
        return legislatorMap;
    }

    private HashMap<String,RandomAccessSparseVector> getVectorMap(List<Sponsorship> sponsorships) {

        HashMap<String,RandomAccessSparseVector> vectorMap = new HashMap<>(sponsorships.size()/50);

        for (Sponsorship sponsorship : sponsorships) {
            if (vectorMap.containsKey(sponsorship.getSponsor())) {
                buildSponsorshipVector(
                        vectorMap.get(sponsorship.getSponsor()),
                        sponsorship
                );
            }
            else {
                buildSponsorshipVector(
                        new RandomAccessSparseVector(),
                        sponsorship
                );
            }
        }

        return vectorMap;
    }

    private RandomAccessSparseVector buildSponsorshipVector(
            RandomAccessSparseVector vector, Sponsorship sponsorship) {

        return vector;
    }

    public SVDRecommender svdRecommender() {
        DataModel dataModel = mahoutUtil.mongoModel("sponsorship_matrix");
        try {
            Factorizer factorizer = new SVDPlusPlusFactorizer(
                    dataModel, //Data model
                    2,         //Number of features
                    0.5,       //Learning rate
                    0.5,       //Prevent over-fitting
                    0.5,       //Random noise
                    2000,      //Number of interations
                    5.0        //learning rate decay
            );

            Factorization factorization = factorizer.factorize();

        } catch (TasteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
