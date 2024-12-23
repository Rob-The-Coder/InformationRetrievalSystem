package it.unipv.compeng.model.utility.observer;

/***********************************************************/
//INTERFACE IExceptionSubscriber FROM OBSERVER PATTERN
/***********************************************************/
public interface IExceptionSubscriber{
  /********************************/
  //Methods
  /********************************/
  void update(Exception e);
  /********************************/
}
