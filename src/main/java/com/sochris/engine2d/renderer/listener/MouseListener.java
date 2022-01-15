package com.sochris.engine2d.renderer.listener;

public class MouseListener {

    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging = false;

    private MouseListener(){
        scrollX = 0.0;
        scrollY = 0.0;
        xPos = 0.0;
        yPos = 0.0;
        lastX = 0.0;
        lastY = 0.0;
    }

    public static MouseListener get(){
        if(MouseListener.instance==null){
            MouseListener.instance = new MouseListener();
        }
        return MouseListener.instance;
    }

}
