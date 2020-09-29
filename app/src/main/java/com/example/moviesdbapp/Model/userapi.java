package com.example.moviesdbapp.Model;

public class userapi
{
    public String username;

    public static userapi instance;
    public static userapi getInstance()
    {
        if (instance==null)
            instance=new userapi();
        return instance;

    }
    public userapi()
    {

    }
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
