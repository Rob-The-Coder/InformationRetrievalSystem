package it.unipv.compeng.view.presets;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;
public class documentCard extends Card{
  /********************************/
  //Attributes
  /********************************/

  /********************************/
  //Constructors
  /********************************/
  public documentCard(){
    super();
    initComponents();
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  private void initComponents(){
    Tile tile = new Tile();
    FontIcon icon=new FontIcon(Material2AL.ARTICLE);
    icon.setIconSize(12);
    tile.setGraphic(icon);
    tile.setTitle("TITLE_OF_THE_SONG.TXT");
    tile.setDescription("DESCRIPTION_OF_THE_SONG");
    Button openButton = new Button(null, new FontIcon(Material2MZ.OPEN_IN_NEW));
    openButton.getStyleClass().addAll(Styles.BUTTON_CIRCLE, Styles.FLAT);
    tile.setAction(openButton);

    this.setHeader(tile);
    this.getStyleClass().addAll(Styles.INTERACTIVE);
  }
  /********************************/
}
