package it.unipv.compeng.view;

import atlantafx.base.controls.CustomTextField;
import atlantafx.base.theme.Styles;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import org.kordamp.ikonli.javafx.*;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HomePageGUI{
  /********************************/
  //Attributes
  /********************************/
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private CustomTextField searchTextField=null;
  private FontIcon clearIcon=null;
  private Scene scene=null;
  /********************************/
  //Constructors
  /********************************/
  public HomePageGUI() throws FileNotFoundException{
    initComponents();
  }
  /********************************/
  //Getter/Setter
  /********************************/
  public Scene getScene(){
    return scene;
  }
  public CustomTextField getSearchTextField(){
    return searchTextField;
  }
  public FontIcon getClearIcon(){
    return clearIcon;
  }
  /********************************/
  //Methods
  /********************************/
  private void initComponents() throws FileNotFoundException{
    //HBox containing the logo
    Pane whitePane1=new Pane();
    Pane whitePane2=new Pane();
    ImageView logoImageView=new ImageView(new Image(new FileInputStream("src/main/resources/Images/Logo/logo.png")));
    logoImageView.setPreserveRatio(true);
    logoImageView.setFitWidth((double)clientWidth /3);
    HBox logoHBox=new HBox(whitePane1, logoImageView, whitePane2);
    HBox.setHgrow(whitePane1, Priority.ALWAYS);
    HBox.setHgrow(whitePane2, Priority.ALWAYS);

    //HBox containing the search TextField
    searchTextField=new CustomTextField();
    searchTextField.getStyleClass().addAll(Styles.LARGE, Styles.ROUNDED);
    searchTextField.setLeft(new FontIcon(Material2MZ.SEARCH));
    clearIcon=new FontIcon(Material2AL.CLEAR);
    clearIcon.setCursor(Cursor.HAND);
    searchTextField.setRight(clearIcon);
    searchTextField.setPromptText("Search...");
    searchTextField.setFocusTraversable(Boolean.FALSE);
    HBox.setHgrow(searchTextField, Priority.ALWAYS);

    HBox searchHBox=new HBox(searchTextField);

    //VBox containing the logo and the searchTextField
    VBox homePageVBox=new VBox(logoHBox, searchHBox);
    homePageVBox.setAlignment(Pos.TOP_CENTER);

    GridPane gp=new GridPane();
    gp.add(homePageVBox, 1, 1);

    ColumnConstraints[] columnConstraints=new ColumnConstraints[3];
    for(int i=0; i<3; i+=1){
      columnConstraints[i]=new ColumnConstraints();
      columnConstraints[i].setPercentWidth((double)100 /3);
    }//end-for
    gp.getColumnConstraints().addAll(columnConstraints);

    RowConstraints[] rowConstraints=new RowConstraints[3];
    for(int i=0; i<3; i+=1){
      rowConstraints[i]=new RowConstraints();
      rowConstraints[i].setPercentHeight((double)100 /3);
    }//end-for

    gp.getRowConstraints().addAll(rowConstraints);

    scene=new Scene(gp, clientWidth, clientHeight);
  }
  /********************************/
}
