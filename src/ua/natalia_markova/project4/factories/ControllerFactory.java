package ua.natalia_markova.project4.factories;

import com.sun.istack.internal.NotNull;
import ua.natalia_markova.project4.controllers.*;
import ua.natalia_markova.project4.controllers.RequestHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by natalia_markova on 23.07.2016.
 */
public class ControllerFactory {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("resource.requestURI");
    private static ControllerFactory instance;

    private Map<String, RequestHandler> handlerMap;

    private ControllerFactory(@NotNull ServiceFactory serviceFactory) {

        AdminController adminController = new AdminController(serviceFactory.getAdminService());
        UserController userController = new UserController(serviceFactory.getUserService());
        CoursesController coursesController = new CoursesController(serviceFactory.getCourseService());
        ProfessorController professorController = new ProfessorController(serviceFactory.getProfessorService());
        StudentController studentController = new StudentController(serviceFactory.getStudentService());

        handlerMap = new HashMap<>();
        handlerMap.put(bundle.getString("index"), userController);
        handlerMap.put(bundle.getString("authenticate"), userController);
        handlerMap.put(bundle.getString("logout"), userController);
        handlerMap.put(bundle.getString("registration"), userController);
        handlerMap.put(bundle.getString("register"), userController);
        handlerMap.put(bundle.getString("users"), adminController);
        handlerMap.put(bundle.getString("departments"), adminController);
        handlerMap.put(bundle.getString("new_department"), adminController);
        handlerMap.put(bundle.getString("edit_department"), adminController);
        handlerMap.put(bundle.getString("update_department"), adminController);
        handlerMap.put(bundle.getString("new_course"), adminController);
        handlerMap.put(bundle.getString("add_course"), adminController);
        handlerMap.put(bundle.getString("courses"), coursesController);
        handlerMap.put(bundle.getString("course_information"), coursesController);
        handlerMap.put(bundle.getString("archive"), studentController);
        handlerMap.put(bundle.getString("available_courses"), studentController);
        handlerMap.put(bundle.getString("enroll_on_course"), studentController);
        handlerMap.put(bundle.getString("put_mark"), professorController);
        handlerMap.put(bundle.getString("do_put_mark"), professorController);
        handlerMap.put(bundle.getString("edit_user"), userController);
        handlerMap.put(bundle.getString("do_edit_user"), userController);
        handlerMap.put(bundle.getString("change_password"), userController);
        handlerMap.put(bundle.getString("do_change_password"), userController);
    }

    public static ControllerFactory getControllerFactory(@NotNull ServiceFactory serviceFactory) {
        synchronized (ControllerFactory.class) {
            if (instance == null) {
                instance = new ControllerFactory(serviceFactory);
            }
        }
        return instance;
    }

    public RequestHandler getRequestHandler(String requestURI) {
        return handlerMap.get(requestURI);
    }
}
