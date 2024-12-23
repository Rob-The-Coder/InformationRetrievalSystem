package it.unipv.compeng.model.utility.iterator.VBCIterator;

import it.unipv.compeng.model.utility.VariableByteCode;

import java.util.BitSet;

/***********************************************************/
//CLASS VBCIterator IS A CONCRETE IMPLEMENTATION OF ITERATOR
/***********************************************************/
public class VBCIterator implements IVBCIterator{
  /********************************/
  //Attributes
  /********************************/
  private final VariableByteCode vbc;
  private int pointer;
  /********************************/
  //Constructors
  /********************************/
  public VBCIterator(VariableByteCode vba){
    this.vbc=vba;
    this.pointer=-Byte.SIZE;
  }
  /********************************/
  //Getter/Setter
  /********************************/
  @Override
  public int getPointer(){
    return this.pointer;
  }
  /********************************/
  //Methods
  /********************************/
  @Override
  public boolean hasNext(){
    return ((this.pointer+Byte.SIZE)/Byte.SIZE)<this.vbc.getCurrentNumberOfBytes();
  }

  @Override
  public int next(){
    if(!hasNext() && this.vbc.getCurrentNumberOfBytes()<=0){
      return -1;
    }//end-if
    this.pointer+=Byte.SIZE;

    BitSet code=this.vbc.getCode();
    BitSet tmp=new BitSet();

    int i=0, con=0, numberOfBytes=0;
    boolean terminationBit=false;
    while(!terminationBit && this.pointer<this.vbc.getCurrentNumberOfBytes()*Byte.SIZE){
      //Reading the 7 next bits from the termination bit
      for(int j=0; j<Byte.SIZE-1; j+=1){
        tmp.set(con, code.get(this.pointer+j+1));
        con+=1;
      }//end-for

      //Checking if pointed bit is the termination bit
      if(code.get(this.pointer)){
        terminationBit=true;
      }else{
        //Pointing to next byte
        this.pointer+=Byte.SIZE;
        i+=1;
      }//end-if

      numberOfBytes+=1;
    }//end-while

    int ris=0;
    int fin=(numberOfBytes*Byte.SIZE) - numberOfBytes;
    for(i=1; i<=fin; i+=1){
      ris+=(int)(Math.pow(2, fin-i)*(tmp.get(i-1)?1:0));
    }//end-for
    
    return ris;
  }
  /********************************/
}
