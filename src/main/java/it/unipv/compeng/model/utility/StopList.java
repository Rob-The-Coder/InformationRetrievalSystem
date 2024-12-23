package it.unipv.compeng.model.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/***********************************************************/
//CLASS StopList CONTAINS A LIST READ FROM PROPERTIES WHICH
//CONTAINS WORDS THAT NEED TO BE SKIPPED DURING PREPROCESSING
/***********************************************************/
public class StopList extends ArrayList<String>{
  /********************************/
  //Attributes
  /********************************/
  private static StopList instance=null;
  private final static String PROPERTY_STOPLIST = "StopList";
  /********************************/
  //Constructors
  /********************************/
  public StopList(){
    try{
      Properties properties=new Properties(System.getProperties());
      properties.load(new FileInputStream("Properties/Properties"));
      this.addAll(Arrays.asList(properties.getProperty(PROPERTY_STOPLIST).split(",")));
    }catch(IOException ignored){}//end-try-catch
  }
  /********************************/
  //Getter/Setter
  /********************************/
  public static StopList getInstance(){
    if(instance==null){
      instance=new StopList();
    }//end-if
    return instance;
  }
  /********************************/

}
