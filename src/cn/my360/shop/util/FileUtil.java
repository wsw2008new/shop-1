package cn.my360.shop.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

public class FileUtil {
	public static String IMG_PATH=Environment.getExternalStorageDirectory()
			.toString() + "/DCIM/Camera/szjy";
	/**
	 * 删除文件
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名，要在系统内保持唯一
	 * @return boolean 存储成功的标志
	 */
	public static boolean deleteFile(Context context, String fileName) {
		return context.deleteFile(fileName);
	}

	/**
	 * 文件是否存在
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static boolean exists(Context context, String fileName) {
		return new File(context.getFilesDir(), fileName).exists();
	}

	/**
	 * 存储文本数据
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名，要在系统内保持唯一
	 * @param content
	 *            文本内容
	 * @return boolean 存储成功的标志
	 */
	public static boolean writeFile(Context context, String fileName, String content) {
		boolean success = false;
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			byte[] byteContent = content.getBytes();
			fos.write(byteContent);

			success = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return success;
	}

	/**
	 * 存储文本数据
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名，要在系统内保持唯一
	 * @param content
	 *            文本内容
	 * @return boolean 存储成功的标志
	 */
	public static boolean writeFile(String filePath, String content) {
		boolean success = false;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			byte[] byteContent = content.getBytes();
			fos.write(byteContent);

			success = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return success;
	}

	/**
	 * 读取文本数据
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名
	 * @return String, 读取到的文本内容，失败返回null
	 */
	public static String readFile(Context context, String fileName) {
		if (!exists(context, fileName)) {
			return null;
		}
		FileInputStream fis = null;
		String content = null;
		try {
			fis = context.openFileInput(fileName);
			if (fis != null) {

				byte[] buffer = new byte[1024];
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				while (true) {
					int readLength = fis.read(buffer);
					if (readLength == -1)
						break;
					arrayOutputStream.write(buffer, 0, readLength);
				}
				fis.close();
				arrayOutputStream.close();
				content = new String(arrayOutputStream.toByteArray());

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			content = null;
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * 读取文本数据
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名
	 * @return String, 读取到的文本内容，失败返回null
	 */
	public static String readFile(String filePath) {
		if (filePath == null || !new File(filePath).exists()) {
			return null;
		}
		FileInputStream fis = null;
		String content = null;
		try {
			fis = new FileInputStream(filePath);
			if (fis != null) {

				byte[] buffer = new byte[1024];
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				while (true) {
					int readLength = fis.read(buffer);
					if (readLength == -1)
						break;
					arrayOutputStream.write(buffer, 0, readLength);
				}
				fis.close();
				arrayOutputStream.close();
				content = new String(arrayOutputStream.toByteArray());

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			content = null;
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * 读取文本数据
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名
	 * @return String, 读取到的文本内容，失败返回null
	 */
	public static String readAssets(Context context, String fileName) {
		InputStream is = null;
		String content = null;
		try {
			is = context.getAssets().open(fileName);
			if (is != null) {

				byte[] buffer = new byte[1024];
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				while (true) {
					int readLength = is.read(buffer);
					if (readLength == -1)
						break;
					arrayOutputStream.write(buffer, 0, readLength);
				}
				is.close();
				arrayOutputStream.close();
				content = new String(arrayOutputStream.toByteArray());

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			content = null;
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * 存储单个Parcelable对象
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名，要在系统内保持唯一
	 * @param parcelObject
	 *            对象必须实现Parcelable
	 * @return boolean 存储成功的标志
	 */
	public static boolean writeParcelable(Context context, String fileName, Parcelable parcelObject) {
		boolean success = false;
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			Parcel parcel = Parcel.obtain();
			parcel.writeParcelable(parcelObject, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
			byte[] data = parcel.marshall();
			fos.write(data);

			success = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}

		return success;
	}

	/**
	 * 存储List对象
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名，要在系统内保持唯一
	 * @param list
	 *            对象数组集合，对象必须实现Parcelable
	 * @return boolean 存储成功的标志
	 */
	public static boolean writeParcelableList(Context context, String fileName, List<Parcelable> list) {
		boolean success = false;
		FileOutputStream fos = null;
		try {
			if (list instanceof List) {
				fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
				Parcel parcel = Parcel.obtain();
				parcel.writeList(list);
				byte[] data = parcel.marshall();
				fos.write(data);

				success = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}

		return success;
	}

	/**
	 * 读取单个数据对象
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名
	 * @return Parcelable, 读取到的Parcelable对象，失败返回null
	 */
	@SuppressWarnings("unchecked")
	public static Parcelable readParcelable(Context context, String fileName, ClassLoader classLoader) {
		Parcelable parcelable = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = context.openFileInput(fileName);
			if (fis != null) {
				bos = new ByteArrayOutputStream();
				byte[] b = new byte[4096];
				int bytesRead;
				while ((bytesRead = fis.read(b)) != -1) {
					bos.write(b, 0, bytesRead);
				}

				byte[] data = bos.toByteArray();

				Parcel parcel = Parcel.obtain();
				parcel.unmarshall(data, 0, data.length);
				parcel.setDataPosition(0);
				parcelable = parcel.readParcelable(classLoader);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			parcelable = null;
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return parcelable;
	}

	/**
	 * 读取数据对象列表
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名
	 * @return List, 读取到的对象数组，失败返回null
	 */
	@SuppressWarnings("unchecked")
	public static List<Parcelable> readParcelableList(Context context, String fileName, ClassLoader classLoader) {
		List<Parcelable> results = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = context.openFileInput(fileName);
			if (fis != null) {
				bos = new ByteArrayOutputStream();
				byte[] b = new byte[4096];
				int bytesRead;
				while ((bytesRead = fis.read(b)) != -1) {
					bos.write(b, 0, bytesRead);
				}

				byte[] data = bos.toByteArray();

				Parcel parcel = Parcel.obtain();
				parcel.unmarshall(data, 0, data.length);
				parcel.setDataPosition(0);
				results = parcel.readArrayList(classLoader);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			results = null;
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return results;
	}

	public static boolean saveSerializable(Context context, String fileName, Serializable data) {
		boolean success = false;
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
			oos.writeObject(data);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	public static Serializable readSerialLizable(Context context, String fileName) {
		Serializable data = null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(context.openFileInput(fileName));
			data = (Serializable) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return data;
	}

	/**
	 * 从assets里边读取字符串
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String getFromAssets(Context context, String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 复制文件
	 * 
	 * @param srcFile
	 * @param dstFile
	 * @return
	 */
	public static boolean copy(String srcFile, String dstFile) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {

			File dst = new File(dstFile);
			if (!dst.getParentFile().exists()) {
				dst.getParentFile().mkdirs();
			}

			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(dstFile);

			byte[] buffer = new byte[1024];
			int len = 0;

			while ((len = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return true;
	}

	

	

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
	
	
	// 从assets文件夹 移动程序安装包到SD卡
	public static File getAssetFileToSD(Context context, String fileName,String tagetFileDir) {
		try {
			final String tagetPath = tagetFileDir + File.separator + fileName;
			InputStream is = context.getAssets().open(fileName);
			File file = new File(tagetPath);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];

			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	// 从assets文件夹 移动程序安装包到SD卡
//	public static File getAssetFileToCacheDir(Context context, String fileName) {
//		return AssetToSD(context, fileName, App.appContext.getExternalCacheDir().getAbsolutePath());
//	}
//	public static File getAssetFileToFileDir(Context context,String dir ,String fileName) {
//		return AssetToSD(context, fileName, App.appContext.getExternalFilesDir(dir).getAbsolutePath());
//	}
	
	
	// 从assets文件夹 移动程序安装包到SD卡
	public static File AssetToSD(Context context,String tagetPath,String assetFileName){
		try {
			InputStream is = context.getAssets().open(assetFileName);
			File file = new File(tagetPath);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];

			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String createImageFileNameByHead()
	  {
	    StringBuilder localStringBuilder = new StringBuilder();
	    localStringBuilder.append("head");
//	    localStringBuilder.append(DateFormat.format("yyyyMMdd_hhmmss", new Date()).toString());
	    localStringBuilder.append(".jpg");
	    return localStringBuilder.toString();
	  }
	
	public static String createImageFileNameByTimeStamp()
	  {
	    StringBuilder localStringBuilder = new StringBuilder();
	    localStringBuilder.append("UHD_IMG_");
	    localStringBuilder.append(DateFormat.format("yyyyMMdd_hhmmss", new Date()).toString());
	    localStringBuilder.append(".jpg");
	    return localStringBuilder.toString();
	  }
	public static String createVideoFileNameByTimeStamp()
	  {
	    StringBuilder localStringBuilder = new StringBuilder();
	    localStringBuilder.append("VHD_");
	    localStringBuilder.append(DateFormat.format("yyyyMMdd_hhmmss", new Date()).toString());
	    localStringBuilder.append(".mp4");
	    return localStringBuilder.toString();
	  }
	public static String createImageFileNameByTimeStampThum()
	  {
	    StringBuilder localStringBuilder = new StringBuilder();
	    localStringBuilder.append("UHD_IMG_THUM");
	    localStringBuilder.append(DateFormat.format("yyyyMMdd_hhmmss", new Date()).toString());
	    localStringBuilder.append(".jpg");
	    return localStringBuilder.toString();
	  }
	
	
	
	 public static int readPictureDegree(String path) {
	        int degree  = 0;
	        try {
	                ExifInterface exifInterface = new ExifInterface(path);
	                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
	                switch (orientation) {
	                case ExifInterface.ORIENTATION_ROTATE_90:
	                        degree = 90;
	                        break;
	                case ExifInterface.ORIENTATION_ROTATE_180:
	                        degree = 180;
	                        break;
	                case ExifInterface.ORIENTATION_ROTATE_270:
	                        degree = 270;
	                        break;
	                }
	        } catch (IOException e) {
	                e.printStackTrace();
	        }
	        return degree;
	    } 
	 
	 public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
		    Bitmap returnBm = null;
		  
		    // 根据旋转角度，生成旋转矩阵
		    Matrix matrix = new Matrix();
		    matrix.postRotate(degree);
		    try {
		        // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
		        returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
		    } catch (OutOfMemoryError e) {
		    }
		    if (returnBm == null) {
		        returnBm = bm;
		    }
		    if (bm != returnBm) {
		        bm.recycle();
		    }
		    return returnBm;
		}
	 
	 
	 
	/*****************************************************************************/
	 
	 
	 public static String SDPATH = Environment.getExternalStorageDirectory()
				+ "/Photo_LJ/";

		public static void saveBitmap(Bitmap bm, String picName) {
			try {
				if (!isFileExist("")) {
					File tempf = createSDDir("");
				}
				File f = new File(SDPATH, picName + ".JPEG"); 
				if (f.exists()) {
					f.delete();
				}
				FileOutputStream out = new FileOutputStream(f);
				bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static File createSDDir(String dirName) throws IOException {
			File dir = new File(SDPATH + dirName);
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {

				System.out.println("createSDDir:" + dir.getAbsolutePath());
				System.out.println("createSDDir:" + dir.mkdir());
			}
			return dir;
		}

		public static boolean isFileExist(String fileName) {
			File file = new File(SDPATH + fileName);
			file.isFile();
			return file.exists();
		}
		
		public static void delFile(String fileName){
			File file = new File(SDPATH + fileName);
			if(file.isFile()){
				file.delete();
	        }
			file.exists();
		}

		public static void deleteDir() {
			File dir = new File(SDPATH);
			if (dir == null || !dir.exists() || !dir.isDirectory())
				return;
			
			for (File file : dir.listFiles()) {
				if (file.isFile())
					file.delete(); 
				else if (file.isDirectory())
					deleteDir(); 
			}
			dir.delete();
		}

		public static boolean fileIsExists(String path) {
			try {
				File f = new File(path);
				if (!f.exists()) {
					return false;
				}
			} catch (Exception e) {

				return false;
			}
			return true;
		}
	 
	 
		public static void DeleteFile(File file) {
			if (file.exists() == false) {
				return;
			} else {
				if (file.isFile()) {
					file.delete();
					return;
				}
				if (file.isDirectory()) {
					File[] childFile = file.listFiles();
					if (childFile == null || childFile.length == 0) {
						file.delete();
						return;
					}
					for (File f : childFile) {
						DeleteFile(f);
					}
					file.delete();
				}
			}
		}
	 
}
