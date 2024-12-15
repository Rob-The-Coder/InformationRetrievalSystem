package it.unipv.compeng.model.preprocessing;

import it.unipv.compeng.model.term.Term;
import it.unipv.compeng.model.utility.StopList;
import it.unipv.compeng.model.utility.iterator.DocumentIterator.IDatasetIterable;
import it.unipv.compeng.model.utility.iterator.DocumentIterator.IDatasetIterator;

import java.io.IOException;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.*;

public abstract class Preprocessor implements Serializable{
  /********************************/
  //Attributes
  /********************************/
  private transient IDatasetIterator datasetIterator;
  /********************************/
  //Constructors
  /********************************/
  public Preprocessor(IDatasetIterable dataset){
    this.datasetIterator = dataset.iterator();
  }

  public Preprocessor(){}
  /********************************/
  //Getter/Setter
  /********************************/
  public void setDataset(IDatasetIterable dataset){
    this.datasetIterator = dataset.iterator();
  }

  private int getPosition(){
    return datasetIterator.getPosition();
  }
  /********************************/
  //Methods
  /********************************/
  public final boolean hasNextToProcess(){
    return datasetIterator.hasNext();
  }

  public final Term process(String s){
    return stem(caseFold(normalize(s)));
  }

  public final Term[] processNext() throws IOException{
    ArrayList<Term> terms = new ArrayList<>();
    Scanner scanner=new Scanner(datasetIterator.next().read());

    int positionInDocument=0;
    while (scanner.hasNextLine()){
      String line = scanner.nextLine();

      String[] tokens=tokenize(caseFold(normalize(line)));
      for(String token : tokens){
        if(!StopList.getInstance().contains(token) && !token.isEmpty()){
          terms.add(stem(token));
          terms.getLast().setTermPositionInDocument(positionInDocument);
          terms.getLast().setDocId(getPosition());
          positionInDocument+=1;
        }//end-if
      }//end-for-each
    }//end-while
    scanner.close();

    return terms.toArray(new Term[0]);
  }

  protected String[] tokenize(String s){
    return s.replaceAll("\\[.*?]|[-_—   \u200B \u007F]", " ")
      .replaceAll("[<>'’,.\"?()!;:`‘“/~”…{}+*^«&]", " ")
      .split("\\s| |\n");
  }

  protected String normalize(String s){
    return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\x00-\\x7F]", " ");
  }

  protected String caseFold(String s){
    return s.toLowerCase(Locale.US);
  }

  protected abstract Term stem(String s);
  /********************************/
}
