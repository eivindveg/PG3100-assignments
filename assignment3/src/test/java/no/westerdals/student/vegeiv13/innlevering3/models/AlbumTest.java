package no.westerdals.student.vegeiv13.innlevering3.models;

import com.j256.ormlite.field.DatabaseField;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.time.Year;

import static org.junit.Assert.*;

public class AlbumTest {

    private Album album;

    @Before
    public void setup() {
        album = new Album();
    }

    @Test
    public void testNoArgAccessible()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Album> noArgConstructor = Album.class.getDeclaredConstructor();
        assertNotNull(noArgConstructor);
        int modifiers = noArgConstructor.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));

        // This invocation should be successful, and should be considered a test in itself in case of exceptions
        noArgConstructor.newInstance();
    }

    @Test
    public void testHasIdentifier() {
        Field[] declaredFields = Album.class.getDeclaredFields();
        boolean hasId = false;
        for (Field f : declaredFields) {
            Annotation[] annotations = f.getAnnotations();
            for (Annotation a : annotations) {
                if (a instanceof DatabaseField) {
                    DatabaseField dbFieldAnnotation = (DatabaseField) a;
                    if (dbFieldAnnotation.id() | dbFieldAnnotation.generatedId()) {
                        hasId = true;
                        break;
                    }
                }
            }
            if (hasId) {
                break;
            }
        }

        assertTrue(hasId);
    }

    @Test
    public void testId() {
        Long expected = 1L;
        assertNull(album.getId());
        album.setId(expected);
        Long actual = album.getId();
        assertEquals("Album ID has been set", actual, expected);
    }

    @Test
    public void testTitle() {
        String expected = "TestAlbum";
        assertNull(album.getTitle());
        album.setTitle(expected);
        String actual = album.getTitle();
        assertEquals("Album Title has been set", actual, expected);
    }

    @Test
    public void testArtist() {
        String expected = "Great Artist";
        assertNull(album.getArtist());
        album.setArtist(expected);
        String actual = album.getArtist();
        assertEquals("Album Artist has been set", actual, expected);
    }

    @Test
    public void testGenre() {
        String expected = "Awesome";
        assertNull(album.getGenre());
        album.setGenre(expected);
        String actual = album.getGenre();
        assertEquals("Album genre has been set", actual, expected);
    }

    @Test
    public void testNumberOfTracks() {
        int expected = Integer.MAX_VALUE;
        assertEquals(album.getTracks(), 0);
        album.setTracks(expected);
        int actual = album.getTracks();
        assertEquals("Album tracks have been set", actual, expected);
    }

    @Test
    public void testReleasedYear() {
        Year expected = Year.of(2000);
        assertNull(album.getReleasedYear());
        album.setReleasedYear(expected);
        Year actual = album.getReleasedYear();
        assertEquals("Album release year has been set", actual, expected);
        assertEquals("Given years match exactly", 2000, expected.getValue());
    }

    @Test
    public void testToString() {
        String expected = "null (null) | 0 tracks | released in null";
        assertEquals("toString does not fail", album.toString(), expected);
    }
}
