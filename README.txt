Our encoder always finishes with a one character entry. This is simply so our decoder knows what the last character is. It has 
minimal impact on compression, as it only increases the number of entries by one.

Our encoder takes in an input file (use a cat command) and creates a file. The decoder automatically uses that file if it is in the
shared directory, but exports its output to the command line. The input file must be hexadecimal digits on a single line

Compiling and executing:

javac encode.java

javac mainDecode2.java

cat input.txt | java encode

java mainDecode2