

import java.util.Scanner;

/*
 * @Author  Mohammed El Saka
 * Accepts alphabet input and returns longest word combination 
 * without repeating sequences.
 */
public class substrings {
	 public static void main(String[] args) {
		 	    Scanner sc = new Scanner(System.in);
		 	    System.out.println("Enter the alphabet letters:");
		 	    while(sc.hasNext()) {
		 	    	String alphabet = sc.nextLine();
		 	
		 	    	String[] words = alphabet.split("\\s+"); 
		 	    	for (int i=0; i<words.length; i++) {
		 	    		System.out.println(alphabetOuput(words[i]));
		 	    	}
		 	    }	    	
		 	    
	 }
	 
	 /*
	  * Method runs the entire code for outputting unique string and 
	  * checking errors.
	  * @param alphabet is the amount of alphabet letters inputted.
	  * @return is the no-repeat sequences string, or an error message.
	  */
	 public static String alphabetOuput(String alphabet) {
		        String sub = "";
		        String temp = "";
		        String[][] check = new String[alphabet.length()][alphabet.length()];
		        
		        
		        
		        boolean checked = true;
		        //adding elements to the checked double array.
		        for(int i =0; i < alphabet.length();i++){
		            for(int j = 0; j < alphabet.length();j++){
		                check[i][j] ="" + alphabet.charAt(i) + alphabet.charAt(j);
		            }
		        }
		        //adding to the resulting longest sequence.
		        for(int i = 0; i < alphabet.length();i++){
		            sub = sub + alphabet.charAt(i)+alphabet.charAt(i);
		        }
		        //adding the elements to a temporary string which are the tail end sequence after double character combinations. 
		        for(int i = 0; i < alphabet.length()-1;i++){
		            temp = temp + alphabet.charAt(i);
		            for(int j = alphabet.length()-1; j > i+1;j--){
		                temp = temp + alphabet.charAt(j);
		                temp = temp + alphabet.charAt(i);
		            }
		        }
		        //adding the tail end sequence after double character combinations. 
		        for(int i =temp.length()-1; i > -1 ;i--){
		            sub = sub + temp.charAt(i);
		        }

		        //Error checking to determine if repeating inputs were used. 
		        for(int i =0; i < alphabet.length();i++){
		            for(int j = 0; j < alphabet.length();j++) {
		                checked=false;
		                for(int k = 0; k < sub.length()-1; k++){
		                    if(check[i][j].equals("" + sub.charAt(k) + sub.charAt(k+1))){
		                        if(checked==false){
		                            checked=true;
		                        } else{
		                        	return ("error - entered same character more than once");
		                          
		                        }
		                    }
		                }
		                if(checked==false){
		                    return ("checking if entered repeating substrings");
		                 
		                }
		            }
		        }
		        
		        //returning the resulting longest string. 
		        return ("Longest string that contains no repeated sequences: " + sub +
		        		"\nLength of string: " + sub.length());
		        
		    
	 }
}
