package ua.natalia_markova.project4.controllers;

import com.sun.istack.internal.NotNull;
import ua.natalia_markova.project4.exceptions.WrongRequestURIException;
import ua.natalia_markova.project4.factories.ServiceFactory;
import ua.natalia_markova.project4.wrappers.RequestWrapper;
import ua.natalia_markova.project4.wrappers.RequestWrapperImpl;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by natalia_markova on 23.07.2016.
 */
public class ControllerManager {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("resource.requestURI");
    private static ControllerManager instance;

    private Map<String, Controller> controllerMap;
    private Map<String, Method> methodMap;

    private ControllerManager(@NotNull ServiceFactory serviceFactory) {
        List<Controller> handlers = new ArrayList<>();
        handlers.add(new AdminController(serviceFactory.getAdminService()));
        handlers.add(new UserController(serviceFactory.getUserService()));
        handlers.add(new CoursesController(serviceFactory.getCourseService()));
        handlers.add(new ProfessorController(serviceFactory.getProfessorService()));
        handlers.add(new StudentController(serviceFactory.getStudentService()));

        controllerMap = new HashMap<>();
        methodMap = new HashMap<>();

        for (Controller handler: handlers) {
            Class cls = handler.getClass();
            Method[] methods = cls.getMethods();
            for (Method method: methods) {
                RequestHandler annotation = (RequestHandler) method.getAnnotation(RequestHandler.class);
                if (annotation == null) {
                    continue;
                }
                if (method.getParameterCount() != 1) {
                    continue;
                }
                Class[] parameterTypes = method.getParameterTypes();
                if (parameterTypes[0] != RequestWrapper.class) {
                    continue;
                }
                String mapping = annotation.value();
                controllerMap.put(mapping, handler);
                methodMap.put(mapping, method);
            }
        }
    }

    public static ControllerManager getControllerManager(@NotNull ServiceFactory serviceFactory) {
        synchronized (ControllerManager.class) {
            if (instance == null) {
                instance = new ControllerManager(serviceFactory);
            }
        }
        return instance;
    }

    /**
     *  Gets request URI from the request, finds appropriate controller that will handle the request
     *  and finds a method to be invoked and than invokes this method to get a result
     *  @throws WrongRequestURIException in case no appropriate controller found
     *  @throws InvocationTargetException in case fails to invoke a method
     *  @throws IllegalAccessException in case fails to invoke a method
     *  @param request HttpServletRequest object
     *  @return the name of jsp-page or request URI
     */
    public String manageRequest(RequestWrapper request, String requestURI) throws WrongRequestURIException, InvocationTargetException, IllegalAccessException {
        Controller controller = controllerMap.get(requestURI);
        if (controller == null) {
            throw new WrongRequestURIException();
        }
        Method method = methodMap.get(requestURI);
        Object[] parameters = {request};
        return (String) method.invoke(controller, parameters);
    }
}
