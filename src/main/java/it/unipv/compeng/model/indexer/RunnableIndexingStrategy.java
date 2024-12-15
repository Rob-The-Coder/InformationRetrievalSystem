package it.unipv.compeng.model.indexer;

import it.unipv.compeng.model.index.Index;
import it.unipv.compeng.model.preprocessing.Preprocessor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Properties;
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
    Properties properties=new Properties(System.getProperties());
    properties.load(new FileInputStream("Properties/Properties"));
    String indexesFolder=properties.getProperty(PROPERTY_INDEXES_FOLDER);

    FileOutputStream fileOutputStream=new FileOutputStream(new StringBuilder(indexesFolder).append("/").append(index.getClass().getName()).append(".bin").toString());
    ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
    objectOutputStream.writeObject(index);
    objectOutputStream.flush();
    objectOutputStream.close();
    System.out.println("Storing complete");
  }
  /********************************/
}
