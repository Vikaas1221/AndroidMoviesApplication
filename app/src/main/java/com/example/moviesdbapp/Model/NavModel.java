package com.example.moviesdbapp.Model;

public class NavModel
{
    String nav_item_name;



    int image;
    public NavModel(String nav_item_name,int image)
    {
        this.nav_item_name=nav_item_name;
        this.image=image;

    }
    public int getImage() {
        return image;
    }
    public String getNav_item_name()
    {
        return nav_item_name;
    }

    public void setNav_item_name(String nav_item_name)
    {
        this.nav_item_name = nav_item_name;
    }
}
