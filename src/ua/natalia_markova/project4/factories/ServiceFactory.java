package ua.natalia_markova.project4.factories;

import com.sun.istack.internal.NotNull;
import ua.natalia_markova.project4.enums.ServiceType;
import ua.natalia_markova.project4.service.*;

/**
 * Created by natalia_markova on 17.07.2016.
 */
public abstract class ServiceFactory {
    public abstract AdminService getAdminService();
    public abstract UserService getUserService();
    public abstract CourseService getCourseService();
    public abstract ProfessorService getProfessorService();
    public abstract StudentService getStudentService();

    public static ServiceFactory getFactory(@NotNull DaoFactory daoFactory, ServiceType type) {
        if (type == ServiceType.SIMPLE) {
            return SimpleServiceFactory.getInstance(daoFactory);
        }
        return null;
    }
}
