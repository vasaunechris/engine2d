package com.sochris.engine2d.renderer;


public class GraphicalEngine implements Runnable {

    public Thread game;
    public static Window window;
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
        window.init(WIDTH, HEIGHT);
    }

    private void onQuit(){
        System.out.println("Quitting Game");
        window.quit();
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
