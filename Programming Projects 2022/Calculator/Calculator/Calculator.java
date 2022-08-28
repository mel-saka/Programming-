import java.util.ArrayList;
import java.util.Scanner;
import java.lang.annotation.Target;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * This program calculates (n k) = (n!)/K!(n-k)! without using numeric Data
 * types.
 * 
 * @author Mohammed El Saka
 */
public class Calculator {
    /**
     * The Main method does the primary calculation of (n k)
     */
    public static void main(String[] args) {
        String input;
        String k;
        String numerator = "";
        String denominator = "";
        String nTarget = "";
        String kTarget = "1";
        String currentN = "";
        String currentK = "";
        boolean firstCount = true;
        boolean inputIs = true;
        Scanner sc = new Scanner(System.in);
        System.out.println("Number of Cards in a pack, n:");
        while (sc.hasNextLine()) {
            input = sc.nextLine();
            String n = "";
            if (input.indexOf(",") != -1) {
                n = input.substring(0, input.indexOf(","));
            } else {
                System.out.println("ERROR: no coma was found. Input must be: n,k");
                inputIs = false;
            }
            if (inputIs == true && ContainsJustDigits(n) == false) { // checks if input for n is valid
                System.out.println("ERROR: Number of cards in the pack, n, can only be a whole number!");
                inputIs = false;
            }
            k = input.substring(input.indexOf(",") + 1);
            // System.out.println("input k= " + k);
            if (inputIs == true && ContainsJustDigits(k) == false) { // checks if input for n is valid // change this
                System.out.println("ERROR: Number of cards drawn from the pack, k, can only be a whole number!");
                inputIs = false;
            }
            if (inputIs == true && isGreaterOrEqual(k, n) == true && !k.equals(n)) { // checks if input for n and k are
                                                                                     // valid
                System.out.println("ERROR: k cannot be larger than n");
                inputIs = false;
            }
            if (inputIs == true) {
                nTarget = subtract(n, subtract(k, "1")); // n - (k - 1)
                if (k.equals("1")) { 
                    System.out.println(n);
                    return;
                }
                if (k.equals(n)) {
                    System.out.println("1");
                    return;
                }

                while (!currentN.equals(nTarget)) { //while n is not equal to target
                    if (firstCount == true) {
                        currentN = subtract(n, "1"); //n -1
                        // System.out.println("n: " + currentN);
                        numerator = multiply(n, currentN); // n(n-1) 
                        firstCount = false;
                    } else {
                        currentN = subtract(currentN, "1");// n-1
                        numerator = multiply(numerator, currentN); //n(n-1)
                    }
                    if (numerator.length() > 6500) {
                        System.out.println("Undefined");
                        System.exit(0);
                    }
                }
                
                firstCount = true;
                while (!currentK.equals(kTarget)) { // when k is not equal to 1
                    if (firstCount == true) {
                        currentK = subtract(k, "1"); // k -1
                        denominator = multiply(k, currentK); // k(k-1)
                        firstCount = false;
                    } else {
                        currentK = subtract(currentK, "1");// 4
                        denominator = multiply(denominator, currentK);
                    }
                }


                System.out.println(longDivides(numerator, denominator));
            }
            numerator = "";
            denominator = "";
            nTarget = "";
            currentN = "";
            currentK = "";
            inputIs = true;
            firstCount = true;

        }
    }

    /**
     * multiply preforms Long multiplication on two numbers
     * 
     * @param x a number represented as a string
     * @param y a number represented as a string
     */
    public static String multiply(String x, String y) {
        int carry = 0;
        int nZero = 1;
        int ans = 0;
        String sum = "";
        ArrayList<Integer> xnum = new ArrayList<Integer>();
        ArrayList<Integer> ynum = new ArrayList<Integer>();
        ArrayList<String> ansList = new ArrayList<String>();

        for (int i = x.length() - 1; i >= 0; i--) { // store each digit of number x as an item in the array lsit in
                                                    // reverse order
            char currentNumber = x.charAt(i);
            int number = Character.getNumericValue(currentNumber);
            xnum.add(number);
        }

        for (int i = y.length() - 1; i >= 0; i--) {
            char currentNumber = y.charAt(i);
            int number = Character.getNumericValue(currentNumber); // store each digit of number y as an item in the
                                                                   // array lsit in reverse order
            ynum.add(number);
        }
        for (int i = 0; i < ynum.size(); i++) { // double nested loop to multiply the two numbers
            for (int h = 0; h < xnum.size(); h++) {
                ans = (ynum.get(i) * xnum.get(h)) + carry;
                String ansIndex = String.valueOf(ans);
                if (ansIndex.length() == 2) {
                    carry = Character.getNumericValue(ansIndex.charAt(0));
                } else {
                    carry = 0;
                }
                if (ansIndex.length() == 2) {
                    sum = ansIndex.charAt(1) + sum;
                } else {
                    sum = ansIndex + sum;
                }
                if (xnum.size() - 1 == h) {
                    String finalCarry = String.valueOf(carry);
                    sum = finalCarry + sum;
                    if (i != 0) {
                        String zero = "0";
                        for (int j = 0; j < nZero; j++) {
                            sum += zero;
                        }
                        nZero++;
                    }
                }

            }
            String finalSum = "";
            for (int s = 0; s < sum.length(); s++) {
                if (sum.charAt(s) != '0') {
                    finalSum = sum.substring(s);
                    break;
                } else {
                    finalSum = sum.substring(s + 1);
                }
            }
            ansList.add(finalSum);
            carry = 0;
            sum = "";
            finalSum = "";

        }
        String products = "";
        String prevProducts = "";
        int indexOfProducts = 0;
        if (ansList.size() > 1) { // adds all products from long dvision
            while (indexOfProducts < ansList.size()) {
                prevProducts = products;
                if (indexOfProducts == 0) {
                    products = addition(ansList.get(indexOfProducts), ansList.get(indexOfProducts + 1));
                } else if (indexOfProducts == ansList.size() - 1) {
                    products = addition(ansList.get(indexOfProducts), prevProducts);
                } else {
                    products = addition(ansList.get(indexOfProducts), ansList.get(indexOfProducts + 1));
                    products = addition(products, prevProducts);
                }
                indexOfProducts = indexOfProducts + 2;

            }
        } else {
            products = ansList.get(indexOfProducts);
        }
        if (products.equals("")) {
            products = "0";
        }
        return products;
    }

    /**
     * addition performs long addition on two numbers. It stores the numbers as
     * strings, and converts the
     * strings to integers when doing the addition. It also accounts for the carry
     * 1, and stores the result
     * as a string. This mitigates overflow without implementing java Big Integer.
     * 
     * @param num1 a number represented as a string
     * @param num2 a number represented as a string
     */
    public static String addition(String num1, String num2) {
        int carry = 0;
        String resultaddition = "";
        int indexnum2 = num2.length() - 1;
        int indexnum1 = num1.length() - 1;
        while (indexnum2 > -1 || indexnum1 > -1) {
            int currentnum1 = 0;
            int currentnum2 = 0;
            if (indexnum1 > -1) {
                currentnum1 = Integer.parseInt(Character.toString(num1.charAt(indexnum1))); // current num1
            } else {
                currentnum1 = 0;
            }
            if (indexnum2 > -1) {
                currentnum2 = Integer.parseInt(Character.toString(num2.charAt(indexnum2))); // current num2
            } else {
                currentnum2 = 0;
            }
            int result = currentnum1 + currentnum2 + carry;

            if (result > 9) {

                carry = Integer.parseInt(Integer.toString(result).substring(0, 1)); // last num
                resultaddition = String.valueOf(result).charAt(1) + resultaddition;
                indexnum2--;
                indexnum1--;
            } else {
                carry = 0;
                // System.out.println(result);
                resultaddition = String.valueOf(result) + resultaddition;
                indexnum2--;
                indexnum1--;

            }
        }
        if (carry != 0) {
            resultaddition = String.valueOf(carry) + resultaddition;
        }
        return resultaddition;

    }

    /**
     * subtract preforms Long subtraction on two numbers which are represented as
     * strings. It converts the
     * strings to integers when doing the subtraction. It accounts for the carry and
     * the +10 principles of
     * long subtraction. It also works left to right, and stores the result as a
     * string. This mitigates
     * overflow without implementing java Big Integer.
     * 
     * @param num1 a number represented as a string
     * @param num2 a number represented as a string
     */
    public static String subtract(String num1, String num2) {
        if (num1.equals(num2)) {
            return "0";
        }
        int carry = 0;
        int add10 = 0;
        String resultsubtract = "";
        int indexnum2 = num2.length() - 1;
        int indexnum1 = num1.length() - 1;
        while (indexnum2 > -1 || indexnum1 > -1) {
            int currentnum1 = 0;
            int currentnum2 = 0;
            if (indexnum1 > -1) {
                currentnum1 = Integer.parseInt(Character.toString(num1.charAt(indexnum1))); // current num1
            } else {
                currentnum1 = 0;
            }
            if (indexnum2 > -1) {
                currentnum2 = Integer.parseInt(Character.toString(num2.charAt(indexnum2))); // current num2
            } else {
                currentnum2 = 0;
            }
            int result = (currentnum1 + carry) - currentnum2;
            if (result < 0) {
                add10 = 10; // last num`
                result = (currentnum1 + carry + add10) - currentnum2;
                resultsubtract = String.valueOf(result).charAt(0) + resultsubtract;
                indexnum2--;
                indexnum1--;

                carry = -1;
                add10 = 0;
            } else {
                carry = 0;
                resultsubtract = String.valueOf(result) + resultsubtract;
                indexnum2--;
                indexnum1--;
            }
        }
        for (int x = 0; x < resultsubtract.length(); x++) {
            if (resultsubtract.charAt(x) != '0') {
                return resultsubtract.substring(x);
            }
        }
        return resultsubtract;

    }

    /**
     * subtract preforms Long devision on two numbers
     * 
     * @param x a number represented as a string
     * @param y a number represented as a string
     */
    public static String longDivides(String x, String y) {
        if (x.equals("0") || y.equals("0")) {
            return "0";
        }
        String quotient = "";
        String currQuotient = "";
        String product = "";
        String divisor = y;
        String dividend = x;
        int lenOfDivisor = divisor.length();
        String currDividend = "";
        int indexOfDividend = 0;
        String difference = "";
        String carry = "";
        boolean end = false;
        String finalQuotient = "";
        while (end == false) {

            if (indexOfDividend == 0) {
                currDividend = dividend.substring(0, lenOfDivisor);
                dividend = dividend.substring(lenOfDivisor);
            }
            if (dividend.equals("")) {
                end = true;
            }
            if (isGreaterOrEqual(currDividend, divisor) == false) {
                currQuotient = "0";

            } else {
                currQuotient = divides(currDividend, divisor);
            }
            if (end == true) {
                quotient += currQuotient;
                break;
            }
            product = multiply(divisor, currQuotient);
            difference = subtract(currDividend, product);
            carry = dividend.substring(0, 1);
            if (difference.equals("0") && carry.equals("0")) {
                currDividend = "0";
            }else if(difference.equals("0") && !carry.equals("0")){
                currDividend = carry;
            } else {
                currDividend = difference + carry;
            }
            dividend = dividend.substring(1);
            quotient += currQuotient;
            indexOfDividend++;
        }

        for (int q = 0; q < quotient.length(); q++) {
            if (quotient.charAt(q) != '0') {
                finalQuotient = quotient.substring(q);
                break;
            } else {
                finalQuotient = quotient.substring(q + 1);
            }
        }
        return finalQuotient;

    }

    /**
     * subtract preforms devision by reduction on two numbers
     * 
     * @param x a number represented as a string
     * @param y a number represented as a string
     */
    public static String divides(String x, String y) {
        String quotient = "";
        while (isGreaterOrEqual(x, y)) {
            x = subtract(x, y);
            quotient = addition(quotient, "1");
        }
        return quotient;
    }

    /**
     * isGreaterOrEqual compares x and y to check of x is equal or greater than y
     * 
     * @param x a number represented as a string
     * @param y a number represented as a string
     */

    public static boolean isGreaterOrEqual(String x, String y) {
        int xLen = x.length();
        int yLen = y.length();

        if (x.equals(y)) {
            return true;
        }
        if (xLen > yLen) {
            return true;
        } else if (xLen < yLen) {
            return false;
        }

        if (xLen == yLen) {
            for (int i = 0; i < xLen; i++) {
                if (x.charAt(i) > y.charAt(i)) {
                    return true;
                }

                if (x.charAt(i) < y.charAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * ContainsJustDigits checks if a string has only numbers present
     * 
     * @param s a string
     */
    public static Boolean ContainsJustDigits(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            if (i < s.length() - 1) {
                str = s.substring(i, i + 1);
            } else {
                str = s.substring(i);
            }
            if (str.matches("[^0-9]+")) {
                return false;
            }
        }
        return true;

    }
}
