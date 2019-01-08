package com.example.rohilscomputer.finalproject;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;

public class MySurfaceView extends GLSurfaceView implements Runnable
{
    final MyRenderer renderer;

    public MySurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        renderer = new MyRenderer(context);

        setEGLConfigChooser(8,8,8,8,16,0);

        setRenderer(renderer);

        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        setZOrderOnTop(true);

        Thread update = new Thread(this);
        update.start();
    }


    // !!! GAME LOOP !!!
    @Override
    public void run() {
        try {
            long currentTime = System.currentTimeMillis();
            double deltaTime;
            long newTime;

            long sleepTime;

            while (true) {
                newTime = System.currentTimeMillis();
                deltaTime = (newTime - currentTime);

                sleepTime = (1000 / 30) - (newTime - currentTime); //30fps
                deltaTime /= 1000.0;

                currentTime = newTime;

                for(Updatable u : renderer.updatables)
                    u.Update(deltaTime);

                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
