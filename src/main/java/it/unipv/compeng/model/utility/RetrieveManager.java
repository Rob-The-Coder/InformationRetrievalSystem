package it.unipv.compeng.model.utility;

import it.unipv.compeng.model.document.Document;
import it.unipv.compeng.model.term.DummyTerm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
public class RetrieveManager{
  /********************************/
  //Attributes
  /********************************/
  private static RetrieveManager instance;
  private ArrayList<Document> docs;
  private static final char AND_SIGN='*';
  private static final char OR_SIGN='+';
  /********************************/
  //Constructors
  /********************************/
  private RetrieveManager(){
    docs = new ArrayList<>();
  }
  /********************************/
  //Getter/Setter
  /********************************/
  public static RetrieveManager getInstance(){
    if(instance==null){
      instance=new RetrieveManager();
    }//end-if

    return instance;
  }
  /********************************/
  //Methods
  /********************************/
  public ArrayList<Document> booleanRetrieve(String s){
    //Understand how many OR and ANDS
    String signs=s.replaceAll("[a-zA-Z]+| ", "");
    String[] splittedString=s.replaceAll(" ", "").split("[+*]");

    System.out.println(signs);
    System.out.println(Arrays.toString(splittedString));

    //Computing
    if(splittedString.length>=1){
      System.out.println("Computing initial or");
      or(IndexManager.getInstance().searchIndexes(splittedString[0]));
      StringBuilder sb=new StringBuilder(signs);
      for(int i=0; i<sb.length(); i+=1){
        if(sb.charAt(i)==AND_SIGN){
          System.out.println("Computing and");
          and(IndexManager.getInstance().searchIndexes(splittedString[i+1]));
        }else if(sb.charAt(i)==OR_SIGN){
          System.out.println("Computing or");
          or(IndexManager.getInstance().searchIndexes(splittedString[i+1]));
        }//end-if
      }//end-for
    }//end-if

//    System.out.println(docs);
//    for(Document d:docs){
//      try{
//        System.out.println(d.read());
//      }catch(IOException e){
//        throw new RuntimeException(e);
//      }
//    }//end-for-each
//
    return docs;
  }

  private void and(ArrayList<Document> docs){
    //Efficient AND between this.docs and docs
    if(this.docs.size()>docs.size()){
      ArrayList<Document> tmp;
      tmp=this.docs;
      this.docs=docs;
      docs=tmp;
    }//end-if

    this.docs.retainAll(docs);
  }

  private void or(ArrayList<Document> docs){
    //OR between this.docs and docs
    for(Document d:docs){
      if(!this.docs.contains(d)){
        this.docs.add(d);
      }//end-if
    }//end-for-each
  }

  public static void main(String[] args){
    IndexManager.getInstance();
  }
  /********************************/
}