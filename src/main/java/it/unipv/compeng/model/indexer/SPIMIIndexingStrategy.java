package it.unipv.compeng.model.indexer;

import it.unipv.compeng.model.index.Index;
import it.unipv.compeng.model.term.Term;
import it.unipv.compeng.model.utility.Logger;

import java.io.*;

/***********************************************************/
//CLASS SPIMIIndexingStrategy IMPLEMENTS THE SINGLE PASS IN MEMORY INDEXING ALGORITHM
/***********************************************************/
public class SPIMIIndexingStrategy extends RunnableIndexingStrategy{
  /********************************/
  //Constructors
  /********************************/
  public SPIMIIndexingStrategy(Index index){
    super(index);
  }
  /********************************/
  //Methods
  /********************************/
  @Override
  public void buildIndex() throws IOException{
    //SPIMI algorithm
    Logger.getInstance().log("SPIMI");

    Term[] terms=null;

    int count=0;
    while(super.index.getPreprocessor().hasNextToProcess()){
      Logger.getInstance().log("SPIMI: indexing document "+ count);
      terms=super.index.getPreprocessor().processNext();

      for(Term term : terms){
        index.addToDictionary(term);
      }//end-for
      count+=1;
    }//end-while

    super.storeIndex();
  }
  /********************************/
}
