package ua.natalia_markova.project4.factories;

import com.sun.istack.internal.NotNull;
import ua.natalia_markova.project4.service.*;

/**
 * Created by natalia_markova on 17.07.2016.
 */
public class SimpleServiceFactory extends ServiceFactory {

    private static SimpleServiceFactory instance;
    private DaoFactory daoFactory;

    private SimpleServiceFactory(@NotNull DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public static SimpleServiceFactory getInstance(@NotNull DaoFactory daoFactory) {
        synchronized (SimpleServiceFactory.class) {
            if (instance == null) {
                instance = new SimpleServiceFactory(daoFactory);
            }
        }
        return instance;
    }

    @Override
    public AdminService getAdminService() {
        return new AdminServiceImpl(daoFactory.getUserDao(), daoFactory.getCourseDao(), daoFactory.getDepartmentDao());
    }

    @Override
    public UserService getUserService() {
        return new UserServiceImpl(daoFactory.getUserDao());
    }

    @Override
    public CourseService getCourseService() {
        return new CourseServiceImpl(daoFactory.getCourseDao(), daoFactory.getArchiveItemDao());
    }

    @Override
    public ProfessorService getProfessorService() {
        return new ProfessorServiceImpl(daoFactory.getArchiveItemDao(), daoFactory.getUserDao(), daoFactory.getCourseDao());
    }

    @Override
    public StudentService getStudentService() {
        return new StudentServiceImpl(daoFactory.getCourseDao(), daoFactory.getArchiveItemDao());
    }
}
