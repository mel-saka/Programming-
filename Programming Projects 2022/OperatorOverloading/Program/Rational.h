#pragma once
#ifndef RATIONAL_H_INCLUDED
#define RATIONAL_H_INCLUDED

#include <iostream>
#include <string>
#include "Integer.h"
namespace cosc326 {

	class Rational {
	public:
            Integer numerator;
            Integer denominator;
            Integer Answer;
            int signOfRat;
            bool Xminus;
            bool Yminus;
            bool Zminus;
            Rational();
            Rational(const std::string& str);
            Rational(const Rational& r);
            Rational(const Integer& a); // a
            Rational(const Integer& a, const Integer& b); // a/b
            Rational(const Integer& a, const Integer& b, const Integer& c); // a + b/c
            ~Rational();
            
            Rational& operator=( const Rational& r); // q = r;
            // Unary operators
            Rational operator-(); // -r;
            Rational operator+(); // +r;
            // Arithmetic assignment operators
            Rational& operator+=(const Rational& r); // q += r;
            Rational& operator-=(const Rational& r); // q -= r;
            Rational& operator*=(const Rational& r); // q *= r;
            Rational& operator/=(const Rational& r); // q /= r;
            // lhs < rhs -- a 'friend' means < isn't a member, but can access the private parts of the class.
            // You may need to make some other functions friends, but do so sparingly.
            friend bool operator<(const Rational& lhs, const Rational& rhs);
	private:
            // Can add internal storage or methods here
	};
	// Binary operators
	Rational operator+(const Rational& lhs, const Rational& rhs); // lhs + rhs;
	Rational operator-(const Rational& lhs, const Rational& rhs); // lhs - rhs;
	Rational operator*(const Rational& lhs, const Rational& rhs); // lhs * rhs;
	Rational operator/(const Rational& lhs, const Rational& rhs); // lhs / rhs;
	std::ostream& operator<<(std::ostream& os, const Rational& i);  // std::cout << i << std::endl;
	std::istream& operator>>(std::istream& is, Rational& i);        // std::cin >> i;
	bool operator> (const Rational& lhs, const Rational& rhs); // lhs > rhs
	bool operator<=(const Rational& lhs, const Rational& rhs); // lhs <= rhs
	bool operator>=(const Rational& lhs, const Rational& rhs); // lhs >= rhs
	bool operator==(const Rational& lhs, const Rational& rhs); // lhs == rhs
	bool operator!=(const Rational& lhs, const Rational& rhs); // lhs != rhs

}

#endif
