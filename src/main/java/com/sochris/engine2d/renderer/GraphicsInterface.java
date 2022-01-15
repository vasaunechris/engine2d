package com.sochris.engine2d.renderer;


public class GraphicsInterface implements Runnable {

    public Thread game;
    private boolean isRunning = true;
    public static Window window;
    public static final int WIDTH = 300, HEIGHT = 300;

    public void start(){
        this.game = new Thread(this,"game");
        game.start();
    }

    private void onInit(){
        System.out.println("Initialisation Game");
        window = new Window();
        window.init(WIDTH, HEIGHT);
    }

    private void onQuit(){
        System.out.println("Quitting Game");
        this.isRunning = false;
    }

    @Override
    public void run() {

        onInit();
        while(!window.shouldClose()){
            update();
            render();
        }
        onQuit();
        
    }

    private void update(){
        System.out.println("Updating Game");
        window.update();
    }

    private void render(){
        System.out.println("rending Game");
        window.swapBuffers();
    }
    
}
