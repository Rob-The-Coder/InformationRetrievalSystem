package it.unipv.compeng.model.utility;

import it.unipv.compeng.model.document.Dataset;
import it.unipv.compeng.model.document.SampleTextDocument;
import it.unipv.compeng.model.index.Index;
import it.unipv.compeng.model.indexer.Indexer;
import it.unipv.compeng.model.indexer.SPIMIStrategy;
import it.unipv.compeng.model.preprocessing.PorterStringPreprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
public class IndexManager extends Thread{
  /********************************/
  //Attributes
  /********************************/
  private static IndexManager instance=null;
  private final static String PROPERTY_DATASETS_FOLDER = "DatasetsFolder";
  private final static String PROPERTY_INDEXES_FOLDER = "IndexesFolder";
  private final static String PROPERTY_INDEXES = "IndexesClassName";
  /********************************/
  //Constructors
  /********************************/
  private IndexManager(){}
  /********************************/
  //Getter/Setter
  /********************************/
  public static IndexManager getInstance(){
    if(instance==null){
      instance=new IndexManager();
    }//end-if
    return instance;
  }
  /********************************/
  //Methods
  /********************************/
  @Override
  public void run(){
    while(true){
      //Check if indexes are present
      try{
        Properties properties=new Properties(System.getProperties());
        properties.load(new FileInputStream("Properties/Properties"));
        String propertyFolder=properties.getProperty(PROPERTY_INDEXES_FOLDER);
        String[] indexes=properties.getProperty(PROPERTY_INDEXES).split(",");

        //Forming dataset
        String datasetFolder=properties.getProperty(PROPERTY_DATASETS_FOLDER);
        System.out.println(datasetFolder);
        ArrayList<File> listOfFiles=new ArrayList<>();
        buildDocumentList(listOfFiles, new File(datasetFolder));
        Dataset dataset=new Dataset();
        for(File file:listOfFiles){
          dataset.add(new SampleTextDocument(file));
        }//end-for

//        System.out.println(listOfFiles);

        for(String index:indexes){
          if(!Files.exists(Path.of(new StringBuilder(propertyFolder).append("/").append(index).append(".txt").toString()))){
            Indexer.getInstance().init();
            Indexer.getInstance().setStrategy(new SPIMIStrategy(((Index)(Class.forName(index).getConstructor().newInstance())), new PorterStringPreprocessor(dataset.clone())));
            Indexer.getInstance().index();
            Indexer.getInstance().stop();
            System.out.println("Indexing complete.");
          }else{
            System.out.println("Index already exists");
          }//end-if
        }//end-for

        sleep(1000000);
      }catch(Exception e){
        throw new RuntimeException(e);
      }//end-try-catch
    }
  }

  private void buildDocumentList(ArrayList<File> listOfFiles, File dir) {
    File[] files = dir.listFiles();
    for (File file : files){
      if(file.isDirectory()){
        buildDocumentList(listOfFiles, file);
      }else{
        listOfFiles.add(file);
      }//end-if
    }//end-for
  }
  /********************************/
}
