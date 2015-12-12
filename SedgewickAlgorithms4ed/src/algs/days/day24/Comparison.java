package algs.days.day24;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import algs.days.day23.EdgeWeightedDigraph;

// will post friday
//import algs.days.day24.hw5.FloydWarshall;

public class Comparison {
	public static void main(String[] args) {
		StdRandom.setSeed(100); // reproducible
		for (int N = 32; N <= 2048; N *= 2) {
			EdgeWeightedDigraph dg = RandomWeightedDigraph.random(N, .17);

			Stopwatch swD = new Stopwatch();
			DijkstraAllPairsSP path = new DijkstraAllPairsSP(dg);
			double timeD = swD.elapsedTime();
			Stopwatch swF = new Stopwatch();
			//On Friday I will be able to post this code.
			//FloydWarshall fw = new FloydWarshall(dg);
			double timeFW = swF.elapsedTime();

			// compute longest non-infinity distance found in the graph for ASP
			double maxD = 0;
			int srcD = -1;
			int targetD = -1;
			for (int i = 0; i < dg.V(); i++) {
				for (int j = 0; j < dg.V(); j++) {
					if (path.hasPath(i, j)) {
						if (path.dist(i, j) > maxD) {
							srcD = i;
							targetD = j;
							maxD = path.dist(i, j);
						}
					}
				}
			}

			// compute longest non-infinity distance found in the graph for ASP
			double maxFW = 0;
			int srcFW = -1;
			int targetFW = -1;
//			for (int i = 0; i < dg.V(); i++) {
//				for (int j = 0; j < dg.V(); j++) {
//					if (fw.hasPath(i, j)) {
//						if (fw.dist(i, j) > maxFW) {
//							srcFW = i;
//							targetFW = j;
//							maxFW = fw.dist(i, j);
//						}
//					}
//				}
//			}

			
			System.out.println(N + "\t" + dg.E() + "\t" + "maxD=" + maxD + "(" + srcD + "," + targetD + ") time=" + timeD + "\t" + 
									"maxD=" + maxFW + "(" + srcFW + "," + targetFW + ") time=" + timeFW);
		}
	}
}

/*

0.06 scale]
32		5		maxD=3.1132118190615516(1,11) time=0.006		maxD=3.1132118190615516(1,11) time=0.007
64		25		maxD=2.804584539882267(32,60) time=0.002		maxD=2.804584539882267(32,60) time=0.004
128		97		maxD=4.311075905398344(11,97) time=0.004		maxD=4.311075905398344(11,97) time=0.009
256		339		maxD=8.042328725647609(51,208) time=0.016		maxD=8.042328725647609(51,208) time=0.067
512		1411	maxD=11.24921375262222(73,509) time=0.069		maxD=11.24921375262222(73,509) time=0.532
1024	5599	maxD=21.900742047319962(103,1012) time=0.445	maxD=21.900742047319962(103,1012) time=4.361
2048	22238	maxD=26.283094444642597(562,2017) time=3.047	maxD=26.283094444642597(562,2017) time=35.412

0.13 scale
32		25		maxD=3.230724209879762(2,25) time=0.007		maxD=3.230724209879762(2,25) time=0.007
64		102		maxD=6.5737510455055235(3,49) time=0.003	maxD=6.5737510455055235(3,49) time=0.003
128		403		maxD=9.92235059993795(16,114) time=0.007	maxD=9.92235059993795(16,114) time=0.009
256		1596	maxD=12.479428137193105(75,236) time=0.032	maxD=12.479428137193105(75,236) time=0.073
512		6298	maxD=22.439549468626947(0,402) time=0.215	maxD=22.439549468626947(0,402) time=0.561
1024	24749	maxD=26.734301379446187(3,703) time=1.705	maxD=26.734301379446187(3,703) time=4.696
2048	97959	maxD=28.67458026760832(46,537) time=16.825	maxD=28.67458026760832(46,537) time=37.714

0.15 scale
32		29		maxD=3.7885199023426295(1,26) time=0.006	maxD=3.7885199023426295(1,26) time=0.006
64		138		maxD=10.9779343751023(10,47) time=0.003		maxD=10.9779343751023(10,47) time=0.004
128		490		maxD=13.87445683787349(1,126) time=0.008	maxD=13.87445683787349(1,126) time=0.009
256		2013	maxD=16.767170128474977(5,255) time=0.035	maxD=16.767170128474977(5,255) time=0.071
512		8118	maxD=17.21538290559843(160,511) time=0.264	maxD=17.21538290559843(160,511) time=0.568
1024	32452	maxD=24.727855069685464(425,836) time=2.27	maxD=24.727855069685464(425,836) time=4.771
2048	131190	maxD=23.871040882780722(39,365) time=28.819	maxD=23.871040882780722(39,365) time=36.842

0.17 scale
32		35		maxD=4.813279678481953(2,26) time=0.007		maxD=4.813279678481953(2,26) time=0.006
64		181		maxD=10.87249909925824(0,58) time=0.002		maxD=10.87249909925824(0,58) time=0.003
128		665		maxD=13.201243306431817(16,118) time=0.008	maxD=13.201243306431817(16,118) time=0.009
256		2509	maxD=16.49414337442694(5,236) time=0.04		maxD=16.49414337442694(5,236) time=0.072
512		9985	maxD=19.819213837254782(189,490) time=0.307	maxD=19.819213837254782(189,490) time=0.56
1024	40773	maxD=20.355631263722323(89,331) time=2.814	maxD=20.355631263722323(89,331) time=4.639
2048	161701	maxD=22.448556686198668(38,255) time=36.622	maxD=22.448556686198668(38,255) time=36.668


0.19 scale
32		47		maxD=4.751576371463347(9,29) time=0.007			maxD=4.751576371463347(9,29) time=0.006
64		215		maxD=8.509607310151646(1,59) time=0.002			maxD=8.509607310151646(1,59) time=0.003
128		818		maxD=13.142901519185843(5,91) time=0.009		maxD=13.142901519185843(5,91) time=0.009
256		2960	maxD=14.906891565515606(4,205) time=0.045		maxD=14.906891565515606(4,205) time=0.071
512		12593	maxD=19.440178150675386(255,474) time=0.392		maxD=19.440178150675386(255,474) time=0.536
1024	50671	maxD=18.799497575031772(897,1015) time=3.509	maxD=18.799497575031772(897,1015) time=4.438
2048	200935	maxD=21.72587299856029(1356,1560) time=48.855	maxD=21.72587299856029(1356,1560) time=37.217

0.25 scale
32		78		maxD=5.08268351018479(1,29) time=0.007			maxD=5.08268351018479(1,29) time=0.006
64		342		maxD=8.498731760122254(5,61) time=0.002			maxD=8.498731760122254(5,61) time=0.003
128		1192	maxD=13.051709606925483(2,50) time=0.01			maxD=13.051709606925483(2,50) time=0.009
256		5179	maxD=13.13330646363203(161,212) time=0.077		maxD=13.13330646363203(161,212) time=0.067
512		20777	maxD=16.205552906935324(237,291) time=0.631		maxD=16.205552906935324(237,291) time=0.529
1024	81788	maxD=16.245501344590913(781,917) time=6.164		maxD=16.245501344590913(781,917) time=4.354
2048	327319	maxD=15.73546319215895(1316,1425) time=87.56	maxD=15.73546319215895(1316,1425) time=36.837

0.5 scale
32		254		maxD=5.372451428074707(17,30) time=0.006		maxD=5.372451428074707(17,30) time=0.008
64		1014	maxD=6.264646688650744(6,30) time=0.004			maxD=6.264646688650744(6,30) time=0.003
128		3893	maxD=6.7432971923131895(118,124) time=0.028		maxD=6.7432971923131895(118,124) time=0.009
256		15921	maxD=7.211079493240524(16,27) time=0.235		maxD=7.211079493240524(16,27) time=0.07
512		65978	maxD=8.76610210939775(142,152) time=2.87		maxD=8.76610210939775(142,152) time=0.565
1024	252442	maxD=9.02199640654793(232,245) time=39.223		maxD=9.02199640654793(232,245) time=4.597
2048	1001287	maxD=8.28499057345727(1537,1550) time=395.074	maxD=8.28499057345727(1537,1550) time=35.38

 
 */
