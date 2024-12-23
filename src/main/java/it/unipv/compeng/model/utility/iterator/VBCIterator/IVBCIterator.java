package it.unipv.compeng.model.utility.iterator.VBCIterator;

/***********************************************************/
//INTERFACE IVBCIterator FROM ITERATOR PATTERN
/***********************************************************/
public interface IVBCIterator{
  /********************************/
  //Methods
  /********************************/
  boolean hasNext();
  int next();
  int getPointer();
  /********************************/
}
