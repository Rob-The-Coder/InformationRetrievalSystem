package it.unipv.compeng.model.utility;

import it.unipv.compeng.model.utility.iterator.VBCIterator.IVBCIterable;
import it.unipv.compeng.model.utility.iterator.VBCIterator.IVBCIterator;
import it.unipv.compeng.model.utility.iterator.VBCIterator.VBCIterator;

import java.io.*;
import java.util.ArrayList;
import java.util.BitSet;

/***********************************************************/
//CLASS VariableByteCode IMPLEMENTS IVBCIterable FROM ITERATOR PATTERN.
//IMPLEMENTS A COMPRESSION MECHANISM FOR POSTING LISTS.
//BASED ON THE GAP VARIABLE VALUES ARE ADDED AS GAP WITH RESPECT TO
//THE LAST INSERTED VALUE OR AS IT IS RESPECTIVELY.
/***********************************************************/
public class VariableByteCode implements IVBCIterable, Serializable{
  /********************************/
  //Attributes
  /********************************/
  private final boolean GAP;
  private final BitSet code;
  private int currentNumberOfBytes;
  private transient int lastInserted;
  /********************************/
  //Constructors
  /********************************/
  public VariableByteCode(boolean gap){
    this.GAP=gap;
    this.code=new BitSet();
    this.currentNumberOfBytes=0;
    this.lastInserted=0;
  }
  /********************************/
  //Getter/Setter
  /********************************/
  public BitSet getCode(){
    return code;
  }

  public int getCurrentNumberOfBytes(){
    return currentNumberOfBytes;
  }
  /********************************/
  //Methods
  /********************************/
  public void add(int n){
    if(GAP){
      addGap(n-this.lastInserted);
      this.lastInserted=n;
    }else{
      addGap(n);
    }//end-if
  }

  private void addGap(int gap){
    //Converting gap to variable byte code binary representation
    int necessaryBits=gap==0 ? 1 : (int)Math.floor(Math.log(gap)/Math.log(2))+1;
    int necessaryBytes=(int)Math.ceil((double)necessaryBits / (Byte.SIZE - 1));
    int padding=((necessaryBytes*Byte.SIZE) - necessaryBytes) - necessaryBits;

    //Conversion of gap in binary
    BitSet binaryGap=new BitSet(necessaryBits);
    int con=0;
    boolean firstOneEncountered=false;
    for(int i=Integer.SIZE-1; i>=0; i-=1){
      int k = gap >> i;
      if((k & 1) > 0){
        binaryGap.set(con);
        firstOneEncountered=true;
      }//end-if
      if(firstOneEncountered){
        con+=1;
      }//end-if
    }//end-for

    int j=currentNumberOfBytes*Byte.SIZE;
    con=0;
    for(int i=0; i<necessaryBytes; i+=1){
      if(i==necessaryBytes-1){
        code.set(j);
      }//end-if
      j+=1;

      //Padding
      int k=0;
      while(padding>0 && k<(Byte.SIZE-1)){
        code.set(j, false);
        padding-=1;
        j+=1;
        k+=1;
      }//end-while

      //Inserting actual bits
      while(k<(Byte.SIZE-1)){
        code.set(j, binaryGap.get(con));
        k+=1;
        j+=1;
        con+=1;
      }//end-while
      this.currentNumberOfBytes+=1;
    }//end-for
  }

  public static void print(BitSet bitSet){
    int con=1;
    for(int i=0; i<bitSet.size(); i+=1){
      Logger.getInstance().log(bitSet.get(i)? "1" : "0");
      Logger.getInstance().log(con%Byte.SIZE==0 ? " " : "");
      con+=1;
    }//end-for
    Logger.getInstance().log("");
  }

  public void print(){
    print(code);
  }

  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();

    int con=1;
    for(int i=0; i<code.size(); i+=1){
      sb.append(code.get(i)? "1" : "0");
      sb.append(con%Byte.SIZE==0 ? " " : "");
      con+=1;
    }//end-for

    return sb.toString();
  }

  public int[] toArray(){
    ArrayList<Integer> list=new ArrayList<>();
    IVBCIterator iterator=iterator();

    if(this.GAP){
      int prev=0;
      while(iterator.hasNext()){
        prev=prev+iterator.next();
        list.add(prev);
      }//end-while
    }else{
      while(iterator.hasNext()){;
        list.add(iterator.next());
      }//end-while
    }//end-if
    return list.stream().mapToInt(i->i).toArray();
  }

  @Override
  public IVBCIterator iterator(){
    return new VBCIterator(this);
  }
  /********************************/
}
