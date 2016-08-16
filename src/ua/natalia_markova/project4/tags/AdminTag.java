package ua.natalia_markova.project4.tags;

import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.enums.UserType;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by natalia_markova on 23.07.2016.
 */
public class AdminTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && user.getUserType() == UserType.ADMIN) {
            return TagSupport.EVAL_BODY_INCLUDE;
        } else {
            return TagSupport.SKIP_BODY;
        }
    }
}

