package listener;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class EmfLoader implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("booktopia");
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("emf", emf);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (sce.getServletContext().getAttribute("emf") != null) {
            EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("emf");
            emf.close();
        }
    }
}
