package com.unrulymedia.logback.rolling;

import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.util.List;

public class TimeBasedS3RollingPolicy extends TimeBasedRollingPolicy {

    private static final String BUCKET_NAME = "BUCKET_NAME";
    private Boolean bucketExists = false;

    private AWSCredentials awsCredentials = new BasicAWSCredentials("ACCESS_KEY", "SECRET_KEY");
    private AmazonS3 s3Client = new AmazonS3Client(awsCredentials);
    private List<Bucket> buckets = s3Client.listBuckets();

    @Override
    public void rollover() {
        super.rollover();

        for (Bucket bucket : buckets) {
            if (bucket.getName().equals(BUCKET_NAME)) {
                bucketExists = true;
            }
        }

        if (!bucketExists) {
            s3Client.createBucket(BUCKET_NAME);
        }

        String elapsedPeriodsFileName = super.getTimeBasedFileNamingAndTriggeringPolicy().getElapsedPeriodsFileName();

        System.out.println("file name is " + elapsedPeriodsFileName);

        File file = new File("PATH_TO_FILE_NAME" + elapsedPeriodsFileName);

        System.out.println("file im trying to put on s3 is" + file.getAbsolutePath());

        s3Client.putObject(new PutObjectRequest(BUCKET_NAME, elapsedPeriodsFileName, file));
    }
}
