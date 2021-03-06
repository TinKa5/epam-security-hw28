package epam.ua.javacore.repository.jdbc;


import epam.ua.javacore.model.Skill;
import epam.ua.javacore.util.jdbc.JDBCConnectionPool;
import org.junit.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import static epam.ua.javacore.repository.jdbc.UtilTest.ChangePropertyToTest;
import static epam.ua.javacore.repository.jdbc.UtilTest.ChangePropertyToWork;
import static epam.ua.javacore.repository.jdbc.UtilTest.populateDB;

public class JdbcSkillRepositoryTest {

    static Connection connection;
    JdbcSkillRepository repository=new JdbcSkillRepository();;

    Skill entity1=new Skill("Php");
    Skill entity2=new Skill("C++");


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
        assertThat(4).isEqualTo(entities.size());
        assertThat(repository.getAll()).usingElementComparatorIgnoringFields("id").contains(entity1);
        assertThat(repository.getAll()).doesNotHaveDuplicates();

    }

    @Test
    public void testGetId() {

        assertThat(repository.get(2L)).isEqualToIgnoringGivenFields(entity1,"id");
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
        Collection<Skill> allOld=repository.getAll();
        Skill skill=(Skill) allOld.toArray()[0];
        assertThat(repository.delete(skill.getId())).isTrue();
        assertThat(repository.getAll().size()).isEqualTo(allOld.size()-1);
        assertThat(repository.getAll()).doesNotContain(skill);
    }

    @Test
    public void testDeleteNotExist(){
        Collection<Skill> allOld=repository.getAll();
        Long maxid=allOld.stream().map(Skill::getId).max(Long::compareTo).get();
        assertThat(repository.delete(maxid+1)).isFalse();
        assertThat(repository.getAll().size()).isEqualTo(allOld.size());
    }

}