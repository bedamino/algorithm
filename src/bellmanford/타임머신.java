package bellmanford;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class 타임머신 {
	
	public static int N;
	public static int M;
	public static List<Edge> edgeList;
	public static long[] min;
	public static long INF = Long.MAX_VALUE;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("타임머신")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;

		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		edgeList = new ArrayList<Edge>();
		
		min = new long[N+1];
		Arrays.fill(min, INF);
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			edgeList.add(new Edge(a, b, c));
		}
		
		min[1] = 0; //출발 도시의 최단 시간은 0
		boolean hasCycle = false;
		
		for(int i=1; i<=N; i++) { //총 N번의 edge relaxation 수행
			for(Edge edge : edgeList) {
				
				//출발지점의 최단 시간이 갱신 되었을 경우
				//이미 저장되어 있는 다음 지점까지의 최단 시간보다 (현재 지점까지의 최단 시간 + 다음 지점까지의 시간)이 작으면 갱신
				if(min[edge.from] != INF && min[edge.to] > min[edge.from] + edge.time) {
					min[edge.to] = min[edge.from] + edge.time;
					
					//N번째 edge relaxation이 발생하면 cycle이 존재 
					if(i == N) { 
						hasCycle = true;
					}
				}
			}
		}
		
		if(hasCycle) {
			bw.append("-1").append("\n");
			
		}else {
			for(int i=2; i<=N; i++) {
				if(min[i] == INF) {
					bw.append("-1").append("\n");
				}else {
					bw.append(String.valueOf(min[i])).append("\n");
				}
			}
		}
		
		bw.close();
	}
	
	public static class Edge {
		public int from;
		public int to;
		public int time;
		
		public Edge(int from, int to, int time) {
			this.from = from;
			this.to = to;
			this.time = time;
		}
	}
}
