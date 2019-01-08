package com.example.rohilscomputer.finalproject;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.rohilscomputer.finalproject.UpdatableBehaviours.Animator;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRenderer implements GLSurfaceView.Renderer
{
    Context context;
    Sprite player;
    Sprite boss;

    ArrayList<Updatable> updatables = new ArrayList<>();

    private final float[] viewProj = new float[16];
    private final float[] projMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    public MyRenderer(Context context)
    {
        this.context = context;
    }


    public int PlayerIdleAnim;
    public int PlayerLightAttackAnim;
    public int PlayerHeavyAttackAnim;

    public int BossIdleAnim;
    public int BossAttackAnim;
    public int BossHitAnim;
    public int BossDeadAnim;


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        //next two lines enable transparency in the textures
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        player = new Sprite();
        player.loadTexture(context, R.drawable.player);

        Animator playerAnimator = new Animator(4, 3);

        playerAnimator.anims.remove(0);
        playerAnimator.curAnim = null;

        PlayerIdleAnim = playerAnimator.NewAnim(0,0,0, 0);
        PlayerLightAttackAnim = playerAnimator.NewAnim(0,3,1, 0);
        PlayerHeavyAttackAnim = playerAnimator.NewAnim(0,3,2, 0);

        player.SetAnimator(playerAnimator);

        boss = new Sprite();
        boss.SetPosition(0, 1, 0);
        boss.loadTexture(this.context, R.drawable.boss);

        Animator bossAnimator = new Animator(4, 4);
        bossAnimator.anims.remove(0);
        bossAnimator.curAnim = null;

        BossIdleAnim = bossAnimator.NewAnim(0, 0, 0, 0);
        BossAttackAnim = bossAnimator.NewAnim(0, 3, 1, 2);
        BossHitAnim = bossAnimator.NewAnim(0, 0, 2, 1);
        BossDeadAnim = bossAnimator.NewAnim(0, 0, 3, 1);

        boss.SetAnimator(bossAnimator);

        updatables.add(player);
        updatables.add(boss);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // eyeZ will likely need to relate to the mapview zoom
        Matrix.setLookAtM(viewMatrix, 0,0,0,-3, 0f, 0f,0f, 0f, 1f, 0f);

        Matrix.multiplyMM(viewProj, 0, projMatrix, 0, viewMatrix, 0);



        player.draw(viewProj);
        boss.draw(viewProj);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        Matrix.frustumM(projMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public static int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void updateCameraPos(double lat, double lng) {

    }
}
