package model;

/**
 * User class that creates user objects. These objects are used in the List class and ModifyAppointment controller to construct objects from a result set.
 *
 * @author José L Dávila Montalvo
 */
public class User {
    private int userId;
    private String userName;

    /**
     * Constructor for user objects. This is the main constructor.
     *
     * @param userId User id
     * @param userName Name of user
     * */
    public User(int userId, String userName){
        this.userId = userId;
        this.userName = userName;
    }

    /**
     * Getter method for user id
     *
     * @return returns user id
     * */
    public int getUserId(){
        return userId;
    }

    /**
     * Setter method for user id. Sets user id
     *
     * @param userId user id
     * */
    public void setUserId(int userId){
        this.userId = userId;
    }

    /**
     * Getter method for user name
     *
     * @return returns user name
     * */
    public String getUserName(){
        return userName;
    }

    /**
     * Setter method for user name. Sets user name
     *
     * @param userName user name
     * */
    public void setUserName(String userName){
        this.userName = userName;
    }

    /**
     * Override method used to display a user's name in a combobox.
     *
     * @return Name of user
     * */
    @Override
    public String toString(){
        return(userName);
    }
}
