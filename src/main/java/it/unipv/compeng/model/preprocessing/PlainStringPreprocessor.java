package it.unipv.compeng.model.preprocessing;

import it.unipv.compeng.model.term.StringTerm;
import it.unipv.compeng.model.term.Term;
import it.unipv.compeng.model.utility.iterator.DocumentIterator.IDatasetIterable;

/***********************************************************/
//CONCRETE IMPLEMENTATION OF A PREPROCESSOR. IN PARTICULAR
//THIS PREPROCESSOR DOES NOT STEM WORDS, THIS BECAUSE SOME
//INDEXES WORK ON THE WHOLE TERM AND NOT ON THE STEMMED VERSION
//LIKE SOUNDEX INDEX
/***********************************************************/
public class PlainStringPreprocessor extends Preprocessor{
  /********************************/
  //Constructors
  /********************************/
  public PlainStringPreprocessor(IDatasetIterable dataset){
    super(dataset);
  }

  public PlainStringPreprocessor(){}
  /********************************/
  //Methods
  /********************************/
  @Override
  protected Term stem(String s){
    return new StringTerm(s);
  }
  /********************************/
}
