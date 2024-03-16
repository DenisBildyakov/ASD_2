import java.util.*;

public class AlgorithmsDataStructures2 {
    public static int[] GenerateBBSTArray(int[] a) {
        Arrays.sort(a);
        int[] resultArray = new int[a.length];
        int index = a.length / 2;
        getBST(a, resultArray, 0, index);
        return resultArray;
    }

    private static void getBST(int[] a, int[] resultArray, int rootIndex, int index) {
        resultArray[rootIndex] = a[index];
        int leftChild = 2 * rootIndex + 1;
        int rightChild = 2 * rootIndex + 2;
        int[] leftPart = Arrays.copyOfRange(a, 0, index);
        int[] rightPart = Arrays.copyOfRange(a, index + 1, a.length);
        if (leftPart.length < 1 || rightPart.length < 1) return;
        getBST(leftPart, resultArray, leftChild, leftPart.length / 2);
        getBST(rightPart, resultArray, rightChild, rightPart.length / 2);
    }
}