package vn.aptech.project4.service;

import java.util.List;


import vn.aptech.project4.entity.Membership;

public interface MembershipService {
	List<Membership> getAllMembership();
	void saveMembership(Membership membership);
	Membership getMembershipById(int id);
	void deleteMembershipById(int id);

}
