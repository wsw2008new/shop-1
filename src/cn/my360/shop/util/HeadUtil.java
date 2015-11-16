package cn.my360.shop.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class HeadUtil {
	
	
	// ����ͼƬ
		public static void setViewByRequest(Intent data,Context mContext,ImageView headImage) {
			ContentResolver resolver = mContext.getContentResolver();
			Uri originalUri = data.getData();
			try {
				// ʹ��ContentProviderͨ��URI��ȡԭʼͼƬ
				Bitmap photo = MediaStore.Images.Media.getBitmap(resolver,
						originalUri);
				setImage(headImage, photo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public static  void setImage(ImageView iv_image, Bitmap photo) {
			if (photo != null) {
				// Ϊ��ֹԭʼͼƬ�������ڴ��������������Сԭͼ��ʾ��Ȼ���ͷ�ԭʼBitmapռ�õ��ڴ�
//				Bitmap smallBitmap = zoomBitmap(photo, photo.getWidth() * 4 / 5,
//						photo.getHeight() * 4 / 5);
				photo= FileUtil.rotateBitmapByDegree(photo,FileUtil.readPictureDegree(FileUtil.IMG_PATH+"/head.jpg"));
				Bitmap smallBitmap = zoomBitmap(photo, 200,
						200);
				// �ͷ�ԭʼͼƬռ�õ��ڴ棬��ֹout of memory�쳣����
				photo.recycle();
				iv_image.setImageBitmap(smallBitmap);
				// myBitmap = smallBitmap;
				iv_image.setVisibility(View.VISIBLE);
				savePhotoToSDCard(FileUtil.IMG_PATH+"/", "head.jpg", smallBitmap);
				
				
			}
		}
		/** Save image to the SD card **/

		public static void savePhotoToSDCard(String path, String photoName,
				Bitmap photoBitmap) {
			if (android.os.Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED)) {
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File photoFile = new File(path, photoName); // ��ָ��·���´����ļ�
				FileOutputStream fileOutputStream = null;
				try {
					fileOutputStream = new FileOutputStream(photoFile);
					if (photoBitmap != null) {
						if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
								fileOutputStream)) {
							fileOutputStream.flush();
						}
					}
				} catch (FileNotFoundException e) {
					photoFile.delete();
					e.printStackTrace();
				} catch (IOException e) {
					photoFile.delete();
					e.printStackTrace();
				} finally {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	
		
		public static Bitmap readBitmapAutoSize(String filePath, int outWidth,
				int outHeight) {
			// outWidth��outHeight��Ŀ��ͼƬ������Ⱥ͸߶ȣ���������
			FileInputStream fs = null;
			BufferedInputStream bs = null;
			try {
				fs = new FileInputStream(filePath);
				bs = new BufferedInputStream(fs);
				BitmapFactory.Options options = setBitmapOption(filePath, outWidth,
						outHeight);
				return BitmapFactory.decodeStream(bs, null, options);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					bs.close();
					fs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		

		public static File getAlbumDir() {
			File storageDir = null;
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				storageDir = new File(FileUtil.IMG_PATH+"/");
				;
				if (!storageDir.exists()) {
					storageDir.mkdirs();
				}
			} else {
			}
			return storageDir;
		}
		
		
		
		
		

		private static BitmapFactory.Options setBitmapOption(String file,
				int width, int height) {
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inJustDecodeBounds = true;

			// ����ֻ�ǽ���ͼƬ�ı߾࣬�˲���Ŀ���Ƕ���ͼƬ��ʵ�ʿ�Ⱥ͸߶�
			BitmapFactory.decodeFile(file, opt);

			int outWidth = opt.outWidth; // ���ͼƬ��ʵ�ʸߺͿ�
			int outHeight = opt.outHeight;
			if (outWidth > outHeight) {
				width = 200;
				height = 200;
			}
			opt.inDither = false;
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			// ���ü���ͼƬ����ɫ��Ϊ16bit��Ĭ����RGB_8888����ʾ24bit��ɫ��͸��ͨ������һ���ò���
			opt.inSampleSize = 1;
			// �������ű�,1��ʾԭ������2��ʾԭ�����ķ�֮һ....
			// �������ű�
			if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
				int sampleSize = (outWidth / width + outHeight / height) / 2;
				opt.inSampleSize = sampleSize;
			}
			System.out.println(opt.inSampleSize);
			opt.inJustDecodeBounds = false;// ���ѱ�־��ԭ
			return opt;
		}

		public static void setImage(String path, Bitmap photo) {
			if (photo != null) {

				photo = FileUtil.rotateBitmapByDegree(photo,
						FileUtil.readPictureDegree(path));
				// Ϊ��ֹԭʼͼƬ�������ڴ��������������Сԭͼ��ʾ��Ȼ���ͷ�ԭʼBitmapռ�õ��ڴ�
				Bitmap smallBitmap;
				if (photo.getWidth() > photo.getHeight()) {
					smallBitmap = zoomBitmap(photo, 200, 200);
				} else
					smallBitmap = zoomBitmap(photo, 200, 200);
				// �ͷ�ԭʼͼƬռ�õ��ڴ棬��ֹout of memory�쳣����
				photo.recycle();

				savePhotoToSDCard(path, smallBitmap);

			}
		}

		/** ����BitmapͼƬ **/

		private static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			Matrix matrix = new Matrix();
			float scaleWidth = ((float) width / w);
			float scaleHeight = ((float) height / h);
			matrix.postScale(scaleWidth, scaleHeight);// ���þ���������Ų�������ڴ����
			Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
			return newbmp;

		}

		/** Save image to the SD card **/

		public static void savePhotoToSDCard(String path, Bitmap photoBitmap) {
			if (android.os.Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED)) {
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File photoFile = new File(path); // ��ָ��·���´����ļ�
				FileOutputStream fileOutputStream = null;
				try {
					fileOutputStream = new FileOutputStream(photoFile);
					if (photoBitmap != null) {
						if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 90,
								fileOutputStream)) {
							fileOutputStream.flush();
						}
					}
				} catch (FileNotFoundException e) {
					photoFile.delete();
					e.printStackTrace();
				} catch (IOException e) {
					photoFile.delete();
					e.printStackTrace();
				} finally {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
}
