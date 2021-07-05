package services;

import models.Sneaker;
import models.Whiskey;
import utils.CSVUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WhiskeyService {
    private static int nextId = 1;
    private static ArrayList<Whiskey> inventory = new ArrayList<>();
    public  Whiskey create(String name, String brand, int proof, float size, int quantity, float price) {
        Whiskey createdWhiskey = new Whiskey(nextId++, name, brand, proof, size, quantity, price);
        inventory.add(createdWhiskey);
        return createdWhiskey;
    }

    public Whiskey findWhiskey(int id) {
        for(Whiskey element : inventory){
            if(element.getId() == id){
                return element;
            }
        }
        return null;
    }

    public Whiskey[] findAll() {
        // should return a basic array copy of the ArrayList
        return inventory.toArray(new Whiskey[0]);
    }

    public boolean delete(int id) {
        for(Whiskey element: inventory){
            if(element.getId() == id){
                inventory.remove(element);
                return true;
            }
        }
        return false;
    }

    public static void csvFileSaver() throws IOException {
        String csvFile = "/Users/zach/Desktop/Whiskey.csv";
        FileWriter writer = new FileWriter(csvFile);

        CSVUtils.writeLine(writer, new ArrayList<String>(Arrays.asList(String.valueOf(nextId))));  // (2)

        for (Whiskey s : inventory) {
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(s.getId()));
            list.add(s.getName());
            list.add(s.getBrand());
            list.add(String.valueOf(s.getProof()));
            list.add(String.valueOf(s.getSize()));
            list.add(String.valueOf(s.getQty()));
            list.add(String.valueOf(s.getPrice()));

            CSVUtils.writeLine(writer, list);
        }


        writer.flush();
        writer.close();
    }

    public static void loadData(){
        // (1)
        String csvFile = "/Users/zach/Desktop/Whiskey.csv";
        String line = "";
        String csvSplitBy = ",";

        // (2)
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            nextId = Integer.parseInt(br.readLine());  // (3)

            while ((line = br.readLine()) != null) {
                // split line with comma
                String[] beer = line.split(csvSplitBy);

                // (4)
                int id = Integer.parseInt(beer[0]);
                String name = beer[1];
                String brand = beer[2];
                int proof = Integer.parseInt(beer[3]);
                float size = Float.parseFloat(beer[4]);
                int qty = Integer.parseInt(beer[5]);
                float price = Float.parseFloat(beer[6]);

                // (5)
                inventory.add(new Whiskey(id, name, brand, proof, size, qty, price));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
