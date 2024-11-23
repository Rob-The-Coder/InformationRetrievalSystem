package it.unipv.compeng.model.indexer;

import java.io.IOException;
public interface IndexingStrategy extends Runnable{
  /********************************/
  //Constants
  /********************************/

  /********************************/
  //Methods
  /********************************/
  void buildIndex() throws IOException;
  void storeIndex() throws IOException;
  /********************************/
}
