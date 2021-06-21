package kruskal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class 학교탐방하기 {
	
	private static int N;
	private static int M;
	private static List<Edge> input;
	private static int[] root;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("학교탐방하기")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		input = new ArrayList<Edge>();
		
		for(int i=0; i<M+1; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			input.add(new Edge(a, b, c));
		}
		
		Collections.sort(input); //오르막길 -> 내리막길 순으로 정렬
		int up = mst(); //오르막길부터 연결했을 때 생성되는 MST에서의 오르막길 수
		
		Collections.reverse(input); //내리막길 -> 오르막길 순으로 정렬
		int down = mst(); //내리막길부터 연결했을 때 생성되는 MST에서의 오르막길 수
		
		bw.append(String.valueOf((up * up) - (down * down))); //피로도 계산
		
		bw.close();
	}
	
	private static int mst() {
		int count = 0;
		
		root = new int[N+1];
		for(int i=1; i<=N; i++) {
			root[i] = i;
		}
		
		for(Edge edge : input) {
			int a = find(edge.from);
			int b = find(edge.to);
			
			if(a == b) {
				continue;
			}
			
			root[b] = a;
			
			if(edge.type == 0) {
				//오르막길 수
				count++;
			}
		}
		
		return count;
	}

	private static int find(int a) {
		if(root[a] == a) {
			return a;
		}
		return root[a] = find(root[a]);
	}

	public static class Edge implements Comparable<Edge> {
		public int from;
		public int to;
		public int type;
		
		public Edge(int from, int to, int type) {
			this.from = from;
			this.to = to;
			this.type = type;
		}

		@Override
		public int compareTo(Edge o) {
			return this.type - o.type;
		}
		
	}
}
