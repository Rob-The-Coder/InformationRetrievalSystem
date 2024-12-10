package it.unipv.compeng.controller.handler;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import it.unipv.compeng.view.ResultsGUI;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Callback;


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
    Callback<Integer, Node> callback=new Callback<Integer, Node>(){
      @Override
      public Node call(Integer integer){
        VBox resultsVBox=new VBox(20);

        for(int i=0; i<5; i+=1){
          Card card=new Card();
          card.setHeader(new Text("Title"));
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

    this.resultsGUI.getPagination().setPageFactory(callback);
  }
  /********************************/
}
