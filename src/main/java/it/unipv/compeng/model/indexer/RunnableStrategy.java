package it.unipv.compeng.model.indexer;

import it.unipv.compeng.model.index.Index;
import it.unipv.compeng.model.preprocessing.Preprocessor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
public abstract class RunnableStrategy implements IndexingStrategy{
  /********************************/
  //Attributes
  /********************************/
  protected Index index;
  protected Preprocessor preprocessor;
  protected int offset;
  /********************************/
  //Constructors
  /********************************/
  public RunnableStrategy(Index index, Preprocessor preprocessor) {
    this.index = index;
    this.preprocessor = preprocessor;
    this.offset=0;
  }

  public RunnableStrategy(Index index, Preprocessor preprocessor, int offset) {
    this.index = index;
    this.preprocessor = preprocessor;
    this.offset=offset;
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  public abstract void buildIndex() throws IOException;

  @Override
  public void run(){
    try{
      buildIndex();
    }catch(IOException e){
      throw new RuntimeException(e);
    }
  }

  @Override
  public void storeIndex() throws IOException{
    FileOutputStream fileOutputStream=new FileOutputStream("/home/roberto/Scaricati/"+ index.getClass().getName() +".txt");
    ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
    objectOutputStream.writeObject(index);
    objectOutputStream.flush();
    objectOutputStream.close();
    System.out.println("Storing complete");
  }
  /********************************/
}
