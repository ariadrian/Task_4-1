/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consoleapp;

import javaChat.*;
/**
 *
 * @author Ari Adrian Ibrahim
 */
public class ConsoleApplication {
    private ClientConnection client;
    
    public class readInput extends Thread {
        
        @Override
        public void run() {            
            try {
                String inputKeyboard;
                do {
                    System.out.print(">> ");
                    inputKeyboard = client.inputString();
                    client.writeStream(inputKeyboard);                    
                } while (!inputKeyboard.equals("quit"));
                client.disconnect();
            } catch (Exception e) {
                System.out.println("Error "+ e.getMessage());                    
            }
        }
    }
    
    public class writeOutput extends Thread {
        
        @Override
        public void run() {
            try {
                String inputan;
                while ((inputan = client.readStream()) != null) {
                    System.out.println(inputan);
                    System.out.println(">> ");
                }
            } catch (Exception e) {
                System.out.println("Error "+ e.getMessage());
            }
        }
    }
    
    public void startChat () {
        try {
            client = new ClientConnection();
            System.out.print("Input Server IP : ");
            String ip = client.inputString();
            client.connect(ip);
            readInput in = new readInput();
            writeOutput out = new writeOutput();
            in.start();
            out.start();
        } catch (Exception e) {
            System.out.println("Error "+ e.getMessage());             
        }
    }
    
}