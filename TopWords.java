import java.io.File;
import java.io.FileNotFoundException; 
import java.util.*; 

public class TopWords { 
    public static void main(String[] args) { 
        String filePath = "./firstTask"; 
        File file = new File(filePath); 
        Scanner scanner = null; 
        try { 
            scanner = new Scanner(file);         
        } catch (FileNotFoundException e) {             
            e.printStackTrace(); 
            return;
        } 
        Map<String, Integer> wordCountMap = new HashMap<>(); 
        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase();
            word = word.replaceAll("[^a-zA-Zа-яА-Я0-9]", "");
            if (!word.isEmpty()) {
                wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
            }
        }
        scanner.close(); 
        List<Map.Entry<String, Integer>> list = new ArrayList<>(wordCountMap.entrySet()); 

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() { 
            @Override 
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) { 
                return o2.getValue().compareTo(o1.getValue());
            } 
        }); 
        
        System.out.println("Топ-10 самых часто встречающихся слов:");
        for (int i = 0; i < Math.min(10, list.size()); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getKey() + " - " + list.get(i).getValue() + " раз");
        }
    } 
}