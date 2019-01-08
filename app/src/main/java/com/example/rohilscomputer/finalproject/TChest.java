package com.example.rohilscomputer.finalproject;

import android.support.v7.app.AppCompatActivity;

public class TChest extends AppCompatActivity {
    private double TCLatitude;
    private double TCLongitude;
    private int TCExp;
    private int TCCurrency;

    public TChest(double TCLatitude,double TCLongitude, int TCExp, int TCCurrency)
    {
        this.TCLatitude = TCLatitude;
        this.TCLongitude = TCLongitude;
        this.TCExp = TCExp;
        this.TCCurrency = TCCurrency;
    }

    public double getTCLatitude() { return TCLatitude; }
    public void setTCLatitude(long TCLatitude) {this.TCLatitude = TCLatitude; }

    public double getTCLongitude() { return TCLongitude; }
    public void setTCLongitude(long TCLongitude) {this.TCLongitude = TCLongitude; }

    public int getTCExp() { return TCExp; }
    public void setTCExp(int TCExp) {this.TCExp = TCExp; }

    public int getTCCurrency() { return TCCurrency; }
    public void setTCCurrency(int TCCurrency) { this.TCCurrency = TCCurrency; }
}
