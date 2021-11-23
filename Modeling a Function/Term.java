class Term {//doesnt work
  private int A;
  private int B;
  private boolean isTrig;
  private Term whatInsideTrig;
  private int identifier;//if exists, chain rule, 0 is mult, 1 is divide, 2 is trig function with stuff inside
  private int whatTrig; //0 = sin, 1 = cos, 2 = tan, 3 = sec, 
  public Term(int A, int B){
    this.A = A;
    this.B = B;
    isTrig = false;
  }
  public Term(int multOrDivOrTrig){
    identifier = multOrDivOrTrig;
  }

  public Term(int A, int B, boolean isTrig, int whatTrig){
    this.A = A;
    this.B = B;
    this.isTrig = isTrig;
    this.whatTrig = whatTrig;
  }

  public void derivative(){ //takes derivative and returns if will need chain rule
   if(!willNeedChain()){
      if(!isTrig){
        A*=B;
        B--;
      }
      else{ //?????????
        if(whatTrig == 0){
          whatTrig++;
        } 
         else if(whatTrig == 1){
            whatTrig--;
            A*=(-1);
            }
          }
        }//bich}
  }
  public boolean willNeedChain(){
    if(whatTrig == 3 || whatTrig == 4 || whatTrig == 2){
      return true;
    }
    else{
      return false;
    }
  }
  public void integrate(){
    if(isTrig){
      B++;
      A/=B;
    }
    else{

    }
  }
  public boolean isIdentifier(){
    if(identifier != null){
      return true;
    }
    else{
      return false;
    }
  }
  public boolean returnID(){
    return identifier;
  }
  public String toString(){
    if(isIdentifier()){
      if(returnID()){
        return "*";
      }
      else{
        return "/";
      }
    }
    else{
      if(isTrig){//0 = sin, 1 = cos, 2 = tan, 3 = sec, 
        switch(whatTrig){
          case 0: return A + "sin(x)";
          case 1: return A + "cos(x)";
          case 2: return A + "tan(x)";
          case 3: return A + "sec(x)";
        }
      }
    }
  }
}
