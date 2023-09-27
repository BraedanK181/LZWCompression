import java.util.*;
import java.io.*;

public class mainDecode2 {
    

    public static class dictionary{
        ArrayList<dictItem> dict = new ArrayList<dictItem>();
        int sizeIndex = 16;

        public dictionary(){

        }
        public class dictItem{
            char c;
            int input;
            public dictItem(char c, int input){
                this.c = c;
                this.input = input;
            }
        }

        //Return an entire phrase by following the input values and adding each char to the front
        public String returnPhrase(int index)
        {
            int inputvalue = 1;//Set default value as 1, anything but 0
            String phrase = "";
            while (inputvalue != 0)
            {
                phrase = dict.get(index - 1).c + phrase;
                inputvalue = dict.get(index - 1).input;
                if (inputvalue != 0)
                {
                    index = inputvalue;
                }
            }

            return phrase;
        }

        public int returnFirstIndexOfInput(int i)
        {
            int z = 0;
            for (dictItem dictItem : dict) {
                if (dictItem.input == i){
                    return z;
                }
                z++;
            }
            return z;
        }

        //Add an item to the dictionary
        public void addItem(char c, int inputValue)
        {
            dict.add(sizeIndex ,new dictItem(c, inputValue));
            sizeIndex++;
        }

        public boolean containInputKey(int index)
        {
            for (dictItem dictItem : dict) {
                if (dictItem.input == index)
                {
                    return true;
                }
            }

            return false;
        }


        public void insertAlphabet(){
            dict.add(0,new dictItem('0', 0));
			dict.add(1,new dictItem('1', 0));
			dict.add(2,new dictItem('2', 0));
			dict.add(3,new dictItem('3', 0));
			dict.add(4,new dictItem('4', 0));
			dict.add(5,new dictItem('5', 0));
			dict.add(6,new dictItem('6', 0));
			dict.add(7,new dictItem('7', 0));
			dict.add(8,new dictItem('8', 0));
			dict.add(9,new dictItem('9', 0));
			dict.add(10,new dictItem('a', 0));
			dict.add(11,new dictItem('b', 0));
			dict.add(12,new dictItem('c', 0));
			dict.add(13,new dictItem('d', 0));
			dict.add(14,new dictItem('e', 0));
			dict.add(15,new dictItem('f', 0));
            //populate our dictionary with hex
        }
    }
    public static void main(String [] args){
        dictionary ourDictionary = new dictionary();
        ourDictionary.insertAlphabet();
        //the current number indexed
        int bufferSize = 16;
        //read in data to variable k
        int k = -1;
        //index to the next entry
        int w = -2;

        ArrayList<Integer> phrases = new ArrayList<Integer>();
        
        try{
            
			BufferedReader in = new BufferedReader(new FileReader(new File("encodedData.txt")));
            String s = "";
            
            //read input from stdin
            while(w != -1){
                //get the next values index
                s = in.readLine();
                if (w == -2)
                {
                    k = Integer.parseInt(s);
                }
                //add this phrase to the phrase list to keep track
                phrases.add(k);

                //use try catch in case this is the end of file
                try{
                    if (w == -2)
                    {
                        s = in.readLine();
                    }
                    w = Integer.parseInt(s);
                }
                catch (Exception e)
                {
                    w = -1;//If it failed, set w to -1 as a flag to stop
                }

                    int inputTracer = -1;
                    char missingChar = '?';//default missing char in case the next entry refers to the current entry
                    if (w != -1)
                    {

                        boolean referToPreviousIndex = false;
                        if (w == ourDictionary.dict.size() + 1)//If the next entry refers to the current, we must add this entry first with the default ? character
                        {
                            ourDictionary.addItem(missingChar, k);
                            referToPreviousIndex = true;
                        }

                        inputTracer = w;

                        //Find the first character of next phrase, make that the missing character
                        while (ourDictionary.dict.get(inputTracer - 1).input != 0)
                        {
                            inputTracer = ourDictionary.dict.get(inputTracer - 1).input;
                        }

                        missingChar = ourDictionary.dict.get(inputTracer - 1).c;

                        if (referToPreviousIndex == false)//If we did not need to add the missing character entry before, add it now
                        {
                            ourDictionary.addItem(missingChar, k);
                        }
                        else{
                            ourDictionary.dict.get(ourDictionary.dict.size() - 1).c = missingChar;//If we did, simply change the character
                        }
                    }
                    //make the next entry the current entry
                    k = w;
            }
            in.close();
            String output = "";
            //output the decoded data
            for (Integer integer : phrases) {
                output = output + ourDictionary.returnPhrase(integer);
            }

            System.out.println(output);

        }
        catch(Exception e){
            System.out.println(e);;
        }
    }
}