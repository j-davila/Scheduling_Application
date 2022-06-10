package model;

/**
 * Contact class that creates contact objects. Contact objects are used in the List class and ModifyAppointments controller
 *
 * @author José L Dávila Montalvo
 */
public class Contact {
    private int contactId;
    private String contactName;
    private String contactEmail;

    /**
     * Constructor for contact objects. Object has same field as in the database.
     *
     * @param contactId Id of contact
     * @param contactName Name of contact
     * @param contactEmail Email of contact
     * */
    public Contact(int contactId, String contactName, String contactEmail){
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Getter method for contact id
     *
     * @return returns contact id
     * */
    public int getContactId() {
        return contactId;
    }

    /**
     * Setter method for contact id. Sets contact id
     *
     * @param contactId Contact id
     * */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Getter method for contact name
     *
     * @return returns contact name
     * */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter method for contact name. Sets contact name
     *
     * @param contactName Contact name
     * */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter method for contact email
     *
     * @return returns contact email
     * */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Setter method for contact email. Sets contact email
     *
     * @param contactEmail Contact email
     * */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Override method used to display a contact's name in a combobox.
     *
     * @return Name of contact
     * */
    @Override
    public String toString(){
        return(contactName);
    }
}
