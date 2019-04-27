package algorithms;

public class MemoryUsage {
	
	public static String getMemoryUsage() {
		long totalMemory;
		long freeMemory;
		Runtime rt = Runtime.getRuntime();
		totalMemory = rt.totalMemory();
		freeMemory = rt.freeMemory();
		return "Memory usage: " + (totalMemory-freeMemory)/(1024*1024) + " MB";
	}

}
