package com.chandra.problems;

/**
 * 340. Longest Substring with At Most K Distinct Characters
 *
 * Given a string, find the length of the longest substring T that contains at most k distinct characters.
 For example, Given s = “eceba” and k = 2,
 T is "ece" which its length is 3.
 */

public class Problem_340 {

    public static class Solution {
        public static int lengthOfLongestSubstringKDistinct(String s, int k) {
            int[] map = new int[128];
            int start = 0, end = 0, maxLen = 0, counter = 0;

            while (end < s.length()) {
                if (map[s.charAt(end)] == 0) {
                    map[s.charAt(end)]++;
                    counter++;
                }
                end++;

                while (counter > k) {
                    map[s.charAt(start)]--;
                    if (map[s.charAt(start)] == 0) counter--;
                    start++;
                }

                maxLen = Math.max(maxLen, end - start);
            }

            return maxLen;
        }
    }

    public static class Solution_1 {
        // shorter version of above solution
        public static int lengthOfLongestSubstringKDistinct(String s, int k) {
            int[] count = new int[128];
            int start = 0, end = 0, max = 0, len = 0;
            // Sliding window mechanism
            while (end < s.length()) {
                if (count[s.charAt(end++)]++ == 0) len++;

                while (len > k)
                    if (count[s.charAt(start++)]-- == 1) len--;

                max = Math.max(max, end - start);
            }
            return max;
        }
    }


    public static class Solution_1a {
        // If questions asks to return the substring instead of length
        public String lengthOfLongestSubstringKDistinct(String s, int k) {
            String res = "";
            int[] count = new int[128];
            int start = 0, end = 0, max = 0, len = 0;

            while (end < s.length()) {
                if (count[s.charAt(end++)]++ == 0) len++;

                while (len > k)
                    if (count[s.charAt(start++)]-- == 1) len--;

                if (end - start > max) {
                    max = end - start;
                    res = s.substring(start, end);
                }
            }

            return res;
        }
    }

    public static void main(String[] args) {
        String s = "eceba";
        int k = 2;
        System.out.println(Problem_340.Solution.lengthOfLongestSubstringKDistinct(s, k));
        System.out.println(Problem_340.Solution_1.lengthOfLongestSubstringKDistinct(s, k));
    }
}
