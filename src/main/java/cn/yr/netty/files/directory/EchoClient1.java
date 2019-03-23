package cn.yr.netty.files.directory;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author
 * @since
 */
public final class EchoClient1 {

	public static void main(String[] args) throws Exception {
		File file = new File("F:\\git");
		File files [] = file.listFiles();
		for (File file2 : files) {
			if(file2.isFile()){
				System.out.println("  文件路径：    "+file2.getPath());
				new Test(file2.getName(),file2.getPath()).start();
			}
			if (file2.isDirectory())
			{
				File[] files1 = file2.listFiles();
				for (File file1 : files1) {
					if (file1.isFile())
					{
						System.out.println("  文件路径：    "+file2.getPath());
						new Test(file1.getName(),file1.getPath()).start();
					}
				}
			}
		}
		
	}
}
