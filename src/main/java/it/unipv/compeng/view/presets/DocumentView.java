package it.unipv.compeng.view.presets;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.BBCodeParser;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;

public class DocumentView extends Card {
  /********************************/
  //Attributes
  /********************************/

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
//    TextFlow textFlow=BBCodeParser.createFormattedText(text);

    Text t=new Text(text);
    t.setFill(Color.WHITE);
    this.setBody(t);
  }
  /********************************/
  //Methods
  /********************************/
  private void initComponents(){
    this.getStyleClass().addAll(Styles.INTERACTIVE);
  }
  /********************************/
}
