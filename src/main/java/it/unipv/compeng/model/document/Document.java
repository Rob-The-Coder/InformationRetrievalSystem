package it.unipv.compeng.model.document;

import java.io.IOException;

public abstract class Document{
  /********************************/
  //Attributes
  /********************************/
  protected String title;
  protected int docId;
  /********************************/
  //Constructors
  /********************************/

  /********************************/
  //Getter/Setter
  /********************************/
  public int getDocId(){
    return docId;
  }

  public String getTitle(){
    return title;
  }
  /********************************/
  //Methods
  /********************************/
  public abstract String read() throws IOException;
  public abstract Document clone();

  @Override
  public boolean equals(Object obj){
    return this.getDocId()==((Document)obj).getDocId();
  }
  /********************************/
}
