package it.unipv.compeng.main;
import it.unipv.compeng.model.document.Document;
import it.unipv.compeng.model.document.SampleTextDocument;
import it.unipv.compeng.model.index.PositionalBTreeIndex;
import it.unipv.compeng.model.index.SoundedIndex;
import it.unipv.compeng.model.preprocessing.PorterStringPreprocessor;
import it.unipv.compeng.model.preprocessing.Preprocessor;
import it.unipv.compeng.model.term.StringTerm;
import it.unipv.compeng.model.term.Term;

import java.io.*;
import java.util.*;

public class Main{
  public static String SONGS_FOLDER="/home/roberto/Scaricati/Songs/";

  public static void main(String[] args) throws IOException{
    File[] listOfFiles=new File(SONGS_FOLDER).listFiles();
    Map<String, Integer> hashMap = new HashMap<>();

    for(int i=0; i<listOfFiles.length; i+=1){
//      System.out.println(listOfFiles[i].getName());
      Preprocessor preprocessor=new PorterStringPreprocessor(new SampleTextDocument(listOfFiles[i]));
      for(Term word:preprocessor.process()){
        if (hashMap.containsKey(word.toString())){
          hashMap.put(word.toString(), hashMap.get(word.toString()) + 1);
        }else{
          hashMap.put(word.toString(), 1);
        }//end-if
      }//end-for
      //System.out.println(Arrays.toString(preprocessor.process()));
    }//end-for-each
    hashMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(System.out::println);

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
  }
}
