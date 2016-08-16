package ua.natalia_markova.project4.tags;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by natalia_markova on 24.07.2016.
 */
public class CSRFTokenTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        String CSRFToken = (String) session.getAttribute("CSRFToken");

        if (CSRFToken != null) {
            JspWriter writer = pageContext.getOut();
            try {
                writer.print("<input type=\"hidden\" name=\"CSRFToken\" value=\"" + CSRFToken + "\"/>");
            } catch (IOException e) {
                throw new JspException(e.getMessage());
            }
        }

        return TagSupport.SKIP_BODY;
    }
}
