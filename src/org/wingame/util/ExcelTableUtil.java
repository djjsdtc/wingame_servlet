package org.wingame.util;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jxl.JXLException;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelTableUtil extends BaseUtil{
	private static class Users{
		public String username;
		public String realname;
		public String company;
	}
	
	private List<Users> userlist = new ArrayList<Users>();
	private String meetingName = "";
	
	//构造函数中把userlist和meetingName的内容填写好
	public ExcelTableUtil(Connection conn,int meetingId) throws SQLException {
		super(conn);
		PreparedStatement stmt = conn.prepareStatement("select m_title from t_meeting where m_id=?;");
		stmt.setInt(1, meetingId);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) meetingName = rs.getString("m_title");
		rs.close();
		stmt.close();
		stmt = conn.prepareStatement("select t_user.username,realname,company " +
				"from t_user,t_meeting_status " +
				"where t_user.username=t_meeting_status.username and t_meeting_status.m_id=?");
		stmt.setInt(1, meetingId);
		rs = stmt.executeQuery();
		while(rs.next()){
			Users user = new Users();
			user.username = rs.getString("username");
			user.realname = rs.getString("realname");
			user.company = rs.getString("company");
			userlist.add(user);
		}
	}
	
	public void createTable(OutputStream os) throws IOException, JXLException{
		//创建工作簿和工作表
		WritableWorkbook book = Workbook.createWorkbook(os);
		WritableSheet sheet = book.createSheet("参会人员名单", 0);
		//设置列宽
		sheet.setColumnView(0, 16);
		sheet.setColumnView(1, 16);
		sheet.setColumnView(2, 40);
		sheet.setColumnView(3, 10);
		//表标题合并单元格
		sheet.mergeCells(0, 0, 3, 0);
		sheet.mergeCells(0, 1, 3, 1);
		//填写表标题内容
		WritableCellFormat titleformat = new WritableCellFormat();
		titleformat.setAlignment(Alignment.CENTRE);
		Label l00 = new Label(0,0,meetingName,titleformat);
		Label l01 = new Label(0,1,"参会人员名单",titleformat);
		sheet.addCell(l00);
		sheet.addCell(l01);
		//填写表头
		WritableCellFormat format = new WritableCellFormat();
		format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		Label l03 = new Label(0,3,"用户名",format);
		Label l13 = new Label(1,3,"真实姓名",format);
		Label l23 = new Label(2,3,"单位",format);
		Label l33 = new Label(3,3,"缴费",format);
		sheet.addCell(l03);
		sheet.addCell(l13);
		sheet.addCell(l23);
		sheet.addCell(l33);
		//填写内容
		int row = 4;
		for(Users u : userlist){
			Label l0x = new Label(0,row,u.username,format);
			Label l1x = new Label(1,row,u.realname,format);
			Label l2x = new Label(2,row,u.company,format);
			Label l3x = new Label(3,row,"是",format);
			sheet.addCell(l0x);
			sheet.addCell(l1x);
			sheet.addCell(l2x);
			sheet.addCell(l3x);
			row ++;
		}
		book.write();
		book.close();
	}
}
