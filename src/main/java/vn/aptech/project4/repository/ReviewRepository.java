package vn.aptech.project4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.aptech.project4.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
