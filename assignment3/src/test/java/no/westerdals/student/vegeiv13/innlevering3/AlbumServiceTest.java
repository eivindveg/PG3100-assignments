package no.westerdals.student.vegeiv13.innlevering3;

import no.westerdals.student.vegeiv13.innlevering3.models.Album;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.time.Year;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AlbumServiceTest {

    AlbumService service;

    @Before
    public void setup() throws SQLException {
        Configuration configuration = new XMLConfiguration();
        configuration.setProperty("database.schema", "test");
        configuration.setProperty("database.url", "mem:");
        configuration.setProperty("database.type", "h2");
        configuration.setProperty("user", "notRoot");
        configuration.setProperty("password", null);
        service = new AlbumService(configuration);
    }

    @After
    public void tearDown() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = AlbumService.class.getDeclaredMethod("dropTable");
        m.setAccessible(true);
        m.invoke(service);
    }

    @Test
    public void testTableExists() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = AlbumService.class.getDeclaredMethod("assertTables");
        m.setAccessible(true);
        assertTrue("Table already exists or was set up", (boolean) (m.invoke(service)));
    }

    @Test(expected = SQLException.class)
    public void testServiceEnforcesNull() throws SQLException {
        Album a = new Album();
        service.saveAlbum(a);
    }

    @Test
    public void testGetAlbumsByGenre() throws SQLException {
        int expected = 3;
        int actual;
        for(int i = 0; i < expected; i++) {
            Album a = new Album();
            a.setGenre("TestGenre");
            a.setTitle("Test" + i);
            a.setArtist("Test" + i);
            a.setReleasedYear(Year.of(i));
            a.setTracks(i);
            service.saveAlbum(a);
        }
        Album a = new Album();
        a.setGenre("TestGenre2");
        a.setTitle("Test" + 4);
        a.setArtist("Test" + 4);
        a.setReleasedYear(Year.of(4));
        a.setTracks(4);
        service.saveAlbum(a);
        actual = service.getAlbumsByGenre("TestGenre").size();

        assertEquals("Service fetches all albums from one genre", actual, expected);
    }

    @Test
    public void testGetGenres() throws SQLException {

        String genre1 = "TestGenre";
        String genre2 = "TestGenre2";

        Album a = new Album();
        a.setGenre(genre1);
        a.setTitle("Test" + 4);
        a.setArtist("Test" + 4);
        a.setReleasedYear(Year.of(4));
        a.setTracks(4);
        service.saveAlbum(a);
        a = new Album();
        a.setGenre(genre2);
        a.setTitle("Test" + 4);
        a.setArtist("Test" + 4);
        a.setReleasedYear(Year.of(4));
        a.setTracks(4);
        service.saveAlbum(a);

        List<String> genres = service.getExistingGenres();
        assertEquals("Genre list size matches", genres.size(), 2);
        assertTrue("Both genres found in list", genres.contains(genre1) && genres.contains(genre2));
    }

}
