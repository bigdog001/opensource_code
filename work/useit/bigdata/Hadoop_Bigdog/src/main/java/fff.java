import java.util.Random;


public class fff {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
     String ss="abcdefghijklmnopqrstuvwxyz";
     
     Random random1 = new Random();
     int x=0;
     for(;;){
    	 x++;
    	 if(x%50==0){
    		 System.out.println(); 
    	 }
    	 System.out.print(ss.charAt(random1.nextInt(25))+" "); 
     }
	}

}
