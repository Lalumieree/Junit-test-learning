package junittest;

import java.io.*;
import org.junit.*;
import static org.junit.Assert.*;
import simulate.*;

public class jtestEPF {
    teststub trysomework;
	@Before	
	public void setUp()  {
		trysomework=new teststub();
	}
	//@BeforeClass
	
	@Test
	public void testsimulate() throws IOException {
		try{
			//read test cases and expected output from txt files
			FileReader fr=new FileReader("D:/Cruise Control/EdgePairFaster.txt");
            BufferedReader br=new BufferedReader(fr);
            FileReader frr=new FileReader("D:/Cruise Control/EdgePairFasterOracle.txt");
            BufferedReader brr=new BufferedReader(frr);
            String line="";
            String answerneed="";
            String answerwegetnow="";
            while ((line=br.readLine())!=null) { //read test case one by one
        	   answerneed=brr.readLine();
        	   answerwegetnow=trysomework.testdriver(line);
        	   assertEquals(answerneed, answerwegetnow); //do comparison between reality output and expected output
            }
            br.close();
            fr.close();
            brr.close();
            frr.close();
		}catch (Exception e) {}
	}
}
