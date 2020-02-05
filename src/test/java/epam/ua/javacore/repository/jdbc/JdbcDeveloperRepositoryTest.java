package epam.ua.javacore.repository.jdbc;


import epam.ua.javacore.model.Account;
import epam.ua.javacore.model.AccountStatus;
import epam.ua.javacore.model.Developer;
import epam.ua.javacore.model.Skill;
import epam.ua.javacore.util.jdbc.JDBCConnectionPool;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static epam.ua.javacore.repository.jdbc.UtilTest.*;
import static org.assertj.core.api.Assertions.assertThat;

public class JdbcDeveloperRepositoryTest {

    static Connection connection;
    JdbcDeveloperRepository repository=new JdbcDeveloperRepository();;

    Developer entity1=new Developer("Kate",new HashSet<Skill>(
            Arrays.asList(new Skill("Java"), new Skill("Python"))),
            new Account("romik@gmail.com", AccountStatus.valueOf("ACTIVE")));

    Developer entity2=new Developer("Jack",new HashSet<Skill>(
            Arrays.asList(new Skill("Python"))),
            new Account("jack@gmail.com", AccountStatus.valueOf("NONACTIVE")));


    @BeforeClass
    public static void initTestDB(){
        ChangePropertyToTest();
        try {
            connection = JDBCConnectionPool.getConnection();
        }catch (SQLException e){
            ChangePropertyToWork();
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void restoreDB(){
        ChangePropertyToWork();
    }

    @Before
    public void setUp() {
        populateDB(connection);
    }

    @Test
    public void testGetAll() {
        Collection entities=repository.getAll();
        assertThat(3).isEqualTo(entities.size());
        assertThat(repository.getAll()).usingElementComparatorIgnoringFields("id").contains(entity1);
        assertThat(repository.getAll()).doesNotHaveDuplicates();

    }

    @Test
    public void testGetId() {
        repository.get(3L);
        assertThat(repository.get(3L)).isEqualToIgnoringGivenFields(entity1,"id");
    }

    @Test
    public void testGetIdNotExist() {
        assertThat(repository.get(100L)).isNull();
    }

    @Test
    public void testAdd(){
        assertThat(repository.getAll()).usingElementComparatorIgnoringFields("id").doesNotContain(entity2);
        repository.add(entity2);
        assertThat(repository.getAll()).usingElementComparatorIgnoringFields("id").contains(entity2);
    }

    @Test
    public void testDelete(){
        Collection<Developer> allOld=repository.getAll();
        Developer developer=(Developer) allOld.toArray()[0];
        assertThat(repository.delete(developer.getId())).isTrue();
        assertThat(repository.getAll().size()).isEqualTo(allOld.size()-1);
        assertThat(repository.getAll()).doesNotContain(developer);
    }

    @Test
    public void testDeleteNotExist(){
        Collection<Developer> allOld=repository.getAll();
        Long maxid=allOld.stream().map(Developer::getId).max(Long::compareTo).get();
        assertThat(repository.delete(maxid+1)).isFalse();
        assertThat(repository.getAll().size()).isEqualTo(allOld.size());
    }

}