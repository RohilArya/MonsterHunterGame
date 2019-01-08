package com.example.rohilscomputer.finalproject.UpdatableBehaviours;

import com.example.rohilscomputer.finalproject.Updatable;

import java.util.ArrayList;

public class Animator implements Updatable
{

    public int gridSize[];
    public int xpos = 0;
    public int ypos = 0;
    public Animation curAnim;
    public ArrayList<Animation> anims;

    boolean playOnce = false;


    public Animator(int gridX, int gridY)
    {
        if(gridX <= 1) gridX = 1;
        if(gridY <= 1) gridY = 1;

        gridSize = new int[] {gridX, gridY};

        anims = new ArrayList<>();
        anims.add(new Animation(0, gridX - 1, 0, 2)); //default animation
        curAnim = anims.get(0);
    }

    public void PlayAnimation(int anim)
    {
        if(anim < anims.size() && anim >= 0)
        {
            timePassed = 0.0;
            playOnce = false;
            curAnim = anims.get(anim);
            xpos = curAnim.start;
            ypos = curAnim.column;
        }
    }

    public void PlayAnimOnce(int anim)
    {

        if(anim < anims.size() && anim >= 0)
        {
            timePassed = 0.0;
            playOnce = true;
            curAnim = anims.get(anim);
            xpos = curAnim.start;
            ypos = curAnim.column;
        }
    }

    double timePassed;
    @Override
    public void Update(double dt)
    {
        if((curAnim.end - curAnim.start) <= 0)
        {
            if(curAnim.fps > 0 && playOnce)
            {
                timePassed += dt;
                if(timePassed >= curAnim.fps)
                {
                    playOnce = false;
                    PlayAnimation(0);
                }


            }
            return;
        }


        timePassed += dt;

        if(timePassed >= (1.0/curAnim.fps)) {
            timePassed = 0.0;

            xpos++;
            if (xpos > curAnim.end) {
                if (playOnce) PlayAnimation(0);
                else xpos = curAnim.start;
            }
        }
    }

    public int NewAnim(int start, int end, int column, int fps)
    {
        anims.add(new Animation(start, end, column, fps));
        if(curAnim == null) PlayAnimation(0);
        return anims.size() - 1;
    }


    class Animation
    {
        int start = 0;
        int end = 0;
        int column;
        double fps = 2;

        Animation(int start, int end, int column, int fps)
        {
            this.start = start;
            this.end = end;
            this.column = column;
            this.fps = fps;
        }
    }
}
