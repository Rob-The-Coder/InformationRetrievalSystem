package it.unipv.compeng.model.indexer;

import it.unipv.compeng.model.index.Index;
import it.unipv.compeng.model.preprocessing.Preprocessor;
import it.unipv.compeng.model.term.Term;

import java.io.IOException;
public class LMStrategy extends RunnableStrategy{
  /********************************/
  //Attributes
  /********************************/

  /********************************/
  //Constructors
  /********************************/
  public LMStrategy(Index index, Preprocessor preprocessor){
    super(index, preprocessor);
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
