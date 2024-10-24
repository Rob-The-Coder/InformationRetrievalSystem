package it.unipv.compeng.model.term;

public abstract class Term implements Comparable<Term>{

  public abstract int compareTo(Term term);
  public abstract Term returnDigits(int len);
  public abstract String toString();
  public abstract boolean equals(Term term);
  //public abstract int hashCode();
}
