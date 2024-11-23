package it.unipv.compeng.model.utility.iterator.DocumentIterator;

import it.unipv.compeng.model.document.Document;
public interface IDatasetIterator{
  /********************************/
  //Constants
  /********************************/

  /********************************/
  //Methods
  /********************************/
  boolean hasNext();
  Document next();
  int getPosition();
  /********************************/
}
