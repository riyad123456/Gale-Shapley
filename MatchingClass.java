import java.io.*;
import java.util.*;
public class MatchingClass{
  //initializing all variables required
  //first two arrays store only indices
    private static int[] employersindices;
    private static int[] studentsindices;
    //these two arrays store names instead
    private static String[] employers;
    private static String[] students;
    private static List<HeapPriorityQueue<Integer,Integer>> PQ;
    private static int[][] A;
    private static Stack<Integer> Sue;
    private static int N;
    //set of stable matches
    private static HashMap<Integer, Integer> map;
    public static void main(String[] args)throws IOException {
            initialize("testN3.txt");
            execute();
            save("matches_testN3.txt");
    }
    public static void initialize(String st) throws IOException{
        //reading input from file
        Scanner inFile1 = new Scanner(new File(st)).useDelimiter(",");
        List<String> tokens = new ArrayList<String>();
        while (inFile1.hasNext()) {
            tokens.add(inFile1.nextLine());
          }
        //storing number n
        int n = Integer.parseInt(tokens.get(0));
        N = n;
        employersindices = new int[n];
        studentsindices = new int[n];
        employers = new String[n];
        students = new String[n];
        Sue = new Stack<>();
        //loop 1: pushing unmatched employers to Sue
        for(int i=0;i<n;i++){
          Sue.push(i);
        }
          //loop 2: setting all employers and students to -1
        for(int i=0;i<n;i++){
          employersindices[i]=studentsindices[i]=-1;
        }
        //loop 3: filling list of employers with their names
        int count1 = 0;
        for(int i=1;i<n+1;i++){
          employers[count1] = tokens.get(i);
          count1++;
        }
        //loop 4: filling list of students with their names
        int count2 = 0;
        for(int i=n+1;i<2*n+1;i++){
          students[count2] = tokens.get(i);
          count2++;
        }
        //loop 5: setting up the priority queue
        PQ = new ArrayList<>();
        for(int i=2*n+1;i<tokens.size();i++){
          int y = 0;
          //replacing commas with spaces for easier traversal
          String mystring = tokens.get(i).replace(","," ").replace(" ","");
          HeapPriorityQueue<Integer,Integer> h = new HeapPriorityQueue<Integer,Integer>();
          //loop 6: filling up PQ by skipping the second  element of each pair since we don't need it
          for(int x=0;x<n;x++){
              int a=Character.getNumericValue(mystring.charAt(y++));
              y++;
              h.insert(a,x);
            }
            PQ.add(h);
        }
        A = new int[n][n];
        int v = 0;
        //loop 7: setting up the matrix A
        for(int i=2*n+1;i<tokens.size();i++){
          int y = 0;
          String mystring = tokens.get(i).replace(","," ").replace(" ","");
          int[] sublist = new int[n];
          //loop 6: filling up A by skipping the first element of each pair since we don't need it
          for(int x=0;x<n;x++){
              y++;
              int a=Character.getNumericValue(mystring.charAt(y++));
              sublist[x] = a;
            }
            A[v]=sublist;
            v++;
        }
      }
      public static HashMap<Integer, Integer> execute()throws IOException{
        //Gale-Shapley algorithm
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
        //return hash map that stores the indice of the employer as key and the indice of the student matched to that employer as a value
        map = new HashMap<>();
        for(int i=0;i<N;i++){
          map.put(i,employersindices[i]);
        }
        return map;
      }
      public static void save(String filename)throws IOException{
        String output = "";
        int temp = 0;
        //loop 8: extracting names of students and employers from the names arrays using keys and values stored in the HashMap
        //setting up the ouput string to write to the output file
        for(int i=0;i<N;i++){
          int value = map.get(i);
          output+="Match "+temp+": "+employers[i]+"-"+students[value]+"\n";
          temp++;
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(output);
        writer.close();
      }
}
