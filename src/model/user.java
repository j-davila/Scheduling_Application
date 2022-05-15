package model;

public class user {

    private int userId;
    private String userName;
    private String password;

    public user(int userId, String userName, String password){
        this.password = password;
        this.userId = userId;
        this.userName = userName;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
