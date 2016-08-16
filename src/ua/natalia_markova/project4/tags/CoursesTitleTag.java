package ua.natalia_markova.project4.tags;

import ua.natalia_markova.project4.enums.CourseType;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by natalia_markova on 20.07.2016.
 */
public class CoursesTitleTag extends TagSupport {
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int doStartTag() throws JspException {

        HttpSession session = pageContext.getSession();
        Locale locale = (Locale) session.getAttribute("locale");
        if (locale == null) {
            locale = new Locale("en", "US");
        }
        ResourceBundle bundle = ResourceBundle.getBundle("resource.i18n", locale);
        String prefix = type.toLowerCase();
        String title = bundle.getString("courses." +  prefix + ".title");
        JspWriter writer = pageContext.getOut();
        try {
            writer.print("<h3>"  +title + "</h3>");
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }

        return TagSupport.SKIP_BODY;

    }
}
