public class Solution {

    public static void main(String[] args) {
        int [][] dimensions = new int[][] {
                {3,4},
                {4,4}
        };
        System.out.println(FindMaxDiagonal.findMaxDiagonal(dimensions));
    }
}


class FindMaxDiagonal{
    public static int findMaxDiagonal(int [][] dimensions) {
        int MaxDiagonal = 0;
        int MaxArea = 0;

        for(int[] rect: dimensions) {
            int length = rect[0];
            int width = rect[1];
            int diagonal = length*length+width*width;
            int area = length * width;

            if(MaxDiagonal<diagonal) {
                MaxDiagonal = diagonal;
                MaxArea = area;
            } else if(MaxDiagonal == diagonal) {
                if (area > MaxArea) {
                    MaxArea = area;
                }
            }
        }
        return MaxArea;
    }
}
