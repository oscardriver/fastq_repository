package com.sanger;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.FileInputStream;
import java.util.zip.GZIPInputStream;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Verify the functions of FastQProcessor.
 * 
 * @author     Oscar Driver
 * @version    %I%
 * @since      1.17
 *
 */
public class FastQProcessorTest 
{
    // Output of methods is void - what is sent to the console must be tested
    ByteArrayOutputStream testStdOut;

    // Isolate result from fixed output, for testing purposes
    private int getNucleotideOrSequenceCount(ByteArrayOutputStream givenTestStdOut) {
        String[] givenTestStdOutArray = (givenTestStdOut.toString().trim()).split(" ");
        int nucleotideOrSequenceCount = Integer.parseInt(givenTestStdOutArray[givenTestStdOutArray.length - 1]);
        return nucleotideOrSequenceCount;
    }

    /**
     * Test sequence counting in file with only blank lines
     */
    @Test
    public void testCountSequencesBlankLines() throws IOException {
        BufferedReader testInput 
        = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream("./data/blank fastq file.txt.gz"))));

        testStdOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testStdOut));

        FastQProcessor.countSequences(testInput); 

        // Get the sequence count - its digits will be the last entry in the console output
        int sequenceNumber = getNucleotideOrSequenceCount(testStdOut);
        // Compare it to the expected sequence count: 0
        assertEquals(0, sequenceNumber);
    }

    /**
     * Test sequence counting in file with blank and non-blank lines
     */
    @Test
    public void testCountSequencesNonBlankLines() throws IOException {
        BufferedReader testInput 
        = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream("./data/non-blank fastq file.txt.gz"))));

        testStdOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testStdOut));

        FastQProcessor.countSequences(testInput); 

        // Get the sequence count - its digits will be the last entry in the console output
        int sequenceNumber = getNucleotideOrSequenceCount(testStdOut);
        // Compare it to the expected sequence count: 2
        assertEquals(2, sequenceNumber);
    }

    /**
     * Test nucleotide counting in file with only blank lines
     */ 
    @Test
    public void testCountNucleotidesBlankLines() throws IOException {
        BufferedReader testInput 
        = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream("./data/blank fastq file.txt.gz"))));

        testStdOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testStdOut));

        FastQProcessor.countNucleotides(testInput); 

        // Get the nucleotide count - its digits will be the last entry in the console output
        int nucleotideNumber = getNucleotideOrSequenceCount(testStdOut);
        // Compare it to the expected nucleotide count: 0
        assertEquals(0, nucleotideNumber);
    }

    /**
     * Test nucleotide counting in file with blank and non-blank lines
     */ 
    @Test
    public void testCountNucleotidesNonBlankLines() throws IOException {
        BufferedReader testInput 
        = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream("./data/non-blank fastq file.txt.gz"))));

        testStdOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testStdOut));

        FastQProcessor.countNucleotides(testInput); 

        // Get the nucleotide count - its digits will be the last entry in the console output
        int nucleotideNumber = getNucleotideOrSequenceCount(testStdOut);
        // Compare it to the expected nucleotide count: 28
        assertEquals(28, nucleotideNumber);
    }

}
