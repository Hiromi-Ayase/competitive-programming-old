import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class AhoCorasick {
	private static String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static int[] A2I = new int[1000];
	
	static {
		int count = 0;
		for (char c : ALPHABET.toCharArray()) {
			A2I[c] = count ++;
		}
	}

	static class PMA {
		public PMA fail = null;
		public final PMA[] next = new PMA[ALPHABET.length()];
		public final Set<Integer> accept = new HashSet<>();

	}

	private PMA root;
	private int dicSize;
	
	public void build(List<String> words) {
		PMA root = new PMA();
		int count = 0;
		for (String word : words) {
			PMA t = root;
			for (char c : word.toCharArray()) {
				int i = A2I[c];
				if (t.next[i] == null) {
					t.next[i] = new PMA();
				}
				t = t.next[i];
			}
			t.accept.add(count ++);
		}

		LinkedList<PMA> queue = new LinkedList<>();
		for (int i = 0; i < ALPHABET.length(); i ++) {
			if (root.next[i] != null) {
				root.next[i].fail = root;
				queue.add(root.next[i]);
			} else {
				root.next[i] = root;
			}
		}
		
		while (!queue.isEmpty()) {
			PMA t = queue.poll();
			for (int i = 0; i < ALPHABET.length(); i ++) {
				if (t.next[i] != null) {
					queue.push(t.next[i]);
					PMA r = t.fail;
					while (r.next[i] == null) {
						r = r.fail;
					}
					t.next[i].fail = r.next[i];
					t.next[i].accept.addAll(r.next[i].accept);
				}
			}
		}
		this.root = root;
		this.dicSize = words.size();
	}
	
	public int[] match(String s) {
		int[] ret = new int[dicSize];
		PMA v = this.root;
		for (char c : s.toCharArray()) {
			int i = A2I[c];
			while (v.next[i] == null) {
				v = v.fail;
			}
			v = v.next[i];
			for (int j : v.accept) {
				ret[j] ++;
			}
		}
		return ret;
	}
}
