import java.util.Scanner;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * This program Translates a given english phrase into Maori for 
 * a specifed set of verbs in the past, present and future tense
 * 
 * @author Mohammed El Saka
 */
public class MaoriTranslator {
    private static HashMap<String,String> pronouns = new HashMap<String, String>();
    private static HashMap<String,Integer> TenseRefTypes = new HashMap<String, Integer>();
    private static HashMap<String,String> verbs = new HashMap<String, String>();
    private static HashMap<String,String> pastVerbs = new HashMap<String, String>();
    private static HashMap<String,String> pastVerbsED = new HashMap<String, String>();
    private static String REGEX = "[a-z]";
    private static String REGEX1 = "\\(";
    private static String REGEX2 = "\\)";
    private static String REGEX3 = "[0-9]";
    private static String REPLACE = "";
    /**
 * The main method calls all the translation methods 
 * and  prints the relevant answer
 * 
 */
    public static void main(String[] args) {
        //key and value, (English, Maori)
        pronouns.put("i","au");
        pronouns.put("she","ia");
        pronouns.put("he", "ia");
        pronouns.put("we","mātou");
        pronouns.put("we(2incl)","tāua");
        pronouns.put("we(2excl)","māua");
        pronouns.put("we(3incl)","tātou");
        pronouns.put("we(3excl)","mātou");
        pronouns.put("we(excl)","mātou");
        pronouns.put("we(incl)","tātou");
        pronouns.put("you","koe");
        pronouns.put("you(1incl)","koe");
        pronouns.put("you(2incl)","kōrua");
        pronouns.put("you(3incl)","koutou");
        pronouns.put("you(incl)","koutou");
        pronouns.put("they","rātou");
        pronouns.put("they(2excl)","rāua");
        pronouns.put("they(3excl)","rātou");
        pronouns.put("they(excl)","rātou");
        //key and value, (referential verb, type)
        TenseRefTypes.put("am", 1);
        TenseRefTypes.put("is", 2);
        TenseRefTypes.put("are", 3);
        TenseRefTypes.put("did", 4);
        TenseRefTypes.put("will", 5);
        TenseRefTypes.put("", 6);
        //key and value, (English, Maori)
        verbs.put("go", "haere");
        verbs.put("make","hanga");
        verbs.put("see","kite");
        verbs.put("want","hiahia");
        verbs.put("call","karanga");
        verbs.put("ask","pātai");
        verbs.put("read","pānui");
        verbs.put("learn","ako");
        //key and value, (English, Maori)
        pastVerbs.put("went","haere");
        pastVerbs.put("made","hanga");
        pastVerbs.put("saw","kite");
        pastVerbs.put("read","pānui");
        pastVerbs.put("learnt","ako");
        pastVerbsED.put("wanted", "hiahia");
        pastVerbsED.put("called", "karanga");
        pastVerbsED.put("asked","pātai");
        pastVerbsED.put("learned","ako");
        
        String phrase = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Type sentence:");
        while (sc.hasNextLine()) {
        phrase = sc.nextLine();
        phrase = phrase.toLowerCase();
        String sentence = phrase;
        String pronoun = "";
        String refVerb = "";
        String verb = "";
        String maoVerb = "";
        String ogVerb = "";
        boolean inputIs = true;
        String starter = "";
        
        String[] words = sentence.split("\\s+"); 
        for(int i=words.length-1; i>=0; i--) { //iterates through the input and finds the Pronoun, referential verb and verb
          if(i == words.length-1){
              verb = words[i];
          }
          if(i == words.length-2 && i!= 0 && !words[i].substring(words[i].length()-1).equals(")")){
             refVerb = words[i];
          }
          if(words[i].substring(words[i].length()-1).equals(")")|| words[i].substring(0,1).equals("(")){
            pronoun = words[i]  + pronoun;
          }
          if(i == 0){
            pronoun = words[i]  + pronoun;
          }      
    }
    ogVerb = verb;
     pronoun = brackets(pronoun);

    if(TenseRefTypes.get(refVerb) < 4 && TenseRefTypes.get(refVerb) != null){ //translates for phrases with present tense and a refrential verb
        if(pronouns.get(pronoun) == null|| pronounsType(pronoun) != TenseRefTypes.get(refVerb) || TenseRefTypes.get(refVerb) == null){
        inputIs = false;
       }
       if(inputIs == true){
           if(verb.contains("ing")){ 
               verb = (verb.substring(0,verb.indexOf("ing")));
               if(verb.equals("mak")){
                   verb = "make";
                   maoVerb = verbs.get(verb);
               }else{
               maoVerb = verbs.get(verb);
               }
               starter = "Kei te";
           }
    }
    }else if(TenseRefTypes.get(refVerb) == 4 && verbs.get(verb) != null && pronouns.get(pronoun) != null){
        maoVerb = verbs.get(verb); //translates phrases with past tense and a refrential verb
        starter = "I";
    }else if(TenseRefTypes.get(refVerb) == 5 && verbs.get(verb) != null && pronouns.get(pronoun) != null){
        maoVerb = verbs.get(verb); //translates phrases with with future tense and a refrential verb
        starter = "Ka";
    } else if(TenseRefTypes.get(refVerb) == 6){ //translates phrases with no refrential verb
        if(pronoun.equals("he") || pronoun.equals("she")){
             if(verb.equals("goes")){
                verb = "go";
                maoVerb = verbs.get(verb);
                starter = "Kei te";
             }else if(verb.contains("ed")){
                    maoVerb = pastVerbsED.get(verb);
                    starter = "I";
            
            }else if(verb.substring(verb.length()-1).equals("s")){
                verb = verb.substring(0,verb.length()-1);
                maoVerb = verbs.get(verb);
                starter = "Kei te";
            }else if(verb.equals("read")){
                maoVerb = pastVerbs.get(verb);
                starter = "I";
            }else if(pastVerbs.get(verb) != null){
                maoVerb = pastVerbs.get(verb); 
                starter = "I";
            }else{
                inputIs = false;
            }
        }
        if(verb.equals("read") &&  starter.equals("")){
            maoVerb = pastVerbs.get(verb);
            starter = "I";
        } else if(verbs.get(verb) != null &&  starter.equals("")){
            maoVerb = verbs.get(verb); 
            starter = "Kei te";
        }else if(pastVerbs.get(verb) != null && starter.equals("")){
            
            maoVerb = pastVerbs.get(verb); 
            starter = "I";
        }
        if(verb.contains("ed")){
            maoVerb = pastVerbsED.get(verb);
            starter = "I";
        }
    }
    if(pronouns.get(pronoun) == null || inputIs == false){ //checks for false inputs
        System.out.println(phrase +" : invalid sentence"); 
        inputIs = false;
    }else if(maoVerb == null || maoVerb.equals("")){
        inputIs = false;
        if(!(verbs.get(ogVerb) == null) || !(pastVerbs.get(ogVerb) == null) || !(pastVerbsED.get(ogVerb) == null)){
            System.out.println(phrase +" : invalid sentence"); 
        }else{ 
        System.out.println("unknown verb: " + ogVerb);
        }
    }
    if(inputIs == true){
     System.out.println(starter +" " + maoVerb +" " + pronouns.get(pronoun)); // prints answer;
        }
    }
    }
  /**
 * The pronountype method returns the english pronoun type
 * @param engPronoun The english pronoun.
 * 
 */
    public static int pronounsType(String engPronoun) {  
        int proType = 0;
       if(engPronoun.equals("i" )){
           proType = 1;
       }else if(engPronoun.equals("she") || engPronoun.equals("he")){
           proType = 2;

       }else if(pronouns.get(engPronoun) == null){
           proType = 0;
       }else{
           proType = 3;
       }
       return proType;
    }
/**
 * The brackets method checks the number of subjects in bracketed
 * inputs.
 * @param Pronoun The english pronoun.
 * 
 */
    public static String brackets(String pronoun){
        String word = pronoun;
        String word2 = "";
        String word3 = "";
        String word4 = pronoun;
        Pattern pattern = Pattern.compile(REGEX);
        Pattern pattern1 = Pattern.compile(REGEX1);
        Pattern pattern2 = Pattern.compile(REGEX2);
        Pattern pattern3 = Pattern.compile(REGEX3);
       
        Matcher matcher = pattern.matcher(word);
        word = matcher.replaceAll(REPLACE);
        word2 = word;
         Matcher matcher1 = pattern1.matcher(word2);
        word2 = matcher1.replaceAll(REPLACE);
        word3 = word2;
        Matcher matcher2 = pattern2.matcher(word3);
        word3 = matcher2.replaceAll(REPLACE);
        
        if(word3.equals("1") || word3.equals("2") || word3.equals("3") || word3.equals("") ){
            return pronoun;
        }else{
            Matcher matcher3 = pattern3.matcher(word4);
            word4 = matcher3.replaceAll(REPLACE);
            return word4;
        }
    }
}
