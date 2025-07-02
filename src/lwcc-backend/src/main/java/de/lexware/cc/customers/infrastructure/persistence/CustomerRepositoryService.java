package de.lexware.cc.customers.infrastructure.persistence;

import de.lexware.cc.customers.domain.exception.CustomerNotFoundException;
import de.lexware.cc.customers.domain.model.Customer;
import de.lexware.cc.customers.domain.repository.CustomerRepository;
import de.lexware.cc.shared.domain.ListPage;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
public class CustomerRepositoryService implements CustomerRepository {
    private final JpaCustomerRepository jpaCustomerRepository;
    private final EntityManager em;
    private final CustomerMapper customerMapper;

    public CustomerRepositoryService(
        JpaCustomerRepository jpaCustomerRepository,
        EntityManager em,
        CustomerMapper customerMapper
    ) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.em = em;
        this.customerMapper = customerMapper;
    }

    @Override
    public Customer get(UUID id) throws CustomerNotFoundException {
        return this.customerMapper.toDomain(this.loadOrThrow(id));
    }

    @Override
    public Customer save(Customer customer) throws CustomerNotFoundException {
        CustomerEntity customerEntity;
        if (customer.isPersisted()) {
            customerEntity = this.loadOrThrow(customer.id());
            customerEntity = customerMapper.updateEntity(customer, customerEntity);
        } else {
            customerEntity = this.customerMapper.toEntity(customer);
        }

        return customerMapper.toDomain(jpaCustomerRepository.save(customerEntity));
    }

    @Override
    public ListPage<Customer> list(
        long offset,
        long pageSize,
        String sort,
        Sort.Direction direction
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
        Root<CustomerEntity> root = query.from(CustomerEntity.class);

        query.select(
            cb.construct(
                Customer.class,
                root.get("id"),
                root.get("createdDate"),
                root.get("lastModifiedDate"),
                root.get("firstname"),
                root.get("lastname"),
                root.get("infoText"),
                root.get("vatId"),
                root.get("street"),
                root.get("houseNumber"),
                root.get("zip"),
                root.get("city"),
                root.get("country")
            )
        );

        query.orderBy(QueryUtils.toOrders(sort(sort, direction), root, cb));
        TypedQuery<Customer> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult((int) offset);
        typedQuery.setMaxResults((int) pageSize);

        TypedQuery<Long> countQuery = getCountQuery(CustomerEntity.class);
        return new ListPage<>(typedQuery.getResultList(), countQuery.getSingleResult());
    }

    private <T> TypedQuery<Long> getCountQuery(Class<T> type) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        query.select(cb.count(query.from(CustomerEntity.class)));
        return em.createQuery(query);
    }

    private Sort sort(String sort, Sort.Direction direction) {
        if (direction == null) {
            return Sort.unsorted();
        }

        return Sort.by(direction, sort);
    }

    private CustomerEntity loadOrThrow(UUID id) {
        return jpaCustomerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }
}
