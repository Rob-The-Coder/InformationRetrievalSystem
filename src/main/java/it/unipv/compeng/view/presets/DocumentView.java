package it.unipv.compeng.view.presets;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.BBCodeParser;
import javafx.scene.control.ScrollPane;

/***********************************************************/
//CLASS DocumentView REPRESENTS CARD IN WHICH THE CLICKED
//DOCUMENT IS VIEWED
/***********************************************************/
public class DocumentView extends Card {
  /********************************/
  //Constructors
  /********************************/
  public DocumentView(){
    super();
    initComponents();
  }
  /********************************/
  //Getter/Setter
  /********************************/
  public void setText(String text){
    ScrollPane scrollPane=new ScrollPane();
    scrollPane.setContent(BBCodeParser.createLayout(text));
    scrollPane.setFitToHeight(true);
    scrollPane.setFitToWidth(false);
    this.setBody(scrollPane);
  }
  /********************************/
  //Methods
  /********************************/
  private void initComponents(){
    this.getStyleClass().addAll(Styles.INTERACTIVE);
  }
  /********************************/
}
