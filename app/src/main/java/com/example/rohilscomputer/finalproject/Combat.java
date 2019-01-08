package com.example.rohilscomputer.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

public class Combat extends AppCompatActivity {
    private User user;
    private bossFighter bossFighter;
    Combat(User user, bossFighter bossFighter)
    {
        this.user = user;
        this.bossFighter = bossFighter;
        bossFighter.setCombatHealth(bossFighter.getBossHealth());
        user.setCombatHealth(user.getHealth());
    }



    public void attackOnePressed(View view) throws InterruptedException {
        int bossHealth = bossFighter.getCombatHealth();
        int userLightAttack = user.getAttackDamage() / 2;
        bossFighter.setCombatHealth(bossHealth - userLightAttack);
        String result = didUserWin(user.getCombatHealth(), bossFighter.getCombatHealth());
        bossAttack();
        System.out.println("Attack one pressed");
    }

    public void attackTwoPressed(View view)
    {
        try {
            wait(500);
            int bossHealth = bossFighter.getCombatHealth();
            int userLightAttack = user.getAttackDamage() / 2;
            bossFighter.setCombatHealth(bossHealth - userLightAttack);
            System.out.println("Attack 2 pressed");
            String result = didUserWin(user.getCombatHealth(), bossFighter.getCombatHealth());
            bossAttack();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void bossAttack() throws InterruptedException {
        wait(bossFighter.getBossAttackSpeed());
        int bossAttack = bossAttackValue(bossFighter);
        user.setCombatHealth(user.getCombatHealth() - bossAttack);
    }

    public static int bossAttackValue(bossFighter bossFighter)
    {

        Random r = new Random();
        return r.nextInt(bossFighter.getAttackDamage() - 1) + 1;
    }
    public String didUserWin(int userHealth, int bossHealth)
    {
        if(userHealth == 0)
        {
            return "boss";
        }
        else if(bossHealth == 0)
        {
            return "user";
        }
        else
        {
            return "Tie";
        }
    }

}
