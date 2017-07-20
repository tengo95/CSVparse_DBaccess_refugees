package org.codingnomads.refugees.controller;

import org.codingnomads.refugees.model.RefugeeByYearCountry;
import org.codingnomads.refugees.model.SQLPojo;
import org.codingnomads.refugees.model.WorldBankIndicators;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by tanerali on 18/07/2017.
 */
public class ConvertToDB {

    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    public Connection getConnection() {

        try{
            System.out.println("creating connection");
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connection = DriverManager.getConnection("jdbc:mysql://localhost/worldbank?" +
                    "user=root&password=<password>&useSSL=false");
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
                    .prepareStatement("insert into refugees_all " +
                            "(year, country, origin, refugees, asylum_seekers, " +
                            "returned_refugees, internally_displaced_persons, " +
                            "returned_internally_displaced_persons, stateless_persons, " +
                            "others_of_concern, total_population) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for (int i = 0; i< refugees.size(); i++) {
                // Parameters start with 1
                //preparedStatement.setInt(1, refugees.get(i).getId() );
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

                preparedStatement.executeUpdate();
                System.out.println("record £"+ i+ " inserted in refugees_all table");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("All done. You rock. Check your db for all the data.");
        return true;
    }






    public boolean writeToDBWorldBankIndicators(ArrayList<WorldBankIndicators> indicators) {

        try {

            connection = getConnection();

            System.out.println("Preapring statement...");
            // PreparedStatements can use variables and are more efficient
            preparedStatement = connection
                    .prepareStatement("insert into worldbank_indicators " +
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

                preparedStatement.executeUpdate();
                System.out.println("record £"+ i+ " inserted in worldbank_indicators");

                if (i==243) {
                    System.out.println("here");
                }
            }

        } catch (SQLException exc) {
            System.out.println(exc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("All done. You rock. Check your db for all the data.");
        return true;
    }



    //takes the results from a query and puts them in an ArrayList of SQLPojos
    public ArrayList<SQLPojo> writeResultSet(ResultSet resultSet) throws SQLException {

        ArrayList<SQLPojo> queryPojos = new ArrayList<>();

        // loop through the result set to get each next line
        while (resultSet.next()) {

            //assign the values from the corresponding columns to variables
            int year = resultSet.getInt("year");
            int totalRefugees = resultSet.getInt("population");

            //instantiate an object of SQLPojo
            SQLPojo pojo = new SQLPojo();

            //write the values from the variables to the pojo
            pojo.setYear(year);
            pojo.setTotalRefugees(totalRefugees);

            queryPojos.add(pojo);

        }
        return queryPojos;
    }

    public ArrayList<SQLPojo> readAdataBase (String query) {

        ArrayList<SQLPojo> resultPojos = null;
        try {
            connection = getConnection();

            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();
            //
            resultSet = statement.executeQuery(query);

            //
            resultPojos = writeResultSet(resultSet);

        } catch (Exception e) {
            e.getMessage();
        }

        return resultPojos;
    }


}
