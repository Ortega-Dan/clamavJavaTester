package org.danort;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import xyz.capybara.clamav.ClamavClient;
import xyz.capybara.clamav.commands.scan.result.ScanResult;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws FileNotFoundException {

        ClamavClient client = new ClamavClient("localhost");

        FileInputStream fis = new FileInputStream(args[0]);

        ScanResult result = client.scan(fis);

        System.out.println(" ****** " + result.getClass().getSimpleName() + " ****** ");

    }
}
