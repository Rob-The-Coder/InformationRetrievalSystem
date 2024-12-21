package it.unipv.compeng.view;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.CustomTextField;
import atlantafx.base.controls.Popover;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.BBCodeParser;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class ResultsGUI{
  /********************************/
  //Attributes
  /********************************/
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private ImageView logoImageView=null;
  private CustomTextField searchTextField=null;
  private FontIcon clearIcon=null;
  private FontIcon searchIcon=null;
  private Popover searchPopover=null;
  private Pagination pagination=null;
  private VBox documentClickedCard=null;
  private Scene scene=null;
  /********************************/
  //Constructors
  /********************************/
  public ResultsGUI() throws FileNotFoundException{
    initComponents();
  }
  /********************************/
  //Getter/Setter
  /********************************/
  public Scene getScene(){
    return scene;
  }
  public ImageView getLogoImageView(){
    return logoImageView;
  }
  public CustomTextField getSearchTextField(){
    return searchTextField;
  }
  public FontIcon getClearIcon(){
    return clearIcon;
  }
  public FontIcon getSearchIcon(){
    return searchIcon;
  }
  public Popover getSearchPopover(){
    return searchPopover;
  }
  public Pagination getPagination(){
    return pagination;
  }
  public void setDocumentView(Card documentView){
    if(!documentClickedCard.getChildren().isEmpty()){
      documentClickedCard.getChildren().removeFirst();
    }//end-if
    documentClickedCard.getChildren().add(documentView);
  }
  /********************************/
  //Methods
  /********************************/
  private void initComponents() throws FileNotFoundException{
    /* Creating TOP NAVBAR where there is the logo and the searchTextField */

    //HBox containing the logo and the search TextField
    logoImageView=new ImageView(new Image(new FileInputStream("src/main/resources/Images/Logo/logo_background.png")));
    logoImageView.setPreserveRatio(true);
    logoImageView.setCursor(Cursor.HAND);
    logoImageView.setFitWidth((double)clientWidth /6);

    //Text field used for prompting
    searchTextField=new CustomTextField();
    searchTextField.getStyleClass().addAll(Styles.LARGE, Styles.ROUNDED);

    searchIcon=new FontIcon(Material2MZ.SEARCH);
    searchIcon.setCursor(Cursor.HAND);
    String stringTips="""
        Welcome in [b]sound hunt![/b] \n
        A query can be formed using: \n
        [code]+[/code] which represents a logical [i]OR[/i] operator \n
        [code]*[/code] which represents a logical [i]AND[/i] operator""";
    searchPopover=new Popover(BBCodeParser.createLayout(stringTips));
    searchPopover.setArrowLocation(Popover.ArrowLocation.TOP_LEFT);
    searchPopover.setAnimated(true);
    searchPopover.setHeaderAlwaysVisible(false);

    searchTextField.setLeft(searchIcon);
    clearIcon=new FontIcon(Material2AL.CLEAR);
    clearIcon.setCursor(Cursor.HAND);
    searchTextField.setRight(clearIcon);
    searchTextField.setPromptText("Search...");
    searchTextField.setFocusTraversable(Boolean.FALSE);

    //Top NavBar
    HBox navBarHBox=new HBox(logoImageView, searchTextField);
    navBarHBox.setAlignment(Pos.CENTER_LEFT);
    HBox.setMargin(logoImageView, new Insets(11,0,0,0));
    navBarHBox.setSpacing(25);
    HBox.setHgrow(searchTextField, Priority.ALWAYS);

    //VBox for adding the separator
    Separator separator1=new Separator(Orientation.HORIZONTAL);
    separator1.setPadding(Insets.EMPTY);
    Separator separator2=new Separator(Orientation.HORIZONTAL);
    separator2.setPadding(Insets.EMPTY);

    /* creating the SCROLLPANE where there will be all the documents retrieved */
    pagination=new Pagination();
    pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
    pagination.setStyle("-fx-page-information-visible: false;");

    ScrollPane resultsScrollPane=new ScrollPane(pagination);
    resultsScrollPane.setFitToHeight(true);
    resultsScrollPane.setFitToWidth(true);
    resultsScrollPane.setPadding(new Insets(0, 0, 0, 15));

    Separator verticalSeparator=new Separator(Orientation.VERTICAL);

    /* creating SCROLLPANE in which the document clicked will be displayed */
    documentClickedCard=new VBox();
    documentClickedCard.setPadding(new Insets(20));

    /* creating GRIDPANE in charge of holding everything on scene */
    GridPane gp=new GridPane();
    gp.addRow(0, navBarHBox);
    gp.addRow(1, separator1, separator2);
//    gp.addRow(1, resultsHBox);
    gp.addRow(2, resultsScrollPane, documentClickedCard);

    ColumnConstraints columnConstraints=new ColumnConstraints();
    columnConstraints.setPercentWidth(50);
    ColumnConstraints columnConstraints2=new ColumnConstraints();
    columnConstraints2.setPercentWidth(50);

    gp.getColumnConstraints().addAll(columnConstraints, columnConstraints2);

    scene=new Scene(gp, clientWidth, clientHeight);
  }
  /********************************/
}
