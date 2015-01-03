package com.bigdog.server.web.crimedoc.tld;

import com.bigdog.server.web.crimedoc.bean.Article;
import com.bigdog.server.web.crimedoc.tld.base.BaseTage;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/2/13
 * Time: 6:50 AM
 */
public class TitleTag extends BaseTage {


    @Override
    public void doTag() throws JspException, IOException {
        Article article = getTitleTagService().getArticle(getId());
        if (article != null) {
            JspWriter out = getJspContext().getOut();
            printData(out, article);
        } else {
            JspWriter out = getJspContext().getOut();
            String output = "<span>data empty...</span>";
            out.write(output);
        }
    }

    private void printData(JspWriter out, Article article_data) throws IOException {
        int showit = getShowit();
        switch (showit) {
            case 0:  //默认情况,显示标题
                WriteBySize(out, article_data.getTitle() + "", getShowSize());
                break;
            case 1:
                WriteBySize(out, article_data.getId() + "", getShowSize());
                break;
            case 2:
                WriteBySize(out, article_data.getTitle() + "", getShowSize());
                break;
            case 3:
                WriteBySize(out, article_data.getAuthor() + "", getShowSize());
                break;
            case 4:
                WriteBySize(out, article_data.getContent() + "", getShowSize());
                break;
            case 5:
                WriteBySize(out, article_data.getImgname() + "", getShowSize());
                break;
            case 6:
                WriteBySize(out, article_data.getCnt() + "", getShowSize());
                break;
            case 7:
                WriteBySize(out, article_data.getCreateTime() + "", getShowSize());
                break;
            case 8:
                WriteBySize(out, article_data.getCatagory() + "", getShowSize());
                break;
            case 9:
                WriteBySize(out, article_data.getCatagory_order() + "", getShowSize());
                break;
            default:
                WriteBySize(out, "data error!", 0);
                break;
        }
    }

    private void WriteBySize(JspWriter out, String content, int size) {
        String result = "";
        if ("".equals(content) || content == null) {
            result = "";
        }
        if (content.length() > size && size != 0) {
            result = content.substring(0, size - 1);
        }
        if (content.length() <= size || size == 0) {
            result = content;
        }
        try {
            out.write(result);
        } catch (IOException e) {
            logger.info("tag out put error:" + e.getMessage());
        }

    }
}
