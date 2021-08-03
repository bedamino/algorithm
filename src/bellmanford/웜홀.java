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

public class 웜홀 {
	
	private static int T;
	private static int N;
	private static int M;
	private static int W;
	private static List<Edge> list;
	private static int[] min;
	private static int INF = 987654321; //INF + time < Integer.MAX_VALUE가 되는 적절한 값으로 INF값을 설정
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("웜홀")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;

		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			
			list = new ArrayList<Edge>();
			min = new int[N+1];
			Arrays.fill(min, INF);
			
			for(int i=0; i<M; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				
				//양방향
				list.add(new Edge(a, b, c));
				list.add(new Edge(b, a, c));
			}
			
			for(int i=0; i<W; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				
				//단방향
				list.add(new Edge(a, b, -c)); //웜홀 시간 음수 처리
			}
			
			boolean hasCycle = false;
			for(int i=1; i<=N; i++) {
				for(Edge edge : list) {
					//모든 지점에 대해서 최단 거리를 갱신하며 cycle 유/무 판단
					if(min[edge.to] > min[edge.from] + edge.time) {
						min[edge.to] = min[edge.from] + edge.time;
						if(i == N) {
							hasCycle = true;
						}
					}
				}
			}
			
			if(hasCycle) {
				bw.append("YES").append("\n");
			}else {
				bw.append("NO").append("\n");
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
