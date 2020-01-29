package epam.ua.javacore.service;

import epam.ua.javacore.model.Account;
import epam.ua.javacore.model.Developer;
import epam.ua.javacore.model.Skill;
import epam.ua.javacore.repository.AccountRepository;
import epam.ua.javacore.repository.SkillRepository;
import epam.ua.javacore.repository.io.AccountRepositoryImpl;
import epam.ua.javacore.repository.jdbc.JdbcAccountRepository;
import epam.ua.javacore.repository.jdbc.JdbcDeveloperRepository;
import epam.ua.javacore.repository.jdbc.JdbcGeneric;
import epam.ua.javacore.repository.jdbc.JdbcSkillRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static epam.ua.javacore.util.Validate.entityValidation;
import static epam.ua.javacore.util.Validate.idValidation;

public class DeveloperService {
    JdbcDeveloperRepository repository=new JdbcDeveloperRepository();
    JdbcSkillRepository skillRepository=new JdbcSkillRepository();
    JdbcAccountRepository accountRepository=new JdbcAccountRepository();

    public Stream<String> getAll(){
        return repository.getAll().stream().map(x->x.toString());
    }

    public String get(Long id){
        return repository.get(id).toString();
    }

    public String add(String name, Set<Long> skillSetId,Long accountId){
        Set<Skill> skillSet=new HashSet<>();
        for (Long k:skillSetId) {
            skillSet.add(skillRepository.get(k));
        }
        Account account=accountRepository.get(accountId);
        Developer developer=new Developer();
        developer.setName(name);
        developer.setAccount(account);
        developer.setSkillSet(skillSet);
        return repository.add(developer).toString();
    }

    public void delete(Long id){

        repository.delete(id);

    }
}
