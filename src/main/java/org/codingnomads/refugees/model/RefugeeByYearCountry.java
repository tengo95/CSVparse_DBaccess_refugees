package org.codingnomads.refugees.model;

/**
 * Created by tanerali on 18/07/2017.
 */
public class RefugeeByYearCountry {

    int id;
    int year;
    String countryOfResidence;
    String origin;
    double refugees;
    double asylumSeekers;
    double returnedRefugees;
    double internallyDisplacedPersons;
    double returnedIDPs;
    double statelessPersons;
    double othersOfConcern;
    double totalPopulation;




    public RefugeeByYearCountry(int id, int year, String countryOfResidence, String origin, double refugees,
                                double asylumSeekers, double returnedRefugees, double internallyDisplacedPersons,
                                double returnedIDPs, double statelessPersons, double othersOfConcern,
                                double totalPopulation) {
        this.id = id;
        this.year = year;
        this.countryOfResidence = countryOfResidence;
        this.origin = origin;
        this.refugees = refugees;
        this.asylumSeekers = asylumSeekers;
        this.returnedRefugees = returnedRefugees;
        this.internallyDisplacedPersons = internallyDisplacedPersons;
        this.returnedIDPs = returnedIDPs;
        this.statelessPersons = statelessPersons;
        this.othersOfConcern = othersOfConcern;
        this.totalPopulation = totalPopulation;
    }

    public RefugeeByYearCountry() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getRefugees() {
        return refugees;
    }

    public void setRefugees(double refugees) {
        this.refugees = refugees;
    }

    public double getAsylumSeekers() {
        return asylumSeekers;
    }

    public void setAsylumSeekers(double asylumSeekers) {
        this.asylumSeekers = asylumSeekers;
    }

    public double getReturnedRefugees() {
        return returnedRefugees;
    }

    public void setReturnedRefugees(double returnedRefugees) {
        this.returnedRefugees = returnedRefugees;
    }

    public double getInternallyDisplacedPersons() {
        return internallyDisplacedPersons;
    }

    public void setInternallyDisplacedPersons(double internallyDisplacedPersons) {
        this.internallyDisplacedPersons = internallyDisplacedPersons;
    }

    public double getReturnedIDPs() {
        return returnedIDPs;
    }

    public void setReturnedIDPs(double returnedIDPs) {
        this.returnedIDPs = returnedIDPs;
    }

    public double getStatelessPersons() {
        return statelessPersons;
    }

    public void setStatelessPersons(double statelessPersons) {
        this.statelessPersons = statelessPersons;
    }

    public double getOthersOfConcern() {
        return othersOfConcern;
    }

    public void setOthersOfConcern(double othersOfConcern) {
        this.othersOfConcern = othersOfConcern;
    }

    public double getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(double totalPopulation) {
        this.totalPopulation = totalPopulation;
    }
}
