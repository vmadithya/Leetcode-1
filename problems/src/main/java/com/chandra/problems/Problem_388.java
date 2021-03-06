package com.chandra.problems;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Stack;

/**
 * 388. Longest Absolute File Path
 *
 * Suppose we abstract our file system by a string in the following manner:

 The string "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" represents:

 dir
    subdir1
    subdir2
        file.ext
 The directory dir contains an empty sub-directory subdir1 and a sub-directory subdir2 containing a file file.ext.

 The string "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext" represents:

 dir
    subdir1
        file1.ext
        subsubdir1
    subdir2
        subsubdir2
            file2.ext
 The directory dir contains two sub-directories subdir1 and subdir2. subdir1 contains a file file1.ext and an empty second-level sub-directory subsubdir1. subdir2 contains a second-level sub-directory subsubdir2 containing a file file2.ext.

 We are interested in finding the longest (number of characters) absolute path to a file within our file system. For example, in the second example above, the longest absolute path is "dir/subdir2/subsubdir2/file2.ext", and its length is 32 (not including the double quotes).

 Given a string representing the file system in the above format, return the length of the longest absolute path to file in the abstracted file system. If there is no file in the system, return 0.

 Note:
 The name of a file contains at least a . and an extension.
 The name of a directory or sub-directory will not contain a ..
 Time complexity required: O(n) where n is the size of the input string.

 Notice that a/aa/aaa/file1.txt is not the longest file path, if there is another path aaaaaaaaaaaaaaaaaaaaa/sth.png
 */
public class Problem_388 {

    public static class Solution {
        public int lengthLongestPath(String input) {

            // To Store level and length of path at that level
            HashMap<Integer, Integer> map = new HashMap<>();
            map.put(0, 0); // level 0 -> length 0

            int maxLength = 0, level = 1, currentLength = 0;
            boolean isFile = false;

            int index = 0;
            while (index < input.length()) {
                // Identify level by check # of \t
                while (input.charAt(index) == '\t') {
                    index++;
                    level++;
                }

                // Determine the length at each level
                // whenever we encounter \n then it's different level
                while (index < input.length() && input.charAt(index) != '\n') {

                    // if we encounter . then it's a file
                    if (input.charAt(index) == '.') isFile = true;
                    index++;
                    currentLength++;
                }

                // If it's a file then update Max Length accordingly
                if (isFile) {
                    maxLength = Math.max(maxLength, currentLength + map.get(level-1)); // previous level length + current level length
                } else {
                    map.put(level, currentLength + map.get(level - 1) + 1); // update the current level length by adding current level length to previous level length and +1 is for /
                }

                // restore the invariants
                isFile = false;
                currentLength = 0;
                level = 1;
                index++; // after determining the level length index will be pointing at \n hence increment
            }

            return maxLength;
        }
    }



    public static class Solution_1 {
        public int lengthLongestPath(String input) {
            Deque<Integer> stack = new ArrayDeque<>();
            String[] arr = input.split("\n");
            int maxLen = 0;
            stack.push(0); //dummy null length
            for (String s: arr) {
            /*
            numOfTabs is the number of "\t", numOfTabs = 0
            when "\t" is not found, because s.lastIndexOf("\t") returns -1.
            So normally, the first parent "dir" have numOfTabs 0.
            */
                int numOfTabs = s.lastIndexOf("\t") + 1;
            /* Level is defined as numOfTabs + 1.
            For example, in "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext",
            dir is level 1, subdir1 and subdir2 are level 2, file.ext is level3
            */
                int level = numOfTabs + 1;
            /*
            The following part of code is the case that we want to consider when there are
            several subdirectories in a same level. We want to remove
            the path length of the directory or the file of same level
            that we added during previous step, and calculate
            the path length of current directory or file that we are currently looking at.
            */
                while (level < stack.size()) stack.poll();
                int curLen = stack.peek() + s.length() - numOfTabs + 1;
                stack.push(curLen);
                if (s.contains(".")) maxLen = Math.max(maxLen, curLen - 1); //Only update the maxLen when a file is discovered,
                // And remove the "/" at the end of file
            }
            return maxLen;
        }
    }

    public static void main(String[] args) {
        Problem_388.Solution_1 solution_1 = new Solution_1();
        Problem_388.Solution solution = new Solution();
        System.out.println(solution_1.lengthLongestPath("dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"));
        System.out.println(solution.lengthLongestPath("dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"));
    }
}
