package it.unipv.compeng.model.indexer;

import it.unipv.compeng.model.index.Index;
import it.unipv.compeng.model.utility.Logger;

import java.io.*;
import java.util.Properties;

/***********************************************************/
//ABSTRACT CLASS RunnableIndexingStrategy DEFINE THE LAYOUT OF EACH STRATEGY
/***********************************************************/
public abstract class RunnableIndexingStrategy implements IndexingStrategy{
  /********************************/
  //Attributes
  /********************************/
  private final static String PROPERTY_INDEXES_FOLDER="IndexesFolder";
  protected Index index;
  /********************************/
  //Constructors
  /********************************/
  public RunnableIndexingStrategy(Index index) {
    this.index = index;
  }
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
    Properties properties=new Properties(System.getProperties());
    properties.load(new FileInputStream("Properties/Properties"));
    String indexesFolder=properties.getProperty(PROPERTY_INDEXES_FOLDER);

    FileOutputStream fileOutputStream=new FileOutputStream(new StringBuilder(indexesFolder).append(File.separator).append(index.getClass().getName()).append(".bin").toString());
    ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
    objectOutputStream.writeObject(index);
    objectOutputStream.flush();
    objectOutputStream.close();
    Logger.getInstance().log("Storing complete for index "+ index.getClass().getName());
  }
  /********************************/
}
