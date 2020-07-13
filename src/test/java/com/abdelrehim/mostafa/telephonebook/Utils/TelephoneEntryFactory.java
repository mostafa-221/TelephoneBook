package com.abdelrehim.mostafa.telephonebook.Utils;

import com.abdelrehim.mostafa.telephonebook.model.TelephoneEntry;

import java.util.Random;

public class TelephoneEntryFactory {
    public static final Random random = new Random();
    public static TelephoneEntry create(){
        TelephoneEntry entry = new TelephoneEntry();
        entry.setId(random.nextLong());
        entry.setPhoneNumber(""+random.nextInt());
        entry.setName("number"+entry.getId());
        return entry;
    }
    public static TelephoneEntry create(String number, String name){
        TelephoneEntry entry = new TelephoneEntry();
        entry.setId(random.nextLong());
        entry.setPhoneNumber(number);
        entry.setName(name);
        return entry;
    }

    /**
     *  Create a telephone entry that does not have an id
     */
    public static TelephoneEntry createUnidentified(String number, String name){
        TelephoneEntry entry = new TelephoneEntry();
        entry.setPhoneNumber(number);
        entry.setName(name);
        return entry;
    }
}
