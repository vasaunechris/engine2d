package com.sochris.engine2d.renderer;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    private long glfwWindow;
    private static Window window = null;
    public int frames;
    public static long time;

    public Window() throws Exception{
        if(Window.window == null){
            Window.window = this;
        }else{
            throw new Exception("Application déjà crée");
        }
    }

    public static Window get(){
        return Window.window;
    }

    public void run(){
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
    }

    public void init(int width, int height){
        // Setup an error callback. The default implementation
		// will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();
        
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if(!glfwInit()){
            throw new IllegalStateException("Unable to initilize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
		glfwWindow = glfwCreateWindow(width, height, "Hello World!", NULL, NULL);
		if ( glfwWindow == NULL ){
			throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(glfwWindow, (glfwWindow, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(glfwWindow, true); // We will detect this in the rendering loop
		});

        try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(glfwWindow, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				glfwWindow,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

        // Make the OpenGL context current
		glfwMakeContextCurrent(glfwWindow);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }

    public void update(){
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
        frames++;
        if(System.currentTimeMillis() > time + 1000){
            GLFW.glfwSetWindowTitle(glfwWindow, "GAME  | FPS : " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    public void swapBuffers(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        glfwSwapBuffers(glfwWindow); // swap the color buffers
    }

    public boolean shouldClose(){
        return glfwWindowShouldClose(glfwWindow);
    }

    public void freeMemory(){
        // Free the window callbacks and destroy the window
		glfwFreeCallbacks(glfwWindow);
		glfwDestroyWindow(glfwWindow);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
    }    
}
