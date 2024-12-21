package it.unipv.compeng.model.document;

import java.io.IOException;
import java.util.Comparator;

public abstract class Document{
  /********************************/
  //Attributes
  /********************************/
  protected String title;
  protected int docId;
  protected int score=0;
  /********************************/
  //Constructors
  /********************************/

  /********************************/
  //Getter/Setter
  /********************************/
  public int getDocId(){
    return docId;
  }

  public String getTitle(){
    return title;
  }

  public int getScore(){
    return score;
  }

  public void incrementScore(int score){
    this.score+=score;
  }

  public static Comparator<Document> getDocIdComparator(){
    return new Comparator<Document>(){
      @Override
      public int compare(Document o1, Document o2){
        return Integer.compare(o1.getDocId(), o2.getDocId());
      }
    };
  }

  public static Comparator<Document> getScoreComparator(){
    return new Comparator<Document>(){
      @Override
      public int compare(Document o1, Document o2){
        return Integer.compare(o1.getScore(), o2.getScore());
      }
    };
  }

  /********************************/
  //Methods
  /********************************/
  public abstract String read() throws IOException;
  public abstract Document clone();

  @Override
  public boolean equals(Object obj){
    return this.getDocId()==((Document)obj).getDocId();
  }

  @Override
  public String toString(){
    return String.valueOf(docId);
  }
  /********************************/
}
