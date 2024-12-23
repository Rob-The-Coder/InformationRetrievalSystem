package it.unipv.compeng.model.utility;

/***********************************************************/
//CLASS Logger HAS THE PURPOSE TO DECOUPLE EACH CLASS TO
//LOGGING PRIVILEGES, THIS WAY LOGGING CAN BE DISABLED OR ENABLED
//BASED ON THE active ATTRIBUTE
/***********************************************************/
public class Logger{
  /********************************/
  //Attributes
  /********************************/
  private static Logger instance;
  private boolean active;
  /********************************/
  //Constructors
  /********************************/
  private Logger(){
    active=true;
  }
  /********************************/
  //Getter/Setter
  /********************************/
  public static Logger getInstance(){
    if(instance == null){
      instance = new Logger();
    }//end-if
    return instance;
  }

  public void setActive(boolean active){
    this.active = active;
  }
  /********************************/
  //Methods
  /********************************/
  public void log(String message){
    if(active){
      System.out.println(message);
    }//end-if
  }
  /********************************/
}
