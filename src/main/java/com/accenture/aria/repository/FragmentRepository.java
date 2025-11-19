package com.accenture.aria.repository;

import com.accenture.aria.model.Fragment;
import com.accenture.aria.model.FragmentType;
import com.accenture.aria.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FragmentRepository extends JpaRepository<Fragment, Long> {
    List<Fragment> findByType(FragmentType type);
    List<Fragment> findByRole(Role role);
    List<Fragment> findByTypeAndRole(FragmentType type, Role role);
    List<Fragment> findByTypeAndRoleIn(FragmentType type, List<Role> roles);
}
