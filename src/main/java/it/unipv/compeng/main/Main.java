package it.unipv.compeng.main;
import it.unipv.compeng.exceptions.InvalidStrategyException;
import it.unipv.compeng.model.document.Dataset;
import it.unipv.compeng.model.document.SampleTextDocument;
import it.unipv.compeng.model.index.*;
import it.unipv.compeng.model.indexer.Indexer;
import it.unipv.compeng.model.indexer.SPIMIStrategy;
import it.unipv.compeng.model.preprocessing.PlainStringPreprocessor;
import it.unipv.compeng.model.preprocessing.PorterStringPreprocessor;
import it.unipv.compeng.model.preprocessing.Preprocessor;
import it.unipv.compeng.model.term.StringTerm;
import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Arrays;

public class Main extends Application{
  public static String SONGS_FOLDER="/home/roberto/Scaricati/Songs/";
  public static String SONGS_FOLDER_COPY="/home/roberto/Scaricati/Songs_Reduced/";
  public static String PROVA="/home/roberto/Scaricati/Prova/";

  public static void main(String[] args) throws IOException, InvalidStrategyException, ClassNotFoundException, Throwable{
    //Must be done in a more elegant way, as of now is just for testing purposes
//    File[] listOfFiles=new File(SONGS_FOLDER).listFiles();
//    Dataset dataset=new Dataset();
//    for(int i=0; i<listOfFiles.length; i+=1){
//      dataset.add(new SampleTextDocument(listOfFiles[i]));
//    }//end-for
//
//    //Ideally I'd like to run the two indexing algorithms in parallel via threads, don't really know what's the best approach.
//    Preprocessor[] preprocessors={new PorterStringPreprocessor(dataset.clone()), new PlainStringPreprocessor(dataset.clone())};
//    Index[] indexes={new BTreeIndex(2), new SoundexIndex()};
//    for(int i=0; i<indexes.length; i+=1){
//      Indexer.getInstance().setStrategy(new SPIMIStrategy(indexes[i], preprocessors[i]));
//      Indexer.getInstance().index();
//    }//end-for
//    Indexer.getInstance().stop();

//    Indexer.getInstance().setStrategy(new LMStrategy(new SoundedIndex(dataset.size()), preprocessor));
//    Indexer.getInstance().index();

    FileInputStream fileInputStream=new FileInputStream("/home/roberto/Scaricati/"+ BTreeIndex.class.getName() +".txt");
    ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
    Index i = (Index) objectInputStream.readObject();
    objectInputStream.close();
    FileInputStream fileInputStream2=new FileInputStream("/home/roberto/Scaricati/"+ SoundexIndex.class.getName() +".txt");
    ObjectInputStream objectInputStream2=new ObjectInputStream(fileInputStream2);
    Index i2 = (Index) objectInputStream2.readObject();
    objectInputStream2.close();

//    i.traverseDictionary();
    //TODO: insertion sort for docIDs, since in soundex index there's no guaranteed that docIds are in order
    System.out.println(i.getPostingList(new StringTerm("zucchini")));
    System.out.println(Arrays.toString(i.getPostingList(new StringTerm("battleground")).toArray()));
    System.out.println(Arrays.toString(i2.getPostingList(new StringTerm("zucchini")).toArray()));

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

    //CSV, 0 and 6 are respectively the title and the lyrics.
//    String oldPath="/home/roberto/Scaricati/song_lyrics.csv";
//    String path = "/home/roberto/Scaricati/dataset_en.csv";
//    String newPath="/home/roberto/Scaricati/dataset_reduced.csv";
////    HashSet<String> hashSet=new HashSet<>();
//    try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(newPath)).withSkipLines(0).withCSVParser(new RFC4180ParserBuilder().build()).build()) {
////      CSVWriter csvWriter = new CSVWriter(new FileWriter(newPath));
//      String[] values = null;
////      int i=0;
//      while (((values = csvReader.readNext()) != null)) {
////        hashSet.add(values[10]);
////        System.out.println(Arrays.toString(values));
////          if(values[1].equals("rock")){
////            csvWriter.writeNext(new String[]{values[0], values[6]});
//////            csvWriter.writeNext(new String[]{values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10]});
////          }//end-if
//          String title=values[0].replaceAll("[^a-zA-Z0-9-_\\.]", "_");
//          BufferedWriter writer = new BufferedWriter(new FileWriter(SONGS_FOLDER+title.substring(0, Math.min(title.length(), 250))+".txt"));
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
//    System.out.println("Done");
//    hashSet.forEach(System.out::println);

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
    launch();
  }

  @Override
  public void start(Stage stage) throws Exception{

  }
}
