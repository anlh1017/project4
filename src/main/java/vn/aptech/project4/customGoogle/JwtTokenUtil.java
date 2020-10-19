package vn.aptech.project4.customGoogle;

import java.io.Serializable;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import vn.aptech.project4.entity.Customer;

public class JwtTokenUtil implements Serializable {
	 public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
	    public static final String SIGNING_KEY = "devglan123r";

	    public static String generateToken(Customer user) {
	        Claims claims = Jwts.claims().setSubject(user.getCustomerEmail());

	        return Jwts.builder()
	                .setClaims(claims)
	                .setIssuer("https://devglan.com")
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
	                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
	                .compact();
	    }
}
