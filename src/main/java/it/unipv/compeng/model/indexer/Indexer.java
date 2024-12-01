package it.unipv.compeng.model.indexer;

import it.unipv.compeng.exceptions.InvalidStrategyException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Indexer{
  /********************************/
  //Attributes
  /********************************/
  private static Indexer instance=null;
  private RunnableStrategy strategy=null;
  private ExecutorService executor;
  /********************************/
  //Constructors
  /********************************/
  public Indexer(){
    init();
  }
  /********************************/
  //Getter/Setter
  /********************************/
  public void setStrategy(RunnableStrategy strategy){
    this.strategy = strategy;
  }
  /********************************/
  //Methods
  /********************************/
  public static Indexer getInstance(){
    if(instance==null){
      instance=new Indexer();
    }//end-if
    return instance;
  }

  public void init(){
    executor=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
  }

  public void index() throws InvalidStrategyException{
    if(strategy==null){
      throw new InvalidStrategyException();
    }else{
      executor.submit(strategy);
    }//end-if
  }

  public void stop(){
    executor.shutdown();
  }
  /********************************/
}
