package chapman779_qs;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import java.util.Comparator;

import java.util.Random;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public final class Quicksort {
	
	/* Private constructor to prevent instantiation */
	private Quicksort() {}
	
	/**
	 * Comparator class used for string comparison.
	 */
	private static final class StringComparator implements Comparator<String> {
		@Override
		public int compare(String s1, String s2) {
			if (s1 == s2) 
				return 0;
			
			if (s1 == null)
				return -1;
			
			if (s2 == null)
				return 1;
			
			return s1.compareTo(s2);
		}
	}
	
	/**
	 * Given a list of strings, partitions the list from index start to index end based on the ordering computed by some 
	 * String Comparator object. 
	 * 
	 * @param list
	 * 				the list to partition
	 * @param start
	 * 				the index for which to start partitioning elements
	 * @param end
	 * 				the index for which to stop partitioning elements
	 * @param p
	 * 				the partitioner element
	 * @param order
	 * 				used to compare strings within the list
	 * @return index at which the partitioner element appears after the partition operation has been completed
	 * @requires [partitioner element p is in the list] AND [for all integers i such that start <=  i <= partition,
	 * list[i] <= p] AND [for all integers i such that partition <= i <= end, list[i] >= p]
	 * 
	 */
	private static int partition(List<String> list, int start, int end, String p, Comparator<String> order) {
		assert list != null : "Error (partition): list is a null reference";
		assert p != null : "Error (partition): p is a null reference";
		assert order != null : "Error (partition): order is a null reference";
		assert list.contains(p) :"Error (partition): Partitioner element p is not in the list";
		
		int low = start;
		int high = end - 1;
		
		while (low < high) {
			String lowElement = list.get(low);
			String highElement = list.get(high);
			
			/* If the element at index low is less than p, increment the low index */
			if (order.compare(lowElement, p) < 0)
				low++;
			
			/* If the element at index high is greater than p, decrement the high index */
			else if (order.compare(highElement, p) > 0) 
				high--;
			
			/* If the element at index low is greater than or equal to p AND the element at index high is 
			 * less than or equal to p, swap the two elements. 
			 */
			else {
				Collections.swap(list, low, high);
				/* Account for case where both elements are equal to the partitioner element, increment the low counter
				 * so we don't get stuck in an infinite loop.
				 */
				if (list.get(low).equals(p) && list.get(high).equals(p))
					low++;
			}
		}
		
		return high;
	}
	
	/**
	 *  Given a subset of some list of strings, returns some random string.
	 *  
	 *  @param list
	 *  			the list of strings
	 *  @param start
	 *  			the starting index
	 *  @param end
	 *  			the ending index
	 *  @returns a random element of the list such that the position of this element is in the interval [start, end)
	 */
	private static String randomSelect(List<String> list, int start, int end) {
		assert list != null : "Error (randomSelect): list is a null reference";
		assert start < end : "Error (randomSelect): start >= end";
		assert end <= list.size() : "Error (randomSelect): end > list.size()";
		
		/* Generate some random integer in the interval [start, end) */
		Random rand = new Random();
		int index = rand.nextInt(end - start) + start;
		
		return list.get(index);
	}
	
	/** 
	 * Given a list of strings, sorts the list based on the ordering computed by some String Comparator object. 
	 * 
	 * @param list
	 * 				the list of strings
	 * @param start
	 * 				the position at which to start sorting
	 * @param end
	 * 				the length of the (sub)list to be sorted
	 * @param order
	 * 				string comparator object used to sort strings within the list
	 */
	private static void quicksort(List<String> list, int start, int end, Comparator<String> order) {
		assert list != null : "Error (quicksort): list is a null reference";
		
		if (start < end) {
			String rand = randomSelect(list, start, end);
		
			int pIndex = partition(list, start, end, rand, order);
			quicksort(list, start, pIndex, order);
			quicksort(list, pIndex + 1, end, order);
		}
	}
	
	/**
	 * Main method.
	 * 
	 * @param args
	 * 				the command line arguments
	 */
	public static void main(String[] args) {
		/* Get user input that prompts for the name of some file */
		Scanner readInput = new Scanner(System.in);
		System.out.print("Enter a file name: ");
		String fileName = readInput.nextLine();
		/* Done reading keyboard input, close the scanner */
		readInput.close();
		
		File file = new File(fileName);
		List<String> arr = new ArrayList<>();
		
		try {
			Scanner fileInput = new Scanner(file);
			
			/* Read lines from the file until no more lines are left to read */
			while (fileInput.hasNextLine()) {
				String next = fileInput.nextLine();
				
				/* Add each line we read to the list */
				arr.add(next);
			}
			
			/* Done reading input from the file, close the scanner */
			fileInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Comparator<String> order = new StringComparator();
		quicksort(arr, 0, arr.size(), order);
		
		System.out.println("Printing sorted list elements...");
		for (String s : arr) {
			System.out.println(s);
		}
	}
}

