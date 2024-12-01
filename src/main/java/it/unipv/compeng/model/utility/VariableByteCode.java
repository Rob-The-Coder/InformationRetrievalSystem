package it.unipv.compeng.model.utility;

import it.unipv.compeng.model.utility.iterator.VBCIterator.IVBCIterable;
import it.unipv.compeng.model.utility.iterator.VBCIterator.IVBCIterator;
import it.unipv.compeng.model.utility.iterator.VBCIterator.VBCIterator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public class VariableByteCode implements IVBCIterable, Serializable{
  /********************************/
  //Attributes
  /********************************/
  private final BitSet code;
  private int currentNumberOfBytes;
  private transient int lastInserted;
  /********************************/
  //Constructors
  /********************************/
  public VariableByteCode(){
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

  public int getValue(int index){
    //Navigating to index
    int i=0;
    int p=0;
    while(i<index && p<code.length()){
      if(code.get(p)){
        i+=1;
      }//end-if
      p+=Byte.SIZE;
    }//end-while

    BitSet tmp=new BitSet(code.length());
    i=0;
    p=0;
    while(!code.get(p) && p<code.length()){
      if(i%Byte.SIZE!=0){
        tmp.set(i, code.get(i));
      }//end

      i+=1;
      p+=Byte.SIZE;
    }//end-while

    int ris=0;
    int fin=p+Byte.SIZE-((p/Byte.SIZE) + 1);
    for(i=0; i<fin; i+=1){
      ris=(int)(ris+(Math.pow(2, fin-i-1)* (tmp.get(i)? 1 : 0)));
    }//end-for

    return ris;
  }

  public int getCurrentNumberOfBytes(){
    return currentNumberOfBytes;
  }

  /********************************/
  //Methods
  /********************************/
//  @Serial
//  private void writeObject(ObjectOutputStream out) throws IOException{
//    System.out.println(this);
//    out.writeObject(this.code.toLongArray());
//    out.writeInt(this.currentNumberOfBytes);
//  }
//
//  @Serial
//  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
//    Long[] words=(Long[])in.readObject();
//    this.code=new BitSet(words);
//    this.currentNumberOfBytes=in.readInt();
//  }

  public void add(int n){
    addGap(n-lastInserted);
    lastInserted=n;
  }

  private void addGap(int gap){
    //Converting gap to variable byte code binary representation
    int necessaryBits=gap==0 ? 1 : (int)Math.floor(Math.log(gap)/Math.log(2))+1;
    int necessaryBytes=(int)Math.ceil((double)necessaryBits / (Byte.SIZE - 1));
    int padding=((necessaryBytes*Byte.SIZE) - necessaryBytes) - necessaryBits;
//    System.out.println("padding: " + padding);

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
//    print(binaryGap);

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

//    // Size of an integer is assumed to be 32 bits
//    int j=currentNumberOfBytes*Byte.SIZE;
//    int i=0;
//    int byteEndIndex=j+Byte.SIZE-1;
//
//    while(i<necessaryBits){
//      //Termination bit logic
//      if(j%Byte.SIZE==0){
//        //If the remaining bits are less than 7 (7+1=8) then we put 1
//        if(necessaryBits-i<=Byte.SIZE-1){
//          code.set(j);
//        }//end-if
//
//        //Incrementing pointer to the end of the byte
//        if(j!=0){
//          byteEndIndex+=Byte.SIZE;
//        }//end-if
//        currentNumberOfBytes+=1;
//      }else{
//        //i is the pointer to binaryGap, byteEndIndex is the pointer to the end of the current byte
//        code.set((byteEndIndex-(i%(Byte.SIZE-1))), binaryGap.get(i));
//        i+=1;
//      }//end-if
//      j+=1;
//    }//end-while
  }

  public boolean search(int n){
    boolean found=false;

    //Guess-based Binary search
    int left=0, right=currentNumberOfBytes;
    while(left<=right && !found){
      //Initial guess
      int mid=left+((right-left)/2);

      //Refining guess
      int pointer=(mid * Byte.SIZE)-Byte.SIZE;
      while(pointer>=0 && !code.get(pointer)){
        pointer-=Byte.SIZE;
      }//end-while
      mid=pointer+Byte.SIZE;
      pointer=mid;

      //Converting median value
      BitSet tmp=new BitSet(code.length());

      int i=0, lastK=0;
      boolean terminationBit=false;
      while(!terminationBit && pointer<getCurrentNumberOfBytes()*Byte.SIZE){
        //If first bit is not the terminator bit 1, I can simply read the next seven bits
        for(int j=0; j<Byte.SIZE-1; j+=1){
          int k=j + (i*(Byte.SIZE-1));
          tmp.set(k, code.get(k+i+1));
          lastK=k;
        }//end-for

        //Checking if pointed bit is the termination bit
        if(code.get(pointer)){
          terminationBit=true;
        }else{
          //Pointing to next byte
          pointer+=Byte.SIZE;
          i+=1;
        }//end-if
      }//end-while

      int ris=0;
      int fin=lastK;
      for(i=0; i<=fin; i+=1){
        ris=(int)(ris+(Math.pow(2, fin-i) * (tmp.get(i)? 1 : 0)));
      }//end-for

      // Check if x is present at mid
      if(n==ris){
        found=true;
      }//end-if

      // If x greater, ignore left half
      if(ris<n){
        left=(mid/Byte.SIZE) + 1;
        // If x is smaller, ignore right half
      }else{
        right=(mid/Byte.SIZE) - 1;
      }//end-if
    }//end-while

    return found;
  }

  public static void print(BitSet bitSet){
    int con=1;
    for(int i=0; i<bitSet.size(); i+=1){
      System.out.print(bitSet.get(i)? "1" : "0");
      System.out.print(con%Byte.SIZE==0 ? " " : "");
      con+=1;
    }//end-for
    System.out.println();
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

//    StringBuilder sb=new StringBuilder("[ ");
//    int[] array=toArray();
//    for(int i: array){
//      sb.append(i).append(" ");
//    }//end-for
//    sb.append("]");
//
//    return sb.toString();
  }

  public int[] toArray(){
    ArrayList<Integer> list=new ArrayList<>();
    IVBCIterator iterator=iterator();
    int prev=0;
    while(iterator.hasNext()){
      prev=prev+iterator.next();
      list.add(prev);
    }//end-while

    return list.stream().mapToInt(i->i).toArray();
  }

  @Override
  public IVBCIterator iterator(){
    return new VBCIterator(this);
  }
  /********************************/
}
