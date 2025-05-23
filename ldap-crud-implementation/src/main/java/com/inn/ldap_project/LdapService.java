package com.example.ldapdemo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.SearchResultCallback;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class LdapService {

    @Autowired
    private LdapTemplate ldapTemplate;

    // Search method to find users by UID
    public List<String> searchUserByUid(String uid) {
        LdapQuery query = LdapQueryBuilder.query()
                .base("ou=Users,dc=innspark,dc=in")
                .where("uid").is(uid);

        // Perform the search and map the results to a list of Strings (Distinguished Names)
        return ldapTemplate.search(query, new SearchResultCallback() {
            @Override
            public Object doInContext(DirContextOperations context) {
                // Return the DN (Distinguished Name) as a string
                return context.getDn().toString();
            }
        }).stream().collect(Collectors.toList());
    }

    // To retrieve all users
    public List<String> getAllUsers() {
        LdapQuery query = LdapQueryBuilder.query()
                .base("dc=innspark,dc=in")
                .where("objectClass").is("inetOrgPerson");

        // Perform the search and map the results to a list of Strings (Distinguished Names)
        return ldapTemplate.search(query, new SearchResultCallback() {
            @Override
            public Object doInContext(DirContextOperations context) {
                // Return the DN (Distinguished Name) as a string
                return context.getDn().toString();
            }
        }).stream().collect(Collectors.toList());
    }
}
