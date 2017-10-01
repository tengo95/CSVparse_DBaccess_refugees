package org.codingnomads.refugees.controller;

import org.codingnomads.refugees.model.RefugeeByYearCountry;
import org.codingnomads.refugees.model.WorldBankIndicators;

import java.sql.ResultSet;
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

        DBAccess db = new DBAccess();

        if (populate) {
            System.out.println("Starting app...");
            ArrayList<RefugeeByYearCountry> refugees = parseFile();
            System.out.println("Parsing complplete...");
            System.out.println("Calling DBAccess() ");
            boolean success = db.writeToDBRefugeeByYearCountry(refugees);

            System.out.println("Starting app...");
            ArrayList<WorldBankIndicators> indicators = ParseCSVWorldBankIndicators.parseFile();
            System.out.println("Parsing complplete...");
            System.out.println("Calling DBAccess() ");
            boolean success2 = db.writeToDBWorldBankIndicators(indicators);

        } else {
            System.out.println("Which query do you want yo?");
        }


        for (;;) {
            System.out.println("Which query do you want yo?\n1, 2 or 3\nexit to terminate");

            switch (input.nextInt()) {
                case 1:
                    System.out.println("worldbank or refugees yall");
                    resultSet = db.readAdataBase(getQuery(input.next()) );
                    break;
                case 2:
                    resultSet = db.readAdataBase(getQuery2(str[0]));
                    break;
                case 3:
                    resultSet = db.readAdataBase(getQuery3(str[0]) );
                    System.out.println("Year Refugees");
            }
            if (input.nextLine() == "exit" )
                break;
        }

    }


    public static String getQuery (String s) {
        System.out.println("Executing query 1...");

        if (s.equalsIgnoreCase("worldbank") ) {
            return "select * from worldbank.worldbank_indicators;";
        } else if (s.equalsIgnoreCase("refugees") ) {
            return "select * from immigrants.refugees_all;";
        }
        return "worldbank or refugees yall";
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
