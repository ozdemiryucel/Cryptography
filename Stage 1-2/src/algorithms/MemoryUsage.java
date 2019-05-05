package algorithms;

public class MemoryUsage {
	
	public static long getMemoryUsageKB() {
		long totalMemory;
		long freeMemory;
		Runtime rt = Runtime.getRuntime();
		totalMemory = rt.totalMemory();
		freeMemory = rt.freeMemory();
		return (totalMemory-freeMemory)/(1024) ;
	}

}
