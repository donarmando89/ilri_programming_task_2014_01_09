/*
 * @author George Ogalo: This program is written with help of Java Marine API 
 * which is a free software registered under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation
 * 
 */
package programming_task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.SentenceId;

/**
 * example application that takes a filename as command-line argument and prints
 * Position from received GGA sentences.
 *
 * @author ogalo
 */
public class Programming_task implements SentenceListener {

    private SentenceReader reader;

    /**
     * Creates a new instance of Programming_task
     *
     * @param f File from which to read Checksum data
     */
    public Programming_task(File file) throws IOException {
        // create sentence reader and provide input stream
        InputStream stream = new FileInputStream(file);
        reader = new SentenceReader(stream);
        // register self as a listener for GGA sentences
        reader.addSentenceListener(this, SentenceId.GGA);
        reader.start();
    }
    @Override
    public void readingPaused() {
        System.out.println("-- Paused --");
    }
    @Override
    public void readingStarted() {
        System.out.println("-- Started --");
    }
    @Override
    public void readingStopped() {
        System.out.println("-- Stopped --");
    }
    /**
     *
     * @param event
     */
    @Override
    public void sentenceRead(SentenceEvent event) {

        GGASentence s = (GGASentence) event.getSentence();
        System.out.println(s.getPosition());
    }
    /**
     * Main method takes one command-line argument, the name of the file to
     * read.
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("To Read from file run java Programming_task <your nmea_string_file_name>  on the terminal");
            System.exit(1);
        }
        try {
            new Programming_task(new File(args[0]));
            System.out.println("Running, press CTRL-C to stop..");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
