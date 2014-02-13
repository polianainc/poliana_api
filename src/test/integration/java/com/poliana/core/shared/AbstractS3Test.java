package com.poliana.core.shared;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.poliana.core.common.streaming.CrpStreamingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author David Gilmore
 * @date 2/12/14
 */
public abstract class AbstractS3Test {

    protected AmazonS3 s3Connection;
    protected CrpStreamingService osSync;

    protected final String localTmp = "./test_tmp/";
    protected final String s3Key = "test_tmp";
    protected final String s3Bucket = "poliana.test";

    protected boolean needsSync() {

        File localTmpFile = new File(this.localTmp);

        return !localTmpFile.exists();
    }

    protected void syncS3TestData() {

        File localTmpFile = new File(this.localTmp);
        localTmpFile.mkdir();

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(s3Bucket)
                .withPrefix(s3Key);
        ObjectListing objectListing;

        do {

            objectListing = s3Connection.listObjects(listObjectsRequest);

            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {

                try {

                    S3Object object = s3Connection.getObject(
                            new GetObjectRequest(s3Bucket, objectSummary.getKey()));

                    InputStream in = object.getObjectContent();

                    FileOutputStream out = new FileOutputStream(objectSummary.getKey());

                    byte[] b = new byte[1024];

                    int count;
                    while ((count = in.read(b)) >= 0) {
                        out.write(b, 0, count);
                    }

                    out.flush();
                    out.close();
                    in.close();
                }
                catch (IOException e) {

                }
            }

            listObjectsRequest.setMarker(objectListing.getNextMarker());

        } while (objectListing.isTruncated());

    }

    @Autowired
    public void setS3Connection(AmazonS3 s3Connection) {
        this.s3Connection = s3Connection;
    }

    @Autowired
    public void setOsSync(CrpStreamingService osSync) {
        this.osSync = osSync;
    }
}
