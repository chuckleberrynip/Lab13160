//Mohammed Azad
//Categorizes movies in the movie csv provided by grouplens
//Requires Java 13 
import java.io.*;
import java.util.*;
public class GenreList{
    public static void main(String args[]) throws IOException{
        File file = new File("movies.csv");
        System.out.println(file.getCanonicalPath());
        FileInputStream ft = new FileInputStream(file);
        DataInputStream in = new DataInputStream(ft);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String s;
        String[] arrayMap = {"Action", "Adventure", "Animation", "Children", "Comedy", "Crime", "Documentary","Drama",
            "Fantasy", "Film-Noir", "Horror", "IMAX", "Musical", "Mystery", "Romance", "Sci-Fi", "Thriller", "War", "Western", "(no genres listed)"};   
        HashMap<String, Integer> genreCounter = new HashMap<String, Integer>();
        HashMap<Integer, HashMap> yearMap = new HashMap<Integer, HashMap>();       
        for(String genre : arrayMap){
            genreCounter.put(genre, 0);
        }
        while((s = br.readLine()) != null){
            String[] splitarr = s.split(",");
            if(splitarr[0].equals("movieId")){
                continue;
            }
            String[] genre = splitarr[splitarr.length-1].split("\\|");
            for(String genreType : genre){
                genreCounter.replace(genreType, genreCounter.get(genreType)+1);
                String title = "";
                int year;
                for(int i = 1; i < splitarr.length-1; i++){
                    title+= splitarr[i];
                    if(splitarr.length > 3 && i < splitarr.length-2){
                        title+= ",";
                    }
                }
                try{
                    year = Integer.parseInt(title.substring(title.length()-5,title.length()-1));
                }catch(Exception e){
                    try{
                        year = Integer.parseInt(title.substring(title.length()-6,title.length()-2));
                    }catch(Exception f){
                        try{
                            year = Integer.parseInt(title.substring(title.length()-7,title.length()-3));
                        }catch(Exception g){
                            year = 0000;
                        }
                    }
                }
                title = (year != 0000) ? title.substring(0,title.length()-7) : title;
                if(!yearMap.containsKey(year)){
                    HashMap<String, Integer> hm = new HashMap<String, Integer>();
                    hm.put(genreType, 1);
                    yearMap.put(year, hm);
                }else if(!yearMap.get(year).containsKey(genreType)){
                    HashMap hm = yearMap.get(year);
                    hm.put(genreType, 1);
                    yearMap.replace(year, hm);
                }else{
                    HashMap hm = yearMap.get(year);
                    int counter = (Integer)hm.get(genreType);
                    hm.replace(genreType, ++counter);
                }
            }
        }
        in.close();
        HashMap<Integer, String> descendingMap = new HashMap<Integer, String>();
        ArrayList<Integer> counterList = new ArrayList<Integer>();
        HashMap<String, Integer> genreAverage = new HashMap<String, Integer>();
        int numberOfYears = yearMap.size();
        for(Map.Entry mapEle : genreCounter.entrySet()){
            String genre = (String)mapEle.getKey();
            int counter = (Integer)mapEle.getValue();
            int average = counter / numberOfYears;
            descendingMap.put(counter, genre);
            counterList.add(counter);
            genreAverage.put(genre, average);
        }
        Collections.sort(counterList, Collections.reverseOrder());      
        for(int counter : counterList){
            println("There are " + counter + " " + descendingMap.get(counter) + " movies(s). " + genreAverage.get(descendingMap.get(counter)) + " On average.");
        }
        yearMap.forEach((year, genreMap) -> {
            println("In " + year + ", there were:");
            ArrayList<Integer> yearCounter = new ArrayList<Integer>();
            HashMap<String, String> descendingYear = new HashMap<String, String>();
            genreMap.forEach((genre, counter) -> {
                descendingYear.put(counter + (String)genre, (String)genre);
                yearCounter.add((Integer)counter);
            });
            Collections.sort(yearCounter, Collections.reverseOrder());
            Set<String> keys = descendingYear.keySet();
            ArrayList<Integer> tempCounters = new ArrayList<Integer>();
            ArrayList<String> tempGenres = new ArrayList<String>();
            for(String key : keys){
                tempCounters.add(Integer.parseInt(key.replaceAll("\\D+","")));
                tempGenres.add(key.replaceAll("\\d",""));
            }
            int[] indexes = new int[tempCounters.size()];
            ArrayList<Integer> checkList = new ArrayList<Integer>();
            boolean done = false;
            while(!done){
                done = false;
                int index1 = -1;
                for(int i = 0; i < tempCounters.size(); i++){
                    if(!checkList.contains(i)){
                        index1 = i;
                        break;
                    }
                }
                if(index1 == -1) break;
                int largest = tempCounters.get(index1);
                for(int i = index1; i < tempCounters.size(); i++){
                    int element = tempCounters.get(i);
                    if(element > largest && !checkList.contains(i)){
                        largest = tempCounters.get(i);
                        index1 = i;
                    }
                }
                checkList.add(index1);
            }
            int index2 = 0;
            for(int counter : yearCounter){
                String key = tempGenres.get(checkList.get(index2));
                if(key.equals("nogenreslisted")) key = "(no genres listed)";
                String stringAppend;
                if(counter < genreAverage.get(key)) stringAppend = " Less than average.";
                else if(counter > genreAverage.get(key)) stringAppend = " More than average.";
                else stringAppend = " Same as the average.";
                println("\t" + counter + " " + tempGenres.get(checkList.get(index2)) + " movies(s)." + stringAppend);
                index2++;
            }
        });
    }	
    public static void print(String s) {
            System.out.print(s);
    }

    public static void println(String s) {
            System.out.println(s);
    }
}
