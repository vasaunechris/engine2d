package com.sochris.engine2d.renderer;


public class GraphicsInterface implements Runnable {

    public Thread game;
    private boolean isRunning = true;

    public void start(){
        this.game = new Thread(this,"game");
        game.start();
    }

    private void onInit(){
        System.out.println("Initialisation Game");
    }

    private void onQuit(){
        this.isRunning = false;
    }

    @Override
    public void run() {

        onInit();
        while(isRunning){
            update();
            render();
        }
        onQuit();
        
    }

    private void update(){
        System.out.println("Updating Game");
    }

    private void render(){
        System.out.println("rending Game");

    }
    
}
