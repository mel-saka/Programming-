
import java.util.Scanner;
import java.io.File; 
import java.io.FileNotFoundException;
/**
 * Date format algorithm which takes in an input from user and returns eihter
 * correct output
 * or returns if date formate is incorrect witha reason.
 * 
 * @author Mohammed El Saka
 */
public class DateValidation {
    private static char currSeprator = '\0';
    private static char prevSeprator = '\0';
    private static String day = "";
    private static String month = "";
    private static String year = "";
    private static boolean isLeap = false;
    private static String OrginalDate = "";
    private static String dayOutput = "";
    private static String monthOutput = "";
    private static String yearOutput = "";
    private static boolean invalid = false;
    private static String invalidInput = " -Invalid input- ";
    private static boolean noInput = false;
    private static boolean isDay = true;
    private static boolean isMonth = false;
    private static boolean isYear = false;
    private static boolean currDigit = false;
    private static int separatorCount = 0;
    private static boolean sepratorFormat = true;
    private static boolean sepratorDuplicate = false;
    private static boolean sepratorAtStart = true;
    private static boolean sepratorAtend = true;
    private static boolean sepratorAdjacents = true;



    public static void main(String[] args) {
        try {  
        File fileInput = new File("dateFile.txt");
        Scanner input = new Scanner(fileInput);  
        while (input.hasNextLine()) {
        String dateInput = input.nextLine();
    
        if(dateInput.trim().isEmpty()){
           invalidInput += " - error -  No input Found!";
           invalid = true;
           noInput =true;
        }

        OrginalDate = dateInput;
        CheckInput(dateInput);
        CheckSepratorInput(separatorCount, sepratorDuplicate,sepratorAdjacents,sepratorFormat,sepratorAtStart,sepratorAtend); 
        CheckDayInput(separatorCount, sepratorDuplicate, sepratorAdjacents);
        CheckMonthInput(separatorCount, sepratorDuplicate, sepratorAdjacents);
        CheckYearInput(separatorCount, sepratorDuplicate, sepratorAdjacents);

        if(invalid==false){
        CheckYear(year);
        }
        if(invalid==false){
        DateFormat(day, month, year);
        }
        if(invalid == true){
            System.out.println(OrginalDate + invalidInput);
            invalid = false;
        }
        day = "";
        month = "";
        year = "";
        currSeprator = '\0';
        prevSeprator = '\0';
        invalidInput = " -Invalid input- ";
        noInput = false;
        isDay = true;
        isMonth = false;
        isYear = false;
        currDigit = false;
        separatorCount = 0;
        sepratorFormat = true;
        sepratorDuplicate = false;
        sepratorAtStart = true;
        sepratorAtend = true;
        sepratorAdjacents = true;
    }
    input.close();
  } catch (FileNotFoundException e) {
    System.out.println("An error occurred.");
    e.printStackTrace();
  }

    }
/**
     * Examines if input from user is correct or not and identifies the day, month
     * and year
     * 
     * @param s input from user
     */
    public static void CheckInput(String s) {
        for (int i = 0; i < s.length(); i++) { // Iterate through every Charcter From stdin
            if (s.charAt(i) == '-' || s.charAt(i) == '/' || s.charAt(i) == ' ') { // check if seprator is present
                separatorCount++;
                currDigit = true;
                prevSeprator = currSeprator;
                currSeprator = s.charAt(i);

                if (currSeprator != prevSeprator && prevSeprator != '\0') { // check if diffrent seprators are present
                    sepratorFormat = false;

                }
                if (i != 0) { // check if there is two seprators in a row
                    if (s.charAt(i - 1) == '-' || s.charAt(i - 1) == '/' || s.charAt(i - 1) == ' ') {
                        sepratorDuplicate = true;
                    }
                }
                if(i==0){ //check if if there is arguments befor/after seprators
                    sepratorAtStart = false; 
                    sepratorAdjacents = false;
                }
                if(i== s.length()-1){ //check if if there is arguments befor/after seprators
                    sepratorAtend = false; 
                    sepratorAdjacents = false;
                }
                if (isDay == true) { // check if input before sperator was day
                    isDay = false;
                    isMonth = true; // next iteration is for checking month format
                } else if (isMonth == true) { // check if input before sperator was month
                    isMonth = false;
                    isYear = true; // next iteration is for checking month format
                }
            }
            if (isDay == true && currDigit == false) { // check if current character is for the day and not a sperator
                day += s.charAt(i);
            }
            if (isMonth == true && currDigit == false) { // check if current character is for the month and not a
                month += s.charAt(i);
            }
            if (isYear == true && currDigit == false) { // check if current character is for the year and not a sperator
                year += s.charAt(i);
            }
            currDigit = false;
        }

    }
  /**
     * Examines if usage of seprators is correct
     * and year
     * 
     * @param separatorCount The numbers of seprators present
     * @param sepratorDuplicate if there is duplicate seperators
     * @param sepratorAdjacents if there is arguments adjacent to seprators
     * @param sepratorFormat if there is diffrent seprators present
     * @param seperatorAtstart if there is a seprator at the start
     * @param seperatorAtend if there is a seprator at the end
     * 
     */
    public static void CheckSepratorInput(int separatorCount,boolean sepratorDuplicate, boolean sepratorAdjacents,boolean sepratorFormat,boolean sepratorAtStart, boolean sepratorAtend) {
        if (separatorCount == 0 && noInput == false) { // Check if there is only one argument
            day = "";
            invalidInput += "-only one argument is found. ";
            invalid = true;
        }
        if (separatorCount > 2 && sepratorDuplicate == false) {// check if there is too many argumnts

            invalidInput += "-There is too many seprators.- ";
            day = "";
            month = "";
            year = "";
            invalid = true;

        }
        if (separatorCount < 2 && separatorCount != 0 && sepratorDuplicate == false && sepratorAdjacents == true) { // check if there is only two arguments found

            invalidInput += "-This input is not a date- only two arguments are found. - ";
            invalid = true;
        }
        if (sepratorFormat == false) { // check if there is more than one seprator
            invalidInput += "-There is more than one type of seprator present. - ";
            invalid = true;
        }
        if (sepratorDuplicate == true) { // check if there is two seprators in a row
            invalidInput += "-There is duplicate seprators or spaces in a row. -";
            day = "";
            month = "";
            year = "";
            invalid = true;
        }
        if (separatorCount == 1) { // Check if there is only one sperator
            day = "";
            invalidInput += "only one seprator is found. ";
            invalid = true;
        }
         if(sepratorAtStart == false){ //check if seprators dont't have arguments adjacent to it
            invalidInput += "- date cannot have a seprator at the begining. ";
            invalid = true;
        }
        if(sepratorAtend== false){ //check if seprators dont't have arguments adjacent to it
            invalidInput += "- date cannot have a seprator at the end. ";
            invalid = true;
        }
    }

/**
     * Examines if day input  is correct
     * and year
     * 
     * @param separatorCount The numbers of seprators present
     * @param sepratorDuplicate if there is duplicate seperators
     * @param sepratorAdjacents if there is arguments adjacent to seprators

     * 
     */
    public static void CheckDayInput(int separatorCount,boolean sepratorDuplicate, boolean sepratorAdjacents){
        if (day.equals("") && sepratorDuplicate == false && separatorCount < 2 && separatorCount!= 0 && sepratorAdjacents == true) { // check if day has no input
            invalidInput += "-There is no input for day. - ";
            invalid = true;
        }
        if (day.length() > 2) { // check if day has more than two digits
            invalidInput += "-The day has more than two digits. - ";
            invalid = true;
        }
        if (ContainsJustDigits(day) == false && day != "") { //check if day is not a number
            invalidInput += "-The day is not a number. - ";
            invalid = true;
        }
        if(day.length() < 3 && ContainsJustDigits(day) == true && day != ""){ //check if day is out of range
            if(Integer.valueOf(day) > 31){
            invalidInput += "-Invalid day- Day cannot be greater than 31 days for any month. ";
            invalid = true;
            }
        }
        if(day.equals("0") || day.equals("00")){
            invalidInput += "-Invalid day- Day cannot be zero. ";
            invalid = true;
        }
    }

/**
     * Examines if month input is correct
     * and year
     * 
     * @param separatorCount The numbers of seprators present
     * @param sepratorDuplicate if there is duplicate seperators
     * @param sepratorAdjacents if there is arguments adjacent to seprators

     * 
     */
    public static void CheckMonthInput(int separatorCount,boolean sepratorDuplicate, boolean sepratorAdjacents){
        if (month.equals("") && sepratorDuplicate == false && separatorCount < 2 && separatorCount!= 0 && sepratorAdjacents == true) { // check if month has no input
            invalidInput += "-There is no input for month. - ";
            invalid = true;
        }
    if (month.length() < 3 && month != "") { //check if month has correct input for mm or 0m format
        if (ContainsJustDigits(month) == false) {
            invalidInput += "-The month is less than 3 digits but it's not a number. ";
            invalid = true;
        }else{
        { 
            int nMon = Integer.parseInt(month);
            if(nMon > 12 || nMon < 1){ //check if month is out of range
                invalidInput += "-Month out of range-There is only 12 months in a year. ";
                invalid = true;
            }
            }
        }
    }
        if (ContainsJustLetters(month) == true && month.length() > 3) { //check if month input is a real month
            if (month.length() > 9) {
                invalidInput += "-The Month input is not real month. - ";
                invalid = true;

            } else {
                invalidInput += "-only 3 lettered format is allowed for month if it's a non digit. - ";
                invalid = true;

            }
        }else if(ContainsJustLetters(month) == false && month.length() > 3){ //check if month input is a real month
            invalidInput += "-The Month input is not real month. - ";
            invalid = true;
        }
        if (ContainsJustLetters(month) == false && month.length() == 3) { //check if month input is incorrect for mmm format
            invalidInput += "-The Month input is not real month. - ";
            invalid = true;
        }
        if (ContainsJustLetters(month) == true && month.length() == 3) { //check if month input is incorrect for mmm format
            int upperCount = 0;
        for (int i = 0; i < month.length(); i++) { // Counts how many uppercase characters are present in month
            if (Character.isUpperCase(month.charAt(i))) {
                upperCount++;
            }
        }
        if (upperCount == 2) { // checks if there is two characters uppercase and one lower case
            invalidInput += "- Invalid upper/lowerCase Input for Month. ";
           invalid = true;
        }
        if (upperCount == 1 && Character.isLowerCase(month.charAt(0))) { // checks if is there is one uppercase
                                                                     // character but the first charcter is
                                                                     // lowercase
           invalidInput += "- Invalid upper/lowerCase Input for Month. - ";
           invalid = true;
        }
        String nMonth = month.toUpperCase();
        if (!nMonth.equals("JAN") && !(nMonth.equals("FEB")) && !nMonth.equals("MAR") && !nMonth.equals("APR") &&  !nMonth.equals("MAY") && !nMonth.equals("JUN") && !nMonth.equals("JUL")
                && !nMonth.equals("AUG") && !nMonth.equals("SEP") && !nMonth.equals("OCT") &&  !nMonth.equals("NOV") && !nMonth.equals("DEC")) { //checks if month is a real month
                    invalidInput += "-Month input is not a real Month. ";
                    invalid = true;
        } 
    }
}

/**
     * Examines if year input is correct
     * and year
     * 
     * @param separatorCount The numbers of seprators present
     * @param sepratorDuplicate if there is duplicate seperators
     * @param sepratorAdjacents if there is arguments adjacent to seprators

     * 
     */
public static void CheckYearInput(int separatorCount,boolean sepratorDuplicate, boolean sepratorAdjacents) {
    if (year.equals("") && sepratorDuplicate == false && separatorCount < 2 && separatorCount!= 0 && sepratorAdjacents == true) { //check if year has not input
        invalidInput += "-There is no input for year. - ";
        invalid = true;
    }
    if(year.length() > 4){ // checks if year has correct format
        invalidInput += "-The year has more than 4 digits but can only be yy or yyyy. - ";
        invalid = true;
    }
    if(year.length() == 1 && sepratorAdjacents == true){ // checks if year has correct input
        invalidInput += "-The year has only one digits but can only be yy or yyyy. -- ";
        invalid = true;
    }
    if(year.length() == 3){ // checks if year has correct input
        invalidInput += "-The year has 3 digits but can only be yy or yyyy. - ";
        invalid = true;
    }
    if(ContainsJustDigits(year) == false){ // checks if year has correct input
        invalidInput += "-The year has non-number present but can only have numbers. - ";
        invalid = true;
    }
    if(ContainsJustDigits(year) == true && year.length() == 4){ // checks if year is out of range
        //System.out.println("if statement entered");
        if(Integer.valueOf(year) < 1753 || Integer.valueOf(year) > 3000){
        invalidInput += "-Year out of range- ";
        invalid = true;
        }
    }
}


/**
     * checks if date format are one of the accepted cases
     * checks if dates are correct dates
     * 
     * @param d day of year
     * @param m month of year
     * @param y month of year
     */
    public static void DateFormat(String d, String m, String y) {
        if (m.length() <= 2) { // Check if the month format is mm or m or 0m
            int nMon = Integer.parseInt(m);
            if (nMon == 1 || nMon == 3 || nMon == 5 || nMon == 7 || nMon == 8 || nMon == 10 || nMon == 12) {
                int nday = Integer.parseInt(d);
                if (nday > 31) { // check if month has a maximum number of 31 days
                    invalidInput += "- DAY INVALID: The month specified has a maximum of 31 days. ";
                   invalid = true;
                }
            }
            if (nMon == 4 || nMon == 6 || nMon == 9 || nMon == 11) {
                int nday = Integer.parseInt(d);
                if (nday > 30) { // check if month has a maximum number of 30 days
                    invalidInput += "- DAY INVALID: The month specified has a maximum of 30 days. ";
                    invalid = true;
                }
            }
            if (nMon == 2) { // if current month is Feburary
                int nday = Integer.parseInt(d);
                if (isLeap == false && nday >= 28) { // check if month has a maximum number of 28 days in a common year
                    invalidInput +=  " - DAY INVALID: February has a maximum of 28 days in a common year. ";
                   invalid = true;
                    return;
                }
                if (isLeap == true && nday > 29) { //// check if month has a maximum number of 29 days in a leap year
                   invalidInput += "- DAY INVALID: February has a maximum of 29 days in a leap year. ";
                   invalid = true;
                    return;
                }
            }

            switch (nMon) { // matches number of month to output format
                case 1:
                    monthOutput = "Jan";
                    break;
                case 2:
                    monthOutput = "Feb";
                    break;
                case 3:
                    monthOutput = "Mar";
                    break;
                case 4:
                    monthOutput = "Apr";
                    break;
                case 5:
                    monthOutput = "May";
                    break;
                case 6:
                    monthOutput = "Jun";
                    break;
                case 7:
                    monthOutput = "Jul";
                    break;
                case 8:
                    monthOutput = "Aug";
                    break;
                case 9:
                    monthOutput = "Sep";
                    break;
                case 10:
                    monthOutput = "Oct";
                    break;
                case 11:
                    monthOutput = "Nov";
                    break;
                case 12:
                    monthOutput = "Dec";
            }
        }
        if (m.length() == 3) { // Check if the month format is first three letters of month
            String nMonth = m.toUpperCase();
            if (nMonth.equals("JAN") || nMonth.equals("MAR") || nMonth.equals("MAY") || nMonth.equals("JUL")
                    || nMonth.equals("AUG") | nMonth.equals("OCT") || nMonth.equals("DEC")) {
                int nday = Integer.parseInt(d);
                if (nday > 31) { // check if month has a maximum number of 31 days
                    invalidInput += "- DAY INVALID: The month specified has a maximum of 31 days. ";
                    invalid = true;
                }
            } else if (nMonth.equals("APR") || nMonth.equals("JUN") || nMonth.equals("SEP") || nMonth.equals("NOV")) {
                int nday = Integer.parseInt(d);
                if (nday > 30) { // check if month has a maximum number of 30 days
                    invalidInput += "- DAY INVALID: The month specified has a maximum of 30 days. ";
                    invalid = true;
                }
                
            } else if (nMonth.equals("FEB")) { // if current month is Feburary
                int nday = Integer.parseInt(d);
                if (isLeap == false && nday >= 28) { // check if month has a maximum number of 28 days in a common year
                    invalidInput +=  " - DAY INVALID: February has a maximum of 28 days in a common year. ";
                   invalid = true;
                    
                }
                if (isLeap == true && nday > 29) { // check if month has a maximum number of 29 days in a common year
                    invalidInput += "- DAY INVALID: February has a maximum of 29 days in a leap year. ";
                   invalid = true;
                    
                }
            }
            monthOutput = nMonth.substring(0, 1).toUpperCase() + nMonth.substring(1).toLowerCase();  //converts month input to output format
}
if(invalid == false){
if (d.length() == 1) { // check if day format is d
dayOutput = "0" + d; // change day format dd
} else {
dayOutput = d;
}
System.out.print(dayOutput + " " + monthOutput + " " + yearOutput); // Print Final date output
System.out.println("");
}
    }

    /**
     * checks if year has a correct input format
     * checks if year cases are met
     * checks if the year is a leap year
     * 
     * @param y month of year
     */
    public static void CheckYear(String y) {
        String currYear = "";
        if (y.length() == 2) { // if year format is yy
            int nYear = Integer.parseInt(y);
            if (nYear >= 50) { // if year is 50
                currYear = "19" + y;
            } else {
                currYear = "20" + y;
            }
        } else {
            currYear = y;
        }
        int theYear = Integer.parseInt(currYear);
        yearOutput = currYear;
        if (theYear % 4 != 0) { // check if leap year
            isLeap = false;
        } else if (theYear % 400 == 0) {
            isLeap = true;
        } else if (theYear % 100 == 0) {
            isLeap = false;
        } else {
            isLeap = true;
        }
    }
    
    /**
     * checks if an arguments contains only digits
     * 
     * @param s arguments of the date
     */
    public static Boolean ContainsJustDigits(String s){
        String str = "";
        for(int i=0; i<s.length(); i++){
            if(i < s.length() -1){
            str = s.substring(i, i+1);
            }else{
             str =   s.substring(i);
            }
         if(str.matches("[^0-9]+")){
            return false;
         }
        }
        return true;

    }

      /**
     * checks if an arguments contains only letters
     * 
     * @param s arguments of the date
     */
    public static Boolean ContainsJustLetters(String s){
        String str = "";
        for(int i=0; i<s.length(); i++){
            if(i < s.length() -1){
            str = s.substring(i, i+1);
            }else{
             str =   s.substring(i);
            }
         if(str.matches("[^a-z A-z]+")){
            return false;
         }
        }
        return true;

    }
}

