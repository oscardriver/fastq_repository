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

import java.io.InputStream;
import java.io.FileInputStream;
import java.util.zip.GZIPInputStream;

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

        InputStream fastqInputStream = null;
        try {
            fastqInputStream = new FileInputStream(filepath);

            // Assuming FASTQ files to be processed are all formatted as .gzip
            GZIPInputStream gzipFastqInputStream = null;
            try {
                gzipFastqInputStream = new GZIPInputStream(fastqInputStream);
                if (flag.toUpperCase() == "N") {
                  countNucleotides(gzipFastqInputStream);
                } else if (flag.toUpperCase() == "S") {
                  countSequences(gzipFastqInputStream);
                } else {
                    System.err.println("No valid flag specified.");
                    System.err.println("Valid flags are: 'n' and 'N' for nucleotides; 's' and 'S' for sequences.");
                    System.err.println("The flag specified was: " + flag);
                }

            } catch (Exception gzipOpeningError) {
              System.err.println("Error opening gzip stream");
              System.err.println(gzipOpeningError);
            } finally {
                if (gzipFastqInputStream != null) {
                    try {
                        gzipFastqInputStream.close();
                    } catch (Exception gzipClosureError) {
                        System.err.println("Error closing gzip stream");
                        System.err.println(gzipClosureError);
                    }
                }
            }

        } catch (Exception inputStreamOpeningError) {
            System.err.println("Error opening input regardless of whether it's zipped");
            System.err.println(inputStreamOpeningError);
        } finally {
            // Close all streams
            if (fastqInputStream != null) {
                try {
                    fastqInputStream.close();
                } catch (Exception inputStreamClosureError) {
                    System.err.println("Error closing input regardless of whether it's zipped");
                    System.err.println(inputStreamClosureError);
                }
            }
        }
    }

    /**
     * Extract data from an input stream, printing (to the console) the number
     * of nucleotides found. This corresponds to the total number of letters
     * found across all field 2 lines (each sequence has 4 fields).
     * 
     * @param    fastqInput    The stream to extract data from
     */
    private static void countNucleotides( GZIPInputStream fastqInput) {

    } 

    /**
     * Extract data from an input stream, printing (to the console) the number
     * of sequences found. This will always correspond to the total number of 
     * non-blank lines divided by 4
     * 
     * @param    fastqInput    The stream to extract data from
     */
    private static void countSequences( GZIPInputStream fastqInput) {

    }
}
