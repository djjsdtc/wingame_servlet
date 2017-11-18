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
				showMessage(request, response, "д���ļ���¼ʧ�ܡ�");
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
		// ���������Ƿ���һ���ϴ���( ������ post����,��enctype="multipart/form-data")
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// ����һ����ʱ�ļ����Ҫ�ϴ����ļ�����һ������Ϊ�ϴ��ļ���С��10MB�����ڶ�������Ϊ��ŵ���ʱĿ¼
			DiskFileItemFactory factory = new DiskFileItemFactory(
					1024 * 1024 * 10, new File("C:\\temp"));
			// ����һ���ļ��ϴ��ľ��
			ServletFileUpload upload = new ServletFileUpload(factory);
			// �����ϴ��ļ���������С���ϴ��ĵ����ļ���С��10MB��
			upload.setSizeMax(1024 * 1024 * 11);
			upload.setFileSizeMax(1024 * 1024 * 10);
			try {
				// ��ҳ����е�ÿһ����Ԫ�ؽ�����һ��FileItem
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem fileItem : items) {
					// �����һ����ͨ�ı�Ԫ��(type����file�ı�Ԫ��)
					if (fileItem.isFormField()) {
					} else {
						String fileName = fileItem.getName();// �õ��ļ�������
						if (!fileName.equals("")) {
							if (removeOldFile(request)) {
								String fileExt = fileName.substring(
										fileName.lastIndexOf(".") + 1,
										fileName.length());
								try {
									// ���ļ��ϴ�����Ŀ��uploadĿ¼��������getRealPath���Եõ���web��Ŀ�°���/upload�ľ���·��
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
								showMessage(request, response, "ɾ�����ļ�ʧ�ܡ�");
						} else
							showMessage(request, response, "û��ѡ���ļ���");
					}
				}
			} catch (SizeLimitExceededException e) {
				showMessage(request, response,
						"�ļ���С�������������10MB����ѹ�������Ի򴫵����̺��ϴ����ص�ַ��");
			} catch (FileSizeLimitExceededException e) {
				showMessage(request, response,
						"�ļ���С�������������10MB����ѹ�������Ի򴫵����̺��ϴ����ص�ַ��");
			} catch (FileUploadException e) {
				e.printStackTrace();
				showMessage(request, response, "δ֪����");
			}
		}
		return null;
	}

	private void success(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('�ļ��ϴ��ɹ���')</script>");
		
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
