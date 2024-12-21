package it.unipv.compeng.model.utility;

import it.unipv.compeng.model.document.Document;

import java.util.*;
public class RetrieveManager{
  /********************************/
  //Attributes
  /********************************/
  private static RetrieveManager instance;
  private static final int RETURN_SIZE_LIMIT = 100;
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
    String signs=parseSigns(s);
    String[] splittedString=parseTerms(s);

    System.out.println(signs);
    System.out.println(Arrays.toString(splittedString));

    //Computing
    if(splittedString.length>=1){
      System.out.println("Retrieving initial term:");
      this.docs=IndexManager.getInstance().searchIndexes(splittedString[0]);
      System.out.println(splittedString[0] + " retrieved "+this.docs.size()+" documents");
      StringBuilder sb=new StringBuilder(signs);
      for(int i=0; i<sb.length(); i+=1){
        if(sb.charAt(i)==AND_SIGN){
          System.out.println("Computing and");
          and(IndexManager.getInstance().searchIndexes(splittedString[i+1]));
          System.out.println("Retrieved "+this.docs.size()+" documents after computing and");
        }else if(sb.charAt(i)==OR_SIGN){
          System.out.println("Computing or");
          or(IndexManager.getInstance().searchIndexes(splittedString[i+1]));
          System.out.println("Retrieved "+this.docs.size()+" documents after computing or");
        }//end-if
      }//end-for
    }//end-if

    System.out.println("returning: "+this.docs.size()+" documents.");
    this.docs.sort(Document.getScoreComparator().reversed());

    //Limiting return size to RETURN_SIZE_LIMIT
    ArrayList<Document> ret=new ArrayList<>();
    for(int i=0; i<Math.min(this.docs.size(), RETURN_SIZE_LIMIT); i+=1){
      ret.add(this.docs.get(i));
    }//end-for
    this.docs=ret;

    return this.docs;
  }

  public static String parseSigns(String query){
    return query.replaceAll("[a-zA-Z]+| ", "");
  }

  public static String[] parseTerms(String query){
    return query.replaceAll(" ", "").split("[+*]");
  }

  private void and(ArrayList<Document> docs){
    //Efficient AND between this.docs and docs
    if(this.docs.size()>docs.size()){
      ArrayList<Document> tmp;
      tmp=this.docs;
      this.docs=docs;
      docs=tmp;
    }//end-if

    //this.docs < docs
    ArrayList<Document> tmp=new ArrayList<>();
    int p1=0;
    int p2=0;
    while(p1<this.docs.size() && p2<docs.size()){
      if(this.docs.get(p1).getDocId()==docs.get(p2).getDocId()){
        this.docs.get(p1).incrementScore(docs.get(p2).getScore());
        tmp.add(this.docs.get(p1));
        p1+=1;
        p2+=1;
      }else if(this.docs.get(p1).getDocId()<docs.get(p2).getDocId()){
        p1+=1;
      }else{
        p2+=1;
      }//end-if
    }//end-while

    this.docs=tmp;
  }

  private void or(ArrayList<Document> docs){
    //OR between this.docs and docs
    ArrayList<Document> tmp=new ArrayList<>();
    int p1=0;
    int p2=0;

    while(p1<this.docs.size() && p2<docs.size()){
      if(this.docs.get(p1).getDocId()==docs.get(p2).getDocId()){
        tmp.add(this.docs.get(p1));
        p1+=1;
        p2+=1;
      }else if(this.docs.get(p1).getDocId()<docs.get(p2).getDocId()){
        tmp.add(this.docs.get(p1));
        p1+=1;
      }else{
        tmp.add(docs.get(p2));
        p2+=1;
      }//end-if
    }//end-while

    this.docs=tmp;
  }
  /********************************/
}
