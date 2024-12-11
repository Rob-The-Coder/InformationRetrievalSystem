package it.unipv.compeng.controller.handler;

import it.unipv.compeng.view.HomePageGUI;
import it.unipv.compeng.view.ResultsGUI;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

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



          Stage stage=(Stage)((Node)keyEvent.getSource()).getScene().getWindow();

            ResultsGUI resultsGUI= null;
            try {
                resultsGUI = new ResultsGUI();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            ResultsHandler resultsHandler=new ResultsHandler(resultsGUI);

          Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
          stage.setScene(resultsGUI.getScene());
          stage.setTitle("Results");
          stage.setWidth(previousDimension.getWidth());
          stage.setHeight(previousDimension.getHeight());
        }//end-if
      }
    };

    this.homePageGUI.getClearIcon().setOnMouseClicked(clearIconHandler);
    this.homePageGUI.getSearchIcon().setOnMouseClicked(searchIconHandler);
    this.homePageGUI.getSearchTextField().setOnKeyPressed(searchHandler);

  }
  /********************************/
}
