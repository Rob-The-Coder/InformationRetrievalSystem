package it.unipv.compeng.model.indexer;

import it.unipv.compeng.model.index.Index;
import it.unipv.compeng.model.preprocessing.Preprocessor;
public class LMIndexingStrategy extends RunnableIndexingStrategy{
  /********************************/
  //Attributes
  /********************************/

  /********************************/
  //Constructors
  /********************************/
  public LMIndexingStrategy(Index index){
    super(index);
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  @Override
  public void buildIndex(){
    //Logarithmic merging algorithm
    System.out.println("LM");

  }
  /********************************/
}
