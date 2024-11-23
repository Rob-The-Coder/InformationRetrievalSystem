package it.unipv.compeng.main;
import it.unipv.compeng.exceptions.InvalidStrategyException;
import it.unipv.compeng.model.dictionary.PrefixBTree;
import it.unipv.compeng.model.document.Dataset;
import it.unipv.compeng.model.document.SampleTextDocument;
import it.unipv.compeng.model.index.*;
import it.unipv.compeng.model.indexer.Indexer;
import it.unipv.compeng.model.indexer.SPIMIStrategy;
import it.unipv.compeng.model.postinglist.PlainPostingList;
import it.unipv.compeng.model.preprocessing.PorterStringPreprocessor;
import it.unipv.compeng.model.preprocessing.Preprocessor;
import it.unipv.compeng.model.term.StringTerm;
import it.unipv.compeng.model.utility.iterator.DocumentIterator.IDatasetIterator;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application{
  public static String SONGS_FOLDER="/home/roberto/Scaricati/Songs/";
  public static String SONGS_FOLDER_COPY="/home/roberto/Scaricati/Songs_Reduced/";
  public static String PROVA="/home/roberto/Scaricati/Prova/";

  public static void main(String[] args) throws IOException, InvalidStrategyException, ClassNotFoundException, Throwable{
    //Must be done in a more elegant way, as of now is just for testing purposes
    File[] listOfFiles=new File(SONGS_FOLDER).listFiles();
    Dataset dataset=new Dataset();
    for(int i=0; i<listOfFiles.length; i+=1){
      dataset.add(new SampleTextDocument(listOfFiles[i]));
    }//end-for

    //Ideally I'd like to run the two indexing algorithms in parallel via threads, don't really know what's the best approach.
    Preprocessor[] preprocessors={new PorterStringPreprocessor(dataset.clone()), new PorterStringPreprocessor(dataset.clone())};
    Index[] indexes={new BTreeIndex(2), new SoundedIndex(1000000)};
    for(int i=0; i<indexes.length; i+=1){
      Indexer.getInstance().setStrategy(new SPIMIStrategy(indexes[i], preprocessors[i]));
      Indexer.getInstance().index();
    }//end-for
    Indexer.getInstance().stop();

//    Indexer.getInstance().setStrategy(new LMStrategy(new SoundedIndex(dataset.size()), preprocessor));
//    Indexer.getInstance().index();

//    FileInputStream fileInputStream=new FileInputStream("/home/roberto/Scaricati/NormalIndex.txt");
//    ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
//    Index i = (Index) objectInputStream.readObject();
//    objectInputStream.close();
//    i.traverseDictionary();

//    Map<String, Integer> hashMap = new HashMap<>();
//    while(preprocessor.hasNextToProcess()){
//      for(Term word:preprocessor.processNext()){
//        if (hashMap.containsKey(word.toString())){
//          hashMap.put(word.toString(), hashMap.get(word.toString()) + 1);
//        }else{
//          hashMap.put(word.toString(), 1);
//        }//end-if
//      }//end-for
//    }//end-while
//    hashMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(System.out::println);

//    for(int i=0; i<listOfFiles.length; i+=1){
////      System.out.println(listOfFiles[i].getName());
//      Preprocessor preprocessor=new PorterStringPreprocessor(new SampleTextDocument(listOfFiles[i]));
//      for(Term word:preprocessor.process()){
//        if (hashMap.containsKey(word.toString())){
//          hashMap.put(word.toString(), hashMap.get(word.toString()) + 1);
//        }else{
//          hashMap.put(word.toString(), 1);
//        }//end-if
//      }//end-for
//      //System.out.println(Arrays.toString(preprocessor.process()));
//    }//end-for-each
//    hashMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(System.out::println);

//    String[] words={"sight", "site", "rack", "face", "meat", "tune", "mist", "late", "ratt", "man", "drug", "peak", "suit", "fall", "spit", "cage", "high", "bear", "goat", "trap", "urge", "rush", "tape"};
//    PositionalBTreeIndex btreeIndex=new PositionalBTreeIndex(2);
//    SoundedIndex soundedIndex=new SoundedIndex(words.length);
//
//    for(String word : words) {
//      btreeIndex.addToDictionary(new StringTerm(word));
//      soundedIndex.addToDictionary(new StringTerm(word));
//    }//end-for

//    btreeIndex.traverseDictionary();
//    for(String word:words){
//      System.out.println(soundedIndex.search(new StringTerm(word)));
//    }

//    //CSV, 0 and 6 are respectively the title and the lyrics.
//    String path = "/home/roberto/Scaricati/dataset_reduced.csv";
//    String newPath="/home/roberto/Scaricati/dataset_reduced.csv";
//    try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(path)).withSkipLines(0).withCSVParser(new RFC4180ParserBuilder().build()).build()) {
////      CSVWriter csvWriter = new CSVWriter(new FileWriter(newPath));
//      String[] values = null;
////      int i=0;
//      while (((values = csvReader.readNext()) != null)) {
////          System.out.println(values[1]);
////          if(values[1].equals("rock")){
////            csvWriter.writeNext(new String[]{values[0], values[6]});
////          }//end-if
//          String title=values[0].replaceAll("[^a-zA-Z0-9-_\\.]", "_");
//          BufferedWriter writer = new BufferedWriter(new FileWriter(songsFolder+title.substring(0, Math.min(title.length(), 250))+".txt"));
//          writer.write(values[1]);
//          writer.close();
////        i+=1;
//      }//end-while
//      csvReader.close();
////      csvWriter.close();
////      File[] listOfFiles=new File(songsFolder).listFiles();
//    }//end-try-catch
//    catch(IOException | CsvValidationException e){
//      throw new RuntimeException(e);
//    }

//To copy files from a directory to another
//    InputStream in = new BufferedInputStream(new FileInputStream(listOfFiles[i]));
//    OutputStream out = new BufferedOutputStream(new FileOutputStream(SONGS_FOLDER_COPY+listOfFiles[i].getName()));
//
//    byte[] buffer = new byte[1024];
//    int lengthRead;
//    while ((lengthRead = in.read(buffer)) > 0) {
//      out.write(buffer, 0, lengthRead);
//      out.flush();
//    }
    //launch();
  }

  @Override
  public void start(Stage stage) throws Exception{

  }
}
