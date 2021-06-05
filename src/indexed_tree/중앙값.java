package indexed_tree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class 중앙값 {
	
	private static int N;
	private static int K;
	private static int[] input;
	private static int[] tree;
	private static int NN;
	private static int LIMIT = 65537;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("중앙값")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		input = new int[N+1]; 
		
		NN = 1;
		//0~65536개의 input값을 담을 수 있도록 트리 생성
		while(LIMIT > NN) {
			NN *= 2;
		}
		tree = new int[NN*2];
		
		for(int i=1; i<=N; i++) {
			input[i] = Integer.parseInt(br.readLine());
		}
		
		for(int i=1; i<K; i++) {
			//K-1개까지 트리에 업데이트
			updateTree(input[i]+NN, 1);
		}
		
		long answer = 0;
		for(int i=K; i<=N; i++) {
			//K번째 값 트리에 업데이트
			updateTree(input[i]+NN, 1);
			
			//트리에 K개의 값이 업데이트 되었으므로 중앙값 탐색
			answer += query(1, 1, NN, (K+1)/2);
			
			//현재 input값 위치에서 K-1번째 이전의 값 트리에서 제거
			updateTree(input[i-(K-1)]+NN, -1);
		}
		
		bw.append(String.valueOf(answer));
		
		bw.close();
	}

	private static int query(int node, int l, int r, int target) {
		if(l == r) {
			return l-1;
		}
		
		int mid = (l+r)/2;
		
		if(target <= tree[2*node]) {
			//중앙값이 왼쪽 자식에 있는 경우, 왼쪽으로 탐색
			return query(2*node, l, mid, target);
			
		}else {
			//중앙값이 오른쪽 자식에 있는 경우, 왼쪽 자식의 갯수를 빼고 오른쪽 자식으로 탐색
			return query(2*node+1, mid+1, r, target-tree[2*node]);
		}
	}

	private static void updateTree(int node, int value) {
		//입력된 숫자의 리프에 value만큼 더한 후, 구간 합 트리 갱신
		tree[node] += value;
		
		while(node > 1) {
			node /= 2;
			tree[node] = tree[2*node] + tree[2*node+1];
		}
	}
}
