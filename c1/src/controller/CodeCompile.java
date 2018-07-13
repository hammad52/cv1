package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CodeCompile
 */
@WebServlet(description = "servlet for compilation of code", urlPatterns = { "/CodeCompile" })
public class CodeCompile extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CodeCompile() {
        // TODO Auto-generated constructor stub
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try
		{
			Date d=new Date();
			String n=d.getTime()+"";
			String fname=n+".c";
			String code=request.getParameter("code");
			File f=new File("F:\\Programs\\compile\\files\\"+fname);
			f.createNewFile();
			PrintWriter filewrite=new PrintWriter(new FileOutputStream(f));
			filewrite.write(code);
			filewrite.close();
			Process p=Runtime.getRuntime().exec("C:\\Program Files (x86)\\Dev-Cpp\\MinGW64\\bin\\gcc "+f+" -o "+fname);
			BufferedReader br=new BufferedReader(new InputStreamReader(p.getErrorStream()));
			int x=0;
			while(true)
			{
				String str=br.readLine();
				if(str==null && x==0)
				{
					out.println("Program compiled Sucessfully");
					/*out.println("<form>");
					out.println("<br>Enter the Main Class Name    : <input type='text' name='main'>");
					out.println("<br>Enter the Arguments with gap : <input type='text' name='args'>");
					out.println("<br><input type='submit' name='submit' value='run'>");
					out.println("</form>");
					*/
				}
				out.println("<br>");
				if(str==null)break;
				out.println(str);
				x++;
			}
			File fn=new File(n);
			if(fn.exists())
			{
				Process prun=Runtime.getRuntime().exec("./"+n);
				br=new BufferedReader(new InputStreamReader(prun.getInputStream()));
				while(true)
				{
					String str=br.readLine();
					if(str==null)
						break;
					out.println(str+"<br>");
				}
			}
			//out.println(code);
			out.close();
		}
		catch(Exception e)
		{
			out.println(e);
		}
	}

}
