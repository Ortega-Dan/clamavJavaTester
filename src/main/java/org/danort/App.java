package org.danort;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.google.cloud.NoCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.StorageOptions;

// import xyz.capybara.clamav.ClamavClient;
// import xyz.capybara.clamav.commands.scan.result.ScanResult;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException {

        // ClamavClient client = new ClamavClient("localhost");

        // FileInputStream fis = new FileInputStream(args[0]);

        // ScanResult result = client.scan(fis);

        // System.out.println(" ****** " + result.getClass().getSimpleName() + " ******
        // ");

        String fakeGcsExternalUrl = "http://localhost:4443";

        var storageClient = StorageOptions.newBuilder()
                .setHost(fakeGcsExternalUrl)
                .setProjectId("test-project")
                .setCredentials(NoCredentials.getInstance())
                .build()
                .getService();

        Bucket bucket = storageClient.get("sample-bucket2");
        if (bucket == null) {
            // Create a new bucket
            storageClient.create(BucketInfo.newBuilder("sample-bucket2").build());
        }
        WriteChannel channel = storageClient.writer(BlobInfo.newBuilder("sample-bucket2", "some_file2.txt").build());
        channel.write(ByteBuffer.wrap("line3\n".getBytes()));
        channel.write(ByteBuffer.wrap("line4\n".getBytes()));
        channel.close();

        Blob someFile2 = storageClient.get("sample-bucket2", "some_file2.txt");
        String fileContent = new String(someFile2.getContent());

        System.out.println(fileContent);


        // image should run from here: docker run -d -p 4443:4443 fsouza/fake-gcs-server:latest -scheme http
        // check buckets with: curl http://localhost:4443/storage/v1/b
        
    }
}
