package it.unipv.compeng.controller.handler;

import atlantafx.base.controls.RingProgressIndicator;
import atlantafx.base.util.Animations;
import it.unipv.compeng.controller.handler.presets.DocumentCardHandler;
import it.unipv.compeng.model.document.Document;
import it.unipv.compeng.model.utility.Logger;
import it.unipv.compeng.model.utility.observer.ISubcriber;
import it.unipv.compeng.controller.manager.IndexManager;
import it.unipv.compeng.controller.manager.RetrieveManager;
import it.unipv.compeng.view.HomePageGUI;
import it.unipv.compeng.view.ResultsGUI;
import it.unipv.compeng.view.presets.DocumentCard;
import it.unipv.compeng.view.presets.DocumentView;
import it.unipv.compeng.view.presets.ExceptionDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/***********************************************************/
//CLASS ResultHandler PER MVC ARCHITECTURE, HANDLES ALL THE
//USER INTERACTION WITH THE ResultsGUI view
/***********************************************************/
public class ResultsHandler implements ISubcriber{
  /********************************/
  //Attributes
  /********************************/
  private static final int DOCS_PER_PAGE=10;
  private final ResultsGUI resultsGUI;
  private final String computedQuery;
  private final ArrayList<Document> docResults;
  /********************************/
  //Constructors
  /********************************/
  public ResultsHandler(ResultsGUI resultsGUI, String computedQuery, ArrayList<Document> docResults){
    this.resultsGUI=resultsGUI;
    this.computedQuery=computedQuery;
    this.docResults=docResults;
    initComponents();
  }
  /********************************/
  //Methods
  /********************************/
  private void initComponents(){
    //Handler for logo click
    EventHandler<MouseEvent> logoClickHandler=new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent){
        Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();

        try{
          HomePageGUI homePageGUI=new HomePageGUI();
          HomePageHandler homePageHandler=new HomePageHandler(homePageGUI);

          Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
          stage.setScene(homePageGUI.getScene());
          stage.setTitle("Home");
          stage.setWidth(previousDimension.getWidth());
          stage.setHeight(previousDimension.getHeight());
        }catch(FileNotFoundException e) {
          ExceptionDialog exceptionDialog=new ExceptionDialog(e, stage);
        }//end-try-catch
      }
    };

    //Handler for x in searchTextField which clears its content
    EventHandler<MouseEvent> clearIconHandler=new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        resultsGUI.getSearchTextField().setText("");
      }
    };

    //Handler for lens in searchTextField which pops up the instructions
    EventHandler<MouseEvent> searchIconHandler=new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        resultsGUI.getSearchPopover().show(resultsGUI.getSearchIcon());
      }
    };

    //Handler for pagination next or previous
    Callback<Integer, Node> callback=new Callback<Integer, Node>(){
      @Override
      public Node call(Integer integer){
        VBox resultsVBox=new VBox(20);
        resultsVBox.setPadding(new Insets(20, 20, 0, 0));

        int i=DOCS_PER_PAGE*integer;
        int fin=i+DOCS_PER_PAGE;
        while(i<fin && i<docResults.size()){
          DocumentCard documentCard=new DocumentCard();
          DocumentCardHandler documentCardHandler=new DocumentCardHandler(documentCard, docResults.get(i));
          resultsVBox.getChildren().add(documentCard);

          EventHandler<ActionEvent> openButtonClickHandler=getActionEventEventHandler(i);
          documentCard.getOpenButton().setOnAction(openButtonClickHandler);

          i+=1;
        }//end-while

        return resultsVBox;
      }

      private EventHandler<ActionEvent> getActionEventEventHandler(int i){

        return new EventHandler<ActionEvent>(){
          @Override
          public void handle(ActionEvent actionEvent){
            DocumentView documentView=new DocumentView();
            String[] queryTerms=RetrieveManager.parseTerms(computedQuery);
            String tmpText="";
            try{
              tmpText=docResults.get(i).read();
              String docText=tmpText;

              for(String term : queryTerms){
                docText=docText.replaceAll("(?i)" + term, "[code]" + term + "[/code]");
              }//end-for-each

              Logger.getInstance().log("[font=monospace]" + docText + "[/font]");

              documentView.setText(docText);
            }catch(IOException e){
              ExceptionDialog exceptionDialog=new ExceptionDialog(e, (Stage)((Node)actionEvent.getSource()).getScene().getWindow());
            }catch(IllegalStateException e){
              documentView.setText(tmpText);
            }//end-try-catch

            resultsGUI.setDocumentView(documentView);
            var animation=Animations.fadeIn(documentView, Duration.seconds(2));
            animation.playFromStart();
          }
        };
      }
    };

    //Handler for key typing in searchTextField
    EventHandler<KeyEvent> searchHandler=new EventHandler<KeyEvent>(){
      @Override
      public void handle(KeyEvent keyEvent){
        //If ENTER has been pressed then we proceed with the search
        if(keyEvent.getCode()==KeyCode.ENTER){
          resultsGUI.getSearchTextField().setRight(new RingProgressIndicator());

          IndexManager.getInstance().subscribe(ResultsHandler.this);
        }//end-if
      }
    };

    //Adding handlers
    this.resultsGUI.getSearchTextField().setText(computedQuery);
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

        try {
          ResultsGUI results=new ResultsGUI();
          ResultsHandler resultsHandler=new ResultsHandler(results, resultsGUI.getSearchTextField().getText(), RetrieveManager.getInstance().booleanRetrieve(resultsGUI.getSearchTextField().getText()));

          Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
          stage.setScene(results.getScene());
          stage.setTitle("Results");
          stage.setWidth(previousDimension.getWidth());
          stage.setHeight(previousDimension.getHeight());
        }catch(FileNotFoundException e){
          ExceptionDialog exceptionDialog=new ExceptionDialog(e, stage);
        }//end-try-catch
      }
    });
  }
  /********************************/
}
