package ua.natalia_markova.project4.tags;

import ua.natalia_markova.project4.domain.User;

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
public class H2Tag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        Locale locale = (Locale) session.getAttribute("locale");
        if (locale == null) {
            locale = new Locale("en", "US");
        }
        User user = (User) session.getAttribute("user");
        ResourceBundle bundle = ResourceBundle.getBundle("resource.i18n", locale);
        String prefix = user.getUserType().toString().toLowerCase();
        String header = bundle.getString(prefix + ".h2");
        JspWriter writer = pageContext.getOut();
        try {
            writer.print("<h2>" + header + ": " + user.getFullName() + "</h2>");
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }

        return TagSupport.SKIP_BODY;
    }
}
