#include "Integer.h"
#include <vector>
#include <sstream>
#include <string>
#include <stdexcept>
#include <ctype.h>
#include <algorithm>
#include <iterator>
#include <iostream>
#include <vector>
#include <sstream>
#include <string>
#include <stdexcept>
#include <ctype.h>
#include <cstdlib>

namespace cosc326
{
    Integer::Integer()
    {

        input.push_back(0);
        sizeOf = 1;
        signOfInt = 1;
    }
    Integer::Integer(const Integer &i)
    {

        input = i.input;
        signOfInt = i.signOfInt;

    sizeOf = i.sizeOf;
        *this = +*this;
    }
    Integer::Integer(const std::string &s)
    {

        std::istringstream iss(s);
        char st;
        sizeOf = 0;
        iss >> st;

    
            if (st == '+')
        {
            signOfInt = 1;
                
         }
        else if(st == '-')
         {
                
                signOfInt = -1;
          
        }
        else
        {
            signOfInt = 1;
            sizeOf += 1;
            input.insert(input.begin(), st - '0');
        }
        while (iss >> st)
        {
            if (isdigit(st))
            {
                sizeOf += 1;
                input.insert(input.begin(), st - '0');
            }
            else
            {
                std::cerr << "Error Invalid Input: " << s << "\n";
                exit(EXIT_FAILURE);
            }
        }
        if (sizeOf == 0)
        {
           std::cerr << "Error Invalid Input: " << s << "\n";
            exit(EXIT_FAILURE);
        }

        *this = +*this;
    }
    Integer::~Integer()
    {
       
    }
    Integer &Integer::operator=(const Integer &i)
    {
        input = i.input;
        signOfInt = i.signOfInt;
        sizeOf = i.sizeOf;
        return *this;
    }
    Integer &Integer::operator-()
    {
        signOfInt *= -1;
        *this = +*this;
        return *this;
    }
    Integer &Integer::operator+()
    {
        bool start = true;
        int newlength = sizeOf;
        for (int idx = sizeOf - 1; idx >= 0; idx--)
        {

            if (start)
            {
                if (input[idx] == 0)
                {
                    newlength -= 1;
                }
                else
                {
                    start = false;
                }
            }
        }
        if (start)
        {
            Integer zero = Integer();
            *this = zero;
            return *this;
        }
        else
        {
            sizeOf = newlength;

        }
        
        return *this;
    }
    Integer &Integer::operator+=(const Integer &i)
    {
         std::vector<int> answer;

    int currSign = 1;
    std::string num1 = "";
    std::string num2 = "";
    int carry = 0;
    std::string resultaddition = "";
    int Nf = i.sizeOf;

    
    int result = 0;
    for (int a = input.size()-1; a > -1; a--)
    {

     num1 = num1 + std::to_string(input[a]);

    }
    
    for (int n = Nf-1; n > -1; n--)
    {

    num2 = num2 + std::to_string(i.input[n]);
    
    }
    Integer x = Integer(num2);
    Integer y = Integer(num1);
     if(signOfInt == 1 && i.signOfInt == -1 ){ 
       x = x - y;
        sizeOf = x.sizeOf;
         input = x.input;
        *this = +*this;
        return *this;
     }else if(signOfInt == -1 && i.signOfInt == 1){ 
         x = y - x; 
         sizeOf = x.sizeOf;
         input = x.input;
         *this = +*this;
         return *this;
     }else if(signOfInt == -1 && i.signOfInt == -1){
         signOfInt = -1;
     }

    int indexnum2 = num2.size() -1;
    int indexnum1 = num1.size() -1;
    
    
          while (indexnum2 > -1 || indexnum1 > -1) {
            std::string resultStr = "";
              int currentnum1 = 0;
              int currentnum2 = 0;
            if (indexnum1 > -1) {
                std::stringstream ss;
                ss << num1[indexnum1];
                ss >> currentnum1;
                ss.str("");
                ss.clear();
            } else {
                currentnum1 = 0;
            }
            if (indexnum2 > -1) {
                std::stringstream s1;
                s1 << num2[indexnum2];
                s1 >> currentnum2;
                s1.str("");
                s1.clear();
            } else {
                currentnum2 = 0;
    
            }
            result = currentnum1 + currentnum2 + carry;
            if (result > 9) {

                resultStr = std::to_string(result);
                std::stringstream s3;
                s3 << resultStr[0];
                s3 >> carry;
                s3.str("");
                s3.clear();

                resultaddition = resultStr[1] + resultaddition;
                indexnum2--;
                indexnum1--;
            } else {
                carry = 0;
                resultaddition =std::to_string(result) + resultaddition;
                indexnum2--;
                indexnum1--;

            }
        }
        
        if (carry != 0) {
            resultaddition = std::to_string(carry) + resultaddition;
        }
       Integer o = Integer(resultaddition);
        sizeOf =  o.sizeOf; 
         input =   o.input;          
        *this = +*this;
        return *this; 

    }

      
    
    Integer &Integer::operator-=(const Integer &i)
    {
        std::vector<int> answer;
    std::string num1 = "";
    std::string num2 = "";
    int carry = 0;
    std::string resultaddition = "";
    int Nf = i.sizeOf;
 

for (int a = input.size()-1; a > -1; a--)
    {

     num1 = num1 + std::to_string(input[a]);

    }
    
    for (int n = Nf-1; n > -1; n--)
    {

    num2 = num2 + std::to_string(i.input[n]);

      
    }
    Integer x = Integer(num1);
    Integer y = Integer(num2);
     if(signOfInt == 1 && i.signOfInt == -1 ){ 
       x = x + y;
        sizeOf= x.sizeOf;
         input = x.input;
        *this = +*this;
        return *this;
     }else if(signOfInt == -1 && i.signOfInt == 1){ 
         x = -y + -x; 
         sizeOf = x.sizeOf;
        input = x.input;
         *this = +*this;
         return *this;
     }else if(signOfInt == -1 && i.signOfInt == -1){
         if(x < y){
          x = y - x;
          signOfInt = 1;
         }else{
          x = y -x;
          signOfInt = -1;   
         }
         sizeOf = x.sizeOf;
         input = x.input;
         *this = +*this;
         return *this;
     }else if(x < y){
          x = y - x;
          signOfInt = -1;
          sizeOf = x.sizeOf;
         input = x.input;
         *this = +*this;
         return *this;
     }


    if (num1 == num2) {
         Integer z = Integer("0");
        sizeOf = z.sizeOf;
        input = z.input;
        *this = +*this;
        return *this;
        }
    int indexnum2 = num2.size() -1;

    int indexnum1 = num1.size() -1;
    int add10 = 0;
    int result = 0;
    std::string resultsubtract = "";

      
       
         indexnum2 = num2.size() - 1;
         indexnum1 = num1.size() - 1;
        while (indexnum2 > -1 || indexnum1 > -1) {
            std::string resultStr = "";
            int currentnum1 = 0;
            int currentnum2 = 0;
            if (indexnum1 > -1) {
                std::stringstream ss;
                ss << num1[indexnum1];
                ss >> currentnum1;
                ss.str("");
                ss.clear();
            } else {
                currentnum1 = 0;
            }
            if (indexnum2 > -1) {
                std::stringstream s1;
                s1 << num2[indexnum2];
                s1 >> currentnum2;
                s1.str("");
                s1.clear();
            } else {
                currentnum2 = 0;
            }
             result = (currentnum1 + carry) - currentnum2;

            if (result < 0) {
                add10 = 10; 
                result = (currentnum1 + carry + add10) - currentnum2;
                resultStr = std::to_string(result);
                resultsubtract = resultStr[0] + resultsubtract;
                indexnum2--;
                indexnum1--;

                carry = -1;
                add10 = 0;
            } else {
                carry = 0;
                resultsubtract = std::to_string(result) + resultsubtract;
                indexnum2--;
                indexnum1--;
            }
        }
        for (int x = 0; x < resultsubtract.length(); x++) {
            if (resultsubtract[x] != '0') {
              resultsubtract  = resultsubtract.substr(x);
        int an;
        for (int x = resultsubtract.length()-1; x>-1; x--){
             std::stringstream s4;
            s4 << resultsubtract[x];
            s4 >> an;
            s4.str("");
            s4.clear();
             
             answer.push_back(an);
        }
        sizeOf = resultsubtract.size();
        input = answer;
        for (int a = input.size()-1; a > -1; a--)
    {


    }
        *this = +*this;
        return *this;
            }
        }
        int an2;
         for (int x = resultsubtract.length()-1; x>-1; x--){
             std::stringstream s5;
            s5 << resultsubtract[x];
            s5 >> an2;
            s5.str("");
            s5.clear();
             
             answer.push_back(an2);
         }
      //   signOfInt = answer.signOfInt;
        sizeOf = answer.size();
        input = answer;
        *this = +*this;
        return *this;
    }

    Integer &Integer::operator*=(const Integer &i)
    {
 
    int currSign = 1; 
    int xsign = signOfInt;
    int ysign = i.signOfInt;
    int carry = 0;
    int nZero = 1;
    int ans = 0;
    std::string sum = "";
    std::vector<int> ynum;
    std::vector<int> xnum;
    std::vector<int> answer;
    std::vector<std::string> ansList;

    if ( (i.signOfInt == -1 && signOfInt == 1 )  ||  (i.signOfInt == 1 && signOfInt == -1) ){
         currSign = -1;
    }
             
    int Nf = i.sizeOf;
            for (int n = 0; n < Nf; n++)
            {

                ynum.push_back(i.input[n]);
                
            }

    
            for (int n = 0; n < input.size(); n++)
            {
                     xnum.push_back(input[n]);               
            }

    for (int j = 0; j < ynum.size(); j++)
    {
        for (int k = 0; k < xnum.size(); k++)
        {
            ans = (ynum[j] * xnum[k]) + carry;
            std::string ansIndex = std::to_string(ans);
            if (ansIndex.length() == 2)
            {
                std::stringstream ss;
                ss << ansIndex[0];
                ss >> carry;
                ss.str("");

            }
            else
            {
                carry = 0;
            }
            if (ansIndex.length() == 2)
            {
                sum = ansIndex[1] + sum;
            }
            else
            { 
                sum = ansIndex + sum;
            }
            if (xnum.size() - 1 == k)
            {
                std::string finalCarry = std::to_string(carry);
                sum = finalCarry + sum;
                if (j != 0)
                {
                    std::string zero = "0";
                    for (int n = 0; n < nZero; n++)
                    {
                        sum += zero;
                    }
                    nZero++;
                }
            }
        }
        std::string finalSum = "";
        for (int s = 0; s < sum.length(); s++)
        {
            std::string currSum(1, sum[s]);
            int res = currSum.compare("0");
            if (res != 0)
            {
                finalSum = sum.substr(s);
                break;
            }
            else
            {
                finalSum = sum.substr(s + 1);
            }
        }
        ansList.push_back(finalSum);
        carry = 0;
        sum = "";
        finalSum = "";
    }
    std::string products = "";
    std::string prevProducts = "";
    int indexOfProducts = 0;
    int valueAtIdx = 0;
    int valueAtnxIdx = 0;
    int valueAtprev = 0;
    int valueAtpro = 0;
     Integer product = Integer(); 
     

        std::string currans = "";
     for(int i = 0; i<ansList.size(); i++){
       if(i==0){
           if(ansList[i] != ""){
           product = ansList[i];
           }
       }else{
           if(ansList[i] != ""){
           Integer toadd = Integer(ansList[i]);
           product += toadd;

           }

       }

     }
 
 
       signOfInt = currSign; 
       sizeOf = product.sizeOf;
       input = product.input;
       *this = +*this;
        return *this;
     }
    

    Integer &Integer::operator/=(const Integer &i)
    {
     if ( (i.signOfInt == -1 && signOfInt == 1 )  ||  (i.signOfInt == 1 && signOfInt == -1) ){
         signOfInt = -1;
    }
         int Nf = i.sizeOf;
    std::string x = "";
    std::string y = "";
    
for (int a = input.size()-1; a > -1; a--)
    {

     y = y + std::to_string(input[a]);


    }
    
    for (int n = Nf-1; n > -1; n--)
    {

    x = x + std::to_string(i.input[n]);

    }
    
    Integer xn = Integer(x);
    Integer yn = Integer(y);
     
          if (x == "0" || y == "0" || xn < yn) {
        
        Integer zero = Integer("0");
        sizeOf = zero.sizeOf;
        input = zero.input;
        *this = +*this;
        return *this;
        }
      
        std::string quotient = "";
        std::string currQuotient = "";
        std::string product = "";
        std::string divisor = y;
        std::string dividend = x;
        int lenOfDivisor = divisor.size();
        std::string currDividend = "";
        int indexOfDividend = 0;
        std::string difference = "";
        std::string carry = "";
        bool end = false;
        std::string finalQuotient = "";
        Integer z = Integer();
        Integer q = Integer();
        Integer r = Integer();
        std::string cd = "";
        std::string c = "";
        std::string smallQuotient = "";
        std::string one = "1";
        while (end == false) {

            if (indexOfDividend == 0) {
                currDividend = dividend.substr(0, lenOfDivisor);
                dividend = dividend.substr(lenOfDivisor);
            }
            if (dividend == "") {
                end = true;
            }
            z = currDividend;
            q = divisor;
            if (z < q) {
                currQuotient = "0";
            } else {
                smallQuotient = "";

                 z =currDividend;
                 q = divisor;
                bool s = true;
                int count = 0;
                Integer zero = Integer("0");
                r = zero;
                
                while (z >= q) {
               if ( s == true){
               s = false;
               }

                z = z -  q;
                if(count <11){
                }
                count++;
                r = r + one;
                
        }
            sizeOf = r.sizeOf;
            input = r.input;
            currQuotient = "";
            for(int i = input.size()-1; i> -1; i--){
              
              currQuotient +=  std::to_string(input[i]);
            }
           
            
            }
            if (end == true) {
                quotient += currQuotient;
                break;
            }
            z = divisor;
            q = currQuotient;
            r = z * q;
            sizeOf = r.sizeOf;
            input = r.input;
            product = "";
           
            for(int i = input.size()-1; i> -1; i--){
              product +=  std::to_string(input[i]);
            }
            z = currDividend;
            q = product;
            z -= q;
            sizeOf = z.sizeOf;
            input = z.input;
            difference = "";
            
            for(int i = input.size()-1; i> -1; i--){
              difference +=  std::to_string(input[i]);
            }

            carry = dividend.substr(0, 1);

            if (difference == "0" && carry == "0") {
                currDividend = "0";
            }else if(difference == "0" && carry != "0"){
                currDividend = carry;
            } else {
            
                currDividend = difference + carry;
            }
            dividend = dividend.substr(1);
            quotient += currQuotient;
            indexOfDividend++;
        }
         
        for (int k = 0; k < quotient.length(); k++) {
            if (quotient[k] != '0') {
                finalQuotient = quotient.substr(k);
                break;
            } else {
                finalQuotient = quotient.substr(k + 1);
            }
        }
      
         z = finalQuotient;
         signOfInt = z.signOfInt;
        sizeOf = z.sizeOf;
        input = z.input;
        *this = +*this;
        return *this;

        
    }

    Integer &Integer::operator%=(const Integer &i)
    {
        std::string num2 = "";
        Integer num = i;
        for (int a = input.size()-1; a > -1; a--)
    {
      num2 = num2 + std::to_string(input[a]);
    }
      Integer divisor = num2;
       if(num < divisor){
         Integer ans =  num;
        sizeOf = ans.sizeOf;
        input = ans.input;
       *this = +*this;
        return *this;
      }
    
     Integer ans =  num - divisor * (num / divisor);
      signOfInt = ans.signOfInt;
      sizeOf = ans.sizeOf;
      input = ans.input;
    *this = +*this;
     return *this;
   
    }

    Integer operator+(const Integer &lhs, const Integer &rhs)
    {

        Integer right = Integer(rhs);
        Integer left = Integer(lhs);
        left += right;
        return left;
    }

    Integer operator-(const Integer &lhs, const Integer &rhs)
    {
        Integer right = Integer(rhs);
        Integer left = Integer(lhs);
        left -= right;
        return left;
    }

    Integer operator*(const Integer &lhs, const Integer &rhs)
    {
       Integer right = Integer(rhs);
       Integer left = Integer(lhs);
        left *= right;
        return left;
    }

    Integer operator/(const Integer &lhs, const Integer &rhs)
    {
        Integer right = Integer(lhs);
        Integer left = Integer(rhs);
        left /= right;
        return left;
    }

    Integer operator%(const Integer &lhs, const Integer &rhs)
    {
       Integer right = Integer(lhs);
       Integer left = Integer(rhs);
        left %= right;
        return left;
    }
    std::ostream &operator<<(std::ostream &os, const Integer &i)
    {
        if (i.signOfInt < 0)
        {
            os << "-";
        }
        for (int idx = i.sizeOf - 1; idx >= 0; idx--)
        {
            os << std::to_string(i.input[idx]);
        }
        return os;
    }

    std::istream &operator>>(std::istream &is, Integer &i)
    {
        char st;
        std::string in = "";
        while (is >> st)
        {
            in += st;
        }
        i = Integer(in);
        return is;
    }
    bool operator<(const Integer &lhs, const Integer &rhs)
    {
       if (lhs.sizeOf < rhs.sizeOf)
            {
                if (lhs.signOfInt> 0)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        else
        {
           if (lhs.signOfInt< rhs.signOfInt)
           {
            return true;
            }
        else if (lhs.signOfInt> rhs.signOfInt)
            {
            return false;
           }
            else if (lhs.sizeOf > rhs.sizeOf)
            {
                if (lhs.signOfInt> 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            else
            {
                for (int index = lhs.sizeOf - 1; index >= 0; index--)
                {
                    if (lhs.input[index] < rhs.input[index])
                    {
                        return true;
                    }
                    else if (lhs.input[index] > rhs.input[index])
                    {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    bool operator>(const Integer &lhs, const Integer &rhs)
    {

        if (lhs < rhs)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    bool operator<=(const Integer &lhs, const Integer &rhs)
    {

        if (lhs == rhs)
        {
           
            return true;
        } else if(lhs < rhs){
        
            return true;
        }else
        {
            return false;
        }
    }

    bool operator>=(const Integer &lhs, const Integer &rhs)
    {

        if  (lhs == rhs )
        {
            return true;
        }else if(lhs > rhs){
            
            return true;
        
        }
        else
        {
            return false;
        }
    }

    bool operator==(const Integer &lhs, const Integer &rhs)
    {
        if (lhs.signOfInt != rhs.signOfInt)
        {
            return false;
        }
        else if (lhs.sizeOf != rhs.sizeOf)
        {
            return false;
        }
        else
        {
            for (int index = lhs.sizeOf - 1; index >= 0; index--)
            {
                if (lhs.input[index] != rhs.input[index])
                {
                    return false;
                }
            }
        }
        return true;
    }

    bool operator!=(const Integer &lhs, const Integer &rhs)
    {

        return !(lhs == rhs);
    }

    Integer gcd(const Integer &a, const Integer &b)
    {
       Integer bignNum = Integer();
       Integer smallNum = Integer();
       Integer quotient =  Integer();
       Integer remainder = Integer("1");
       Integer prevRem = Integer();
       Integer zero = Integer("0");
       bool done = false;

       if(a > b){
           bignNum = a;
           smallNum = b;
       }else{
           bignNum = b;
           smallNum = a;
       }
       int count = 0;
    while(done == false){

        quotient =  bignNum / smallNum; 
        remainder = bignNum % smallNum;
        bignNum = smallNum;
        smallNum = remainder;
        if(smallNum == zero){
            done = true;
        }
        if(count == 30){
            break;
        }
        count++;
    }
    return bignNum;
}
}
