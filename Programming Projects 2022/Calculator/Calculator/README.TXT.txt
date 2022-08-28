Program CountingItUp. 


Testing/Testing Strategy:
I used test case numbers for the hand size and number of cards to draw, that would conventionally throw a too large error when running with longs, ints, 
or doubles. Also I tried accounting for non-number inputs, and non-whole numbers. I also tested my code against formula using the BigInteger class to 
confirm our string based methods were working properly.The program prints Undefined when the calculation is too complex. This is in par with most online calculators. 
However, My program performs better and is able to calculate relatively large factorials. 

The following is an example explaining how the program works:


 for n = 60 and k = 5,
 
  (1) subtract n - (k - 1), 60 - (5 - 1) = 56, let target = 56 

  (2) Multiply n by n-1 until n = target, 60*59*58*57*56 = 655381440

  (3) multiply k by k-1 until k = 1, 5*4*3*2*1 = 120
  
  (4) Divide 655381440/120 = 5461512


I tried this formula with various different values and they all worked


How To Run Program:
Input number of cards in the pack n, followed by a coma and then enter the number of cards to draw k, without any spaces. 
Example inputs:
52,5
4294967296,2
2000,100

The program will calculate the amount of possible hand combinations and return the amount. 
Should be compilable with any IDE using JDK 17.