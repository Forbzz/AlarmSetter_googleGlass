package models;

import java.io.Serializable;

/**
 * Created by Nicolas on 27/04/2016.
 */
public class alarmModel implements Serializable{
    private String name;
    public String getName(){
        return name;
    }

    private String platform;
    public String getPlatform(){
        return platform;
    }
    public void setPlatform(String platform){
        this.platform=platform;
    }
    private String image;
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image=image;
    }
}