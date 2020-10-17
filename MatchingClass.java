import java.io.File;
import java.io.IOException;
import java.util.*;
public class MatchingClass{
    private static int[] employersindices;
    private static int[] studentsindices;
    private static String[] employers;
    private static String[] students;
    private static List<HeapPriorityQueue<Integer,Integer>> PQ;
    private static int[][] A;
    private static Stack<Integer> Sue;
    private static int N;
    public static void main(String[] args) {

    }
    public static void initialize(String st) throws IOException{
        Scanner inFile1 = new Scanner(new File(st)).useDelimiter(",");
        List<String> tokens = new ArrayList<String>();
        while (inFile1.hasNext()) {
            tokens.add(inFile1.nextLine());
          }
        int n = Integer.parseInt(tokens.get(0));
        N = n;
        employersindices = new int[n];
        studentsindices = new int[n];
        employers = new String[n];
        students = new String[n];
        Sue = new Stack<>();
        for(int i=0;i<n;i++){
          Sue.push(i);
        }
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
        PQ = new ArrayList<>();
        for(int i=2*n+1;i<tokens.size();i++){
          int y = 0;
          String mystring = tokens.get(i).replace(","," ").replace(" ","");
          HeapPriorityQueue<Integer,Integer> h = new HeapPriorityQueue<Integer,Integer>();
          for(int x=0;x<n;x++){
              int a=Character.getNumericValue(mystring.charAt(y++));
              y++;
              h.insert(a,x);
            }
            PQ.add(h);
        }
        A = new int[n][n];
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
      public static HashMap<Integer, Integer> execute(){
          while(!Sue.isEmpty()){
            int e = Sue.pop();
            int s = PQ.get(e).removeMin().getValue();
            int ep = studentsindices[s];
            if(ep==-1){
              studentsindices[s]=e;
              employersindices[e]=s;
              System.out.println("Match: "+employers[e]+"-"+students[s]);
            } else if(A[s][e]<A[s][ep]){
              studentsindices[s]=e;
              employersindices[e]=s;
              employersindices[ep]=-1;
              Sue.push(ep);
              System.out.println("Match: "+employers[e]+"-"+students[s]);
            } else {
              Sue.push(e);
            }
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i=0;i<N;i++){
          map.put(employersindices[i],i);
        }
        return map;
      }
}
