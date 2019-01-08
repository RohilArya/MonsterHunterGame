package com.example.rohilscomputer.finalproject;

import static java.lang.StrictMath.abs;

public class User {

    private String username;
    private String password;
    private String cPassword;
    private String email;
    private String cEmail;
    private int health;
    private int attackDamage;
    private int userLevel;
    private int userTotalEXP;
    private int expNeeded;
    private int combatHealth;

    public User(String username, String password, String cPassword, String email, String cEmail) {
        this.username = username;
        this.password = password;
        this.cPassword = cPassword;
        this.email = email;
        this.cEmail = cEmail;
        this.health = 100;
        this.userTotalEXP = 0;
        this.userLevel = 1;
        this.attackDamage = 14;
        this.expNeeded = 300;
        this.combatHealth = this.health;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getcPassword() {
        return cPassword;
    }

    public void setcPassword(String cPassword) {
        this.cPassword = cPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getcEmail() {
        return cEmail;
    }

    public void setcEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getExpNeeded() {
        return expNeeded;
    }

    public void setExpNeeded(int expNeeded) {
        this.expNeeded = expNeeded;
    }

    public int getUserTotalEXP() {
        return userTotalEXP;
    }

    public void setUserTotalEXP(int userTotalEXP) {
        this.userTotalEXP = userTotalEXP;
    }

    public int getCombatHealth() {
        return combatHealth;
    }

    public void setCombatHealth(int combatHealth) {
        this.combatHealth = combatHealth;
    }


    public void updateLevel(int expRecieved)
    {
        int level = getUserLevel();

        setUserTotalEXP(expRecieved);


        while ((expRecieved > this.expNeeded))
        {
            System.out.println("Entered");
            int temp;
            temp = expRecieved - this.expNeeded;
            setExpNeeded(0);
            expRecieved = temp;
            System.out.println(getExpNeeded());
            if(getExpNeeded() == 0)
            {
                level = level + 1;
                setUserLevel(level);
                setExpNeeded(getUserLevel() * 300);
            }
        }

        setExpNeeded(getExpNeeded() - expRecieved);


        setAttackDamage(getUserLevel() * 14);

        setHealth(getUserLevel() * 150);

        this.userTotalEXP = expRecieved;
        this.health = health;

    }

    public void returnLevel()
    {
        System.out.println(userLevel);
        System.out.println(health);
        System.out.println(attackDamage);
    }
}
