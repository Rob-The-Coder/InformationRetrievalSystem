package it.unipv.compeng.model.document;

import java.io.IOException;

/***********************************************************/
//CONCRETE CSV IMPLEMENTATION OF A DOCUMENT
/***********************************************************/
public class CSVDocument extends Document{
  /********************************/
  //Attributes
  /********************************/

  /********************************/
  //Constructors
  /********************************/

  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  @Override
  public String read() throws IOException{
    return "";
  }

  @Override
  public Document clone(){
    return null;
  }

  /********************************/
}
