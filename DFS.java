package Graph3;

import java.util.ArrayList;
import java.util.List;

import java.lang.*;


public class DFS {
    private List<ArrayList<Integer>> pathList1 = new ArrayList<ArrayList<Integer>>();
    private List<ArrayList<Integer>> pathList2 = new ArrayList<ArrayList<Integer>>();

    private  String st ="";


    private int v;


    private ArrayList<Integer>[] adjList;

    public DFS(int vertices) {
        this.v = vertices;
        pathList1.clear();


        initAdjList();
    }


    private void initAdjList()
    {
        adjList = new ArrayList[v];

        for (int i = 0; i < v; i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    public void addEd(int u, int v) {
        adjList[u].add(v);
    }

    public void printAllPaths(int s, int d) {
        boolean[] isVisited = new boolean[v];
        ArrayList<Integer> pathList = new ArrayList<>();

        pathList.add(s);
        printAllPathsUtil(s, d, isVisited, pathList);
    }

    private void printAllPathsUtil(Integer u, Integer d,
                                   boolean[] isVisited,
                                   List<Integer> localPathList)
    {

        if (u.equals(d)) {
            System.out.println(localPathList);
            st+=localPathList+"\n";
            ArrayList<Integer> tmp = new ArrayList<>();

            for (int i = 0; i< localPathList.size(); i++){
                tmp.add(localPathList.get(i));
            }
            pathList1.add(tmp);
            pathList2.add(tmp);
            return;
        }

        isVisited[u] = true;

        for (Integer i : adjList[u]) {
            if (!isVisited[i]) {
                localPathList.add(i);
                printAllPathsUtil(i, d, isVisited, localPathList);
                localPathList.remove(i);
            }
        }

        isVisited[u] = false;
    }

    public  ArrayList getValue() {
        int index=0;
        int temp=10000;



        List<ArrayList<Integer>> pathList1 = this.pathList1;
        pathList1 = this.pathList1;
        for (int k=0; k<pathList1.size(); k++){
            if (temp>pathList1.get(k).size()){
                temp=pathList1.get(k).size();
                index=k;
            }
        }

        return pathList1.get(index);

    }

    public ArrayList clp(){
        pathList1.clear();
        return pathList2.get(0);
    }




    public String getString(){
        String k = new String(st);
        st = "";
        return k;
    }

}