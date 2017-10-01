package org.codingnomads.refugees.controller;

import org.codingnomads.refugees.model.RefugeeByYearCountry;
import org.codingnomads.refugees.model.WorldBankIndicators;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tanerali on 19/07/2017.
 */

/**
 * This class is designed to parse the worldbank_indiactors.csv file from the UN. This file can be found
 * here: https://data.world/nrippner/refugee-host-nations
 */

public class ParseCSVWorldBankIndicators {


    public static ArrayList<WorldBankIndicators> parseFile () {
        System.out.println("parseFile() called...");

        //create arraylist to hold RefugeesByYearCountry objects that are created in while loop below
        ArrayList<WorldBankIndicators> indicators = new ArrayList();

        //location of csv file
        // TODO: this should be a command line argument for flexibility
        String csvFile = "/Users/tanerali/Desktop/CodingNomads/databases/refugee_data/worldbank_indicators.csv";

        //instantiate "line" which will hold each line read from the csv file
        String line = "";

        int count = 0;

        //use try-with-resources
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            //discard first line of file which is headers/column names
            line = br.readLine();

            //this while loop assigns the next line of the csv file to the variable
            //"line" and while it is not null it continues looping
            while ( (line = br.readLine()) != null) {

                //create empty WorldBankIndicators object to populate with data
                //from current row in csv file - each line in file is a new object
                WorldBankIndicators ind = new WorldBankIndicators();

                //use the "split" method to split the line on each comma
                //the results will automatically be stored in an array called data

//                if (line.contains("") )

                String[] data = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
                ArrayList<String> data2 = customSplitSpecific(line);


//                char temp;
//
//                for (int i = 0; i<data.length; i++) {
//                    if (data[i].contains(",") ) {
//
//                        data[i].replaceAll(",", "");
//                    }
//                }

                //begin setting WorldBankIndicators (ind) instance vars
                //using the data read from the csv file, which is now in the data array
                ind.setSeriesName(data2.get(0));
                ind.setSeriesCode(data2.get(1));
                ind.setCountryName(data2.get(2));
                ind.setCountryCode(data2.get(3));
                try {
                    ind.setYR2000(data[4].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[4]));
                    ind.setYR2001(data[5].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[5]));
                    ind.setYR2002(data[6].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[6]));
                    ind.setYR2003(data[7].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[7]));
                    ind.setYR2004(data[8].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[8]));
                    ind.setYR2005(data[9].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[9]));
                    ind.setYR2006(data[10].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[10]));
                    ind.setYR2007(data[11].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[11]));
                    ind.setYR2008(data[12].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[12]));
                    ind.setYR2009(data[13].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[13]));
                    ind.setYR2010(data[14].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[14]));
                    ind.setYR2011(data[15].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[15]));
                    ind.setYR2012(data[16].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[16]));
                    ind.setYR2013(data[17].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[17]));
                    ind.setYR2014(data[18].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[18]));
                    ind.setYR2015(data[19].equalsIgnoreCase("..") ? 0 : Double.parseDouble(data[19]));
                } catch (ArrayIndexOutOfBoundsException exc) {
                    ind.setYR2000(0);
                    ind.setYR2001(0);
                    ind.setYR2002(0);
                    ind.setYR2003(0);
                    ind.setYR2004(0);
                    ind.setYR2005(0);
                    ind.setYR2006(0);
                    ind.setYR2007(0);
                    ind.setYR2008(0);
                    ind.setYR2009(0);
                    ind.setYR2010(0);
                    ind.setYR2011(0);
                    ind.setYR2012(0);
                    ind.setYR2013(0);
                    ind.setYR2014(0);
                    ind.setYR2015(0);
                }


                //add WorldBankIndicators object created in this loop to the
                //ArrayList called "indicators"
                indicators.add(ind);

                //loop through until all lines of csv file have been processed
            }

        //catch IOException
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indicators;
    }


    //used for splitting the String values in columns 1-4: seriesName
    //seriesCode, countryName, countryCode
    public static ArrayList<String> customSplitSpecific(String s)
    {
        ArrayList<String> words = new ArrayList<String>();
        boolean notInsideComma = true;
        int start =0, end=0;
        for(int i=0; i<s.length()-1; i++)
        {
            if(s.charAt(i)==',' && notInsideComma)
            {
                words.add(s.substring(start,i));
                start = i+1;
            }
            else if(s.charAt(i)=='"')
                notInsideComma=!notInsideComma;
        }
        words.add(s.substring(start));
        return words;
    }
}
