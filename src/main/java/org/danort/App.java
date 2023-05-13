package org.danort;

// import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;


import com.google.cloud.NoCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Notification;
import com.google.cloud.storage.NotificationInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.NotificationInfo.EventType;
import com.google.cloud.storage.NotificationInfo.PayloadFormat;

// import xyz.capybara.clamav.ClamavClient;
// import xyz.capybara.clamav.commands.scan.result.ScanResult;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException {

        // CLAMAV
        {
            // ClamavClient client = new ClamavClient("localhost");

            // FileInputStream fis = new FileInputStream(args[0]);

            // ScanResult result = client.scan(fis);

            // System.out.println(" ****** " + result.getClass().getSimpleName() + " ******
            // ");
        }

        // CLOUDSTORAGE
        {
            String fakeGcsExternalUrl = "http://localhost:4443";

            String projectId = "project-dev";
            String bucketName = "bucketNameNew";
            // String topicName = "projects/project-dev/topics/NEW_FILE_UPLOAD";

            Storage storage = StorageOptions.newBuilder()
                    .setHost(fakeGcsExternalUrl)
                    .setProjectId(projectId)
                    .setCredentials(NoCredentials.getInstance())
                    .build()
                    .getService();

            Bucket bucket = storage.get(bucketName);
            if (bucket == null) {
                // Create a new bucket
                storage.create(BucketInfo.newBuilder(bucketName).build());
            }

            // configuring the bucket to send notifications to a topic (this is not needed for fake-gcs-server. See readme)
            {
                // Create the notification
                // NotificationInfo notificationInfo = NotificationInfo.newBuilder(topicName)
                //         .setPayloadFormat(PayloadFormat.JSON_API_V1).setEventTypes(EventType.OBJECT_FINALIZE)
                //         .build();

                // Notification notification = storage.createNotification(bucketName, notificationInfo);

                // // just showing data
                // String topic = notification.getTopic();
                // System.out.println("Successfully created notification for topic " + topic);

            }

            WriteChannel channel = storage
                    .writer(BlobInfo.newBuilder(bucketName, "veryNewFile.txt").build());
            channel.write(ByteBuffer.wrap("line3\n".getBytes()));
            channel.write(ByteBuffer.wrap("line4\n".getBytes()));
            channel.close();

            Blob someFile2 = storage.get(bucketName, "veryNewFile.txt");
            String fileContent = new String(someFile2.getContent());

            System.out.println(fileContent);

            // image should run from here: docker run -d -p 4443:4443
            // fsouza/fake-gcs-server:latest -scheme http
            // check buckets with: curl http://localhost:4443/storage/v1/b
        }

    }
}
