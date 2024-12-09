package it.unipv.compeng.controller.handler;

import it.unipv.compeng.view.HomePageGUI;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class HomePageHandler{
  /********************************/
  //Attributes
  /********************************/
  private HomePageGUI homePageGUI;
  /********************************/
  //Constructors
  /********************************/
  public HomePageHandler(HomePageGUI homePageGUI){
    this.homePageGUI=homePageGUI;
    initComponents();
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  private void initComponents(){
    EventHandler<MouseEvent> clearIconHandler=new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        homePageGUI.getSearchTextField().setText("");
      }
    };
    this.homePageGUI.getClearIcon().setOnMouseClicked(clearIconHandler);

  }
  /********************************/
}
