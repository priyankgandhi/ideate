package repositories;

import org.springframework.stereotype.Repository;

import pojo.AuthUser;

@Repository
public interface AuthUserRepository extends BaseRepository<AuthUser, Long> {
    
    public AuthUser findByEmail(String email);

    public AuthUser findByUserName(String userName);
    
    public AuthUser findByUserNameOrEmail(String userName, String email);
}
