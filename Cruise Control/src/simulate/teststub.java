package simulate;

public class teststub {
    /**
     * driver for the state machine.
     */
    public String testdriver(String line)  {
        String[] arrs=null;
        StringBuffer answerweget= new StringBuffer(32);
        int i=0;
        int currentstate;
        CruiseControl testone=new CruiseControl();
        arrs=line.split(",");
            while (i < arrs.length){
            //run the state-machine with input test case	
              if (arrs[i].equals("P1") || arrs[i].equals("P11") || arrs[i].equals("P19") || arrs[i].equals("P25")){
                testone.handleCommand("accelerator");
                currentstate=testone.control.getControlState();
                answerweget.insert(i,currentstate);

              }
              if (arrs[i].equals("P2") || arrs[i].equals("P10") || arrs[i].equals("P20") || arrs[i].equals("P23")){
                testone.handleCommand("brake");
                currentstate=testone.control.getControlState();
                answerweget.insert(i,currentstate);

              }
              if (arrs[i].equals("P3") || arrs[i].equals("P12") || arrs[i].equals("P18") || arrs[i].equals("P24")){
                testone.handleCommand("off");
                currentstate=testone.control.getControlState();
                answerweget.insert(i,currentstate);

              }
              if (arrs[i].equals("P4") || arrs[i].equals("P9") || arrs[i].equals("P15") || arrs[i].equals("P22")){
                testone.handleCommand("resume");
                currentstate=testone.control.getControlState();
                answerweget.insert(i,currentstate);

              }
              if (arrs[i].equals("P7") || arrs[i].equals("P13") || arrs[i].equals("P17") || arrs[i].equals("P26")){
                testone.handleCommand("engineOn");
                currentstate=testone.control.getControlState();
                answerweget.insert(i,currentstate);

              }
              if (arrs[i].equals("P5") || arrs[i].equals("P8") || arrs[i].equals("P28") || arrs[i].equals("P27")){
                testone.handleCommand("engineOff");
                currentstate=testone.control.getControlState();
                answerweget.insert(i,currentstate);

              }
              if (arrs[i].equals("P6") || arrs[i].equals("P14") || arrs[i].equals("P16") || arrs[i].equals("P21")){
                testone.handleCommand("on");
                currentstate=testone.control.getControlState();
                answerweget.insert(i,currentstate);

              }
              i++;
            }
       //System.out.println(answerweget);
       return (answerweget.toString()); //return the reality answer

    }
}