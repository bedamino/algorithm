package indexed_tree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class 군인 {
	
	private static int N;
	private static int M;
	private static int[] input;
	private static int[] tree;
	private static int NN;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("군인")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		N = Integer.parseInt(br.readLine());
		input = new int[N+1];
		
		NN = 1;
		while(N > NN) {
			NN*=2;
		}
		tree = new int[NN*2];
		
		st = new StringTokenizer(br.readLine());
		for(int i=1; i<=N; i++) {
			input[i] = Integer.parseInt(st.nextToken());
		}
		
		initTree();
		
		M = Integer.parseInt(br.readLine());
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int cmd = Integer.parseInt(st.nextToken());
			
			if(cmd == 1) {
				//update
				int target = Integer.parseInt(st.nextToken());
				int value = Integer.parseInt(st.nextToken());
				updateTree(NN+target-1, value);
				
			}else {
				//query
				int target = Integer.parseInt(st.nextToken());
				bw.append(String.valueOf(query(1, 1, NN, target))).append("\n");
			}
		}
		
		bw.close();
	}

	private static int query(int node, int l, int r, int target) {
		if(l == r) {
			return l;
		}
		
		int mid = (l+r)/2;
		
		if(target <= tree[2*node]) {
			return query(2*node, l, mid, target);
		}else {
			return query(2*node+1, mid+1, r, target-tree[2*node]);
		}
	}

	private static void updateTree(int node, int value) {
		tree[node] += value;
		while(node > 1) {
			node/=2;
			tree[node] = tree[2*node] + tree[2*node+1];
		}
	}

	private static void initTree() {
		for(int i=1; i<=N; i++) {
			tree[NN+i-1] = input[i];
		}
		
		for(int i=NN-1; i>0; i--) {
			tree[i] = tree[2*i] + tree[2*i+1];
		}
	}
}
