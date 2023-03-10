package com.sanger;

/**
 * A program to perform fast processing of FastQ files (in which no sequences
 * are split over multiple lines.)
 * 
 * @author     Oscar Driver
 * @version    %I%
 * @since      1.17
 *
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class FastQProcessor 
{
    /**
     * Takes a FASTQ file and a flag as input, printing the sequence count or
     * the nucleotide tide count to stdout. What is printed depends on the
     * flag.
     * 
     * @param    filepath    a string representing the FASTQ file's filepath  
     * @param    flag        "N" if the user wishes to print the nucleotide
     *                       count; "S" if the user wishes to print the
     *                       sequence count.
     * @since    1.0 
     */
    public static void main( String[] args )
    {
        String filepath = args[0];
        String flag = args[1];

        // Assuming FASTQ files to be processed are all formatted as .gzip
        GZIPInputStream gzipFastqInputStream = null;

        try {
            gzipFastqInputStream = new GZIPInputStream(new FileInputStream(filepath));

            BufferedReader gzipFastqInputLines = null;
                try {
                    gzipFastqInputLines = new BufferedReader(new InputStreamReader(gzipFastqInputStream));

                    if (flag.toUpperCase().equals("N")) {
                        countNucleotides(gzipFastqInputLines);
                    } else if (flag.toUpperCase().equals("S")) {
                        countSequences(gzipFastqInputLines);
                    } else {
                        System.err.println("No valid flag specified.");
                        System.err.println("Valid flags are: 'n' and 'N' for nucleotides; 's' and 'S' for sequences.");
                        System.err.println("The flag specified was: " + flag);
                    }
                } catch (Exception streamReadersException) {
                    System.err.println("Error with one of the file stream's readers");
                    streamReadersException.printStackTrace();
                } finally {
                    if (gzipFastqInputLines != null) {
                        try {
                            gzipFastqInputLines.close();
                        } catch (Exception streamReadersClosureException) {
                            System.err.println("Error with the closure of one of the file stream's readers");
                            streamReadersClosureException.printStackTrace();
                        }
                    }
                }
        } catch (Exception streamException) {
            System.err.println("Error with one of the file's streams");
            streamException.printStackTrace();
        } finally {
            if (gzipFastqInputStream != null) {
                try {
                    gzipFastqInputStream.close();
                } catch (Exception streamClosureException) {
                    System.err.println("Error with closing one of the file's streams");
                    streamClosureException.printStackTrace();
                }
            }
        }
    }

    /**
     * Extract lines from an input stream, printing (to the console) the number
     * of nucleotides found. This corresponds to the total number of letters
     * found across all field 2 lines (each sequence has 4 fields).
     * 
     * @param    fastqInput    The stream reader to extract data from
     */
    private static void countNucleotides( BufferedReader fastqInput) throws IOException {
        int numberOfNucleotides = 0;
        
        // Line yet to be processed within the file
        String lineToBeProcessed;
        // Each sequence has four non-blank lines
        int sequenceLine = 0;

        while ((lineToBeProcessed = fastqInput.readLine()) != null) {
            if (sequenceLine == 4) {
              sequenceLine = 0;
            } else if (!(lineToBeProcessed.isBlank())) {
              sequenceLine++;

              // Where the number of nucleotides can be found
              if (sequenceLine == 2) {
                String[] sequenceArray = lineToBeProcessed.split("\\W+");

                numberOfNucleotides += sequenceArray.length;
              }  
            }
        }

        System.out.println("Number of nucleotides found in file: " + numberOfNucleotides);
    } 

    /**
     * Extract lines from an input stream, printing (to the console) the number
     * of sequences found. This will always correspond to the total number of 
     * non-blank lines divided by 4
     * 
     * @param    fastqInput    The stream reader to extract data from
     * @throws IOException
     */
    private static void countSequences( BufferedReader fastqInput) throws IOException {
        int nonBlankCount = 0;
        
        // Line yet to be processed within the file
        String lineToBeProcessed;

        while ((lineToBeProcessed = fastqInput.readLine()) != null) {
            if (lineToBeProcessed.isBlank()) {
              nonBlankCount++;
            }  
        }

        int numberOfSequences = 0;
        if (nonBlankCount > 0) {
            numberOfSequences = nonBlankCount / 4;
        }
        System.out.println("Number of sequences found in file: " + numberOfSequences);
    }
}
