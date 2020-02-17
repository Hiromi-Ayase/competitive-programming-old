//import java.util.PriorityQueue;
//
//class HuTucker {
//	long hu_tucker(long N, long[] A) {
//		  PriorityQueue<long[]> mpq;
//		 
//		  for (int i = 0; i < N - 1; i ++) {
//		    hpq[i] = NUlong;
//		    rig[i] = i + 1;
//		    lef[i] = i - 1;
//		    cst[i] = A[i] + A[i + 1];
//		    mpq.push(mp(cst[i], i));
//		  }
//		 
//		  long ans = 0;
//		  rep (k, N - 1) {
//		    long c, i;
//		    do {
//		      c = mpq.top().first;
//		      i = mpq.top().second;
//		      mpq.pop();
//		    } while (rig[i] == -1 || cst[i] != c);
//		 
//		    bool ml = false, mr = false;
//		    if (A[i] + fst(hpq[i]) == c) {
//		      hpq[i] = pop(hpq[i]);
//		      ml = true;
//		    }
//		    else if (A[i] + A[rig[i]] == c) {
//		      ml = mr = true;
//		    }
//		    else if (fst(hpq[i]) + snd(hpq[i]) == c) {
//		      hpq[i] = pop(pop(hpq[i]));
//		    }
//		    else {
//		      hpq[i] = pop(hpq[i]);
//		      mr = true;
//		    }
//		 
//		    ans += c;
//		    hpq[i] = add(hpq[i], c);
//		 
//		    if (ml) A[i] = INF;
//		    if (mr) A[rig[i]] = INF;
//		 
//		    if (ml && i > 0) {
//		      long j = lef[i];
//		      hpq[j] = merge(hpq[j], hpq[i]);
//		      rig[j] = rig[i];
//		      rig[i] = -1;
//		      lef[rig[j]] = j;
//		      i = j;
//		    }
//		    if (mr && rig[i] + 1 < N) {
//		      long j = rig[i];
//		      hpq[i] = merge(hpq[i], hpq[j]);
//		      rig[i] = rig[j];
//		      rig[j] = -1;
//		      lef[rig[i]] = i;
//		    }
//		 
//		    cst[i] = A[i] + A[rig[i]];
//		    cst[i] = min(cst[i], min(A[i], A[rig[i]]) + fst(hpq[i]));
//		    cst[i] = min(cst[i], fst(hpq[i]) + snd(hpq[i]));
//		    mpq.push(mp(cst[i], i));
//		  }
//		 
//		  return ans;
//		}
//}
