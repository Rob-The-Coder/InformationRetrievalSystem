package it.unipv.compeng.model.preprocessing;

import it.unipv.compeng.model.document.Document;
import it.unipv.compeng.model.term.Term;
import it.unipv.compeng.model.utility.StopList;

import java.io.IOException;
import java.text.Normalizer;
import java.util.*;

public abstract class Preprocessor{
  /********************************/
  //Attributes
  /********************************/
  Document document;
  /********************************/
  //Constructors
  /********************************/
  public Preprocessor(Document document){
    this.document = document;
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  public final Term[] process() throws IOException{
    Scanner scanner = new Scanner(document.read());
    ArrayList<Term> terms = new ArrayList<>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();

      String[] tokens=tokenize(normalize(line));
      for(String token : tokens){
        if(!StopList.getInstance().contains(token)){
          terms.add(stem(caseFold(token)));
        }//end-if
      }//end-for-each
    }//end-while
    scanner.close();

    return terms.toArray(new Term[0]);
  }

  protected String[] tokenize(String s){
    return s.replaceAll("\\[.*?]|[-_—   \u200B ]", " ")
      .replaceAll("['’,.\"?()!;:`‘“/~”…{}+*^«&]", "")
      .split("\\s| | \n");
  }

  protected String normalize(String s){
    return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
  }

  protected String caseFold(String s){
    return s.toLowerCase(Locale.US);
  }

  protected abstract Term stem(String s);

  /********************************/
}
