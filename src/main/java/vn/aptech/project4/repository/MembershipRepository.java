package vn.aptech.project4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.aptech.project4.entity.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {

}
