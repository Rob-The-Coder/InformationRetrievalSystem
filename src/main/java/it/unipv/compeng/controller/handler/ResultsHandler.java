package it.unipv.compeng.controller.handler;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import atlantafx.base.util.BBCodeParser;
import it.unipv.compeng.view.HomePageGUI;
import it.unipv.compeng.view.ResultsGUI;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.FileNotFoundException;


public class ResultsHandler{
  /********************************/
  //Attributes
  /********************************/
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private final ResultsGUI resultsGUI;
  /********************************/
  //Constructors
  /********************************/
  public ResultsHandler(ResultsGUI resultsGUI){
    this.resultsGUI=resultsGUI;
    initComponents();
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  private void initComponents(){
    EventHandler<MouseEvent> logoClickHandler=new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();

        HomePageGUI homePageGUI= null;
        try {
          homePageGUI = new HomePageGUI();
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        }
        HomePageHandler homePageHandler=new HomePageHandler(homePageGUI);

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(homePageGUI.getScene());
        stage.setTitle("Home");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };

    EventHandler<MouseEvent> clearIconHandler=new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        resultsGUI.getSearchTextField().setText("");
      }
    };

    EventHandler<MouseEvent> searchIconHandler=new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        resultsGUI.getSearchPopover().show(resultsGUI.getSearchIcon());
      }
    };

    Callback<Integer, Node> callback=new Callback<Integer, Node>(){
      @Override
      public Node call(Integer integer){
        VBox resultsVBox=new VBox(20);

        for(int i=0; i<4; i+=1){
          Card card=new Card();
          card.setHeader(BBCodeParser.createFormattedText("[heading=1][b]TITLE_OF_THE_SONG.TXT[/b][/heading]"));
          card.setSubHeader(new Text("Description"));
          card.setBody(new Text("BODY"));
          card.setFooter(new Text("FOOTER"));
          card.getStyleClass().add(Styles.INTERACTIVE);
          card.setCursor(Cursor.HAND);
          card.setMinHeight((double)clientHeight /6);

          resultsVBox.getChildren().add(card);
        }//end-for

        return resultsVBox;
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

    this.resultsGUI.getLogoImageView().setOnMouseClicked(logoClickHandler);
    this.resultsGUI.getClearIcon().setOnMouseClicked(clearIconHandler);
    this.resultsGUI.getSearchIcon().setOnMouseClicked(searchIconHandler);
    this.resultsGUI.getPagination().setPageFactory(callback);
    this.resultsGUI.getSearchTextField().setOnKeyPressed(searchHandler);
  }
  /********************************/
}
