package no.westerdals.student.vegeiv13.innlevering3;

import no.westerdals.student.vegeiv13.innlevering3.utils.ConfigurationHandler;
import org.apache.commons.configuration.Configuration;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class MusicServletTest {

    private MusicServlet servlet;

    @Before
    public void setup() throws SQLException {
        Configuration configuration = ConfigurationHandler.getInstance().getConfiguration();
        configuration.setProperty("database.schema", "test");
        configuration.setProperty("database.url", "mem:");
        configuration.setProperty("database.type", "h2");
        configuration.setProperty("user", "notRoot");
        configuration.setProperty("password", null);

        servlet = new MusicServlet();
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getRequestDispatcher("genres.jsp")).thenReturn(mock(RequestDispatcher.class));

        servlet.doGet(request, response);
        verify(request, atLeastOnce()).getRequestDispatcher("genres.jsp");
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("genre")).thenReturn("TestGenre");
        when(request.getRequestDispatcher("results.jsp")).thenReturn(mock(RequestDispatcher.class));
        when(request.getRequestDispatcher("error.jsp")).thenReturn(mock(RequestDispatcher.class));

        servlet.doPost(request, response);

        verify(request, atLeastOnce()).getRequestDispatcher("results.jsp");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDoPostEmpty() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("genre")).thenReturn("");
        servlet.doPost(request, response);
    }
}
