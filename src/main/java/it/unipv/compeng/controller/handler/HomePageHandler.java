package it.unipv.compeng.controller.handler;

import atlantafx.base.controls.RingProgressIndicator;
import it.unipv.compeng.model.utility.observer.ISubcriber;
import it.unipv.compeng.controller.manager.IndexManager;
import it.unipv.compeng.controller.manager.RetrieveManager;
import it.unipv.compeng.view.HomePageGUI;
import it.unipv.compeng.view.ResultsGUI;
import it.unipv.compeng.view.presets.ExceptionDialog;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/***********************************************************/
//CLASS HomePageHandler PER MVC ARCHITECTURE, HANDLES ALL THE
//USER INTERACTION WITH THE HomePageGUI view
/***********************************************************/
public class HomePageHandler implements ISubcriber{
  /********************************/
  //Attributes
  /********************************/
  private final HomePageGUI homePageGUI;
  /********************************/
  //Constructors
  /********************************/
  public HomePageHandler(HomePageGUI homePageGUI){
    this.homePageGUI=homePageGUI;
    initComponents();
  }
  /********************************/
  //Methods
  /********************************/
  private void initComponents(){
    //Handler for x in searchTextField which clears its content
    EventHandler<MouseEvent> clearIconHandler=new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        homePageGUI.getSearchTextField().setText("");
      }
    };

    //Handler for lens in searchTextField which pops up the instructions
    EventHandler<MouseEvent> searchIconHandler=new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        homePageGUI.getSearchPopover().show(homePageGUI.getSearchIcon());
      }
    };

    //Handler for key typing in searchTextField
    EventHandler<KeyEvent> searchHandler=new EventHandler<KeyEvent>(){
      @Override
      public void handle(KeyEvent keyEvent){
        //If ENTER has been pressed then we proceed with the search
        if(keyEvent.getCode()== KeyCode.ENTER){
          //In order to search, I need to wait for the indexes to be created/loaded
          homePageGUI.getSearchTextField().setRight(new RingProgressIndicator());

          IndexManager.getInstance().subscribe(HomePageHandler.this);
        }//end-if
      }
    };

    //Adding handlers
    this.homePageGUI.getClearIcon().setOnMouseClicked(clearIconHandler);
    this.homePageGUI.getSearchIcon().setOnMouseClicked(searchIconHandler);
    this.homePageGUI.getSearchTextField().setOnKeyPressed(searchHandler);
  }

  @Override
  public void update(){
    Platform.runLater(new Runnable(){
      @Override
      public void run(){
        //Once the indexes are loaded I can proceed with the search
        Stage stage=(Stage)homePageGUI.getScene().getWindow();

        try{
          ResultsGUI resultsGUI=new ResultsGUI();
          ResultsHandler resultsHandler=new ResultsHandler(resultsGUI, homePageGUI.getSearchTextField().getText(), RetrieveManager.getInstance().booleanRetrieve(homePageGUI.getSearchTextField().getText()));

          Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
          stage.setScene(resultsGUI.getScene());
          stage.setTitle("Results");
          stage.setWidth(previousDimension.getWidth());
          stage.setHeight(previousDimension.getHeight());
        }catch(FileNotFoundException e) {
          ExceptionDialog exceptionDialog=new ExceptionDialog(e, stage);
        }//end-try-catch
      }
    });
  }
  /********************************/
}
