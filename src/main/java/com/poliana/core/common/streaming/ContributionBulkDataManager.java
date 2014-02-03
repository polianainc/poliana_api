package com.poliana.core.common.streaming;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @author graysoncarroll
 * @date 2/2/14
 */

@Component
public class ContributionBulkDataManager {

    private AmazonS3 s3Connection;

    public void createTestEnvironment() {

        String testBucket = "poliana.test";

        File testFile = new File( "./src/test/java/com/poliana/core/common/streaming/odata_meta.xml" );
        s3Connection.putObject(testBucket, "campaign_finance/crp/", testFile);
    }

    @Autowired
    public void setS3Connection(AmazonS3 s3Connection) {
        this.s3Connection = s3Connection;
    }
}
