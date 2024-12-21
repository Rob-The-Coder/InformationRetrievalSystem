package it.unipv.compeng.model.indexer;

import it.unipv.compeng.exceptions.InvalidStrategyException;
import it.unipv.compeng.model.index.Index;

import java.util.concurrent.*;

public class Indexer{
  /********************************/
  //Attributes
  /********************************/
  private static Indexer instance=null;
  private Index index=null;
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
  public void setIndex(Index index){
    this.index = index;
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
    if(index.getStrategy()==null){
      throw new InvalidStrategyException();
    }else{
      executor.submit(index.getStrategy());
    }//end-if
  }

  public void stop(){
    executor.shutdown();
    while(!executor.isTerminated()){}
    System.out.println("Indexing complete.");
  }
  /********************************/
}
