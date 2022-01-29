package com.sochris.engine2d.renderer.scene;

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector2f;

import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.BufferUtils;

import com.sochris.engine2d.listener.KeyListener;
import com.sochris.engine2d.renderer.Window;
import com.sochris.engine2d.renderer.camera.Camera;
import com.sochris.engine2d.renderer.shader.Shader;
import com.sochris.engine2d.renderer.shader.Texture;
import com.sochris.engine2d.util.Time;

public class LevelEditorScene extends Scene {

    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;
    private Shader defaultShader;

    private float[] vertexArray = {
        // position               // color                  // UV Coordinates
        100f,   0f, 0.0f,       1.0f, 0.0f, 0.0f, 1.0f,     1, 1, // Bottom right 0
          0f, 100f, 0.0f,       0.0f, 1.0f, 0.0f, 1.0f,     0, 0, // Top left     1
        100f, 100f, 0.0f ,      1.0f, 0.0f, 1.0f, 1.0f,     1, 0, // Top right    2
          0f,   0f, 0.0f,       1.0f, 1.0f, 0.0f, 1.0f,     0, 1  // Bottom left  3
    };

    // IMPORTANT: Must be in counter-clockwise order
    private int[] elementArray = {
        /*
                x        x


                x        x
        */
        2, 1, 0, // Top right triangle
        0, 1, 3 // bottom left triangle 
    };

    private int vaoID, vboID, eboID;
    private Texture testTexture;

    public LevelEditorScene() {
        
    }

    @Override
    public void init(){

        this.camera = new Camera(new Vector2f(0, 0));
        defaultShader = new Shader("src/main/java/com/sochris/engine2d/ressources/shader/default.glsl");
        defaultShader.compile();
        this.testTexture = new Texture("src/main/java/assets/testImage.png");
        
        // ============================================================
        // Generate VAO, VBO, and EBO buffer objects, and send to GPU
        // ============================================================
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers
        int positionsSize = 3;
        int colorSize = 4;
        int uvSize = 2;
        int vertexSizeBytes = (positionsSize + colorSize + uvSize) * Float.BYTES;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * Float.BYTES);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionsSize + colorSize) * Float.BYTES);
        glEnableVertexAttribArray(2);

    }

    @Override
    public void update(float dt) {

        

        camera.position.x -= dt * 50.0f;
        camera.position.y -= dt * 20.0f;

        defaultShader.use();

        // Upload texture to shader
        defaultShader.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        testTexture.bind();

        // Bind shader program
        defaultShader.use();
        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());
        // Bind the VAO that we're using
        glBindVertexArray(vaoID);

        // Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        defaultShader.detach();
        
    }
    
}
