package it.unipv.compeng.main;
import atlantafx.base.theme.PrimerDark;
import it.unipv.compeng.controller.handler.HomePageHandler;
import it.unipv.compeng.controller.manager.IndexManager;
import it.unipv.compeng.model.utility.Logger;
import it.unipv.compeng.view.HomePageGUI;
import it.unipv.compeng.view.presets.ExceptionDialog;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application{
  /********************************/
  //Attrìibutes
  /********************************/
  private static Scene scene;
  /********************************/
  //Methods
  /********************************/
  public static void main(String[] args){
    Logger.getInstance().setActive(false);

    try{
      HomePageGUI homePageGUI=new HomePageGUI();
      HomePageHandler handler=new HomePageHandler(homePageGUI);
      scene=homePageGUI.getScene();
    }catch(FileNotFoundException e){
      e.printStackTrace();
    }//end-try-catch

    launch();
  }

  @Override
  public void start(Stage stage) throws Exception{
    Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());

    stage.setTitle("Home");
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.getIcons().add(new Image(new FileInputStream("src/main/resources/Images/Logo/logo_background.png")));
    stage.show();

    IndexManager.getInstance().exceptionSubscribe(new ExceptionDialog());
  }
  /********************************/
}
