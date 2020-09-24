package vn.aptech.project4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import vn.aptech.project4.entity.Membership;
import vn.aptech.project4.repository.MembershipRepository;

@Service
public class MempershipServiceImp implements MembershipService {
	
	private MembershipRepository membershipRepository;
	@Autowired
     public MempershipServiceImp(MembershipRepository membershipRepository) {
		this.membershipRepository = membershipRepository;
	}

	@Override
	public List<Membership> getAllMembership() {
		return membershipRepository.findAll();
	}

	@Override
	public void saveMembership(Membership membership) {
		this.membershipRepository.save(membership);
	}

	@Override
	public Membership getMembershipById(int id) {
		Optional<Membership> optional = membershipRepository.findById(id);
		Membership membership = null;
		if(optional.isPresent()) {
			membership = optional.get();
		}else {
			throw new RuntimeException("Membership not found for id :: " +id);
		}
		return membership;
	}

	@Override
	public void deleteMembershipById(int id) {
		this.membershipRepository.deleteById(id);
		
	}

}
