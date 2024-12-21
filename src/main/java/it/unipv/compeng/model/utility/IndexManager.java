package it.unipv.compeng.model.utility;

import it.unipv.compeng.model.document.Dataset;
import it.unipv.compeng.model.document.Document;
import it.unipv.compeng.model.document.SampleTextDocument;
import it.unipv.compeng.model.index.Index;
import it.unipv.compeng.model.indexer.Indexer;
import it.unipv.compeng.model.indexer.SPIMIIndexingStrategy;
import it.unipv.compeng.model.postinglist.PostingList;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
public class IndexManager extends Thread{
  /********************************/
  //Attributes
  /********************************/
  private static IndexManager instance=null;
  private final ArrayList<Index> indexArrayList;
  private final static String PROPERTY_DATASETS_FOLDER = "DatasetsFolder";
  private final static String PROPERTY_INDEXES_FOLDER = "IndexesFolder";
  private final static String PROPERTY_INDEXES = "IndexesClassName";
  private final ArrayList<ISubcriber> subscribers;

  private ArrayList<File> listOfFiles;
  /********************************/
  //Constructors
  /********************************/
  private IndexManager(){
    this.indexArrayList = new ArrayList<>();
    this.listOfFiles = new ArrayList<>();
    this.subscribers = new ArrayList<>();
    this.start();
  }
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
        String indexesFolder=properties.getProperty(PROPERTY_INDEXES_FOLDER);
        String[] indexes=properties.getProperty(PROPERTY_INDEXES).split(",");

        //Forming dataset
        String datasetFolder=properties.getProperty(PROPERTY_DATASETS_FOLDER);
        listOfFiles=new ArrayList<>();
        buildDocumentList(listOfFiles, new File(datasetFolder));
        Collections.sort(listOfFiles);
        Dataset dataset=new Dataset();
        for(int i=0; i<listOfFiles.size(); i+=1){
          dataset.add(new SampleTextDocument(listOfFiles.get(i), i));
        }//end-for
//        System.out.println(listOfFiles);

        Indexer.getInstance().init();
        for(String index:indexes){

          if(!Files.exists(Path.of(new StringBuilder(indexesFolder).append(File.separator).append(index).append(".bin").toString()))){
            System.out.println(index + "  needs indexing...");

            //Using tmpIndex as a Facade to set everything up for indexing
            Index tmpIndex=(Index)(Class.forName(index).getConstructor().newInstance());
            tmpIndex.setDataset(dataset);
            tmpIndex.setStrategy(new SPIMIIndexingStrategy(tmpIndex));

            Indexer.getInstance().setIndex(tmpIndex);
            Indexer.getInstance().index();
          }else{
            System.out.println("Index already exists.");
          }//end-if
        }//end-for
        Indexer.getInstance().stop();

        if(indexArrayList.isEmpty() || indexArrayList.size()<indexes.length){
          for(String index:indexes){
            //Deserializing index from memory and adding it to indexArrayList
            FileInputStream fileInputStream=new FileInputStream(new StringBuilder(indexesFolder).append(File.separator).append(index).append(".bin").toString());
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
            Index i = (Index) objectInputStream.readObject();
            objectInputStream.close();
            if(!indexArrayList.contains(i)){
              System.out.println("Index added.");
              indexArrayList.add(i);
            }//end-if
          }//end-for-each
        }//end-if
        notifySubscribers();

        sleep(1000000);
      }catch(Exception e){
        throw new RuntimeException(e);
      }//end-try-catch
    }
  }

  public ArrayList<Document> searchIndexes(String s){
    ArrayList<Document> documents=new ArrayList<>();

    for(Index index:indexArrayList){
      PostingList pl=index.getPostingList(s);
      if(pl!=null){
        int[] tmpDocIds=pl.docIdsToArray();
        int[] tmpScores=pl.termDocFrequenciesToArray();
        for(int i=0; i<tmpDocIds.length; i+=1){
          Document docToAdd=new SampleTextDocument(this.listOfFiles.get(tmpDocIds[i]), tmpDocIds[i]);
          docToAdd.incrementScore(tmpScores[i]);

          int pos=Collections.binarySearch(documents, docToAdd, Document.getDocIdComparator());
          if(pos<0){
            documents.add(Math.abs(pos+1), docToAdd);
          }else if(documents.get(pos).equals(docToAdd)){
            documents.get(pos).incrementScore(tmpScores[i]);
          }//end-if
        }//end-for
      }//end-if
    }//end-for-each

    return documents;
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

  public void subscribe(ISubcriber subscriber){
    if(!this.indexArrayList.isEmpty()){
      subscriber.update();
    }else{
      this.subscribers.add(subscriber);
    }//end-if
  }

  public void notifySubscribers(){
    for(ISubcriber subscriber: subscribers){
      subscriber.update();
    }//end-for-each
    subscribers.clear();
  }
  /********************************/
}
