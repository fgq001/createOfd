package com.createofd.utils;


import com.createofd.entity.FileModel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {
	// 日志
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

	public static List<FileModel> unzipByte(InputStream inputStream) {
		List<FileModel> fileModelList = new ArrayList<FileModel>();
		ZipInputStream zipStream = new ZipInputStream(inputStream);
		BufferedInputStream bs = new BufferedInputStream(zipStream);
		ZipEntry zipEntry = null;
		String zipFileName = null;
		byte[] bytes = new byte[2048];
		try {
			while ((zipEntry = zipStream.getNextEntry()) != null) {
				zipFileName = zipEntry.getName();
				Assert.notNull(zipFileName, "压缩文件中子文件的名字格式不正确");
				FileModel fileModel = new FileModel();
				fileModel.setFileName(zipFileName);
				bytes = new byte[(int) zipEntry.getSize()];
				bs.read(bytes, 0, (int) zipEntry.getSize());
				InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
				fileModel.setFileInputstream(byteArrayInputStream);
				fileModelList.add(fileModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileModelList;
	}

	/**
	 * 保存文件
	 */
	public static String saveFile(InputStream inputStream) {
		try {
			ZipInputStream zipStream = new ZipInputStream(inputStream);
			ZipEntry entry = null;
			while ((entry = zipStream.getNextEntry()) != null) {
				String entryName = entry.getName();// ==>文件名
				FileOutputStream out = new FileOutputStream("D:\\PDFFile\\caInfo\\" + entryName);
				byte[] byteBuff = new byte[4096];
				int bytesRead = 0;
				while ((bytesRead = zipStream.read(byteBuff)) != -1) {
					out.write(byteBuff, 0, bytesRead);
				}
				out.close();
				zipStream.closeEntry();
			}
			zipStream.close();
			return "0000";
		} catch (Exception e) {
			e.printStackTrace();
			return "9999";
		}
	}

	/**
	 * 对zip类型的文件进行解压
	 *
	 * @author mmy
	 * @date 2018年1月8日
	 */
	public static List<FileModel> unzip(MultipartFile file) {
		// 判断文件是否为zip文件
		String filename = file.getOriginalFilename();
		if (!filename.endsWith("zip")) {
			LOGGER.info("传入文件格式不是zip文件" + filename);
			// new BusinessException("传入文件格式错误" + filename);
			System.out.println("传入文件格式错误");
		}
		List<FileModel> fileModelList = new ArrayList<FileModel>();
		String zipFileName = null;
		// 对文件进行解析
		try {
			Charset gbk = Charset.forName("gbk");
			ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream(), gbk);
			BufferedInputStream bs = new BufferedInputStream(zipInputStream);
			ZipEntry zipEntry;
			byte[] bytes = new byte[2048];
			while ((zipEntry = zipInputStream.getNextEntry()) != null) { // 获取zip包中的每一个zip
																			// file
																			// entry
				// if(zipEntry.getName().endsWith(".txt")){
				zipFileName = zipEntry.getName();
				Assert.notNull(zipFileName, "压缩文件中子文件的名字格式不正确");
				FileModel fileModel = new FileModel();
				fileModel.setFileName(zipFileName);
				bytes = new byte[(int) zipEntry.getSize()];
				bs.read(bytes, 0, (int) zipEntry.getSize());
				InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
				fileModel.setFileInputstream(byteArrayInputStream);
				fileModelList.add(fileModel);
				// }
			}
		} catch (Exception e) {
			LOGGER.error("读取部署包文件内容失败,请确认部署包格式正确:" + zipFileName, e);
			System.out.println(e);
			// new BusinessException("读取部署包文件内容失败,请确认部署包格式正确:" + zipFileName);
		}
		return fileModelList;
	}

	// get请求返回流
	public static InputStream getFileStream(String uri) {
		BufferedInputStream bin = null;
		ByteArrayOutputStream out = null;
		ByteArrayInputStream inputStream = null;
		try {
			out = new ByteArrayOutputStream();
			URL url = new URL(uri);
			HttpURLConnection c = (HttpURLConnection) url.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Charset", "UTF-8");
			bin = new BufferedInputStream(c.getInputStream());

			int size = 0;
			int len = 0;
			byte[] buf = new byte[1024];
			while ((size = bin.read(buf)) != -1) {
				len += size;
				out.write(buf, 0, size);
			}
			inputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			// throw new Exception("[Error] get file failed!");
		} finally {
			if (bin != null) {
				try {
					bin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					bin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					bin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return inputStream;
	}

	/**
	 * 
	 * @Title: encodeBase64File @Description: 根据文件路径进行base64加密 @param @param
	 *         path @param @return @param @throws Exception 设定文件 @return String
	 *         返回类型 @throws
	 */
	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return new BASE64Encoder().encode(buffer).replaceAll("[\\s*\t\n\r]", "");

	}

	// 参数string为你的文件名
	public static String readFileContent(String fileName) throws IOException {
		File file = new File(fileName);
		BufferedReader bf = new BufferedReader(new FileReader(file));
		String content = "";
		StringBuilder sb = new StringBuilder();
		while (content != null) {
			content = bf.readLine();
			if (content == null) {
				break;
			}
			sb.append(content.trim());
		}
		bf.close();
		return sb.toString();
	}

	/**
	 * 文件转为二进制字符串
	 * 
	 * @param file
	 * @return
	 */
	public static String fileToBinStr(File file) {
		try {
			InputStream fis = new FileInputStream(file);
			byte[] bytes = FileCopyUtils.copyToByteArray(fis);
			// return new String(bytes,"GB2312");
			return new String(bytes, "ISO-8859-1");
		} catch (Exception ex) {
			throw new RuntimeException("transform file into bin String 出错", ex);
		}
	}

	/**
	 * 
	 * @Title: printLog @Description: 输出日志文件 @param @param
	 * logContent @param @return 设定文件 @return boolean 返回类型 @throws
	 */
	public static boolean printLog(String logContent, String logPath) {

		// 向文件写入内容
		String payResult = logContent;
		try {
			FileWriter fw = new FileWriter(logPath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("\r\n" + payResult);
			bw.close();
			fw.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @Title: getTrace @Description: 捕获异常内容 @param @param t @param @return
	 * 设定文件 @return String 返回类型 @throws
	 */
	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}

	/**
	 * 
	 * @Title: ftpDownload
	 * @Description: ftp跨服务器文件获取
	 * @author zht
	 * @date 2018年12月29日 下午7:05:13
	 * @return boolean
	 *
	 * @param ftpUrl
	 *            ftp服务地址
	 * @param userName
	 *            //登录名
	 * @param pass
	 *            //密码
	 * @param port
	 *            //端口号，默认21
	 * @param directory
	 *            //FTP服务器上的相对路径 ,\\代表根目录
	 * @param fileName
	 *            //要获取的文件名
	 * @param localPath
	 *            //获取后保存到本地的路径
	 * @return
	 * @throws IOException
	 */
	public static boolean ftpDownload(String ftpUrl, String userName, String pass, int port, String directory,
			String fileName, String localPath) throws IOException {
		FTPClient ftpClient = new FTPClient();
		int reply;
		try {
			ftpClient.connect(ftpUrl, port);
			ftpClient.login(userName, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setBufferSize(1024);
			// 设置文件类型（二进制）
			ftpClient.changeWorkingDirectory(directory);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 设置连接超时和数据传输超时，对于性能有要求的项目，设置这两个属性很重要。例如,设置为60秒:
			ftpClient.setDataTimeout(60000); // 设置传输超时时间为60秒
			ftpClient.setConnectTimeout(60000); // 连接超时为60秒
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) { // reply状态标识码，200<=reply<300登录成功
				ftpClient.disconnect();
				System.err.println("FTP服务连接失败,请检查相关参数是否正确!");
				System.exit(1);
				return false;
			}
			// 获取FTP对应路径下的文件集合
			FTPFile[] fs = ftpClient.listFiles();

			for (FTPFile ff : fs) { // 下载当前目录下所有文件
				if (ff.getName().equals(fileName)) {
					String path = localPath + "\\" + ff.getName();
					File localFile = new File(path);

					OutputStream is = new FileOutputStream(localFile);
					ftpClient.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			System.out.println("FTP文件下载成功！");
			return true;
		} catch (NumberFormatException e) {
			throw e;
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					throw new RuntimeException("关闭FTP连接发生异常！", e);
				}
			}

		}
	}

	public static void main(String[] args) {
		try {
			/*
			 * String readFileContent =
			 * readFileContent("E:\\PDFFile\\Jiangsu.pdf");
			 * System.out.println(readFileContent);
			 */

			// 对文件进行base64加密
			File f = new File("E:\\PDFFile\\Jiangsu.pdf");
			FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(f.toPath()), false, f.getName(),
					(int) f.length(), f.getParentFile());
			try (InputStream input = new FileInputStream(f); OutputStream os = fileItem.getOutputStream();) {
				IOUtils.copy(input, os);
				MultipartFile mulFile = new CommonsMultipartFile(fileItem);
				String pdfstream = EWMbase64util.getPdfstream(mulFile);
				System.out.println(pdfstream.trim());

				// 对文件流进行解密
				boolean generateImage = EWMbase64util.generateImage(pdfstream, "E:\\PDFFile\\after\\a.pdf");

				System.out.println(generateImage);
			} catch (Exception e) {
				e.printStackTrace();
			}

			/*
			 * String fileToBinStr = fileToBinStr(new
			 * File("E:\\PDFFile\\Jiangsu.pdf"));
			 * System.out.println(fileToBinStr);
			 */
			/*
			 * String encodeBase64File =
			 * encodeBase64File("D:\\PDFFile\\qmpdfFile\\9132090076588352XR.pdf"
			 * ); System.out.println(encodeBase64File);
			 */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
