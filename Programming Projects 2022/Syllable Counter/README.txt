Program Syllables. 


Testing:
I used test case words that have exceptions, such as words that have a silent e at end of word (unless it's the only vowel), or consecutive vowels counting as only 1 syllable, or Y can act as a vowel.
Examples of Testing cases:
- beauty = Consecutive vowels only act as 1 syllable
- levee = Silent "e"
- The = no silent "e"
- random words to see if algorithm is generalised

Resources Used:
- https://www.howmanysyllables.com/wordprocessor
- https://www.howmanysyllables.com/syllable_rules/howtocountsyllables

Testing Strategy:
My testing strategy was to test based on generalised words at first to get an algorithm that worked on most words. However, there are many cases and exceptions in the English language, therefore we started testing with exception words and cases, therefore refining the algorithm.

How To Run Program:
Input any word, or list of words, and press enter. The program will calculate the amount of syllables are return the calculated integer/integers corresponding to the amount of syllables per word. 
Should be compilable with any IDE using JDK 17.
