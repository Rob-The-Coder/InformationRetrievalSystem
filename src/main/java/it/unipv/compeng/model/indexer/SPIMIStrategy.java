package it.unipv.compeng.model.indexer;

import it.unipv.compeng.model.index.Index;
import it.unipv.compeng.model.preprocessing.Preprocessor;
import it.unipv.compeng.model.term.Term;

import java.io.*;

public class SPIMIStrategy extends RunnableStrategy{
  /********************************/
  //Attributes
  /********************************/

  /********************************/
  //Constructors
  /********************************/
  public SPIMIStrategy(Index index, Preprocessor preprocessor){
    super(index, preprocessor);
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  @Override
  public void buildIndex() throws IOException{
    //SPIMI algorithm
    System.out.println("SPIMI");

    Term[] terms=null;

    int count=0;
    while(preprocessor.hasNextToProcess()){
      System.out.println("Indexing document: "+ count);
      terms=preprocessor.processNext();

      for(Term term : terms){
        index.addToDictionary(term);
      }//end-for
      count+=1;
    }//end-while

    index.traverseDictionary();
    super.storeIndex();
    //Some sort of sorting and then write output to file
//    Writer writer = new FileWriter("/home/roberto/Scaricati/index.json");
//    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//    gson.toJson(index, writer);
    //index.traverseDictionary();
  }
  /********************************/
}
