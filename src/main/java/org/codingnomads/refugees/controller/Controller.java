package org.codingnomads.refugees.controller;

import org.codingnomads.refugees.model.RefugeeByYearCountry;
import org.codingnomads.refugees.model.SQLPojo;
import org.codingnomads.refugees.model.WorldBankIndicators;

import org.codingnomads.refugees.controller.ParseCSVRefugeeByYearCountry;
import org.codingnomads.refugees.controller.ParseCSVWorldBankIndicators;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.codingnomads.refugees.controller.ParseCSVRefugeeByYearCountry.parseFile;

/**
 * Created by tanerali on 19/07/2017.
 */
public class Controller {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        ResultSet resultSet;

        String[] str = {"Australia"};

        boolean populate = false;

        if (populate) {
            System.out.println("Starting app...");
            ArrayList<RefugeeByYearCountry> refugees = parseFile();
            System.out.println("Parsing complplete...");
            System.out.println("Calling ConvertToDB() ");
            ConvertToDB db = new ConvertToDB();
            boolean success = db.writeToDBRefugeeByYearCountry(refugees);

            System.out.println("Starting app...");
            ArrayList<WorldBankIndicators> indicators = ParseCSVWorldBankIndicators.parseFile();
            System.out.println("Parsing complplete...");
            System.out.println("Calling ConvertToDB() ");
            ConvertToDB db2 = new ConvertToDB();
            boolean success2 = db2.writeToDBWorldBankIndicators(indicators);

        } else {

            ConvertToDB db = new ConvertToDB();

            ArrayList<SQLPojo> resultPojos = null;

            System.out.println("Which query do you want yo?");
            switch (input.nextInt()) {
                case 1:
                    resultPojos = db.readAdataBase(getQuery() );
                    break;
                case 2:
                    resultPojos = db.readAdataBase(getQuery2(str[0]));
                    break;
                case 3:
                    resultPojos = db.readAdataBase(getQuery3(str[0]) );

            }


            System.out.println("Number of result-pojos "+ resultPojos.size());

            System.out.println("Year Refugees");

            for (int i = 0; i < resultPojos.size(); i++) {
                System.out.print(resultPojos.get(i).getYear() + " ");
                System.out.println(resultPojos.get(i).getTotalRefugees() );
            }



        }

    }


    public static String getQuery () {
        System.out.println("Executing query 1...");
        return "select * from worldbank.worldbank_indicators;";
    }

    public static String getQuery2 (String s) {
        System.out.println("Executing query 2...");
        return "SELECT * FROM worldbank.worldbank_indicators\n" +
                    "where countryName = '"+s +"' AND seriesName = 'GDP growth (annual %)';";
    }

    public static String getQuery3 (String s) {
        System.out.println("Executing query 3...");
        return "SELECT year, sum(total_population) as population " +
                "FROM immigrants.refugees_all " +
                "where country = '"+ s+ "' AND year >= 2000 " +
                "group by year;";
    }


}
