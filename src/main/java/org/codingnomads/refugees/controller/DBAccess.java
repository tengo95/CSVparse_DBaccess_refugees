package org.codingnomads.refugees.controller;

import org.codingnomads.refugees.model.RefugeeByYearCountry;
import org.codingnomads.refugees.model.WorldBankIndicators;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by tanerali on 18/07/2017.
 */
public class DBAccess {

    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    //method for creating a connection to DB
    public Connection getConnection() {

        try{
            System.out.println("creating connection");

            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");

            // Setup the connection with the DB
            connection = DriverManager.getConnection("jdbc:mysql://localhost/?" +
                    "user=root&password=CodingNomadsFoEva!&useSSL=false");

            System.out.println("connection succeeded");

        } catch (ClassNotFoundException cnf){
            cnf.printStackTrace();
        } catch (SQLException se){
            se.printStackTrace();
        }
        return connection;
    }


    public boolean writeToDBRefugeeByYearCountry(ArrayList<RefugeeByYearCountry> refugees ) {

        try
        {
            connection = getConnection();

            System.out.println("Preapring statement...");
            // PreparedStatements can use variables and are more efficient
            preparedStatement = connection
                    .prepareStatement("insert into immigrants.refugees_all " +
                            "(year, country, origin, refugees, asylum_seekers, " +
                            "returned_refugees, internally_displaced_persons, " +
                            "returned_internally_displaced_persons, stateless_persons, " +
                            "others_of_concern, total_population) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for (int i = 0; i< refugees.size(); i++) {

                // Parameters start with 1
                preparedStatement.setInt(1, refugees.get(i).getYear() );
                preparedStatement.setString(2, refugees.get(i).getCountryOfResidence() );
                preparedStatement.setString(3, refugees.get(i).getOrigin() );
                preparedStatement.setDouble(4, refugees.get(i).getRefugees() );
                preparedStatement.setDouble(5, refugees.get(i).getAsylumSeekers() );
                preparedStatement.setDouble(6, refugees.get(i).getReturnedRefugees() );
                preparedStatement.setDouble(7, refugees.get(i).getInternallyDisplacedPersons() );
                preparedStatement.setDouble(8, refugees.get(i).getReturnedIDPs() );
                preparedStatement.setDouble(9, refugees.get(i).getStatelessPersons() );
                preparedStatement.setDouble(10, refugees.get(i).getOthersOfConcern() );
                preparedStatement.setDouble(11, refugees.get(i).getTotalPopulation() );

                //add preparedStatement to batch so they can be inserted together as a
                //batch instead of hitting the DB each time for a single insert
                preparedStatement.addBatch();

                //this is to avoid out of memory exceptions which can happen if
                //too many insert statements are put in a batch before executing the batch;
                //this makes sure that every 1000 insert statements get executed as a batch
                if(i % 1000 == 0) {
                    preparedStatement.executeBatch();
                }
            }

            //this executes any remaining insert statements in the last batch
            preparedStatement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("All done. Check your db for all the data.");
        return true;
    }


    //same comments as above
    public boolean writeToDBWorldBankIndicators(ArrayList<WorldBankIndicators> indicators) {

        try {

            connection = getConnection();

            System.out.println("Preapring statement...");
            // PreparedStatements can use variables and are more efficient
            preparedStatement = connection
                    .prepareStatement("insert into worldbank.worldbank_indicators " +
                            "(seriesName, seriesCode, countryName, countryCode, " +
                            "YR2000, YR2001, YR2002, YR2003, YR2004, YR2005, YR2006, YR2007, YR2008, " +
                            "YR2009, YR2010, YR2011, YR2012, YR2013, YR2014, YR2015) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for (int i = 0; i< indicators.size(); i++) {
                // Parameters start with 1
                //preparedStatement.setInt(1, indicators.get(i).getId() );
                preparedStatement.setString(1, indicators.get(i).getSeriesName() );
                preparedStatement.setString(2, indicators.get(i).getSeriesCode() );
                preparedStatement.setString(3, indicators.get(i).getCountryName() );
                preparedStatement.setString(4, indicators.get(i).getCountryCode() );
                preparedStatement.setDouble(5, indicators.get(i).getYR2000() );
                preparedStatement.setDouble(6, indicators.get(i).getYR2001() );
                preparedStatement.setDouble(7, indicators.get(i).getYR2002() );
                preparedStatement.setDouble(8, indicators.get(i).getYR2003() );
                preparedStatement.setDouble(9, indicators.get(i).getYR2004() );
                preparedStatement.setDouble(10, indicators.get(i).getYR2005() );
                preparedStatement.setDouble(11, indicators.get(i).getYR2006() );
                preparedStatement.setDouble(12, indicators.get(i).getYR2007() );
                preparedStatement.setDouble(13, indicators.get(i).getYR2008() );
                preparedStatement.setDouble(14, indicators.get(i).getYR2009() );
                preparedStatement.setDouble(15, indicators.get(i).getYR2010() );
                preparedStatement.setDouble(16, indicators.get(i).getYR2011() );
                preparedStatement.setDouble(17, indicators.get(i).getYR2012() );
                preparedStatement.setDouble(18, indicators.get(i).getYR2013() );
                preparedStatement.setDouble(19, indicators.get(i).getYR2014() );
                preparedStatement.setDouble(20, indicators.get(i).getYR2015() );

                preparedStatement.addBatch();

                if (i % 1000 == 0) {
                    preparedStatement.executeBatch();
                }
            }

            preparedStatement.executeBatch();

        } catch (SQLException exc) {
            System.out.println(exc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("All done. Check your db for all the data.");
        return true;
    }


    public ResultSet readAdataBase (String query) {

        try {

            connection = getConnection();

            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();

            //ResultSet receives the results from the query
            resultSet = statement.executeQuery(query);

            //this gets the metadata from the resultSset, in this case,
            //the number of columns
            ResultSetMetaData rsmd;
            rsmd = resultSet.getMetaData();
            int numColumns = rsmd.getColumnCount();

            //print out column names
            for (int i = 1; i <= numColumns; i++) {

                if (i > 1) System.out.print("   ");
                System.out.print(rsmd.getColumnName(i));
            }
            System.out.println();

            //moves forward one row from current position; goes through each
            //row in the resultSet
            while (resultSet.next() ) {

                //print out value in each field
                for (int i = 1; i <= numColumns; i++) {

                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.getMessage();
        }

        return resultSet;
    }


}
