package model;

/**
 * Country class that creates country objects. Country objects are used in the List class and ModifyCustomer controller
 *
 * @author José L Dávila Montalvo
 */
public class Country {
    private int id;
    private String name;

    /**
     * Constructor for country objects.
     *
     * @param id Id of country
     * @param name Name of country
     * */
    public Country(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * Getter method for country id
     *
     * @return returns country id
     * */
    public int getId() {
        return id;
    }

    /**
     * Setter method for country id. Sets country id
     *
     * @param id Country id
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter method for country name
     *
     * @return returns country name
     * */
    public String getName() {
        return name;
    }

    /**
     * Setter method for country name. Sets country name
     *
     * @param name Country name
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Override method used to display a country's name in a combobox.
     *
     * @return Name of country
     * */
    @Override
    public String toString(){
        return(name);
    }
}
