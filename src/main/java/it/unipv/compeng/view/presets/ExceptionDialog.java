package it.unipv.compeng.view.presets;

import it.unipv.compeng.model.utility.observer.IExceptionSubscriber;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

/***********************************************************/
//CLASS ExceptionDialog IS A UTILITY VIEW COMPONENTS
//CRUCIAL FOR ALERTING THE USER THAT AN EXCEPTION OCCURRED
/***********************************************************/
public class ExceptionDialog extends Alert implements IExceptionSubscriber{
  /********************************/
  //Attributes
  /********************************/
  private final Exception exception;
  /********************************/
  //Constructors
  /********************************/
  public ExceptionDialog() {
    super(AlertType.ERROR);
    exception = new Exception();
  }

  public ExceptionDialog(Exception exception, Stage currentStage) {
    super(Alert.AlertType.ERROR);
    this.exception = exception;
    initComponents(currentStage);
  }
  /********************************/
  //Methods
  /********************************/
  private void initComponents(Stage currentStage){
    this.setTitle("Exception dialog");
    this.setContentText("An exception occurred!\nSee the log for details.");

    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    exception.printStackTrace(printWriter);

    TextArea textArea = new TextArea(stringWriter.toString());
    textArea.setEditable(false);
    textArea.setWrapText(false);
    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    GridPane.setVgrow(textArea, Priority.ALWAYS);
    GridPane.setHgrow(textArea, Priority.ALWAYS);

    GridPane content = new GridPane();
    content.setMaxWidth(Double.MAX_VALUE);
    content.add(new Label("Full stacktrace:"), 0, 0);
    content.add(textArea, 0, 1);

    this.getDialogPane().setExpandableContent(content);
    initOwner(currentStage.getScene().getWindow());
    show();
  }

  @Override
  public void update(Exception e){
    Platform.runLater(() -> {
      try{
        Stage stage=(Stage)Stage.getWindows().getFirst();
        ExceptionDialog exceptionDialog = new ExceptionDialog(e, stage);
      }catch(Exception ex){
        ex.printStackTrace();
      }//end-try
    });
  }
  /********************************/
}
