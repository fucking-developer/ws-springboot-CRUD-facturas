package com.jonhdevelop.springbootapifacturas.dao;

import com.jonhdevelop.springbootapifacturas.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
}
