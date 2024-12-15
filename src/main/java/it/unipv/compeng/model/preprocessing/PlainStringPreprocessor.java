package it.unipv.compeng.model.preprocessing;

import it.unipv.compeng.model.term.StringTerm;
import it.unipv.compeng.model.term.Term;
import it.unipv.compeng.model.utility.iterator.DocumentIterator.IDatasetIterable;
import it.unipv.compeng.model.utility.iterator.DocumentIterator.IDatasetIterator;
public class PlainStringPreprocessor extends Preprocessor{
  /********************************/
  //Attributes
  /********************************/

  /********************************/
  //Constructors
  /********************************/
  public PlainStringPreprocessor(IDatasetIterable dataset){
    super(dataset);
  }

  public PlainStringPreprocessor(){}
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  @Override
  protected Term stem(String s){
    return new StringTerm(s);
  }
  /********************************/
}
