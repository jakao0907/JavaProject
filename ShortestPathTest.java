package ntou.cs.java2020.project;

import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class shortestPathTest extends shortestPathGUI{
	private static int n;
	private static int m=0;
	private static JLabel testNumLabel;
	private static JPanel vertexNumberPanel;
	private static boolean flag=false;
	private static ArrayList<edge> testGraph = new ArrayList<edge>();
	private int[][] adjacencyMartix;
	private int[] X,Y;
	private disjointSet myDS;

	public static void main(String[] args){
		new shortestPathTest();
	}

	public void paintPoint(){
		update(super.g);
		X = new int[n+1];
		Y = new int[n+1];
		final double PIDouble=Math.PI*2;
		int s=0;
		super.g.setColor(Color.BLACK);
		for(double a=0;a<PIDouble;a+=PIDouble/n){
			X[s] = (int) (Math.round((super.gheight/2-1) * Math.cos(a)*Math.pow(10,0))/Math.pow(10,0)*0.8+(super.gwidth/2));
            Y[s] = (int) (Math.round((super.gheight/2-1) * Math.sin(a)*Math.pow(10,0))/Math.pow(10,0)*0.8+(super.gheight/2));
            super.g.drawOval(X[s]+super.gx,Y[s]+super.gy,30,30);
            super.g.drawString(Integer.toString(s+1),X[s]+super.gx+12,Y[s]+super.gy+19);
            // System.out.println((X[s]+super.gx)+ " "+(Y[s]+super.gy ));
            s++;
		}
	}

	public void drawPoint(){
		System.out.println(n);
	}

	public void drawEdge(int a,int b){
		if(a>b){
			int temp = a;
			a = b;
			b = temp;
		}
		double d = (double)(Y[b-1]-Y[a-1])/(X[b-1]-X[a-1]);
		double angle = -Math.atan(d)/(Math.PI/180.0);
		int diffX,diffY;
		diffX = Math.abs((int) Math.round((Math.cos(angle)*15)));
		diffY = Math.abs((int) -Math.round((Math.sin(angle)*15)));
		if(X[a-1]>X[b-1]){
			if(Y[a-1]>Y[b-1]){
				super.g.drawLine(X[a-1]+super.gx+15-diffX,Y[a-1]+super.gy+15-diffY,X[b-1]+super.gx+15+diffX,Y[b-1]+super.gy+15+diffY);
			}
			else{
				super.g.drawLine(X[a-1]+super.gx+15-diffX,Y[a-1]+super.gy+15+diffY,X[b-1]+super.gx+15+diffX,Y[b-1]+super.gy+15-diffY);
			}
		}
		else{
			if(Y[a-1]>Y[b-1]){
				super.g.drawLine(X[a-1]+super.gx+15+diffX,Y[a-1]+super.gy+15-diffY,X[b-1]+super.gx+15-diffX,Y[b-1]+super.gy+15+diffY);
			}
			else{
				super.g.drawLine(X[a-1]+super.gx+15+diffX,Y[a-1]+super.gy+15+diffY,X[b-1]+super.gx+15-diffX,Y[b-1]+super.gy+15-diffY);
			}
		}
		super.g.setColor(Color.BLACK);
		
	}
	
	public void play(){
		super.g.setColor(Color.RED);
		super.g.drawString("initialize-single-source",50,300);
		int[] dis = new int[n+1];
		for(int i=1;i<=n;i++)	dis[i]=(int)1e9;
		PriorityQueue<pair> pq = new PriorityQueue<pair>(n*n,new pairComparator());
		int s = Integer.parseInt(startVertexComboBox.getSelectedItem().toString());
		dis[s] = 0;
		pq.add(new pair(dis[s],s));
		super.g.setColor(Color.BLACK);
		int cnt=0;
		for(edge i:testGraph){
			super.g.drawString(i.u+" - "+i.v+" length = "+i.len,870,300+20*cnt);
			cnt++;
		}
		super.g.drawString("distance from start vertex to each vertex",50,600);
		for(int i=1;i<=n;i++){
			if(dis[i]==(int)(1e9))
				super.g.drawString(i+"= inf",50,600+20*i);
			else
				super.g.drawString(i+"= "+dis[i],50,600+20*i);
		}
		try{
			Thread.sleep(1000);
		}
		catch(InterruptedException ie){
			System.err.println("sleep err");
		}
		super.g.drawString("initialize-single-source",50,300);
		while(pq.size()>0){
			super.g.setColor(Color.RED);
			super.g.drawString("while PQ(PriorityQueue) not empty",50,320);
			try{
				Thread.sleep(1000);
			}
			catch(InterruptedException ie){
				System.err.println("sleep err");
			}
			super.g.setColor(Color.BLACK);
			super.g.drawString("while PQ(PriorityQueue) not empty",50,320);
			super.g.setColor(Color.RED);
			super.g.drawString("    do u <- extract-min(PQ)",50,340);
			try{
				Thread.sleep(1000);
			}
			catch(InterruptedException ie){
				System.err.println("sleep err");
			}
			super.g.setColor(Color.BLACK);
			super.g.drawString("    do u <- extract-min(PQ)",50,340);
			pair node = pq.poll();
			for(int i=1;i<=n;i++){
				super.g.setColor(Color.RED);
				super.g.drawString("    for each neighbor i of u",50,360);
				drawEdge(node.second,i);
				try{
					Thread.sleep(1000);
				}
				catch(InterruptedException ie){
					System.err.println("sleep err");
				}
				super.g.setColor(Color.BLACK);
				super.g.drawString("    for each neighbor i of u",50,360);
				if(adjacencyMartix[node.second][i]>0&&dis[i]>dis[node.second]+adjacencyMartix[node.second][i]){
					super.g.setColor(Color.RED);
					super.g.drawString("        do relex(u,i,weight)",50,380);
					super.g.setColor(new Color(238,238,238));
					if(dis[i]==(int)(1e9))
						super.g.drawString(i+"= inf",50,600+20*i);
					else
						super.g.drawString(i+"= "+dis[i],50,550+20*i);
					dis[i] = dis[node.second]+adjacencyMartix[node.second][i];
					pq.add(new pair(dis[i],i));
					System.out.println("update vertex "+i+" distance to "+dis[i]);
					try{
						Thread.sleep(1000);
					}
					catch(InterruptedException ie){
						System.err.println("sleep err");
					}
					super.g.setColor(Color.BLACK);
					super.g.drawString("        do relex(u,i,weight)",50,380);
					super.g.drawString(i+"= "+dis[i],50,600+20*i);
					drawEdge(node.second,i);
				}
				if(adjacencyMartix[node.second][i]>0){
					super.g.setColor(Color.BLACK);
					drawEdge(node.second,i);	
				}
				// else{
				// 	super.g.setColor(new Color(238,238,238));
				// 	drawEdge(node.second,i);
				// }
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==buildButton){
			if(vertexNumberText.getText().equals("")){
				super.errLabel.setForeground(Color.red);
				super.errLabel.setText("無輸入!!!");
			}
			else{
				super.errLabel.setText("");
				testGraph = new ArrayList<edge>();
				n = Integer.parseInt(vertexNumberText.getText());
				m = 0;
				myDS = new disjointSet(n);
				adjacencyMartix = new int[n+1][n+1];
				for(int i=0;i<=n;i++){
					for(int j=0;j<=n;j++){
						adjacencyMartix[i][j]=0;
					}
				}
				paintPoint();
				startVertexComboBox.removeAllItems();
				startVertexComboBox.addItem("請選擇");
				fromPointTest.removeAllItems();
				fromPointTest.addItem("請選擇");
				for(int i=1;i<=n;i++){
					fromPointTest.addItem(i);
					startVertexComboBox.addItem(i);
				}
				startVertexComboBox.setEnabled(true);
				fromPointTest.setEnabled(true);
				toPointTest.setEnabled(false);
				weightPointTest.setEnabled(true);
				addEdgeButton.setEnabled(true);
				startButton.setEnabled(true);
			}
		}
		else if(e.getSource()==startButton){
			if(startVertexComboBox.getSelectedItem().equals("請選擇")){
				errLabel.setForeground(Color.red);
				errLabel.setText("請輸入起始點");
			}
			else{
				paintPoint();
				super.g.setColor(Color.BLACK);
				for(edge i:testGraph){
					drawEdge(i.u,i.v);
				}
				boolean flag=true;
				errLabel.setText("");
				for(int i=2;i<=n;i++){
					if(myDS.find(i-1)!=myDS.find(i)){
						flag=false;
					}
				}
				if(flag){
					super.g.setColor(Color.BLACK);
					super.g.drawString("initialize-single-source",50,300);
					super.g.drawString("while PQ(PriorityQueue) not empty",50,320);
					super.g.drawString("    do u <- extract-min(PQ)",50,340);
					super.g.drawString("    for each neighbor i of u",50,360);
					super.g.drawString("        do relex(u,i,weight)",50,380);
					play();
				}
				else{
					errLabel.setForeground(Color.red);
					errLabel.setText("需先將整張圖連通!");
				}
			}
		}
		else if(e.getSource()==addEdgeButton){
			if(weightPointTest.getText().equals("")){
				errLabel.setForeground(Color.red);
				errLabel.setText("請輸入權重!");
			}
			else if(fromPointTest.getSelectedItem().equals("請選擇")){
				errLabel.setForeground(Color.red);
				errLabel.setText("請選擇端點1");
			}
			else if(toPointTest.getSelectedItem().equals("請選擇")){
				errLabel.setForeground(Color.red);
				errLabel.setText("請選擇端點2");
			}
			else if(Integer.parseInt(weightPointTest.getText())<=0){
				errLabel.setForeground(Color.red);
				errLabel.setText("邊權重需為正整數!");
			}
			else{
				errLabel.setText("");
				int a = Integer.parseInt(fromPointTest.getSelectedItem().toString());
				int b = Integer.parseInt(toPointTest.getSelectedItem().toString());
				int c = Integer.parseInt(weightPointTest.getText());
				if(adjacencyMartix[a][b]>0){
					errLabel.setForeground(Color.red);
					errLabel.setText("此邊已設值");
				}
				else{
					myDS.union(a,b);
					m++;
					adjacencyMartix[a][b]=adjacencyMartix[b][a]=c;
					System.out.printf("%d %d %d\n",a,b,c);
					super.g.setColor(Color.BLACK);
					super.g.drawString(a+" - "+b+" length = "+c,870,300+20*testGraph.size());
					drawEdge(a,b);
					testGraph.add(new edge(a,b,c));
					fromPointTest.setSelectedIndex(0);
					toPointTest.setSelectedIndex(0);
					weightPointTest.setText("");
					toPointTest.setEnabled(false);
				}
			}
		}
		else if(fromPointTest!=null&&e.getSource()==fromPointTest){
			int n=Integer.parseInt(vertexNumberText.getText());
			if(fromPointTest.getSelectedItem()!=null&&!fromPointTest.getSelectedItem().toString().equals("請選擇")){
				toPointTest.removeAllItems();
				toPointTest.addItem("請選擇");
				for(int i=1;i<=n;i++){
					if(i!=Integer.parseInt(fromPointTest.getSelectedItem().toString()))
					toPointTest.addItem(i);
				}
				toPointTest.setEnabled(true);
			}
		}
		else{
			System.out.println("?");
		}
	}
}