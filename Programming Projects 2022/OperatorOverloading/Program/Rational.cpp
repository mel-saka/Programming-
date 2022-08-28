#include "Rational.h"
#include "Rational.h"
#include "Integer.h"
#include <sstream>

namespace cosc326 {

	Rational::Rational() {
          //numerator
          numerator = Integer();
          denominator = Integer();
		   signOfRat = 1;
            Xminus = false;

	}

	Rational::Rational(const std::string& str) {
           bool isDot = false;
           bool isDotDone = false;
           bool isSlash = false;
           bool atX = false;
           bool atZ = false;
           bool isMinus;
           bool xzIsPlus = false;
           std::string x = "";
           std::string y = "";
           std::string z = "";
           std::istringstream input(str);
           char ch;
           while(input>>ch){
              if(std::isdigit(ch)){
                if(isSlash == false && isDot == false){
                    x += ch;
                    atX = true;
                }else if(isSlash == false && isDot == true){
                    z+= ch;
                    atX = false;
                    atZ = true;
                }else if(isSlash == true && (isDot == false || isDotDone == true)){
                    y+= ch;
                }

              }else if(ch == '/' && isSlash == false && (atZ == true || atX == true)){
                  isSlash = true;
                  isDotDone = true;

              }else if(ch == '.' && isDot == false && atX == true && isSlash == false){
                  isDot = true;

              }else if(ch == '-' || ch == '+'){
                   if(isSlash == false && isDot == false){
                    x += ch;
                    Xminus = true;
                    signOfRat = -1;
                   
                }else if(isSlash == false && isDot == true){
                    z+= ch;
                    Zminus = true;
                    signOfRat = -1;
                
                }else if(isSlash == true && (isDot == false || isDotDone == true)){
                    y+= ch;
                    Yminus = true;
                    signOfRat = -1;
                }
              }else{
                std::cout<<"Error: Inavlid Input"<<std::endl;
                exit(EXIT_FAILURE);
              }

           	}
               if((isDot == true && isSlash == false) ||y == ""  && isSlash  == true|| isDot == true && z == ""){
                std::cout<<"Error: Inavlid Input"<<std::endl;
                exit(EXIT_FAILURE);

               }

               if(y == "" && z == ""){
                 Rational a = Rational(Integer(x));
                   // case1 = true;
                    numerator = Integer(x); 
                    denominator = Integer("1");
                    *this = +*this;              
               }else if(y!= "" && z== ""){
                 
                 numerator = Integer(x);
                 denominator =  Integer(y);
                 if((Yminus == true && Xminus == false) || (Yminus == true && Xminus == true) ){
                  numerator = numerator * Integer("-1");
                  denominator = denominator * Integer("-1");
                 }
                 Rational b = Rational(x,y);
               }else if(y!= "" && z!= ""){
               Integer yn = Integer(y);
               Integer zn = Integer(z);
               Integer xn = Integer(x);
               if (Yminus == true && Zminus == true){
                   xzIsPlus = true;
                   yn = yn * Integer("-1");
                   zn = zn * Integer("-1");
               }else if(Zminus == false && Yminus == true){
                   yn = yn * Integer("-1");
                   xn = xn *Integer("-1");
                   xzIsPlus = true; 
               }else if(Zminus == true && Yminus == false){
                   xn = xn *Integer("-1");
                    zn = zn * Integer("-1"); 
               }
               
                 Rational c = Rational(xn,yn,zn);
                 numerator = (xn * yn) + zn;
                denominator =  Integer(yn);
               }
              
               
               if(isMinus == true){
                   signOfRat = -1;
               }else{
                   signOfRat = 1;
               }

	}

	Rational::Rational(const Rational& r) {
       *this = r;
	}

	Rational::Rational(const Integer& a) {
         numerator = a;
         denominator = Integer("1");
        signOfRat = numerator.signOfInt;
        numerator.signOfInt = 1;
        denominator.signOfInt = 1;
        *this = +*this;
	}

	Rational::Rational(const Integer& a, const Integer& b) {
          numerator = a;
          denominator = b;
          if(numerator.signOfInt * denominator.signOfInt == 1){
              signOfRat = 1;
          }else{
              signOfRat = -1;
          }
         *this = +*this;
	}

	Rational::Rational(const Integer& a, const Integer& b, const Integer& c) {
         
         numerator = Integer(a) * Integer(b) + Integer(c);
         denominator = Integer(b);
         if(numerator.signOfInt * denominator.signOfInt == 1){
              signOfRat = 1;
          }else{
              signOfRat = -1;
          }
         *this = +*this;
	}

	Rational::~Rational() {

	}

	Rational& Rational::operator=(const Rational& r) {

        numerator = r.numerator;
        denominator = r.denominator;    
        signOfRat = r.signOfRat;   
        return *this;
	}

 Rational Rational::operator-(){
         signOfRat = -1 *signOfRat;
        return Rational(*this);
    }

	Rational Rational::operator+(){

       signOfRat = 1;

    return Rational(*this);
	}

	Rational& Rational::operator+=(const Rational& r) {
        *this = *this + r;
        return *this;
	}


	Rational& Rational::operator-=(const Rational& r) {
        *this = *this - r;
		return *this;
	}

	Rational& Rational::operator*=(const Rational& r) {
		 *this = *this * r;
        return *this;
	}

	Rational& Rational::operator/=(const Rational& r) {
         *this = *this / r;
        return *this;
	}

	Rational operator+(const Rational& lhs, const Rational& rhs) {

       Integer denominatorSum = Integer();
       Integer numSum =    Integer();

       if(lhs.signOfRat ==1 && rhs.signOfRat == 1){
        denominatorSum = lhs.denominator * rhs.denominator;
        numSum =  (lhs.numerator * rhs.denominator) + (rhs.numerator * lhs.denominator);
       }else if( lhs.signOfRat == -1 && rhs.signOfRat == -1){
      
      
        denominatorSum = lhs.denominator * rhs.denominator;
        numSum =  (lhs.numerator * rhs.denominator) + (rhs.numerator * lhs.denominator);

       }
        Rational ans = Rational(numSum,denominatorSum);
        return ans;
	}

	Rational operator-(const Rational& lhs, const Rational& rhs) {
        Integer denominatorDif = lhs.denominator * rhs.denominator;
        Integer numDif =  (lhs.numerator * rhs.denominator) - (rhs.numerator * lhs.denominator);
        Rational ans = Rational(numDif,denominatorDif);
		return ans;
	}

	Rational operator*(const Rational& lhs, const Rational& rhs) {
        Integer denominatorMulti = lhs.denominator * rhs.denominator;
        Integer numMulti =  lhs.numerator * rhs.numerator;
        if(((rhs.denominator < Integer("0") && lhs.denominator > Integer("0")) || (lhs.denominator < Integer("0") )) 
        && ((rhs.numerator > Integer("0") && lhs.numerator > Integer("0")) || (rhs.numerator < Integer("0") && lhs.denominator < Integer("0") ))){
           denominatorMulti = denominatorMulti * Integer("-1");
           numMulti = numMulti * Integer("-1");
        }else if(((rhs.denominator < Integer("0") && lhs.denominator > Integer("0")) || (lhs.denominator < Integer("0") )) 
        && ((rhs.numerator < Integer("0") && lhs.numerator > Integer("0")) || (lhs.numerator < Integer("0") ))){
            denominatorMulti = denominatorMulti * Integer("-1");
           numMulti = numMulti * Integer("-1");
        } 
        Rational ans = Rational(numMulti,denominatorMulti);
		return ans;
	}

	Rational operator/(const Rational& lhs, const Rational& rhs) {
		Rational inverese = Rational(rhs.denominator,rhs.numerator);
        Rational ans =  lhs * inverese;
        
        return ans;
	}

	std::ostream& operator<<(std::ostream& os, const Rational& i) {
         
         bool minusFound = false;
         Integer newNum = Integer();
         Integer newdenominator = Integer();
         if(i.denominator == Integer("1")){
             os<<i.numerator;
            return os;
         }else if(i.numerator < i.denominator){
              os<<i.numerator<<"/"<<i.denominator;
         }else if(i.numerator > i.denominator){
             Integer common = gcd(i.numerator,i.denominator);
            newNum = i.numerator / common;
             newdenominator = i.denominator / common;
             Integer x = newNum/newdenominator;
             Integer z = newNum % newdenominator;
             Integer y = newdenominator;
             os<<x<<"."<<z<<"/"<<y;
         }
         
        return os;
        
    
    
	}
	std::istream& operator>>(std::istream& is, Rational& i) {
        char c;
        std::string result = "";
        while(is>>c){
            result +=c;
        }
        i = Rational(result);
        return is;
	}

bool operator<(const Rational& lhs, const Rational& rhs) {

        Integer x = lhs.numerator * rhs.denominator;
        Integer y = rhs.numerator * lhs.denominator;
        if(x <y){
            return true;
        }else{
            return false;
        }
    }
    bool operator> (const Rational& lhs, const Rational& rhs) {
        return !(lhs<rhs);
    }
    bool operator<=(const Rational& lhs, const Rational& rhs) {

        return (lhs<rhs || lhs==rhs);
    }
    bool operator>=(const Rational& lhs, const Rational& rhs) {

        return (lhs>rhs || lhs==rhs);
    }
    
    bool operator==(const Rational& lhs, const Rational& rhs) {
        if(lhs.numerator ==Integer("0") & rhs.numerator==Integer("0")){
            return true;
        }
        if(lhs.numerator== rhs.numerator & lhs.denominator == rhs.denominator){
            return true;
        }else{
            return false;
        }
    }
    
    bool operator!=(const Rational& lhs, const Rational& rhs) {

        return !(lhs==rhs);
    }

}