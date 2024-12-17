package it.unipv.compeng.view.presets;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;
public class DocumentCard extends Card{
  /********************************/
  //Attributes
  /********************************/
  private Tile tile;
  private Button openButton;
  /********************************/
  //Constructors
  /********************************/
  public DocumentCard(){
    super();
    initComponents();
  }
  /********************************/
  //Getter/Setter
  /********************************/
  public Button getOpenButton(){
    return openButton;
  }

  public void setTitle(String title){
    tile.setTitle(title);
  }

  public void setDescription(String description){
    tile.setDescription(description);
  }
  /********************************/
  //Methods
  /********************************/
  private void initComponents(){
    tile = new Tile();
    FontIcon icon=new FontIcon(Material2AL.ARTICLE);
    icon.setIconSize(12);
    tile.setGraphic(icon);

    openButton = new Button(null, new FontIcon(Material2MZ.OPEN_IN_NEW));
    openButton.getStyleClass().addAll(Styles.BUTTON_CIRCLE, Styles.FLAT);
    tile.setAction(openButton);

    this.setHeader(tile);
    this.getStyleClass().addAll(Styles.INTERACTIVE);
  }
  /********************************/
}
