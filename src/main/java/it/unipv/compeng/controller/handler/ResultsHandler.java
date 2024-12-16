package it.unipv.compeng.controller.handler;

import it.unipv.compeng.controller.handler.presets.DocumentCardHandler;
import it.unipv.compeng.model.document.Document;
import it.unipv.compeng.model.utility.ISubcriber;
import it.unipv.compeng.model.utility.IndexManager;
import it.unipv.compeng.model.utility.RetrieveManager;
import it.unipv.compeng.view.HomePageGUI;
import it.unipv.compeng.view.ResultsGUI;
import it.unipv.compeng.view.presets.DocumentCard;
import it.unipv.compeng.view.presets.DocumentView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class ResultsHandler implements ISubcriber{
  /********************************/
  //Attributes
  /********************************/
  private static final int DOCS_PER_PAGE=10;
  private final ResultsGUI resultsGUI;
  private final ArrayList<Document> docResults;
  /********************************/
  //Constructors
  /********************************/
  public ResultsHandler(ResultsGUI resultsGUI, ArrayList<Document> docResults){
    this.resultsGUI=resultsGUI;
    this.docResults=docResults;
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

        int i=DOCS_PER_PAGE*integer;
        int fin=i+DOCS_PER_PAGE;
        while(i<fin && i<docResults.size()){
          DocumentCard documentCard=new DocumentCard();
          DocumentCardHandler documentCardHandler=new DocumentCardHandler(documentCard, docResults.get(i));
          resultsVBox.getChildren().add(documentCard);

          int finalI = i;

          EventHandler<ActionEvent> openButtonClickHandler=new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
              DocumentView documentView=new DocumentView();
              try {
                documentView.setText(docResults.get(finalI).read());
                System.out.println(docResults.get(finalI).read());
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
              resultsGUI.setDocumentView(documentView);
            }
          };
          documentCard.getOpenButton().setOnAction(openButtonClickHandler);

          i+=1;
        }//end-while

        return resultsVBox;
      }
    };

    EventHandler<KeyEvent> searchHandler=new EventHandler<KeyEvent>(){
      @Override
      public void handle(KeyEvent keyEvent){
        //If ENTER has been pressed then we proceed with the search
        if(keyEvent.getCode()== KeyCode.ENTER){
          IndexManager.getInstance().subscribe(ResultsHandler.this);
        }//end-if
      }
    };

    this.resultsGUI.getLogoImageView().setOnMouseClicked(logoClickHandler);
    this.resultsGUI.getClearIcon().setOnMouseClicked(clearIconHandler);
    this.resultsGUI.getSearchIcon().setOnMouseClicked(searchIconHandler);
    this.resultsGUI.getPagination().setPageFactory(callback);
    this.resultsGUI.getPagination().setPageCount((int) Math.ceil((double) docResults.size() /DOCS_PER_PAGE));
    this.resultsGUI.getSearchTextField().setOnKeyPressed(searchHandler);
  }

  @Override
  public void update(){
    Platform.runLater(new Runnable(){
      @Override
      public void run(){
        //Once the indexes are loaded I can proceed with the search
        Stage stage=(Stage)resultsGUI.getScene().getWindow();

        ResultsGUI resultsGUI= null;
        try {
          resultsGUI = new ResultsGUI();
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        }
        System.out.println(resultsGUI.getSearchTextField().getText());
        ResultsHandler resultsHandler=new ResultsHandler(resultsGUI, RetrieveManager.getInstance().booleanRetrieve(resultsGUI.getSearchTextField().getText()));

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(resultsGUI.getScene());
        stage.setTitle("Results");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    });
  }
  /********************************/
}
