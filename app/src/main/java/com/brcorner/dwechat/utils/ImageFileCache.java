package com.brcorner.dwechat.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;


import com.brcorner.dwechat.constant.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 图片文件的读取和保存
 */
public class ImageFileCache {


	/** 过期时间3天 **/
	private static final long mTimeDiff = 3 * 24 * 60 * 60 * 1000;

	/*** 缓存空间最小值 ****/
	private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 4;

	private String path;

	public ImageFileCache() {
		// 清理文件缓存
		removeCache(getDirectory());
	}

	public ImageFileCache(String path) {
		this.path = path;
		// 清理文件缓存
		removeCache(getDirectory());
	}

	/**
	 * 获取图片
	 * @param fileName
	 * @return
	 */
	public Bitmap getImage(String fileName) {
		final String path = getDirectory() + "/" + fileName;
		File file = new File(path);
		Bitmap bitmap = null;
		if (file.exists()) {
			BitmapFactory.Options bfOptions = new BitmapFactory.Options();
			bfOptions.inDither = false;
			bfOptions.inPurgeable = true;
			bfOptions.inInputShareable = true;
			bfOptions.inTempStorage = new byte[32 * 1024];
			FileInputStream fs = null;
			try {
				fs = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				if (fs != null) {
					try {
						bitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(),
								null, bfOptions);
					} catch (OutOfMemoryError e) {
						e.printStackTrace();
						System.gc();
					}
					fs.close();
					if (bitmap == null) {
						file.delete();
					} else {
						updateFileTime(path);
						return bitmap;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}


	/**
	 * 保存到sd卡
	 * @param bm
	 * @return
	 */
	public String saveBmpToSd(Bitmap bm) {
		String bmpName = "";
		if (bm == null) {
			// 需要保存的是一个空值
			return bmpName;
		}
		// 判断sdcard上的空间
		if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			// SD空间不足
			return bmpName;
		}
		bmpName = CommonUtils.generateNum();
		String dir = getDirectory();
		File directory = new File(dir);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		File file = new File(dir + "/" + bmpName);
		try {
			file.createNewFile(); 
			OutputStream outStream = new FileOutputStream(file);
		    bm.compress(Bitmap.CompressFormat.JPEG,80, outStream);
			outStream.flush();
			outStream.close();
		} catch (FileNotFoundException e) {
			Log.w("ImageFileCache", "FileNotFoundException");
		} catch (IOException e) {
			Log.w("ImageFileCache", "IOException");
		}
		return bmpName;
	}

	/** 获得缓存目录 **/
	public String getDirectory() {
		String dir = getSDPath() + "/" + Constant.CACH_DIR + path;
		String substr = dir.substring(0, 4);
		if (substr.equals("/mnt")) {
			dir = dir.replace("/mnt", "");
		}
		return dir;
	}

	private static final int CACHE_SIZE = 50;

	/**
	 * * 计算存储目录下的文件大小， *
	 * 当文件总大小大于规定的CACHE_SIZE或者sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定 *
	 * 那么删除40%最近没有被使用的文件 * * @param dirPath * @param filename
	 * */
	private boolean removeCache(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return true;
		}
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return false;
		}
		int dirSize = 0;
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().contains(Constant.WHOLESALE_CONV)) {
				dirSize += files[i].length();
			}
		}
		if (dirSize > CACHE_SIZE * MB
				|| FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			int removeFactor = (int) ((0.4 * files.length) + 1);
			Log.i("ImageFileCache", "清理缓存文件");
			try {
				System.setProperty("java.util.Arrays.useLegacyMergeSort",
						"true");
				Arrays.sort(files, new FileLastModifSort());
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < removeFactor; i++) {
				if (files[i].getName().contains(Constant.WHOLESALE_CONV)) {
					files[i].delete();
				}
			}
		}
		if (freeSpaceOnSd() <= CACHE_SIZE) {
			return false;
		}
		return true;
	}

	/**
	 * * TODO 根据文件的最后修改时间进行排序 *
	 * */
	private class FileLastModifSort implements Comparator<File> {
		public int compare(File arg0, File arg1) {
			if (arg0.lastModified() > arg1.lastModified()) {
				return 1;
			} else if (arg0.lastModified() < arg1.lastModified()) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	/**
	 * 删除过期文件
	 * @param dirPath
	 * @param filename
	 */
	public void removeExpiredCache(String dirPath, String filename) {
		File file = new File(dirPath, filename);
		if (System.currentTimeMillis() - file.lastModified() > mTimeDiff) {
			Log.i("ImageFileCache", "Clear some expiredcache files ");
			file.delete();
		}
	}

	/**
	 *  修改文件的最后修改时间
	 *  这里需要考虑,是否将使用的图片日期改为当前日期
	 *  @param path
	 */
	public void updateFileTime(String path) {
		File file = new File(path);
		long newModifiedTime = System.currentTimeMillis();
		file.setLastModified(newModifiedTime);
	}

	/**
	 * * 计算sdcard上的剩余空间 * @return
	 * */
	private int MB = 1024 * 1024;

	private int freeSpaceOnSd() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;
		return (int) sdFreeMB;
	}

	/**
	 * 获取SD卡路径不带 /
	 * @return
	 */
	private String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		// 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
			// 获取跟目录 }
			if (sdDir != null) {
				return sdDir.toString();
			} else {
				return "";
			}
		}
		return "";
	}
}
