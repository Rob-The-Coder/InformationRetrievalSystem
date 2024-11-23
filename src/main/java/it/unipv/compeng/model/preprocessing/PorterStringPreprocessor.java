package it.unipv.compeng.model.preprocessing;

import it.unipv.compeng.model.term.StringTerm;
import it.unipv.compeng.model.term.Term;
import it.unipv.compeng.model.utility.iterator.DocumentIterator.IDatasetIterable;

public class PorterStringPreprocessor extends Preprocessor{
  /********************************/
  //Attributes
  /********************************/
  private StringBuilder stemBuilder=null;
  /********************************/
  //Constructors
  /********************************/
  public PorterStringPreprocessor(IDatasetIterable dataset){
    super(dataset);
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  @Override
  protected Term stem(String s){
    if(s.length()<3){
      return new StringTerm(s);
    }else{
      stemBuilder=new StringBuilder(s);
      step1a();
      step1b();
      step1c();
      step2();
      step3();
      step4();
      step5a();
      step5b();
      return new StringTerm(stemBuilder.toString());
    }//end-if
  }

  //SSES -> SS
  //IES -> I
  //SS -> SS
  //S ->
  private void step1a(){
    String appString=stemBuilder.toString();
    if(appString.endsWith("sses")){
      stemBuilder.replace(stemBuilder.length() - 4, stemBuilder.length(), "ss");
    }else if(appString.endsWith("ies")){
      stemBuilder.replace(stemBuilder.length() - 3, stemBuilder.length(), "i");
    }else if(!appString.endsWith("ss") && appString.endsWith("s")){
      stemBuilder.deleteCharAt(stemBuilder.length() - 1);
    }//end-if
  }

  //(m>0) EED -> EE
  //(*v*) ED ->
  //(*v*) ING ->
  //If the second or third of the rules is successful, the following is performed
  //AT -> ATE
  //BL -> BLE
  //IZ -> IZE
  //(*d AND NOT (*L OR *S OR *Z)) -> single letter
  //(m=1 AND *o) -> E
  private void step1b(){
    String appString=stemBuilder.toString();

    if(appString.endsWith("eed") && computeM(appString.substring(0, appString.length()-3))>0){
      stemBuilder.deleteCharAt(stemBuilder.length() - 1);
    }else if((appString.endsWith("ed") && isStarVStar(appString.substring(0, appString.length()-2))) || (appString.endsWith("ing") && isStarVStar(appString.substring(0, appString.length()-3)))){
      if(appString.endsWith("ed")){
        stemBuilder.delete(stemBuilder.length() - 2, stemBuilder.length());
      }else{
        stemBuilder.delete(stemBuilder.length() - 3, stemBuilder.length());
      }//end-if
      appString=stemBuilder.toString();

      if(appString.endsWith("at")){
        stemBuilder.replace(stemBuilder.length() - 2, stemBuilder.length(), "ate");
      }else if(appString.endsWith("bl")){
        stemBuilder.replace(stemBuilder.length() - 2, stemBuilder.length(), "ble");
      }else if(appString.endsWith("iz")){
        stemBuilder.replace(stemBuilder.length() - 2, stemBuilder.length(), "ize");
      }else if((isStarD(appString) && !(isStarCapitalLetter(appString, 'l') || isStarCapitalLetter(appString, 's') || isStarCapitalLetter(appString, 'z')))){
        stemBuilder.deleteCharAt(stemBuilder.length() - 1);
      }else if(computeM(appString)==1  && isStarO(appString)){
        stemBuilder.append("e");
      }//end-if
    }//end-if
  }

  //(*v*) Y -> I
  private void step1c(){
    String appString=stemBuilder.toString();

    if(appString.endsWith("y") && isStarVStar(appString.substring(0, appString.length()-2))){
      stemBuilder.replace(stemBuilder.length() - 1, stemBuilder.length(), "i");
    }//end-if
  }

  //(m>0) ATIONAL- > ATE
  //(m>0) TIONAL -> TION
  //(m>0) ENCI -> ENCE
  //(m>0) ANCI -> ANCE
  //(m>0) IZER -> IZE
  //(m>0) ABLI -> ABLE
  //(m>0) ALLI -> AL
  //(m>0) ENTLI -> ENT
  //(m>0) ELI -> E
  //(m>0) OUSLI -> OUS
  //(m>0) IZATION -> IZE
  //(m>0) ATION -> ATE
  //(m>0) ATOR -> ATE
  //(m>0) ALISM -> AL
  //(m>0) IVENESS -> IVE
  //(m>0) FULNESS -> FUL
  //(m>0) OUSNESS -> OUS
  //(m>0) ALITI -> AL
  //(m>0) IVITI -> IVE
  //(m>0) BILITI -> BLE
  private void step2(){
    String appString=stemBuilder.toString();

    String substringSeven=appString.length()>=7 ? appString.substring(0, appString.length() - 7) : "";
    String substringSix=appString.length()>=6 ? appString.substring(0, appString.length()-6) : "";
    String substringFive=appString.length()>=5 ? appString.substring(0, appString.length()-5) : "";
    String substringFour=appString.length()>=4 ? appString.substring(0, appString.length()-4) : "";
    String substringThree=appString.length()>=3 ? appString.substring(0, appString.length()-3) : "";

    if(appString.endsWith("ational") && computeM(substringSeven)>0){
      stemBuilder.replace(stemBuilder.length() - 7, stemBuilder.length(), "ate");
    }else if(appString.endsWith("tional") && computeM(substringSix)>0){
      stemBuilder.replace(stemBuilder.length() - 6, stemBuilder.length(), "tion");
    }else if(appString.endsWith("enci") && computeM(substringFour)>0){
      stemBuilder.replace(stemBuilder.length() - 4, stemBuilder.length(), "ence");
    }else if(appString.endsWith("anci") && computeM(substringFour)>0){
      stemBuilder.replace(stemBuilder.length() - 4, stemBuilder.length(), "ance");
    }else if(appString.endsWith("izer") && computeM(substringFour)>0){
      stemBuilder.replace(stemBuilder.length() - 4, stemBuilder.length(), "ize");
    }else if(appString.endsWith("abli") && computeM(substringFour)>0){
      stemBuilder.replace(stemBuilder.length() - 4, stemBuilder.length(), "able");
    }else if(appString.endsWith("alli") && computeM(substringFour)>0){
      stemBuilder.replace(stemBuilder.length() - 4, stemBuilder.length(), "al");
    }else if(appString.endsWith("entli") && computeM(substringFive)>0){
      stemBuilder.replace(stemBuilder.length() - 5, stemBuilder.length(), "ent");
    }else if(appString.endsWith("eli") && computeM(substringThree)>0){
      stemBuilder.replace(stemBuilder.length() - 3, stemBuilder.length(), "e");
    }else if(appString.endsWith("ousli") && computeM(substringFive)>0){
      stemBuilder.replace(stemBuilder.length() - 5, stemBuilder.length(), "ous");
    }else if(appString.endsWith("ization") && computeM(substringSeven)>0){
      stemBuilder.replace(stemBuilder.length() - 7, stemBuilder.length(), "ize");
    }else if(appString.endsWith("ation") && computeM(substringFive)>0){
      stemBuilder.replace(stemBuilder.length() - 5, stemBuilder.length(), "ate");
    }else if(appString.endsWith("ator") && computeM(substringFour)>0){
      stemBuilder.replace(stemBuilder.length() - 4, stemBuilder.length(), "ate");
    }else if(appString.endsWith("alism") && computeM(substringFive)>0){
      stemBuilder.replace(stemBuilder.length() - 5, stemBuilder.length(), "al");
    }else if(appString.endsWith("iveness") && computeM(substringSeven)>0){
      stemBuilder.replace(stemBuilder.length() - 7, stemBuilder.length(), "iven");
    }else if(appString.endsWith("fulness") && computeM(substringSeven)>0){
      stemBuilder.replace(stemBuilder.length() - 7, stemBuilder.length(), "ful");
    }else if(appString.endsWith("ousness") && computeM(substringSeven)>0){
      stemBuilder.replace(stemBuilder.length() - 7, stemBuilder.length(), "ous");
    }else if(appString.endsWith("aliti") && computeM(substringFive)>0){
      stemBuilder.replace(stemBuilder.length() - 5, stemBuilder.length(), "al");
    }else if(appString.endsWith("iviti") && computeM(substringFive)>0){
      stemBuilder.replace(stemBuilder.length() - 5, stemBuilder.length(), "ive");
    }else if(appString.endsWith("biliti") && computeM(substringSix)>0){
      stemBuilder.replace(stemBuilder.length() - 6, stemBuilder.length(), "ble");
    }//end-if
  }

  //(m>0) ICATE -> IC
  //(m>0) ATIVE ->
  //(m>0) ALIZE -> AL
  //(m>0) ICITI -> IC
  //(m>0) ICAL -> IC
  //(m>0) FUL ->
  //(m>0) NESS ->
  private void step3(){
    String appString=stemBuilder.toString();

    String substringFive=appString.length()>=5 ? appString.substring(0, appString.length()-5) : "";
    String substringFour=appString.length()>=4 ? appString.substring(0, appString.length()-4) : "";
    String substringThree=appString.length()>=3 ? appString.substring(0, appString.length()-3) : "";

    if(appString.endsWith("icate") && computeM(substringFive)>0){
      stemBuilder.replace(stemBuilder.length() - 5, stemBuilder.length(), "ic");
    }else if(appString.endsWith("ative") && computeM(substringFive)>0){
      stemBuilder.delete(stemBuilder.length() - 5, stemBuilder.length());
    }else if(appString.endsWith("alive") && computeM(substringFive)>0){
      stemBuilder.replace(stemBuilder.length() - 5, stemBuilder.length(), "al");
    }else if(appString.endsWith("iciti") && computeM(substringFive)>0){
      stemBuilder.replace(stemBuilder.length() - 5, stemBuilder.length(), "ic");
    }else if(appString.endsWith("ical") && computeM(substringFour)>0){
      stemBuilder.replace(stemBuilder.length() - 4, stemBuilder.length(), "ic");
    }else if(appString.endsWith("ful") && computeM(substringThree)>0){
      stemBuilder.delete(stemBuilder.length() - 3, stemBuilder.length());
    }else if(appString.endsWith("ness") && computeM(substringFour)>0){
      stemBuilder.delete(stemBuilder.length() - 4, stemBuilder.length());
    }//end-if
  }

  //(m>1) AL ->
  //(m>1) ANCE ->
  //(m>1) ENCE ->
  //(m>1) ER ->
  //(m>1) IC ->
  //(m>1) ABLE ->
  //(m>1) IBLE ->
  //(m>1) ANT ->
  //(m>1) EMENT ->
  //(m>1) MENT ->
  //(m>1) ENT ->
  //(m>1 AND (*S OR *T)) ION ->
  //(m>1) OU ->
  //(m>1) ISM ->
  //(m>1) ATE ->
  //(m>1) ITI ->
  //(m>1) OUS ->
  //(m>1) IVE ->
  //(m>1) IZE ->
  private void step4(){
    String appString=stemBuilder.toString();

    String substringFive=appString.length()>=5 ? appString.substring(0, appString.length()-5) : "";
    String substringFour=appString.length()>=4 ? appString.substring(0, appString.length()-4) : "";
    String substringThree=appString.length()>=3 ? appString.substring(0, appString.length()-3) : "";
    String substringTwo=appString.length()>=2 ? appString.substring(0, appString.length()-2) : "";

    if(appString.endsWith("al") && computeM(substringTwo)>1){
      stemBuilder.delete(stemBuilder.length() - 2, stemBuilder.length());
    }else if(appString.endsWith("ance") && computeM(substringFour)>1){
      stemBuilder.delete(stemBuilder.length() - 4, stemBuilder.length());
    }else if(appString.endsWith("ence") && computeM(substringFour)>1){
      stemBuilder.delete(stemBuilder.length() - 4, stemBuilder.length());
    }else if(appString.endsWith("er") && computeM(substringTwo)>1){
      stemBuilder.delete(stemBuilder.length() - 2, stemBuilder.length());
    }else if(appString.endsWith("ic") && computeM(substringTwo)>1){
      stemBuilder.delete(stemBuilder.length() - 2, stemBuilder.length());
    }else if(appString.endsWith("able") && computeM(substringFour)>1){
      stemBuilder.delete(stemBuilder.length() - 4, stemBuilder.length());
    }else if(appString.endsWith("ible") && computeM(substringFour)>1){
      stemBuilder.delete(stemBuilder.length() - 4, stemBuilder.length());
    }else if(appString.endsWith("ant") && computeM(substringThree)>1){
      stemBuilder.delete(stemBuilder.length() - 3, stemBuilder.length());
    }else if(appString.endsWith("ement") && computeM(substringFive)>1){
      stemBuilder.delete(stemBuilder.length() - 5, stemBuilder.length());
    }else if(appString.endsWith("ment") && computeM(substringFour)>1){
      stemBuilder.delete(stemBuilder.length() - 4, stemBuilder.length());
    }else if(appString.endsWith("ent") && computeM(substringThree)>1){
      stemBuilder.delete(stemBuilder.length() - 3, stemBuilder.length());
    }else if(appString.endsWith("ion") && (computeM(substringThree)>1 && (isStarCapitalLetter(substringThree, 's') || isStarCapitalLetter(substringThree, 't')))){
      stemBuilder.delete(stemBuilder.length() - 3, stemBuilder.length());
    }else if(appString.endsWith("ou") && computeM(substringTwo)>1){
      stemBuilder.delete(stemBuilder.length() - 2, stemBuilder.length());
    }else if(appString.endsWith("ism") && computeM(substringThree)>1){
      stemBuilder.delete(stemBuilder.length() - 3, stemBuilder.length());
    }else if(appString.endsWith("ate") && computeM(substringThree)>1){
      stemBuilder.delete(stemBuilder.length() - 3, stemBuilder.length());
    }else if(appString.endsWith("iti") && computeM(substringThree)>1){
      stemBuilder.delete(stemBuilder.length() - 3, stemBuilder.length());
    }else if(appString.endsWith("ous") && computeM(substringThree)>1){
      stemBuilder.delete(stemBuilder.length() - 3, stemBuilder.length());
    }else if(appString.endsWith("ive") && computeM(substringThree)>1){
      stemBuilder.delete(stemBuilder.length() - 3, stemBuilder.length());
    }else if(appString.endsWith("ize") && computeM(substringThree)>1){
      stemBuilder.delete(stemBuilder.length() - 3, stemBuilder.length());
    }//end-fi
  }

  //(m>1) E ->
  //(m=1 and not *o) E ->
  private void step5a(){
    String appString=stemBuilder.toString();

    String substringOne=!appString.isEmpty() ? appString.substring(0, appString.length()-1) : "";

    if(appString.endsWith("e") && computeM(substringOne)>1){
      stemBuilder.delete(stemBuilder.length() - 1, stemBuilder.length());
    }else if(appString.endsWith("e") && (computeM(substringOne)==1 && !isStarO(substringOne))){
      stemBuilder.delete(stemBuilder.length() - 1, stemBuilder.length());
    }//end-if
  }

  //(m > 1 and *d and *L) -> single letter
  private void step5b(){
    String appString=stemBuilder.toString();

    if(computeM(appString)>1 && isStarD(appString) && isStarCapitalLetter(appString, 'l')){
      stemBuilder.deleteCharAt(stemBuilder.length() - 1);
    }//end-if
  }

  private boolean isStarO(String s){
    if(s.length()<3){
      return false;
    }//end-if

    String appString=classifyChars(s);
    if(appString.endsWith("cvc")){
      char lastDigit=s.charAt(s.length()-1);
      return lastDigit != 'w' && lastDigit != 'x' && lastDigit != 'y';
    }//end-if
    return false;
  }

  private boolean isStarCapitalLetter(String s, char letter){
    return s.charAt(s.length()-1) == letter;
  }

  private boolean isStarD(String s){
    return !isVowel(s.charAt(s.length()-1)) && !isVowel(s.charAt(s.length()-2));
  }

  private boolean isStarVStar(String s){
    boolean found=false;
    int i=0;

    while(i<s.length() && !found){
      if(isVowel(s.charAt(i))){
        found=true;
      }else{
        i+=1;
      }//end-if
    }//end-while
    return found;
  }

  private int computeM(String s){
    s=getTypes(s);
    int i=1;
    int con=0;

    while(i<s.length()){
      if(s.charAt(i - 1) == 'v' && s.charAt(i) == 'c'){
        con+=1;
        i+=2;
      }else{
        i+=1;
      }//end-if
    }//end-while

    return con;
  }

  private String getTypes(String s){
    StringBuilder stringBuilder=new StringBuilder(classifyChars(s));

    //Grouping consonants and vowels
    int i=0;
    int j;
    while(i<stringBuilder.length()){
      j=i+1;
      while(j<stringBuilder.length() && stringBuilder.charAt(i)==stringBuilder.charAt(j)){
        j+=1;
      }//end-while
      if(j>i+1){
        stringBuilder.replace(i, j, String.valueOf(stringBuilder.charAt(i)));
      }//end-if
      i+=1;
    }//end-while

    return stringBuilder.toString();
  }

  private String classifyChars(String s){
    StringBuilder stringBuilder=new StringBuilder();

    //Marking consonants and vowels
    for(int i=0; i<s.length(); i+=1){
      char previous=i == 0 ? '\0' : s.charAt(i - 1);
      char current=s.charAt(i);
      if(isVowel(current)){
        stringBuilder.append('v');
      }else if(current == 'y'){
        if(previous != '\0' && isVowel(previous)){
          stringBuilder.append('c');
        }else{
          stringBuilder.append('v');
        }//end-if
      }else{
        stringBuilder.append('c');
      }//end-if
    }//end-for-each

    return stringBuilder.toString();
  }

  private boolean isVowel(char c){
    return (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
  }
  /********************************/
}
