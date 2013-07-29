Project 2 readme.txt
CS61BL Section 110
GSI: Doug
Anastasia Shuler    cs61bl-nl
Iris Jang           cs61bl-ni
Ji-hern Baek        cs61bl-nj
Katherine Chao      cs61bl-no

Testing:
    

Contributions:
Anastasia was responsible for the ProofTree class, and, in extension, the Expression class. These two classes go hand-in-hand: an Expression object stores a ProofTree object as one of its fields. The Expression class's main function is to store information about an expression, both as a string and as a ProofTree. ProofTree's main function is to create and manipulate arithmetic trees formed from the user-inputed expressions. ProofTree contains methods to create, print, and perform tests upon an expressoin (given as a string). ProofTree additionally deals with theorem checking, another task Anastasia was responsible for. The isSimilar() method of the ProofTree class checks that a theorem application adheres to the proper form and syntax of the expressions given in the theoremSet. isSimilar() checks that all operators of a potential theorem application match those of the theorem, and that each of the theorem variables coorespond to exactly one unique expression in the user supplied expression. Finally, Anastasia implemented the Inference class, which contains methods for checking the validity of all inferences made by the user. This includes the ic, mp, mt, and co inferences as well as the 'assume' statements. All of these checks rely heavily on the work done in the ProofTree class, and use ProofTrees for basic pattern matching and syntax checking.

Katherine was responsible for a large part of the Proof class (parts of extendProof and the toString() and isComplete() methods, and the setup of the instance variables), the LineNumber class, and some of the Format class and tests. The LineNumber class uses an array representation to store each layer of the line number as indexes in the array. The toString() method in Proof iterates through an ArrayList of stored line numbers, which were then retreived from a Hashtable that related line numbers and user input. The isComplete() method checks the beginning and last expressions of the proof to see if they're equal, which returns true if they are and false if they aren't, barring certain special circumstances. She also dealt with the throwing/handling of exceptions in checkFormat() in the Format class and implemented the format checker in extendProof.
