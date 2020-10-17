import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
public class MatchingClass{
    public static void main(String[] args) throws IOException {
      Scanner inFile1 = new Scanner(new File("test.txt")).useDelimiter(",");
      List<String> tokens = new ArrayList<String>();
      while (inFile1.hasNext()) {
          tokens.add(inFile1.nextLine());
}
      int n = Integer.parseInt(tokens.get(0));
      int[] employersindices = new int[n];
      int[] studentsindices = new int[n];
      String[] employers = new String[n];
      String[] students = new String[n];
      for(int i=0;i<n;i++){
        employersindices[i]=studentsindices[i]=-1;
      }
      int count1 = 0;
      for(int i=1;i<n+1;i++){
        employers[count1] = tokens.get(i);
        count1++;
      }
      int count2 = 0;
      for(int i=n+1;i<2*n+1;i++){
        students[count2] = tokens.get(i);
        count2++;
      }
      List<HeapPriorityQueue<Integer,Integer>> result = new ArrayList<>();
      for(int i=2*n+1;i<tokens.size();i++){
        int y = 0;
        String mystring = tokens.get(i).replace(","," ").replace(" ","");
        HeapPriorityQueue<Integer,Integer> h = new HeapPriorityQueue<Integer,Integer>();
        for(int x=0;x<n;x++){
            int a=Character.getNumericValue(mystring.charAt(y++));
            y++;
            h.insert(a,x);
          }
          result.add(h);
      }
      int[][] A = new int[n][n];
      int v = 0;
      for(int i=2*n+1;i<tokens.size();i++){
        int y = 0;
        String mystring = tokens.get(i).replace(","," ").replace(" ","");
        int[] sublist = new int[n];
        for(int x=0;x<n;x++){
            y++;
            int a=Character.getNumericValue(mystring.charAt(y++));
            sublist[x] = a;
          }
          A[v]=sublist;
          v++;
      }

    }
}
