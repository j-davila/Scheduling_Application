package model;

/**
 * Customer class that creates Division objects. These objects are used in the List class and ModifyCustomer controller to construct objects from a result set.
 *
 * @author José L Dávila Montalvo
 */
public class Division {
    private int id;
    private String division;
    private int countryId;

    /**
     * Constructor for division objects. This is the main constructor.
     *
     * @param id Division id
     * @param division Name of division
     * @param countryId Id of country
     * */
    public Division(int id, String division, int countryId){
        this.id = id;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * Getter method for division id
     *
     * @return returns division id
     * */
    public int getId() {
        return id;
    }

    /**
     * Setter method for division id. Sets division id
     *
     * @param id division id
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter method for division name
     *
     * @return returns division name
     * */
    public String getDivision() {
        return division;
    }

    /**
     * Setter method for division name. Sets division name
     *
     * @param division division name
     * */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Getter method for country id
     *
     * @return returns country id
     * */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Setter method for country id. Sets country id
     *
     * @param countryId country id
     * */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Override method used to display a division's name in a combobox.
     *
     * @return Name of division
     * */
    @Override
    public String toString(){
        return(division);
    }
}
