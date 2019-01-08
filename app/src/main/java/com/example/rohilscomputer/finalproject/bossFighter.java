package com.example.rohilscomputer.finalproject;

import android.support.v7.app.AppCompatActivity;

public class bossFighter extends AppCompatActivity {
    private String bossName;
    private int bossHealth;
    private int expRecieved;
    private double bossLatitude;
    private double bossLongitude;
    private int attackDamage;
    private long attackSpeed; //in milliseconds
    private int combatHealth;
    public bossFighter(String bossName, int bossHealth, int expRecieved, double bossLatitude, double bossLongitude)
    {
        this.bossName = bossName;
        this.bossHealth = bossHealth;
        this.expRecieved = expRecieved;
        this.bossLatitude = bossLatitude;
        this.bossLongitude = bossLongitude;
        this.attackDamage = 28;
        this.attackSpeed = 800;
        this.combatHealth = this.bossHealth;
    }
    public String getBossName() { return bossName; }
    public void setBossName(String name) {this.bossName = bossName; }

    public int getBossHealth() { return bossHealth; }
    public void setBossHealth(int bossHealth) {this.bossHealth = bossHealth; }

    public int getExpRecieved() { return expRecieved; }
    public void setExpRecieved(int expRecieved) {this.expRecieved = expRecieved; }


    public double getBossLatitude() { return bossLatitude; }
    public void setBossLatitude(long bossLatitude) {this.bossLatitude = bossLatitude; }

    public double getBossLongitude() { return bossLongitude; }
    public void setBossLongitude(long bossLongitude) {this.bossLongitude = bossLongitude; }

    public int getAttackDamage() { return attackDamage; }
    public void setAttackDamage(int attackDamage) {this.attackDamage = attackDamage; }

    public long getBossAttackSpeed() { return attackSpeed; }
    public void setBossAttackSpeed(long attackSpeed) {this.attackSpeed = attackSpeed; }

    public int getCombatHealth() { return combatHealth; }
    public void setCombatHealth(int combatHealth) {this.combatHealth = combatHealth; }
}
