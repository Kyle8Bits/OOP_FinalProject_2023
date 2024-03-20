package com.example.finalproject_oop_2023.Utils;

import com.example.finalproject_oop_2023.Items.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    static File file = new File("src/main/java/com/example/finalproject_oop_2023/SavingText/items.txt");

    private static int cnt = 1;

    public List<Item> readFromDatabase(){

        List<Item> itemList = new ArrayList<>();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;

            while( (line = bufferedReader.readLine()) != null){
                String[] tokens = line.split(",");

                //Split to each part of array
                String id = tokens[0];
                String title = tokens[1];

                String rentalTypeString = tokens[2];
                Item.RentalType rentalType = convertRentalType(rentalTypeString);

                String loanTypeString = tokens[3];
                Item.LoanType loanType = convertLoanType(loanTypeString);
                int copies = Integer.parseInt(tokens[4]);
                String fee = tokens[5];

                String genreString = tokens[6];

                String testGenre;
                if(rentalTypeString.equalsIgnoreCase("GAME")){
                    testGenre = "N/A";
                } else{
                    testGenre = genreString;
                }
                Item.Genre genre = convertGenre(testGenre);

                itemList.add(new Item(id, title, rentalType, loanType, genre, copies, fee));
//                return new Item(id, title, rentalType, loanType, genre, copies, fee, new Button("Rent"));
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return itemList;
    }

    public boolean addItem(String publishYear, String itemTitle, String rentalType, String loanType, int copies, String rentalFee,
                        String genre){
        try{
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(randomAccessFile.length());

            if(checkItemTitle(itemTitle)){
                return false;
            } else{
                Item item = new Item(generateId() + "-" + publishYear, itemTitle, convertRentalType(rentalType), convertLoanType(loanType),
                        convertGenre(genre), copies, rentalFee);

                //Wirte data into items.txt
                String itemData = generateId() + "-" + publishYear + "," + item.getTitle() + "," + item.getRentalType() + "," + item.getLoanType()
                        + "," + item.getCopies() + "," + item.getFee() + "," + item.getGenre();
                randomAccessFile.writeBytes(itemData + "\n");

                System.out.println("Add item successfully");
                return true;
            }
        } catch(FileNotFoundException e){
            System.out.println("File cannot null");
            throw new RuntimeException(e);
        } catch(IOException e){
            throw  new RuntimeException(e);
        }
    }

    public boolean checkItemTitle(String title) throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

        String itemTitle = null;
        String line;

        while( (line = randomAccessFile.readLine()) != null){
            String[] tokens = line.split(",");
            itemTitle = tokens[1];

            if(title.equals(itemTitle)){
                return true;
            }
        }
        return false;
    }

    public String generateId() throws IOException{
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

        String line;
        String id = null;

        while( (line = randomAccessFile.readLine()) != null){
            String[] tokens = line.split(",");
            id = tokens[0];

        }
        String[] id1= id.split("-");
        String id3= id1[0];
        if(id == null){
            return "I" + "001";
        } else{
            System.out.println(id3);
            String numberString = id3.substring(1);
            int afterIdScan = Integer.parseInt(numberString);

            String itemId = String.format("I%03d", cnt + afterIdScan);
            return itemId;
        }
    }

    public static Item.RentalType convertRentalType(String rentalType){
        if (rentalType.equalsIgnoreCase("Record") || rentalType.equalsIgnoreCase("OLD_MOVIE_RECORD")
        || rentalType.equalsIgnoreCase("Old movie records")){
            return Item.RentalType.OLD_MOVIE_RECORD;
        }
        if (rentalType.equalsIgnoreCase("DVD") || rentalType.equalsIgnoreCase("DVDs")){
            return Item.RentalType.DVD;

        }
        if (rentalType.equalsIgnoreCase("Game") || rentalType.equalsIgnoreCase("VIDEO_GAME")
        || rentalType.equalsIgnoreCase("Video Games")){
            return Item.RentalType.VIDEO_GAME;
        }

        return null;
    }


    public static Item.LoanType convertLoanType(String loanType){
        if(loanType.equalsIgnoreCase("1-week") || loanType.equalsIgnoreCase("ONE_WEEK_LOAN")
        || loanType.equalsIgnoreCase("1 week loan")){
            return Item.LoanType.ONE_WEEK_LOAN;
        }
        if(loanType.equalsIgnoreCase("2-day") || loanType.equalsIgnoreCase("TWO_DAYS_LOAN")
        || loanType.equalsIgnoreCase("2 days loan")){
            return Item.LoanType.TWO_DAYS_LOAN;
        }

        return null;
    }

    public ArrayList<String> readFile() throws IOException {


        ArrayList<String> lines= new ArrayList<>();

        BufferedReader bufferedReader= new BufferedReader(new FileReader(file));
        String line;
        while((line = bufferedReader.readLine()) != null){
            lines.add(line);
        }

        ArrayList<String[]> valueList= new ArrayList<>();
        for(String linevalue: lines){
            String[] tokens= linevalue.split(",");
            valueList.add(tokens);
        }
        bufferedReader.close();
        return lines;
    }



    public static Item.Genre convertGenre(String genre) {
        if(genre.equalsIgnoreCase("Action") || genre.equalsIgnoreCase("ACTION")){
            return Item.Genre.ACTION;
        }
        if(genre.equalsIgnoreCase("Horror") || genre.equalsIgnoreCase("HORROR")){
            return Item.Genre.HORROR;
        }
        if(genre.equalsIgnoreCase("Drama") || genre.equalsIgnoreCase("DRAMA")){
            return Item.Genre.DRAMA;
        }
        if(genre.equalsIgnoreCase("Comedy") || genre.equalsIgnoreCase("COMEDY")){
            return Item.Genre.COMEDY;
        }
        if(genre.equalsIgnoreCase("") || genre.equalsIgnoreCase("N/A") || genre.equalsIgnoreCase("NOT_APPLICABLE")){
            return Item.Genre.NOT_APPLICABLE;
        }

        return null;
    }

    public void saveItem(ArrayList<Item> items){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/java/com/example/finalproject_oop_2023/SavingText/items.txt")))) {
            for (Item item3 : items) {
                String line = itemToString(item3);
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String itemToString(Item item) {
        StringBuilder sb = new StringBuilder();
        sb.append(item.getId()).append(",");
        sb.append(item.getTitle()).append(",");
        sb.append(item.getRentalType()).append(",");
        sb.append(item.getLoanType()).append(",");
        sb.append(item.getCopies()).append(",");
        sb.append(item.getFee()).append(",");
        sb.append(item.getGenre());
        return sb.toString();
    }
}
