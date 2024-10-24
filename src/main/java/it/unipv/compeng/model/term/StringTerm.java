package it.unipv.compeng.model.term;

public class StringTerm extends Term{
  private final String term;

  public StringTerm(String term) {
    this.term = term;
  }

  @Override
  public Term returnDigits(int len){
    return new StringTerm(term.chars().limit(len).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString());
  }

  @Override
  public int compareTo(Term term){
    return this.term.compareTo(((StringTerm) term).term);
  }

  @Override
  public String toString(){
    return term;
  }
}
