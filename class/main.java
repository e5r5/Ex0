

import java.util.Date;

public class main {
    
    public static void main(String [] args){
    
			String name_file = args[0];
			String name_file_BL =args[1];
			long start = new Date().getTime();
			System.out.println(" Ex1: partual solution");
			System.out.println("Loading graph file: " + name_file + " runing a test " + name_file_BL);
			Graph g = new Graph(name_file);
			long s1 = new Date().getTime();
			System.out.println("Done Loading graph file in: " + (s1-start) +  "  ms" );
			System.out.println("start algo : ");
		     g.BL(name_file_BL);
			long s2 = new Date().getTime();
			System.out.println("Done!!!  Total time: " + (s2 - start) + "  ms");
		
    }
}
