package it.unipv.compeng.model.document;

import java.io.FileNotFoundException;
import java.io.IOException;
public abstract class Document{
  /********************************/
  //Attributes
  /********************************/
  private int docId;
  /********************************/
  //Constructors
  /********************************/

  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  public abstract String read() throws IOException;
  /********************************/
}