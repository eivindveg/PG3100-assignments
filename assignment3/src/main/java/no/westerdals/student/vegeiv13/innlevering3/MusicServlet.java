package no.westerdals.student.vegeiv13.innlevering3;

import no.westerdals.student.vegeiv13.innlevering3.models.Album;
import no.westerdals.student.vegeiv13.innlevering3.utils.ConfigurationHandler;
import org.apache.commons.configuration.Configuration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/", name = "MusicServlet")
public class MusicServlet extends HttpServlet {

    private AlbumService albumService;

    public MusicServlet() throws SQLException {
        this(ConfigurationHandler.getInstance().getConfiguration());
    }

    public MusicServlet(Configuration configuration) throws SQLException {
        super();
        albumService = new AlbumService(configuration);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Build standard response
            List<String> genres = albumService.getExistingGenres();
            request.setAttribute("genres", genres);
            request.getRequestDispatcher("genres.jsp").forward(request, response);

        } catch (SQLException | ServletException | IOException e) {
            // In the event we cannot build response. Throw back default responses to server if we're unable
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get and assign request
        String genre = request.getParameter("genre");
        // Make sure the request is valid
        if (genre.equals("")) {
            throw new UnsupportedOperationException("No post value");
        }
        // Build response
        List<Album> albumsByGenre;
        try {
            albumsByGenre = albumService.getAlbumsByGenre(genre);
            request.setAttribute("albums", albumsByGenre);
            request.setAttribute("genre", genre);
            request.getRequestDispatcher("results.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
