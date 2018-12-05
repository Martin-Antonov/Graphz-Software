package tests;


import model.GraphGenerator;

public class Test6Speed {

    public void execute(){
        GraphGenerator g = new GraphGenerator();

        this.testForVertices(100,g);
        this.testForVertices(200,g);
        this.testForVertices(500,g);
        this.testForVertices(1000,g);
        this.testForVertices(100,g);
        this.testForVertices(2000,g);
        this.testForVertices(4000,g);
        this.testForVertices(10000,g);
    }

    private void testForVertices(int n,GraphGenerator g){
        System.out.println("--Testing for "+n+" vertices--");
        long start = System.currentTimeMillis();
        g.complete(n);
        long end = System.currentTimeMillis();
        System.out.println("Tim: "+(end-start));
    }
}
