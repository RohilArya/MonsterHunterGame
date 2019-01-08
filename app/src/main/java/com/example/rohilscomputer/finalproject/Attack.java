package com.example.rohilscomputer.finalproject;

import android.support.v7.app.AppCompatActivity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Attack extends AppCompatActivity {

    MyRenderer ren;

    public Attack(MyRenderer renderer)
    {
        ren = renderer;
    }

    public void heavyAttack(bossFighter bossFighter, User user)
    {
        ren.player.animator.PlayAnimOnce(ren.PlayerHeavyAttackAnim);
        ren.boss.animator.PlayAnimOnce(ren.BossHitAnim);
        bossFighter.setCombatHealth(bossFighter.getCombatHealth() - user.getAttackDamage());
    }

    public void lightAttack(bossFighter bossFighter, User user)
    {
        ren.player.animator.PlayAnimOnce(ren.PlayerLightAttackAnim);
        ren.boss.animator.PlayAnimOnce(ren.BossHitAnim);
        int lightAttack = user.getAttackDamage() / 2;
        bossFighter.setCombatHealth(bossFighter.getCombatHealth() - lightAttack);
    }

    public void bossAttack(bossFighter bossFighter, User user) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(bossFighter.getBossAttackSpeed());
        ren.boss.animator.PlayAnimOnce(ren.BossAttackAnim);
        int atkDamage = bossFighter.getAttackDamage();
        int bossAttack = bossAttackValue(atkDamage);
        System.out.println("Attack" + bossAttack);
        user.setCombatHealth(user.getCombatHealth() - bossAttack);
    }

    public static int bossAttackValue(int atkDamage)
    {

        Random r = new Random();
        return r.nextInt(atkDamage - 1) + 1;
    }

    public boolean isFightOver(bossFighter bossFighter, User user)
    {
        if(bossFighter.getCombatHealth() <= 0 || user.getCombatHealth() <= 0)
        {
            ren.boss.animator.PlayAnimation(ren.BossDeadAnim);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean didUserWin(bossFighter bossFighter, User user)
    {
        if(user.getCombatHealth() <= 0) {
            System.out.println("randy kicked our ass");
            return false;
        }
        else {
            System.out.println("User won");
            return true;
        }

    }


}
