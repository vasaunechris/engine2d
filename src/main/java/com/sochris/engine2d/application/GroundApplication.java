package ground.application;

import java.lang.Exception;

public class GroundApplication{


    /*Private champs*/
    private static GroundApplication m_instance = null; 
    private boolean isRunning = true;

    public GroundApplication() throws Exception{

        if(m_instance == null){
            m_instance = this;
        }else{
            throw new Exception("Application déjà crée");
        }

    }

    public void onInit(){

    }

    public void onQuit(){

    }

    public void run(){

        onInit();
        while(isRunning){


            
        }
        onQuit();

    }
       

    public void quit(){
        isRunning = false;
    }

    public static GroundApplication get(){
        return m_instance;
    }

}