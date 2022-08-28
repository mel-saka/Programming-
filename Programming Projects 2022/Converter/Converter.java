
import java.util.Arrays;

/**
 * Converter class with varioyus functions used to help convert
 * numbers to different types of binary and floats.
 *
 * @author  Mohammed El Saka
 */
public class Converter {
    public Converter(){

    }

    /**
     * converts bytes into a binary String which is easier to work with
     * @param bytes the bytes to be converted
     * @return a binary string representing the bytes.
     */
    public static String bytesToString(byte[] bytes){
        String s ="";
        String temp = "";
        for(int i = 0; i < bytes.length; i++) {
            int result = bytes[i] & 0xff;
            temp = Integer.toBinaryString(result);
            while(temp.length() != 8){
                temp = '0' + temp;
            }
            s += temp;
        }
        return s;

    }

    /**
     * converts a string back into bytes for output.
     * @param s the string to be converted
     * @return an array of bytes representing the string
     */
    public static byte[] stringToBytes(String s){
        byte[] bytes = new byte[s.length()/8];
        for(int i = 0; i < bytes.length; i++){
            bytes[i] = (byte)(Integer.parseInt(s.substring(8*i, 8+8*i), 2) & 0xff) ;
        }
        return bytes;
    }

    /**
     * Converts Binary in the form of an array of booleans to a string of
     * 1s and 0s.
     * @param b the boolean array to be converted.
     * @return a string of 1s and 0s representing binary.
     */
    public static String binaryToString(boolean[] b){
        String s = "";
        for(int i = 0; i < b.length;i++){
            if(b[i]){
                s = s + "1";
            } else{
                s = s + "0";
            }
        }
        return s;
    }

    /**
     * Converts a string of 1s and 0s to an array of booleans representing
     * binary.
     * @param s the string to be converted.
     * @return a corresponding array of booleans.
     * @throws Exception if the string given is not in proper binary form.
     */
    public static boolean[] stringToBinary(String s) throws Exception{
        boolean[] b = new boolean[s.length()];
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '0'){
                b[i] = false;
            } else if(s.charAt(i) == '1'){
                b[i] = true;
            } else{
                throw new Exception("INPUT FROM FILE NOT BINARY");
            }
        }
        return b;
    }

    /**
     * converts binary representing a fraction into decimal.
     * @param b an array of booleans representing the fraction in binary.
     * @return the fraction in the form of a double.
     */
    public static double binaryToFraction(boolean[] b){
        double f = 0;
        for(int i = 0; i < b.length; i++){
            if(b[i]){
                f += Math.pow(0.5, i+1);
            }
        }
        return f;
    }

    /**
     * converts binary in the form of an array of booleans into a decimal number.
     * @param b the array of booleans to be converted.
     * @return a number the binary represented.
     */
    public static double binaryToNumber(boolean[] b){
        double n = 0;
        for(int i = 0; i < b.length; i++){
            if(b[b.length-(i+1)]){
                n += Math.pow(2, i);
            }
        }
        return n;
    }

    /**
     * converts a decimal number into an array of booleans representing binary.
     * @param n the number to be converted.
     * @return an array of booleans representing the number in binary.
     */
    public static boolean[] numberToBinary(double n) {
        if(n == 0) {
            return (new boolean[]{});
        }
        if(n<0) {
            n = -n;
        }
        int i = (int) (Math.log10(n) / Math.log10(2));
        boolean[] b = new boolean[i+1];
        Arrays.fill(b, false);
        b[0] = true;
        n -= Math.pow(2, i);
        while (n != 0){
            i = (int) (Math.log10(n) / Math.log10(2));
            b[(b.length-1) - i] = true;
            n -= Math.pow(2, i);
        }
        return b;
    }

    /**
     * converts a decimal fraction into binary.
     * @param n the decimal fraction to be converted.
     * @return an array of booleans representing the number in binary.
     */
    public static boolean[] fractionToBinary(double n){

        if(n==0){
            return (new boolean[]{false});
        }
        if(n < 0){
            n = -n;
        }
        boolean[] b = new boolean[((int)(Math.ceil(Math.log10(n)/Math.log10(0.5)))) + 64];
        int i = 0;
        while(n!=0){
            i = (int)(Math.ceil(Math.log10(n)/Math.log10(0.5)));
            /*if(i>63){
                if(i==64){
                    i--;
                    while(true){
                        if(b[i]==true){
                            b[i]=false;
                            i--;
                        }else{
                            b[i] = true;
                            break;
                        }
                    }
                }
                break;
            }*/
            if(i > b.length){
                break;
            }
            b[i-1] = true;
            n -= Math.pow(0.5, i);
        }
        return b;
    }

    /**
     * converts a decimal number to a binary float in the form of IBM 360 of either
     * single or double precision.
     * @param n the number to be converted
     * @param isSingle a boolean representing either single or double precision.
     * @return an array of booleans representing a binary float.
     */
    public static boolean[] numToFloat360(double n, boolean isSingle){
        boolean[] output;
        boolean negative = false;
        boolean[] fraction;

        if(isSingle){
            output = new boolean[32];
            fraction = new boolean[24];
        } else{
            output = new boolean[64];
            fraction = new boolean[56];
        }


        Arrays.fill(fraction, false);

        if(n < 0){
            negative = true;
        }
        boolean[] whole = numberToBinary((int)n);
        boolean[] decimal = fractionToBinary(n - (int)n);
        int roundedLength;
        if(whole.length%4!=0) {
            roundedLength = whole.length + 4 - (whole.length % 4);
        } else{
            roundedLength = whole.length;
        }

        int k = output.length-1;
        if(decimal[(k) - (roundedLength+7)]) {
            while (true) {
                if(decimal[(k) - (roundedLength+7)]) {
                    decimal[k - (roundedLength+7)] = false;
                    k--;
                } else {
                    decimal[k - (roundedLength+7)] = true;
                    break;
                }
            }
        }
        int remainder = 0;
        if(whole.length%4!=0){
            remainder = 4- whole.length%4;
        }

        for(int i = 0; i < whole.length; i++){
            fraction[i+remainder]=whole[i];
        }

        for(int i = roundedLength; i < fraction.length; i++){
            fraction[i] = decimal[i - roundedLength];
        }

        boolean[] expo = numberToBinary(64 + roundedLength/4);

        output[0] = negative;
        for(int i = 0; i < expo.length; i++){
            output[i+1] = expo[i];
        }
        for(int i = 0; i < fraction.length; i++){
            output[i+8] = fraction[i];
        }
        return output;
    }

    /**
     * converts a decimal number into a float in IEEE 754 format of
     * either single or double precision.
     * @param n the number being converted.
     * @param isSingle a boolean representing either single or double precision.
     * @return an array of booleans representing an IEEE 754 float of the given number.
     */
    public static boolean[] numToFloatIEEE(double n, boolean isSingle){
        boolean[] output;
        boolean negative = false;
        boolean[] fraction;
        boolean[] exponent;


        if(isSingle){
            output = new boolean[32];
            fraction = new boolean[23];
            exponent = new boolean[8];
        } else{
            output = new boolean[64];
            fraction = new boolean[52];
            exponent = new boolean[11];
        }
        if(n==0){
            Arrays.fill(output, false);
            return output;
        }
        if(n < 0){
            negative = true;
            n = -n;
        }

        Arrays.fill(fraction, false);
        boolean[] decimal;

        if(isSingle) {
            if (n > 3.4028234664E38) {
                output[0] = negative;
                Arrays.fill(output, 1, 9, true);
                return output;
            }
            System.out.println(n);
            if (n < 1.4012984643E-45) {
                output[0] = negative;
                return output;
            }
            if(n < 1.1754943508E-38){
                n /= 1E-38;
                output[0]=negative;
                decimal = fractionToBinary(n);
                for(int i = 9; i < output.length; i++){
                    if(i-9 < decimal.length) {
                        output[i] = decimal[i - 9];
                    }
                }
                return output;
            }
        }



        boolean[] whole = numberToBinary(Math.floor(n));



        if(n - Math.floor(n) > 0) {

            decimal = fractionToBinary(n - Math.floor(n));

            int k = output.length;
            /*if (decimal[(k) - (whole.length + exponent.length)]) {
                while (true) {
                    if (decimal[(k) - (whole.length + exponent.length)]) {
                        decimal[(k) - (whole.length + exponent.length)] = false;
                        k--;
                    } else {
                        decimal[(k) - (whole.length + exponent.length)] = true;
                        break;
                    }
                }
            }*/
        } else{
            decimal = new boolean[]{};
        }

        int leadingNum = 0;
        boolean[] expo;
        if(whole.length!=0) {

            if (isSingle) {
                expo = numberToBinary(127 + (whole.length - 1));
            } else {
                expo = numberToBinary(1023 + (whole.length - 1));
            }
        } else{

            boolean leading = true;
            for(int i = 0; i < decimal.length; i++){
                if(!decimal[i]&&leading){
                    leadingNum++;
                }else{
                    leading = false;
                }
            }
            if (isSingle) {
                expo = numberToBinary(127 - leadingNum - 1);
            } else {
                expo = numberToBinary(1023 - leadingNum - 1);
            }
        }

        for(int i = 0; i < whole.length-1; i++){
            if(i < fraction.length) {
                fraction[i] = whole[i + 1];
            }
        }
        if(whole.length!=0) {
            if (decimal.length != 0) {
                for (int i = whole.length - 1; i < fraction.length; i++) {
                    fraction[i] = decimal[i - (whole.length - 1)];
                }
            }
        }else{
            if (decimal.length != 0) {
                boolean leading = true;
                int j = 0;
                for (int i = 1 + leadingNum; i < decimal.length; i++) {
                    if(j < fraction.length) {
                        fraction[j++] = decimal[i];
                    }
                }
            }
        }

        output[0] = negative;
        if(expo.length <= exponent.length) {
            for (int i = 0; i < expo.length; i++) {
                output[exponent.length-i] = expo[expo.length - i-1];
            }
        }else{
            for(int i = 0; i < expo.length; i++){
                output[i+1]=true;
            }
        }

        for(int i = 0; i < fraction.length; i++){
            output[i + exponent.length + 1] = fraction[i];

        }
        return output;
    }

    /**
     * converts an IBM 360 float in the form of an array of booleans into a double.
     * precision not necessary as it is determined by the input array.
     * @param b the float to be converted.
     * @return the number the float represented in the form of a double.
     */
    public static double float360ToNum(boolean[] b){
        boolean isZero = true;
        for(int i = 0; i < b.length; i++){
            if(b[i]){
                isZero=false;
            }
        }
        if(isZero){
            return 0;
        }
        boolean negative = b[0];
        int maxFrac = 0;
        if(b.length==32){
            maxFrac = 24;
        } else{
            maxFrac = 56;
        }
        int exp = (int)binaryToNumber(Arrays.copyOfRange(b, 1, 8)) - 64;
        double whole;
        double fraction;
        if(exp <= 0){
            fraction = binaryToFraction(Arrays.copyOfRange(b, 8, b.length));
            exp = -exp;

            fraction *=  Math.pow(16, -exp);

        } else if(exp*4<=maxFrac) {
            whole = binaryToNumber(Arrays.copyOfRange(b, 8, 8 + exp * 4));
            fraction = binaryToFraction(Arrays.copyOfRange(b, 8 + exp * 4, b.length));
            fraction += whole;
        } else{
            whole = binaryToNumber(Arrays.copyOfRange(b, 8, 8 + maxFrac));
            fraction = 0;
            fraction += whole;
            fraction *= Math.pow(16, (exp - (maxFrac/4)));
        }


        if(negative) {
            fraction = -fraction;
        }
        return fraction;
    }

    /**
     * converts an IEEE 754 float in the form of an array of booleans into a double.
     * precision not necessary as it is determined by the input array.
     * @param b the float to be converted.
     * @return the number the float represented in the form of a double.
     */
    public static double floatIEEEToNum(boolean[] b){

        boolean negative = b[0];
        int exp;
        double whole;
        double fraction;
        if(b.length==32){
            exp = (int)binaryToNumber(Arrays.copyOfRange(b, 1, 9)) - 127;

            b[8] = true;
            whole = binaryToNumber(Arrays.copyOfRange(b, 8, 8 + exp+1));

            fraction = binaryToFraction(Arrays.copyOfRange(b, 8 + exp+1, b.length));
        } else{
            exp = (int)binaryToNumber(Arrays.copyOfRange(b, 1, 12)) - 1023;
            b[11] = true;
            whole = binaryToNumber(Arrays.copyOfRange(b, 11, 11 + exp+1));
            fraction = binaryToFraction(Arrays.copyOfRange(b, 11 + exp+1, b.length));
        }

        fraction += whole;
        if(negative) {
            fraction = -fraction;
        }

        return fraction;
    }


}
