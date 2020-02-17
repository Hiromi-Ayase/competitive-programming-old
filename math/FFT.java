
class FFT {
	public static int[] convolute(int[] g, int[] h) {
		double[] gg = new double[g.length];
		for (int i = 0; i < g.length; i++) {
			gg[i] = g[i];
		}
		double[] hh = new double[h.length];
		for (int i = 0; i < h.length; i++) {
			hh[i] = h[i];
		}
		double[] ret = convolute(gg, hh);
		int[] result = new int[ret.length];
		for (int i = 0; i < ret.length; i++) {
			result[i] = (int) Math.round(ret[i]);
		}
		return result;
	}

	public static double[] convolute(double[] g, double[] h) {
		int deg = g.length + h.length;
		int n = 1 << (32 - Integer.numberOfLeadingZeros(deg));

		double[] gRe = new double[n];
		double[] gIm = new double[n];
		double[] hRe = new double[n];
		double[] hIm = new double[n];
		for (int i = 0; i < g.length; i++) {
			gRe[i] = g[i];
		}
		for (int i = 0; i < h.length; i++) {
			hRe[i] = h[i];
		}

		fft(gRe, gIm, false);
		fft(hRe, hIm, false);

		double[] fRe = new double[n];
		double[] fIm = new double[n];
		for (int i = 0; i < n; i++) {
			fRe[i] = gRe[i] * hRe[i] - gIm[i] * hIm[i];
			fIm[i] = gIm[i] * hRe[i] + gRe[i] * hIm[i];
		}
		fft(fRe, fIm, true);
		return fRe;
	}

	public static void fft(double[] fRe, double[] fIm, boolean inverse) {
		int n = fRe.length;

		int shift = 1 + Integer.numberOfLeadingZeros(n);
		for (int k = 0; k < n; k++) {
			int j = Integer.reverse(k) >>> shift;
			if (j > k) {
				double tempRe = fRe[j];
				double tempIm = fIm[j];
				fRe[j] = fRe[k];
				fIm[j] = fIm[k];
				fRe[k] = tempRe;
				fIm[k] = tempIm;
			}
		}

		for (int i = 2; i <= n; i = i + i) {
			for (int k = 0; k < i / 2; k++) {
				double theta = -2 * k * Math.PI / i;

				double zetaRe = Math.cos(theta);
				double zetaIm = (inverse ? -1.0 : 1.0) * Math.sin(theta);

				for (int j = 0; j < n / i; j++) {
					int index1 = j * i + k + i / 2;
					int index2 = j * i + k;
					double f1Re = fRe[index1];
					double f1Im = fIm[index1];
					double f2Re = fRe[index2];
					double f2Im = fIm[index2];

					double taoRe = f1Re * zetaRe - f1Im * zetaIm;
					double taoIm = f1Im * zetaRe + f1Re * zetaIm;

					fRe[index1] = f2Re - taoRe;
					fIm[index1] = f2Im - taoIm;
					fRe[index2] = f2Re + taoRe;
					fIm[index2] = f2Im + taoIm;
				}
			}
		}

		if (inverse) {
			for (int i = 0; i < n; i++) {
				fRe[i] /= n;
				fIm[i] /= n;
			}
		}
	}
}
