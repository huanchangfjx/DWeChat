package com.brcorner.dwechat.utils;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

/**
 * 外部存储相关的工具类
 * @author dong
 *
 */
public class ExternalStorageUtils
{
	private ExternalStorageUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 判断外部存储是否可用
	 * 
	 * @return
	 */
	public static boolean isExternalStorageEnable()
	{
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);

	}

	/**
	 * 获取外部存储的路径
	 * 例如nexus5：/storage/emulated/0      其内部程序安装路径为data/data
	 * @return
	 */
	public static String getExternalStoragePath()
	{
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
	}

	/**
	 * 获取外部存储的容量单位是byte
	 * 
	 * @return
	 */
	public static long getExternalStorageAllSize()
	{
		if (isExternalStorageEnable())
		{
			StatFs stat = new StatFs(getExternalStoragePath());
			// 获取空闲的数据块的数量
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// 获取单个数据块的大小（byte）
			long freeBlocks = stat.getAvailableBlocks();
			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	/**
	 * 获取指定路径所在空间的剩余可用容量字节数，单位byte
	 * 
	 * @param filePath
	 * @return 容量字节 SDCard可用空间，内部存储可用空间
	 */
	public static long getFreeBytes(String filePath)
	{
		// 如果是sd卡的下的路径，则获取sd卡可用容量
		if (filePath.startsWith(getExternalStoragePath()))
		{
			filePath = getExternalStoragePath();
		} else
		{// 如果是内部存储的路径，则获取内存存储的可用容量
			filePath = Environment.getDataDirectory().getAbsolutePath();
		}
		StatFs stat = new StatFs(filePath);
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		return stat.getBlockSize() * availableBlocks;
	}

	/**
	 * 获取系统存储路径
	 * 
	 * @return
	 */
	public static String getRootDirectoryPath()
	{
		return Environment.getRootDirectory().getAbsolutePath();
	}

	/**
	 * 获取文件夹大小
	 * @param file
	 * @return
	 */
	public static long getFolderSize(File file){
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++)
			{
				if (fileList[i].isDirectory())
				{
					size = size + getFolderSize(fileList[i]);

				}else{
					size = size + fileList[i].length();

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return size/1048576;
		return size;
	}

	/**
	 * 删除文件
	 * @param path
	 */
	public static void clearPath(File path)
	{
		File files[] = path.listFiles(); // 声明目录下所有的文件 files[];
		for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
			files[i].delete(); // 把每个文件 用这个方法进行迭代
		}
	}
}
