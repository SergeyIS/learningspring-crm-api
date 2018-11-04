package com.learningspring.crm.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learningspring.crm.data.model.Customer;
import com.learningspring.crm.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RedisDataStorage implements DataStorage {

    private final JedisPool jedisPool;
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String CUSTOMER_HMAP_NAME = "customers";
    private static final String USERS_HMAP_NAME = "users";

    @Autowired
    public RedisDataStorage(@Qualifier("jedisPool") JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public Customer getCustomerById(Long id) {
        Customer result = null;
        try (Jedis jedis = jedisPool.getResource()) {
            String json = jedis.hget(CUSTOMER_HMAP_NAME, String.valueOf(id));
            if (!StringUtils.isEmpty(json)) {
                result = mapper.readValue(json, Customer.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> result = new ArrayList<>();
        try (Jedis jedis = jedisPool.getResource()) {
            Map<String, String> map = jedis.hgetAll(CUSTOMER_HMAP_NAME);
            if (map != null) {
                map.forEach((k, v) -> {
                    try {
                        Customer t = mapper.readValue(v, Customer.class);
                        result.add(t);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        return result;
    }

    @Override
    public Long putCustomer(Customer customer) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (customer.getId() == null) {
                customer.setId(jedis.hlen(CUSTOMER_HMAP_NAME));
            }
            String json = mapper.writeValueAsString(customer);
            jedis.hset(CUSTOMER_HMAP_NAME, String.valueOf(customer.getId()), json);
            return customer.getId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    @Override
    public Customer deleteCustomer(Long id) {
        Customer result = null;
        try (Jedis jedis = jedisPool.getResource()) {
            String json = jedis.hget(CUSTOMER_HMAP_NAME, String.valueOf(id));
            if (!StringUtils.isEmpty(json)) {
                result = mapper.readValue(json, Customer.class);
                jedis.hdel(CUSTOMER_HMAP_NAME, String.valueOf(id));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void updateCustomer(Customer customer) {
        deleteCustomer(customer.getId());
        putCustomer(customer);
    }

    @Override
    public List<Customer> getCustomers(Long from, Long count) {
        List<Customer> result = new ArrayList<>();
        try (Jedis jedis = jedisPool.getResource()) {
            Long length = jedis.hlen(CUSTOMER_HMAP_NAME);
            Long end = from + count;
            for (long id = from; id < end && id < length; id++) {
                try {
                    String json = jedis.hget(CUSTOMER_HMAP_NAME, String.valueOf(id));
                    Customer customer = mapper.readValue(json, Customer.class);
                    result.add(customer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public Long putUser(User user) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (user.getId() == null) {
                user.setId(jedis.hlen(USERS_HMAP_NAME));
            }
            String json = mapper.writeValueAsString(user);
            jedis.hset(USERS_HMAP_NAME, String.valueOf(user.getUsername()), json);
            return user.getId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    @Override
    public User getUserByUsername(String username) {
        User result = null;
        try (Jedis jedis = jedisPool.getResource()) {
            String json = jedis.hget(USERS_HMAP_NAME, username);
            if (!StringUtils.isEmpty(json)) {
                result = mapper.readValue(json, User.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
