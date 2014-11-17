package no.westerdals.student.vegeiv13.innlevering3;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.spring.DaoFactory;
import com.j256.ormlite.spring.TableCreator;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import no.westerdals.student.vegeiv13.innlevering3.models.Album;
import no.westerdals.student.vegeiv13.innlevering3.utils.ConnectionSourceManager;
import org.apache.commons.configuration.Configuration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlbumService {

    private final JdbcConnectionSource connectionSource;
    private final Dao<Album, Long> dao;

    public AlbumService(Configuration configuration) throws SQLException {
        connectionSource = ConnectionSourceManager.mapConfigurationToJdbcConnectionSource(configuration);
        dao = DaoFactory.createDao(connectionSource, Album.class);
        if (!assertTables()) {
            throw new SQLException("Unable to assert table for model Album");
        }
    }

    /**
     * Returns all albums with a given genre. Must be exact match, though ORMLite ignores case by default.
     * @param genre The genre to search for
     * @return A list of all albums with the given genre
     * @throws SQLException
     */
    public List<Album> getAlbumsByGenre(String genre) throws SQLException {
        QueryBuilder<Album, Long> albumLongQueryBuilder = dao.queryBuilder();
        SelectArg arg = new SelectArg(genre);
        return dao.query(albumLongQueryBuilder.where().eq("genre", arg).prepare());
    }

    /**
     * Returns all genres found in the database.
     * @return A list of genres
     * @throws SQLException
     */
    public List<String> getExistingGenres() throws SQLException {
        List<Album> allAlbums = dao.queryForAll();
        List<String> allGenres = new ArrayList<>();
        allAlbums.stream().filter(a -> !allGenres.contains(a.getGenre())).forEach(a -> allGenres.add(a.getGenre()));
        return allGenres;
    }

    /**
     * Saves a given album to the database
     * @param album The album to save
     * @throws SQLException
     */
    public void saveAlbum(Album album) throws SQLException {
        this.dao.createOrUpdate(album);
    }

    /**
     * Attempts to ensure the table(s) handled by this service exist.
     * @return Whether or not the table(s) exist.
     * @throws SQLException
     */
    private boolean assertTables() throws SQLException {
        if (!dao.isTableExists()) {
            System.setProperty(TableCreator.AUTO_CREATE_TABLES, "true");
            List<Dao<?, ?>> daoList = Arrays.asList(dao);
            TableCreator tableCreator = new TableCreator(connectionSource, daoList);
            tableCreator.maybeCreateTables();
        }
        return dao.isTableExists();
    }

    /**
     * Attempts to drop the table(s) handled by this service.
     * @return Inversely whether the tables exist.
     * @throws SQLException
     */
    private boolean dropTable() throws SQLException {
        if (dao.isTableExists()) {
            System.setProperty(TableCreator.AUTO_DROP_TABLES, "true");
            List<Dao<?, ?>> daoList = Arrays.asList(dao);
            TableCreator tableCreator = new TableCreator(connectionSource, daoList);
            tableCreator.maybeDropTables();
        }
        return !dao.isTableExists();
    }
}
