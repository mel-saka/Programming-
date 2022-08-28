import java.util.Scanner;

/**
 * Simple algorithm which takes in an input word and returns the number
 * of syllables in that word.
 * @author Mohammed El Saka
 */
class SyllableCounter{
    public static void main(String[] args) {
        String[] listOfWords;
        String word;
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Input: " );
        while(input.hasNext()) {
            //Takes input.

            
            word = input.nextLine();
            listOfWords = word.split("\\W+");
            for (int i=0; i< listOfWords.length; i++) {
                boolean wordChecker = true;
                word = listOfWords[i].toLowerCase();
                String checkword = word.toLowerCase();
                for (int j=0; j<checkword.length(); j++) {
                    if (Character.isDigit(checkword.charAt(j)) == true&&wordChecker==true) {
                        wordChecker = false;
                        System.out.println("ERROR: NOT WORD");
                    } else {
                        wordChecker = true;
                    }
                }

                if (wordChecker == true) {
                    System.out.println("syllables for " + "'" + word + "'" + " : " + Counter(word));
                }
            }
        }
    }


    /**
     * used to count the current words syllables by going through and counting vowels, while
     * applying various rules for what does and does not count as a syllable
     * @param word the string
     * @return the amount of syllables in a given word
     */
    public static int Counter(String word) {
        boolean currIsL = false;
        boolean currIsED = false;
        int count = 0;
        for(int i = 0; i<word.length(); i++){ //Iterate through every letter in the word.


            char current = word.charAt(i); //The current letter in the word


            char after = '\0'; //set after to null
            if(i+1 != word.length()){ //if the current letter is not the last one in the word.
                after = word.charAt(i+1); // store the leter after the current letter in the word,
            }

    /*
      Check to see if there is multiple vowels in a row creating a a singlular vowel sound. For example: "eau", (Case: ...-Vowel-Vowel-..)
      Check if the letter is not a silent e. (Case: ...-VOWEL-Silent"E"-...)
      Check if the letter after the Vowel is not 'Y'. (CASE: ...-Vowel-Y-...)
    */
            if(((isVowel(current) == true && isVowel(after) == false) && !(current == 'e' && i+1 == word.length()) && after != 'y' )){
                if(current == 'e' && currIsL == true ){ // Check if the current letter is 'E' and the previous letter was 'L', (case: 'LE').
                    currIsL = false; //Store that current letter is not L.
                }else if(current == 'e' && currIsED == true ){
                    currIsED = false;

                }else{
                    count++; //Count Syllable
                }

            }
            if(current == 'l' && after == 'e' && i+2 == word.length()){ // Check if the current letter is 'L' followed by an 'E'and it's at the end of the word, (case: 'LE').
                count++; //Count Syllable
                currIsL = true;  //store that L is Current
            }

            if(current == 'e' && after == 'e' && i+2 == word.length()){ // Check if the current letter is 'E' followed by an 'E' and it's at the end of the word, (case: 'EE').
                count++; //Count Syllable
            }
            if(current == 'e' && after == 'd' && i+2 == word.length()){ // Check if the current letter is 'E' followed by an 'D' and it's at the end of the word, (case: 'EE').
                count--; //Count Syllable
                currIsED = true; 
            }

            if(current == 'y'){ //Check if the current letter is 'Y'
                count++; //Count Syllable
            }
        }

        return(count);  // print Syllable count
    }

    /**
     * Method which returns true or false depending on whether the given char is a vowel.
     * @param c the given char to be looked at.
     * @return a true or false value which represents if the char is a vowel.
     */
    public static boolean isVowel(char c){
        if(c=='a'  || c=='e' ||  c=='i' ||  c=='o' ||  c=='u'){
            return true;
        }
        else
        {
            return false;
        }
    }

}