package it.unipv.compeng.model.utility.iterator.DocumentIterator;

import it.unipv.compeng.model.document.Document;

/***********************************************************/
//INTERFACE IDatasetIterator FROM ITERATOR PATTERN
/***********************************************************/
public interface IDatasetIterator{
  /********************************/
  //Methods
  /********************************/
  boolean hasNext();
  Document next();
  int getPosition();
  /********************************/
}
