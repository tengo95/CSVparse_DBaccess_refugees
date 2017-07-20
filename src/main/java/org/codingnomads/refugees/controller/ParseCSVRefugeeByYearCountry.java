package org.codingnomads.refugees.controller;

/**
 * This class is designed to parse the refugees_all_years.csv file form the UN. Thia file can be found
 * here: https://data.world/nrippner/refugee-host-nations
 */
import org.codingnomads.refugees.model.RefugeeByYearCountry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParseCSVRefugeeByYearCountry {


    public static ArrayList<RefugeeByYearCountry> parseFile () {
        System.out.println("parseFile() called...");


        //create arraylist to hold RefugeesByYearCountry objects that are created in while loop below
        ArrayList<RefugeeByYearCountry> refugees = new ArrayList();


        //location of csv file
        // TODO: this should be a command line argument for flexibility
        String csvFile = "/Users/tanerali/Desktop/CodingNomads/databases/refugee_data/refugees_all_years.csv";


        //instantiate "line" which will hold each line read from the csv file
        String line = "";


        //use try-with-resources
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            //discard first line of file which is headers/column names
            line = br.readLine();

            //this while loop assigns the next line of the csv file to the variable
            //"line" and while it is not null it continues looping
            while ( (line = br.readLine()) != null) {
                //create empty pojo of RefugeeByYearCountry to populate
                //from csv file - each line in file is a new object
                RefugeeByYearCountry ref = new RefugeeByYearCountry();

                //use the "split" method to split the line on each comma
                //the results will automatically be stored in an array called data
                String[] data = line.split(",");

                //begin setting RefugeeByYearCountry (ref) instance vars
                //using the data read from the csv file, which is now in the data array
                ref.setId(Integer.parseInt(data[0]) );
                ref.setYear(Integer.parseInt(data[1]) );
                ref.setCountryOfResidence(data[2] );
                ref.setOrigin(data[3]);

                //nested try catch to catch rows that have nothing but empty values after
                //index 3. the split method deos not store the empty values if all are empty
                //after a given index
                try {
                    ref.setRefugees(data[4].equalsIgnoreCase("") ? 0 : Double.parseDouble(data[4]));
                    ref.setAsylumSeekers(data[5].equalsIgnoreCase("") ? 0 : Double.parseDouble(data[5]));
                    ref.setReturnedRefugees(data[6].equalsIgnoreCase("") ? 0 : Double.parseDouble(data[6]));
                    ref.setReturnedIDPs(data[7].equalsIgnoreCase("") ? 0 : Double.parseDouble(data[7]));
                    ref.setInternallyDisplacedPersons(data[8].equalsIgnoreCase("") ? 0 : Double.parseDouble(data[8]));
                    ref.setStatelessPersons(data[9].equalsIgnoreCase("") ? 0 : Double.parseDouble(data[9]));
                    ref.setOthersOfConcern(data[10].equalsIgnoreCase("") ? 0 : Double.parseDouble(data[10]));
                    ref.setTotalPopulation(data[11].equalsIgnoreCase("") ? 0 : Double.parseDouble(data[11]));
                } catch (IndexOutOfBoundsException exc) {
                    //if exception is caught then set all remaining values to default of zero
                    ref.setRefugees(0);
                    ref.setAsylumSeekers(0);
                    ref.setReturnedRefugees(0);
                    ref.setReturnedIDPs(0);
                    ref.setInternallyDisplacedPersons(0);
                    ref.setStatelessPersons(0);
                    ref.setOthersOfConcern(0);
                    ref.setTotalPopulation(0);
                }

                //add RefugeeByYearCountry object created in this loop to the
                //ArrayList called "refugees"
                refugees.add(ref);

                //loop through until all lines of csv file have been processed
            }
            //catch IOException
        } catch (IOException e) {
            e.printStackTrace();
        }
        return refugees;
    }

}