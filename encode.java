import java.util.*;
import java.io.*;
import java.io.File;

public class encode{

    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        trie encoderTrie = new trie();

        String s = br.readLine();
        char[] line;
        String currentPhrase = "";
        File encodedData = new File("encodedData.txt");
        PrintWriter writer = new PrintWriter(encodedData);

        while (s != null)
        {
            line = s.toCharArray();
            
            for (int i = 0; i < s.length(); i++)
            {
                
                currentPhrase = currentPhrase + line[i];
                if (encoderTrie.getNode(currentPhrase) != null)
                {
                    if (i + 1 >= s.length())//If this is the last phrase
                    {
                        //We must encode the last character separately, in order for
                        //the decoder to know what the last character is. 
                        //This has virtually no effect on compression, as it is only one
                        //more entry
                        String lastChar = "";
                        lastChar = currentPhrase.substring(currentPhrase.length() - 1);
                        currentPhrase = currentPhrase.substring(0, currentPhrase.length() - 1);
                        trie.node n = encoderTrie.getNode(currentPhrase);
                        trie.node lastCNode = encoderTrie.getNode(lastChar);
                        try {
                            writer.println(n._phraseNum);
                        }
                        catch(Exception e)
                        {

                        }
                        writer.println(lastCNode._phraseNum);
                        writer.close();
                        return;
                    }
                }
                else{
                    if (currentPhrase.length() == 1)
                    {
                        encoderTrie.createPhrase(currentPhrase.toCharArray()[0]);
                    }
                    else{
                        String previousPhrase = currentPhrase.substring(0, currentPhrase.length() - 1);
                        trie.node n = encoderTrie.getNode(previousPhrase);
                        char[] c = currentPhrase.toCharArray();
                        encoderTrie.extendPhrase(c[c.length - 1], n);
                        //System.out.println(n._phraseNum);
                        writer.println(n._phraseNum);
                        i--;
                    }
                    currentPhrase = "";
                }
            }
            
            s = br.readLine();
        }

        
        
    }


    public static class trie{
        
        //Initialise with hexadecimal digits
        private node[] _children = {new node(1, '0'), new node(2, '1'), new node(3, '2'), new node(4, '3'), new node(5, '4'), new node(6, '5'), new node(7, '6'), new node(8, '7'), new node(9, '8'), new node(10, '9'), new node(11, 'A'), new node(12, 'B'), new node(13, 'C'), new node(14, 'D'), new node(15, 'E'), new node(16, 'F')};

        public int phraseCount = 17;
        
        public trie(){
            
        } 

        public class node{
            private int _phraseNum;
            private char _character;
            private node[] _children;

            public node(int phraseNum, char character){
                _phraseNum = phraseNum;
                _character = character;
                _children = new node[16];
            }

            public int getPhraseNumber()
            {
                return _phraseNum;
            }

            public char getChar()
            {
                return _character;
            }
        }

        public void extendPhrase(char character, node extendedNode)
        {
            //Add a character to a branch in the trie
            for (int i = 0; i < extendedNode._children.length; i++)
            {
                if (extendedNode._children[i] == null)
                {
                    extendedNode._children[i] = new node(phraseCount, character);
                    phraseCount++;
                    return;
                }
            }
        }

        //Get the final node that completes a string
        public node getNode(String s)
        {
            char[] phrase = s.toCharArray();
            node nodePointer = null;

            boolean nextcharFound = false;
            

            for (int i = 0; i < phrase.length; i++)
            {
                nextcharFound = false;
                if (i == 0)//If this is the first iteration, we are checking the trie's children and not the children of a node
                {
                    for (node n : this._children)
                    {
                        if (n != null)
                        {
                            if (n._character == phrase[i])
                            {
                                nodePointer = n;
                                nextcharFound = true;
                            }
                        }
                    }
                }
                else
                {
                    for (node n : nodePointer._children)
                    {
                        if (n != null)
                        {
                            if (n._character == phrase[i])
                            {
                                nodePointer = n;
                                nextcharFound = true;
                            }
                        }
                    }
                }
                if (nextcharFound == false)//If we could not find a node for that phrase
                {
                    return null;
                }
            }

            return nodePointer;

        }

        public void createPhrase(char character)
        {

            for (int i = 0; i < this._children.length; i++)
            {
                if (this._children[i] == null)
                {
                    this._children[i] = new node(phraseCount, character);
                    return;
                }
            }
        }

        
    }
}
