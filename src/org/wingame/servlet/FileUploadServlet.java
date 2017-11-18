package org.wingame.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.FileUploadBase.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.wingame.util.FileUtil;

public class FileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8667485372447568757L;

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "fileupload/");
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FileUtil util = new FileUtil((Connection) request.getServletContext().getAttribute("conn"));
		String filename = uploadFile(request, response);
		if (filename != null) {
			if (util.createFileRecord(request, filename)) {
				success(request, response);
			} else {
				showMessage(request, response, "写入文件记录失败。");
			}
		}
	}

	public boolean removeOldFile(HttpServletRequest request) {
		FileUtil util = new FileUtil((Connection) request.getServletContext().getAttribute("conn"));
		String filename = util.getFileName(request);
		if (filename == null)
			return true;
		File file = new File(getServletContext().getRealPath("/upload") + File.separator
				+ filename);
		return util.removeFile(request, file);
	}

	@SuppressWarnings("unchecked")
	private String uploadFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// 检查该请求是否是一个上传表单( 必须是 post请求,和enctype="multipart/form-data")
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// 创建一个临时文件存放要上传的文件，第一个参数为上传文件大小（10MB），第二个参数为存放的临时目录
			DiskFileItemFactory factory = new DiskFileItemFactory(
					1024 * 1024 * 10, new File("C:\\temp"));
			// 创建一个文件上传的句柄
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 设置上传文件的整个大小和上传的单个文件大小（10MB）
			upload.setSizeMax(1024 * 1024 * 11);
			upload.setFileSizeMax(1024 * 1024 * 10);
			try {
				// 把页面表单中的每一个表单元素解析成一个FileItem
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem fileItem : items) {
					// 如果是一个普通的表单元素(type不是file的表单元素)
					if (fileItem.isFormField()) {
					} else {
						String fileName = fileItem.getName();// 得到文件的名字
						if (!fileName.equals("")) {
							if (removeOldFile(request)) {
								String fileExt = fileName.substring(
										fileName.lastIndexOf(".") + 1,
										fileName.length());
								try {
									// 将文件上传到项目的upload目录并命名，getRealPath可以得到该web项目下包含/upload的绝对路径
									fileName = UUID.randomUUID().toString()
											+ "." + fileExt;
									fileItem.write(new File(getServletContext()
											.getRealPath("/upload")
											+ "/"
											+ fileName));
									return fileName;
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else
								showMessage(request, response, "删除旧文件失败。");
						} else
							showMessage(request, response, "没有选择文件。");
					}
				}
			} catch (SizeLimitExceededException e) {
				showMessage(request, response,
						"文件大小超过了最大限制10MB，请压缩后重试或传到网盘后上传下载地址。");
			} catch (FileSizeLimitExceededException e) {
				showMessage(request, response,
						"文件大小超过了最大限制10MB，请压缩后重试或传到网盘后上传下载地址。");
			} catch (FileUploadException e) {
				e.printStackTrace();
				showMessage(request, response, "未知错误");
			}
		}
		return null;
	}

	private void success(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('文件上传成功。')</script>");
		
		out.print("<script>window.location.href='" + request.getContextPath()
				+ "/fileupload/selectmeeting.jsp?m_id="
				+ request.getSession().getAttribute("id").toString() + "'</script>");
	}

	private void showMessage(HttpServletRequest request,
			HttpServletResponse response, String message) throws IOException,
			ServletException {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('" + message + "')</script>");
		
		out.print("<script>window.history.back()</script>");
	}
}
