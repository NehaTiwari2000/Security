package com.security.rolebased.auth.repo;

import com.security.rolebased.auth.entity.RoleRouting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRoutingRepo extends JpaRepository<RoleRouting,Integer> {
}
