package com.example.fredlee.practiceapp;

import java.util.Date;
import android.graphics.Bitmap;

public class ImageItem {
    private Bitmap image;
    private double rate;
    private Date date;

    public ImageItem(Bitmap image, double rate, Date date) {
        super();
        this.image = image;
        this.rate = rate;
        this.date = date;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }
}
