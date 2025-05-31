package SpellChecker;
import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException;
import java.util.Scanner;

import GTUArrayList.GTUArrayList;
import GTUHashSet.GTUHashSet;

// A spell checker implementation that suggests corrections for misspelled words
public class SpellChecker {
    // Dictionary to store valid words
    private static GTUHashSet<String> dictionary;
    // Memory usage tracking for dictionary loading
    private static long dictionaryMemoryUsage;

    // Load dictionary from file and measure memory usage
    public static void initializeDictionary(String filename) throws IOException {
        // Measure memory before loading
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); 
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        
        // Load dictionary words
        dictionary = new GTUHashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String word;
        while((word = reader.readLine()) != null) {
            dictionary.add(word.trim().toLowerCase());
        }
        reader.close();
        
        // Calculate memory used
        runtime.gc(); 
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = memoryAfter - memoryBefore;
        dictionaryMemoryUsage = memoryUsed;
    }

    // Find spelling suggestions for a given word
    public static GTUArrayList findSuggestions(String word) {
        word = word.toLowerCase();
        GTUHashSet<String> seen = new GTUHashSet<>();
        GTUArrayList suggestions = new GTUArrayList();
        
        // Return empty list if word is correct
        if (dictionary.contains(word)) {
            return suggestions;
        }

        // Check words that are one edit away
        GTUArrayList dist1Variants = generateEditDistance1(word);
        for (int i = 0; i < dist1Variants.size(); i++) {
            String variant = dist1Variants.get(i);
            if (!seen.contains(variant) && dictionary.contains(variant)) {
                seen.add(variant);
                suggestions.add(variant);
            }
        }

        // Check words that are two edits away
        GTUArrayList dist2Variants = generateEditDistance2(word);
        for (int i = 0; i < dist2Variants.size(); i++) {
            String variant = dist2Variants.get(i);
            if (!seen.contains(variant) && dictionary.contains(variant)) {
                seen.add(variant);
                suggestions.add(variant);
            }
        }

        return suggestions;
    }

    // Generate all possible words that are one edit away from the input word
    public static GTUArrayList generateEditDistance1(String word) {
        GTUArrayList variants = new GTUArrayList();
        int len = word.length();
        char[] chars = word.toCharArray();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int alphabetLength = alphabet.length;

        // Generate transpositions (swap adjacent letters)
        for (int i = 0; i < len - 1; i++) {
            char temp = chars[i];
            chars[i] = chars[i + 1];
            chars[i + 1] = temp;
            variants.add(new String(chars));
            chars[i + 1] = chars[i];
            chars[i] = temp;
        }

        // Generate substitutions (replace one letter)
        for (int i = 0; i < len * alphabetLength; i++) {
            int charIndex = i / alphabetLength;
            int alphabetIndex = i % alphabetLength;

            char original = chars[charIndex];
            if (original != alphabet[alphabetIndex]) {
                chars[charIndex] = alphabet[alphabetIndex];
                variants.add(new String(chars));
            }
            chars[charIndex] = original;
        }

        // Generate deletions (remove one letter)
        StringBuilder sb = new StringBuilder(word);
        for (int i = 0; i < len; i++) {
            char deleted = sb.charAt(i);
            sb.deleteCharAt(i);
            variants.add(sb.toString());
            sb.insert(i, deleted);
        }

        // Generate insertions (add one letter)
        for (int i = 0; i < (len + 1) * alphabetLength; i++) {
            int insertIndex = i / alphabetLength;
            int alphabetIndex = i % alphabetLength;

            sb = new StringBuilder(word);
            sb.insert(insertIndex, alphabet[alphabetIndex]);
            variants.add(sb.toString());
        }

        return variants;
    }   

    // Generate all possible words that are two edits away from the input word
    public static GTUArrayList generateEditDistance2(String word) {
        GTUArrayList variants = new GTUArrayList();
        GTUHashSet<String> seen = new GTUHashSet<>();
        
        // Get all distance-1 variants first
        GTUArrayList dist1Variants = generateEditDistance1(word);
        
        // For each distance-1 variant, generate its distance-1 variants
        for (int i = 0; i < dist1Variants.size(); i++) {
            String dist1Variant = dist1Variants.get(i);
            
            // Skip variants that are too different in length
            int lenDiff = dist1Variant.length() - word.length();
            if (lenDiff > 2 || lenDiff < -2) {
                continue;
            }
            
            // Generate and add unique distance-2 variants
            GTUArrayList dist2Variants = generateEditDistance1(dist1Variant);
            for (int j = 0; j < dist2Variants.size(); j++) {
                String variant = dist2Variants.get(j);
                if (!seen.contains(variant)) {
                    seen.add(variant);
                    variants.add(variant);
                }
            }
        }
        
        return variants;
    }
    
    // Main program loop for interactive spell checking
    public static void main(String[] args) throws IOException {
        // Load dictionary and initialize scanner
        initializeDictionary("dictionary.txt");
        Scanner scanner = new Scanner(System.in);

        // Print initial statistics
        System.out.printf("Total Collisions: %d\n", dictionary.getCollisionCount());
        System.out.printf("Memory used while loading dictionary: %.2f MB\n", dictionaryMemoryUsage / (1024.0 * 1024.0)); 

        // Main program loop
        while(true) {
            System.out.print("Enter a word: ");
            String input = scanner.nextLine().trim();
            long startTime = System.nanoTime();

            // Check if word is in dictionary
            if(dictionary.contains(input.toLowerCase())) {
                System.out.println("Correct.");
            } else {
                System.out.println("Incorrect.");
                System.out.print("Suggestions: ");
                GTUArrayList suggestions = findSuggestions(input);
                
                // Print suggestions if any found
                if (suggestions.size() > 0) {
                    for (int i = 0; i < suggestions.size(); i++) {
                        System.out.print(suggestions.get(i));
                        if (i < suggestions.size() - 1) {
                            System.out.print(", ");
                        }
                    }
                } else {
                    System.out.print("No suggestions found");
                }
                System.out.println();
            }
            // Print performance metrics
            long endTime = System.nanoTime(); 
            System.out.printf("Lookup and suggestion took %.2f ms\n", (endTime - startTime) / 1e6); 
        }
    }
}
