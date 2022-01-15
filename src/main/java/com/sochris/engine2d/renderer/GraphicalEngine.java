package com.sochris.engine2d.renderer;

import com.sochris.engine2d.util.Time;

public class GraphicalEngine implements Runnable {

    public Thread game;
    public static Window window;
    float beginTime;
    float endTime;
    public static final int WIDTH = 300, HEIGHT = 300;

    public void start(){
        this.game = new Thread(this,"game");
        game.start();
    }

    private void onInit() {
        System.out.println("Initialisation Game");
        try {
            window = new Window();
        } catch (Exception e) {
            e.printStackTrace();
        }
        window.init(WIDTH, HEIGHT, "GAME");
        float beginTime = Time.getTime();
        float endTime = Time.getTime();
    }

    private void onQuit(){
        System.out.println("Quitting Game");
        window.freeMemory();
    }

    @Override
    public void run() {

        onInit();
        while(!window.shouldClose()){
            update();
            render();
            endTime = Time.getTime();
            float dt = endTime - beginTime;
            beginTime = endTime;
        }
        onQuit();
        
    }

    private void update(){
        //System.out.println("Updating Game");
        window.update();
    }

    private void render(){
        //System.out.println("rending Game");
        window.swapBuffers();
    }
    
}
