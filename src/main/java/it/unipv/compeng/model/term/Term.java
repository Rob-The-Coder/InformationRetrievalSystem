package it.unipv.compeng.model.term;

import java.io.Serializable;

/***********************************************************/
//ABSTRACT REPRESENTATION OF A TERM
/***********************************************************/
public abstract class Term implements Comparable<Term>, Serializable{
  /********************************/
  //Attributes
  /********************************/
  protected String termEquivalentString;
  protected transient int docId;
  protected transient int termPositionInDocument;
  /********************************/
  //Getter/Setter
  /********************************/
  public int getDocId(){
    return docId;
  }

  public void setDocId(int docId){
    this.docId=docId;
  }

  public int getTermPositionInDocument(){
    return termPositionInDocument;
  }

  public void setTermPositionInDocument(int termPositionInDocument){
    this.termPositionInDocument=termPositionInDocument;
  }
  /********************************/
  //Methods
  /********************************/
  public abstract int compareTo(Term term);

  public abstract Term returnDigits(int len);

  public abstract String toString();

  public abstract void setString(String string);

  public abstract StringBuilder toStringBuilder();

  public abstract boolean equals(Term term);

  public abstract int hashCode();
  /********************************/
}
