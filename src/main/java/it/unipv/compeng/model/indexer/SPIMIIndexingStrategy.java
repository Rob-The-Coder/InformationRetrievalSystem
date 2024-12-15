package it.unipv.compeng.model.indexer;

import it.unipv.compeng.model.index.Index;
import it.unipv.compeng.model.preprocessing.Preprocessor;
import it.unipv.compeng.model.term.Term;

import java.io.*;

public class SPIMIIndexingStrategy extends RunnableIndexingStrategy{
  /********************************/
  //Attributes
  /********************************/

  /********************************/
  //Constructors
  /********************************/
  public SPIMIIndexingStrategy(Index index){
    super(index);
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  @Override
  public void buildIndex() throws IOException{
    //SPIMI algorithm
    System.out.println("SPIMI");

    Term[] terms=null;

    int count=0;
    while(super.index.getPreprocessor().hasNextToProcess()){
      System.out.println("Indexing document: "+ count);
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
