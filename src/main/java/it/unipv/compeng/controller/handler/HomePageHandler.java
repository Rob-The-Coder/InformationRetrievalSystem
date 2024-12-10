package it.unipv.compeng.controller.handler;

import it.unipv.compeng.view.HomePageGUI;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    EventHandler<MouseEvent> searchIconHandler=new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        homePageGUI.getSearchPopover().show(homePageGUI.getSearchIcon());
      }
    };

    EventHandler<KeyEvent> searchHandler=new EventHandler<KeyEvent>(){
      @Override
      public void handle(KeyEvent keyEvent){
        //If ENTER has been pressed then we proceed with the search
        if(keyEvent.getCode()== KeyCode.ENTER){


        }//end-if
      }
    };

    this.homePageGUI.getClearIcon().setOnMouseClicked(clearIconHandler);
    this.homePageGUI.getSearchIcon().setOnMouseClicked(searchIconHandler);
    this.homePageGUI.getSearchTextField().setOnKeyPressed(searchHandler);

  }
  /********************************/
}
