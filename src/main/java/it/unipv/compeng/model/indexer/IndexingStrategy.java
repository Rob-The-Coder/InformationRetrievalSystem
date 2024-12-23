package it.unipv.compeng.model.indexer;

import java.io.IOException;

/***********************************************************/
//INTERFACE INDEXINGSTRATEGY FROM STRATEGY PATTERN.
//DICTATES THE METHODS THAT EACH INDEXING ALGORITHM MUST IMPLEMENT.
//EXTENDS RUNNABLE BECAUSE EACH INDEXING IS COMPUTED IN PARALLEL.
/***********************************************************/
public interface IndexingStrategy extends Runnable{
  //********************************/
  //Methods
  /********************************/
  void buildIndex() throws IOException;
  void storeIndex() throws IOException;
  /********************************/
}
