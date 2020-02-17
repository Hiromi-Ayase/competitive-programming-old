
public class Geometry {
	public static int cross(double[] A, double[] B, double[] C, double[] D, double[] P)
	{
		double r, s;
		double[] AC = new double[2];
		double BUNBO, EPS = 1.0e-8;
		int sw = -1;

		AC[0] = C[0] - A[0];
		AC[1] = C[1] - A[1];
		BUNBO = (B[0] - A[0]) * (D[1] - C[1]) - (B[1] - A[1]) * (D[0] - C[0]);
		if (Math.abs(BUNBO) > EPS) {
			r = ((D[1] - C[1]) * AC[0] - (D[0] - C[0]) * AC[1]) / BUNBO;
			s = ((B[1] - A[1]) * AC[0] - (B[0] - A[0]) * AC[1]) / BUNBO;
			if (r > -EPS && r < 1.0+EPS && s > -EPS && s < 1.0+EPS) {
				P[0] = A[0] + r * (B[0] - A[0]);
				P[1] = A[1] + r * (B[1] - A[1]);
				sw   = 1;
			}
			else
				sw = 0;
		}

		return sw;
	}

	public static boolean isCross(double[] A, double[] B, double[] C, double[] D)
	{

		double x1 = (A[0] - C[0]) * (B[1] - C[1]) - (B[0] - C[0]) * (A[1] - C[1]);
		double x2 = (A[0] - D[0]) * (B[1] - D[1]) - (B[0] - D[0]) * (A[1] - D[1]);

		double y1 = (C[0] - A[0]) * (D[1] - A[1]) - (D[0] - A[0]) * (C[1] - A[1]);
		double y2 = (C[0] - B[0]) * (D[1] - B[1]) - (D[0] - B[0]) * (C[1] - B[1]);
		return x1 * x2 < 0 && y1 * y2 < 0;
	}
}
