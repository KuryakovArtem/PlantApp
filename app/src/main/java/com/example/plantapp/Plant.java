package com.example.plantapp;

import android.widget.ImageView;

import com.google.type.DateTime;

public class Plant {
    private String plantName;
    private String plantPic;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Plant() {
    }

    public Plant(String plantName, String plantPic, String uuid) {
        this.plantName = plantName;
        this.plantPic = plantPic;
        this.uuid = uuid;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantPic() {
        return plantPic;
    }

    public void setPlantPic(String plantPic) {
        this.plantPic = plantPic;
    }
}
