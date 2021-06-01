package graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class 단절점찾기 {

	private static int V;
	private static int E;
	private static List<Integer>[] input;
	private static boolean[] visit;
	private static int[] in;
	private static int order;
	private static boolean[] cut;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("단절점찾기")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		visit = new boolean[V+1];
		in = new int[V+1];
		cut = new boolean[V+1];
		
		input = new List[V+1];
		for(int i=1; i<=V; i++) {
			input[i] = new ArrayList<Integer>();
		}
		
		for(int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			input[a].add(b);
			input[b].add(a);
		}
		
		//문제엑서 주어진 그래프가 연결 그래프가 아닐 수 있기 떄문에 모든 노드에 대해서 탐색
		for(int i=1; i<=V; i++) {
			if(!visit[i]) {
				dfs(i, true);
			}
		}
		
		int count = 0;
		for(int i=0; i<cut.length; i++) {
			if(cut[i]) {
				count++;
			}
		}
		
		bw.append(String.valueOf(count)).append("\n");
		
		for(int i=0; i<cut.length; i++) {
			if(cut[i]) {
				bw.append(String.valueOf(i)).append(" ");	
			}
		}
		
		bw.close();
	}

	private static int dfs(int cur, boolean isRoot) {
		in[cur] = ++order;  //현재 노드 방문 순서
		visit[cur] = true;
		int child = 0;
		
		int result = in[cur];
		
		for(int next : input[cur]) {
			if(!visit[next]) {
				child++;
				//자식 노드의 방문 순서중 가장 작은 값
				int low = dfs(next, false);
				
				//루트 노드가 아닌 경우에만 판별 가능
				if(!isRoot && low >= in[cur]) {
					cut[cur] = true;
				}
				
				result = Math.min(result, low);
				
			}else {
				//이미 방문한 자식인 경우, 그 자식과 현재 노드의 방문 순서중 작은 값을 선택 (우회 확인)
				result = Math.min(result, in[next]);
			}
		}
		
		//루트 노드인 경우 자식이 2개 이상일 때 무조건 단절점
		if(isRoot && child > 1) {
			cut[cur] = true;
		}
		
		return result;
	}
}
