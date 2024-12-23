package it.unipv.compeng.model.term;

/***********************************************************/
//CONCRETE REPRESENTATION OF A TERM WHICH IS A STRING
/***********************************************************/
public class StringTerm extends Term{
  /********************************/
  //Contructor
  /********************************/
  public StringTerm(String term) {
    super.termEquivalentString = term;
  }
  /********************************/
  //Methods
  /********************************/
  @Override
  public Term returnDigits(int len){
    return new StringTerm(super.termEquivalentString.chars().limit(len).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString());
  }

  @Override
  public int compareTo(Term term){
    return super.termEquivalentString.compareTo(((StringTerm)term).toString());
  }

  @Override
  public String toString(){
    return super.termEquivalentString;
  }

  @Override
  public StringBuilder toStringBuilder(){
    return new StringBuilder(super.termEquivalentString);
  }

  @Override
  public void setString(String string){
    super.termEquivalentString = string;
  }

  @Override
  public boolean equals(Term term) {
    return super.termEquivalentString.equals(term.toString());
  }

  @Override
  public int hashCode(){
    return super.termEquivalentString.hashCode();
  }
  /********************************/
}
