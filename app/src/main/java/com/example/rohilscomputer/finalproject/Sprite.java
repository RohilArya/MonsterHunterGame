package com.example.rohilscomputer.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import com.example.rohilscomputer.finalproject.UpdatableBehaviours.Animator;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sprite implements Updatable
{
    private final int COORDS_PER_VERTEX = 3;
    private float vertices[] = {
            -0.5f,  0.5f, 0.0f, // top left
            -0.5f, -0.5f, 0.0f, // bottom left
             0.5f, -0.5f, 0.0f, // bottom right
             0.5f,  0.5f, 0.0f   // top right

    };

    private float texCoords[] = {
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 0.0f
    };

    private short indices[] = {0, 1, 2, 0, 2, 3};

    public float color[] = {1.0f, 0.3f, 0.6f, 1.0f};

    private FloatBuffer vBuffer;
    private ShortBuffer iBuffer;
    private FloatBuffer tBuffer;

    public Sprite()
    {

        ByteBuffer vByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4); //4 bytes per float
        vByteBuffer.order(ByteOrder.nativeOrder());
        vBuffer = vByteBuffer.asFloatBuffer();
        vBuffer.put(vertices);
        vBuffer.position(0);

        ByteBuffer iByteBuffer = ByteBuffer.allocateDirect(indices.length * 2); //2 bytes per short
        iByteBuffer.order(ByteOrder.nativeOrder());
        iBuffer = iByteBuffer.asShortBuffer();
        iBuffer.put(indices);
        iBuffer.position(0);

        ByteBuffer tByteBuffer = ByteBuffer.allocateDirect(texCoords.length * 4);
        tByteBuffer.order(ByteOrder.nativeOrder());
        tBuffer = tByteBuffer.asFloatBuffer();
        tBuffer.put(texCoords);
        tBuffer.position(0);

        int vShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertShader);
        int fShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragShader);

        shaderProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(shaderProgram, vShader);
        GLES20.glAttachShader(shaderProgram, fShader);
        GLES20.glLinkProgram(shaderProgram);

        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.scaleM(modelMatrix, 0, modelMatrix,0, 0.5f, 0.5f, 0.5f); // sets the scale of the player


    }


    public ArrayList<Updatable> updatables = new ArrayList<>();
    public void AddUpdatable(Updatable newUpdatable)
    {
        updatables.add(newUpdatable);
    }

    Animator animator;
    public void SetAnimator(Animator anim)
    {
        animator = anim;
        if(!updatables.contains(animator))
            updatables.add(animator);
    }


    public void SetPosition(float x, float y, float z)
    {
        Matrix.translateM(modelMatrix, 0, x, y, z);
    }


    public void Update(double dt)
    {

        for (Updatable u : updatables) {
            u.Update(dt);
        }


    }

    public void draw(float[] viewProj)
    {


        GLES20.glUseProgram(shaderProgram);

        posHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(posHandle);
        GLES20.glVertexAttribPointer(posHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, vStride, vBuffer);

        tcHandle = GLES20.glGetAttribLocation(shaderProgram, "texCoords");
        GLES20.glEnableVertexAttribArray(tcHandle);
        GLES20.glVertexAttribPointer(tcHandle, 2, GLES20.GL_FLOAT, false, tStride, tBuffer);

        /*
        maxFramesHandle = GLES20.glGetUniformLocation(shaderProgram, "maxFrames");
        GLES20.glUniform1f(maxFramesHandle, 8.0f);
        frameHandle = GLES20.glGetUniformLocation(shaderProgram, "frame");
        GLES20.glUniform1f(frameHandle, 3.0f); // from 0 to maxFrames-1

        numAnimsHandle = GLES20.glGetUniformLocation(shaderProgram, "numAnims");
        GLES20.glUniform1f(numAnimsHandle, 1.0f);
        animHandle = GLES20.glGetUniformLocation(shaderProgram, "anim");
        GLES20.glUniform1f(animHandle, 0.0f); // from 0 to numAnims-1
        */

        FrAnHandle = GLES20.glGetUniformLocation(shaderProgram, "FrAn");
        if(animator != null) {
            GLES20.glUniform4f(FrAnHandle, (float) animator.xpos, (float) animator.gridSize[0], (float) animator.ypos, (float) animator.gridSize[1]);
        }else{
            GLES20.glUniform4f(FrAnHandle, 0.0f, 1.0f, 0.0f, 1.0f);
        }

        colHandle = GLES20.glGetUniformLocation(shaderProgram, "vColor");
        GLES20.glUniform4fv(colHandle, 1, color, 0);

        vpHandle = GLES20.glGetUniformLocation(shaderProgram, "viewProj");
        GLES20.glUniformMatrix4fv(vpHandle, 1, false, viewProj, 0);

        modHandle = GLES20.glGetUniformLocation(shaderProgram, "model");
        GLES20.glUniformMatrix4fv(modHandle, 1, false, modelMatrix, 0);

        texHandle = GLES20.glGetUniformLocation(shaderProgram, "texture");
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
        GLES20.glUniform1i(texHandle, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, iBuffer);

        GLES20.glDisableVertexAttribArray(posHandle);
        GLES20.glDisableVertexAttribArray(tcHandle);
    }

    private int posHandle;
    private int colHandle;
    private int vpHandle;
    private int modHandle;
    private int texHandle;
    private int tcHandle;
    final int[] textureHandle = new int[1];

    private int FrAnHandle;

    private float[] modelMatrix = new float[16];

    private final int vCount = vertices.length / COORDS_PER_VERTEX;
    private final int vStride = COORDS_PER_VERTEX * 4; //4 bytes per vertex
    private final int tStride = 2 * 4;

    private final int shaderProgram;

    private final String vertShader =
            "uniform mat4 viewProj;" +
                    "uniform mat4 model;" +

                    // tc.x = texCoords.x / maxFrames + (frame/maxFrames);
                    // tc.y = texCoords.y / numAnims + (anim/numAnims);

                    // x = curFrame, y = maxFrames, z = curAnim, w = numAnims
                    "uniform vec4 FrAn;" +

                    "attribute vec4 vPosition;" +
                    "attribute vec2 texCoords;" +
                    "varying vec2 tc;" +
                    "void main(){" +
                    "   gl_Position = viewProj * model * vPosition;" +
                    "   tc = vec2(texCoords.x/FrAn.y + FrAn.x/FrAn.y, texCoords.y/FrAn.w + FrAn.z/FrAn.w);" +
                    //"   tc = vec2(texCoords.x/8.0 + 4.0/8.0, texCoords.y/1.0 + 0.0/1.0);" +
                    "}";
    private final String fragShader =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "uniform sampler2D texture;" +
                    "varying vec2 tc;" +
                    "void main() {" +
                    "   vec4 texCol = texture2D(texture, tc);" +
                    "   if(texCol.a > 0.25 && texCol.a < 0.75){" +
                    "       gl_FragColor = vColor;" +
                    "   } else{" +
                    "       gl_FragColor = texCol;" +
                    "   }" +
                    "}";

    public void loadTexture(Context context, int resourceID)
    {

        // Read in the resource
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceID);

        GLES20.glGenTextures(1, textureHandle, 0);

        // Bind to the texture in OpenGL
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

        // Set filtering
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        // Recycle the bitmap, since its data has been loaded into OpenGL.
        bitmap.recycle();

        if(textureHandle[0] == 0)
        {
            throw new RuntimeException("texture load error");
        }
    }
}
