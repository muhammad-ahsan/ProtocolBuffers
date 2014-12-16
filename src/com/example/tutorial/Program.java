/*

Tutorial
https://developers.google.com/protocol-buffers/docs/javatutorial

*/

package com.example.tutorial;

import java.io.*;
import static com.example.tutorial.AddPerson.PromptForAddress;

public class Program {

    public static void main(String[] args) throws Exception {

        /*  Relevant in HDFS
         * /user/streambase/...........
         */

        String path = "file";
        Boolean PBRead = true;
        if (PBRead) {
            // Read the existing address book.
            AddressBookProtos.AddressBook addressBook
                    = AddressBookProtos.AddressBook.parseFrom(new FileInputStream(path));
            ListPeople.Print(addressBook);
        } else {
            AddressBookProtos.AddressBook.Builder addressBook = AddressBookProtos.AddressBook.newBuilder();

            // Read the existing address book.
            try {
                addressBook.mergeFrom(new FileInputStream(path));
            } catch (FileNotFoundException e) {
                System.out.println(path + ": File not found.  Creating a new file.");
            }

            // Add an address.
            addressBook.addPerson(
                    PromptForAddress(new BufferedReader(new InputStreamReader(System.in)),
                            System.out));

            // Write the new address book back to disk.
            FileOutputStream output = new FileOutputStream(path);
            addressBook.build().writeTo(output);
            output.close();
        }
    }
}
