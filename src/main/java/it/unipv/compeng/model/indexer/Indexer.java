package it.unipv.compeng.model.indexer;

import it.unipv.compeng.exceptions.InvalidStrategyException;
import it.unipv.compeng.model.index.Index;
import it.unipv.compeng.model.utility.Logger;

import java.util.concurrent.*;

/***********************************************************/
//CLASS Indexer OFFERS MANY UTILITY METHODS TO PROCESS THE
//INDEXING METHOD. ACTS AS THE CONTEXT IN THE STRATEGY PATTERN
/***********************************************************/
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
  public static Indexer getInstance(){
    if(instance==null){
      instance=new Indexer();
    }//end-if
    return instance;
  }

  public void setIndex(Index index){
    this.index = index;
  }
  /********************************/
  //Methods
  /********************************/
  //Method init initialize executor service to use all the available processors
  public void init(){
    executor=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
  }

  //Method index delegates the indexing algorithm to the specified strategy (if set)
  public void index() throws InvalidStrategyException{
    if(index.getStrategy()==null){
      throw new InvalidStrategyException();
    }else{
      executor.submit(index.getStrategy());
    }//end-if
  }

  //Method stop wait for all the running tasks to finish execution
  public void stop(){
    executor.shutdown();
    while(!executor.isTerminated()){}
    Logger.getInstance().log("Indexing complete.");
  }
  /********************************/
}
