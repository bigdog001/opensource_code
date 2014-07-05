package com.ok.tags;

/**
 * Created by WangJiuNian.
 * User: Administrator
 * Date: 2011-3-25
 * Time: 14:39:00
 */
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;
public class ListTest extends TagSupport implements DynamicAttributes
{
	private String name;
	private HashMap map = new HashMap();
	public void setName(String name)
	{
		this.name = name;
	}
	public void setDynamicAttribute(String uri, String localName, Object value)
									throws JspException
	{
		map.put(localName,value);
	}
	public int doStartTag() throws JspException
	{
		JspWriter out = pageContext.getOut();
		Set list = map.entrySet();
		try
		{
			out.print("<select name='" + name + "'>");
			Iterator it = list.iterator();
			while(it.hasNext())
			{
				Map.Entry entry = (Map.Entry)it.next();
				out.print("  <option value='");
				out.print(entry.getKey());
				out.print("'>");
				out.print(entry.getValue());
				out.println("</option>");
			}  out.println("</select>");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return super.doStartTag();
	}
}