package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.models.Employee;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.ciandt.feedfront.utils.PersistenceUtil.getEntityManagerFactory;

public class EmployeeDAO implements DAO<Employee> {
    private EntityManager entityManager;

    public EmployeeDAO() {
        entityManager = getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<Employee> listar() {
        return entityManager.createQuery("SELECT emp FROM Employee emp").getResultList();
    }

    @Override
    public Optional<Employee> buscar(long id) {
        Employee employee = entityManager.find(Employee.class, id);
        return Optional.ofNullable(employee);
    }

    @Override
    public Employee salvar(Employee employee) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        employee = entityManager.merge(employee);
        transaction.commit();
        entityManager.clear();
        return employee;
    }

    @Override
    public boolean apagar(long id) {
        Optional<Employee> employee = buscar(id);
        if (employee.isPresent()) {
            var transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(employee.get());
            transaction.commit();
            return true;
        }
        return false;

    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}